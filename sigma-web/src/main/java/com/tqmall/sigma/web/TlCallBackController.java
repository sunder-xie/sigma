package com.tqmall.sigma.web;

import com.google.common.collect.Maps;
import com.tqmall.sigma.biz.beans.param.CloudCheckCompanyParam;
import com.tqmall.sigma.biz.beans.tonglian.result.ResponseResult;
import com.tqmall.sigma.biz.bizenum.CloudCheckStatusEnum;
import com.tqmall.sigma.biz.service.member.MemberInfoService;
import com.tqmall.sigma.biz.service.tonglian.InterfaceInvokeBiz;
import com.tqmall.sigma.component.utils.DateUtils;
import com.tqmall.sigma.component.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by linjian on 17/4/14.
 */
@Slf4j
@Controller
@RequestMapping("allinpay/back/url")
public class TlCallBackController {

    @Autowired
    private InterfaceInvokeBiz interfaceInvokeBiz;

    @Autowired
    private MemberInfoService memberInfoService;

    @RequestMapping("setCompanyInfo")
    @ResponseBody
    public void setCompanyInfoBackUrl(HttpServletRequest request) {
        Boolean flag = checkSign(request);
        if (!flag) {
            return;
        }
        Map<String, String> reqParam = getAllRequestParam(request);
        ResponseResult rps = JsonUtils.fromJson(reqParam.get("rps"), ResponseResult.class);
        log.info("rps:{}", JsonUtils.toJson(rps));
        CloudCheckCompanyParam param = new CloudCheckCompanyParam();
        param.setMemberId(Integer.parseInt(rps.getReturnValue().get("bizUserId")));
        param.setCloudCheckTime(DateUtils.stringToDate(rps.getReturnValue().get("checkTime"), "yyyy-MM-dd HH:mm:ss"));
        if (!rps.getReturnValue().get("result").equals("2")) {
            log.error("企业信息审核失败");
            param.setCloudCheckStatus(CloudCheckStatusEnum.FAIL.getId());
            param.setCloudFailReason(rps.getReturnValue().get("failReason"));
        } else {
            log.info("企业信息审核成功");
            param.setCloudCheckStatus(CloudCheckStatusEnum.SUCCESS.getId());
        }
        log.info("参数：{}", JsonUtils.toJson(param));
        memberInfoService.checkCompanyInfoCallBack(param);
    }

    @RequestMapping("setPayPwd")
    @ResponseBody
    public void setPayPwdBackUrl(HttpServletRequest request) {
        Boolean flag = checkSign(request);
        if (!flag) {
            return;
        }
        Map<String, String> reqParam = getAllRequestParam(request);
        ResponseResult rps = JsonUtils.fromJson(reqParam.get("rps"), ResponseResult.class);
        log.info("rps:{}", JsonUtils.toJson(rps));
        if (!rps.getStatus().equals("OK")) {
            log.error("设置支付密码失败");
            return;
        }
        log.info("设置支付密码成功");
    }

    @RequestMapping("updatePhoneByPayPwd")
    @ResponseBody
    public void updatePhoneByPayPwdBackUrl(HttpServletRequest request) {
        Boolean flag = checkSign(request);
        if (!flag) {
            return;
        }
        Map<String, String> reqParam = getAllRequestParam(request);
        ResponseResult rps = JsonUtils.fromJson(reqParam.get("rps"), ResponseResult.class);
        log.info("rps:{}", JsonUtils.toJson(rps));
        if (!rps.getStatus().equals("OK")) {
            log.error("修改手机(密码验证版)失败");
            return;
        }
        log.info("修改手机(密码验证版)成功");
        String newPhone = rps.getReturnValue().get("newPhone");
        String bizUserId = rps.getReturnValue().get("bizUserId");
        log.info("newPhone:{},bizUserId:{}", newPhone, bizUserId);
    }

    @RequestMapping("pay")
    public void confirmPayNotify(HttpServletRequest request) {
        Boolean flag = checkSign(request);
        if (!flag) {
            return;
        }
        Map<String, String> reqParam = getAllRequestParam(request);
        ResponseResult rps = JsonUtils.fromJson(reqParam.get("rps"), ResponseResult.class);
        log.info("rps:{}", JsonUtils.toJson(rps));
        if (!rps.getStatus().equals("OK")) {
            log.error("订单支付失败");
            return;
        }
        log.info("订单支付成功");
    }

    private Boolean checkSign(HttpServletRequest request) {
        Boolean ret = Boolean.FALSE;
        try {
            //开始接收通联后台通知
            request.setCharacterEncoding("UTF-8");
            log.info("================================通用后台通知开始===========================");
            // 获取通联通知服务器发送的后台通知参数
            Map<String, String> reqParam = getAllRequestParam(request);
            log.info("通用后台通知参数：" + reqParam);
            String sign5 = interfaceInvokeBiz.getSign(reqParam.get("rps"), reqParam.get("timestamp"));
            String sign = reqParam.get("sign");
            log.info("sign5:" + sign5);
            log.info("sign:" + sign);
            ret = sign5.equals(sign);
            log.info("签名验证结果：" + ret);
            log.info("================================通用后台通知结束===========================");
        } catch (Exception e) {
            log.error("通用后台通知错误：" + e.getMessage(), e);
        }
        return ret;
    }

    private static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = Maps.newHashMap();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
            }
        }
        return res;
    }
}
