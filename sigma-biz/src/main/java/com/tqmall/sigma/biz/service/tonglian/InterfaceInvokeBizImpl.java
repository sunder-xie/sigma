package com.tqmall.sigma.biz.service.tonglian;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.core.common.exception.BusinessProcessFailException;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;
import com.tqmall.sigma.component.utils.DateUtils;
import com.tqmall.sigma.component.utils.JsonUtils;
import com.tqmall.sigma.component.utils.RSAUtil;
import com.tqmall.sigma.component.utils.http.HttpClientResult;
import com.tqmall.sigma.component.utils.http.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzhangting on 17/3/30.
 */
@Slf4j
@Service
public class InterfaceInvokeBizImpl implements InterfaceInvokeBiz {
    @Value("${tl.domain}")
    private String domain; //域名

    @Value("${tl.interface.version}")
    private String version; //接口版本

    @Value("${tl.sys.id}")
    private String sysId; //应用系统编号

    @Value("${tl.account.set.no}")
    private String accountSetNo; //账户集编号

    @Value("${tl.certificate.password}")
    private String certificatePassword; //证书密码

    @Value("${tl.certificate}")
    private String certificate; //证书文件


    private String getSoaUrl() {
        return domain + "service/soa";
    }

    //fixme 获取证书，暂时先放置在系统用户路径下
    private String getCertificate() {
        String path = System.getProperty("user.home");
        return path + "/" + certificate;
//        return "./document/" + certificate;
    }

    //获取时间戳
    private String getTimestamp() {
        return DateUtils.dateToString(new Date(), DateUtils.yyyy_MM_dd_HH_mm_ss);
    }

    //获取签名
    @Override
    public String getSign(String req, String timestamp) {
        String path = getCertificate();
        log.info("证书路径：" + path);
        String sign = "";
        try {
            PrivateKey privateKey = RSAUtil.loadPrivateKey(sysId, path, certificatePassword);
            log.info("密钥：" + privateKey);

            String signStr = sysId + req + timestamp;
            sign = RSAUtil.sign(privateKey, signStr);
            log.info("签名：" + sign);
        } catch (Exception e) {
            log.error("生成签名失败");
            e.printStackTrace();
        }
        return sign;
    }

    //组装请求参数
    private String getReqParamStr(String req) throws Exception {
        String timestamp = getTimestamp();
        Map<String, String> map = new HashMap<>();
        map.put("timestamp", timestamp);
        map.put("sysid", sysId);
        map.put("v", version);
        map.put("req", req);
        map.put("sign", getSign(req, timestamp));

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        sb.deleteCharAt(0);
        return sb.toString();
    }

    //处理返回结果
    private <T> T handleResult(Class<T> resultType, HttpClientResult result) {
        if (result == null) {
            throw new BusinessCheckFailException(SigmaError.INVOKE_ERROR);
        }
        String data = result.getData();
        log.info("通联返回结果："+data);
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        JsonObject dataNode = JsonUtils.toJsonObject(data);
        if (dataNode == null) {
            return null;
        }
        JsonElement statusNode = dataNode.get("status");
        if (!"OK".equals(statusNode.getAsString())) {
            handleError(dataNode);
        }
        log.info("handle tong lian OK result:{}", data);
        String signedValue = dataNode.get("signedValue").getAsString();
        //return JsonUtils.fromJson(signedValue, resultType);
        return JSON.parseObject(signedValue, resultType);
    }

    //处理返回的错误
    private void handleError(JsonObject dataNode) {
        log.error("handle tong lian error result:{}", dataNode.toString());
        JsonElement messageNode = dataNode.get("message");
        JsonElement errorCodeNode = dataNode.get("errorCode");
        if (messageNode == null || errorCodeNode == null) {
            throw new BusinessCheckFailException(SigmaError.SYSTEM_ERROR);
        }
        String message = messageNode.getAsString();
        String errorCode = errorCodeNode.getAsString();
        if (StringUtils.isEmpty(message)) {
            throw new BusinessCheckFailException(SigmaError.SYSTEM_ERROR);
        }
        throw new BusinessProcessFailException(message, errorCode);
    }

    @Override
    public <T> T invoke(Class<T> resultType, Map<String, Object> req) {
        Assert.notNull(resultType, "返回结果class不能为空");
        Assert.notEmpty(req, "参数不能为空");

        req = filterNullParam(req);
        String reqStr = JsonUtils.toJson(req);
        log.info("invoke tong lian interface, req:{}", reqStr);
        try {
            String reqParamStr = getReqParamStr(reqStr);
            log.info("invoke tong lian interface, reqParam:{}", reqParamStr);

            String url = getSoaUrl() + "?" + reqParamStr;
            HttpClientResult result = HttpClientUtil.get(url);
            return handleResult(resultType, result);
        } catch (Exception e) {
            log.error("invoke tong lian interface error, req:" + reqStr, e);
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public <T> T invoke(Class<T> resultType, String service, String method, Map<String, Object> param) {
        Map<String, Object> req = getReq(service, method, param);
        return invoke(resultType, req);
    }

    @Override
    public Map<String, Object> getReq(String service, String method, Map<String, Object> param) {
        Assert.hasLength(service, "服务对象不能为空");
        Assert.hasLength(method, "调用方法不能为空");
        Assert.notEmpty(param, "参数不能为空");

        Map<String, Object> map = new HashMap<>();
        map.put("service", service);
        map.put("method", method);
        map.put("param", param);
        return map;
    }

    @Override
    public String encrypt(String str) {
        String result = "";
        try {
            String path = getCertificate();
            PrivateKey privateKey = RSAUtil.loadPrivateKey(sysId, path, certificatePassword);
            PublicKey publicKey = RSAUtil.loadPublicKey(sysId, path, certificatePassword);
            RSAUtil rsaUtil = new RSAUtil((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
            result = rsaUtil.encrypt(str);
        } catch (Exception e) {
            log.error("加密失败，str：{}", str);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String dencrypt(String str) {
        String result = "";
        try {
            String path = getCertificate();
            PrivateKey privateKey = RSAUtil.loadPrivateKey(sysId, path, certificatePassword);
            PublicKey publicKey = RSAUtil.loadPublicKey(sysId, path, certificatePassword);
            RSAUtil rsaUtil = new RSAUtil((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
            result = rsaUtil.dencrypt(str);
        } catch (Exception e) {
            log.error("解密失败，str：{}", str);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String getRequestParamStr(Map<String, Object> param, boolean isEncodeTwice) {
        param = filterNullParam(param);
        String reqStr = JsonUtils.toJson(param);
        String reqParamStr = "";
        try {
            String timestamp = getTimestamp();
            Map<String, String> map = new HashMap<>();
            map.put("timestamp", timestamp);
            map.put("sysid", sysId);
            map.put("v", version);
            map.put("req", reqStr);
            map.put("sign", getSign(reqStr, timestamp));

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (isEncodeTwice) {
                    sb.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(URLEncoder.encode(entry.getValue(), "UTF-8"), "UTF-8"));
                } else {
                    sb.append("&").append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }
            }
            sb.deleteCharAt(0);
            reqParamStr = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reqParamStr;
    }

    private Map<String, Object> filterNullParam(Map<String, Object> reqParam) {
        Map<String, Object> result = Maps.newHashMap();
        for (Map.Entry<String, Object> entry : reqParam.entrySet()) {
            Object value = entry.getValue();
            if (value == null) continue;
            result.put(entry.getKey(), value);
        }
        return result;
    }
}
