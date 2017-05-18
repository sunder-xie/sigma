package com.tqmall.sigma.biz.service.tonglian;

import java.util.Map;

/**
 * 通联支付，接口访问通用方法
 * <p/>
 * Created by huangzhangting on 17/3/30.
 */
public interface InterfaceInvokeBiz {

    /**
     * 调用通联接口
     *
     * @param resultType 返回结果class
     * @param req        请求对象
     * @param <T>
     * @return
     */
    <T> T invoke(Class<T> resultType, Map<String, Object> req);

    /**
     * 调用通联接口
     *
     * @param resultType 返回结果class
     * @param service    服务对象
     * @param method     调用方法
     * @param param      请求参数
     * @param <T>
     * @return
     */
    <T> T invoke(Class<T> resultType, String service, String method, Map<String, Object> param);

    /**
     * 组装请求对象
     *
     * @param service 服务对象
     * @param method  调用方法
     * @param param   请求参数
     * @return map
     */
    Map<String, Object> getReq(String service, String method, Map<String, Object> param);

    /**
     * 字符串加密
     *
     * @param str 需要加密的字符串
     * @return str
     */
    String encrypt(String str);

    /**
     * 字符串解密
     *
     * @param str 需要解密的字符串
     * @return str
     */
    String dencrypt(String str);

    /**
     * 获取前台页面请求参数字符串
     *
     * @param param 请求参数
     * @return str
     */
    String getRequestParamStr(Map<String, Object> param, boolean isEncodeTwice);

    String getSign(String req, String timestamp);
}
