package com.tqmall.sigma.biz.beans.tonglian.result.order;

import lombok.Data;

/**
 *
 * 消费申请，返回结果
 *
 * Created by huangzhangting on 17/4/6.
 */
@Data
public class ConsumeApplyResult {
    private String orderNo; //云账户订单号
    private String bizOrderNo; //商户订单号

    /* 非必传字段 */
    private String tradeNo; //交易编号,快捷支付必传
    private String receiveUrl; //移动认证支付后台回调地址,作为请求移动认证支付控件接口时的后台通知地址。
    private String certPayOrderNo; //移动认证支付订单号
    private String orderDatetime; //移动认证支付订单生成时间（yyyy-MM-dd HH:mm:ss）
    private String payCode; //POS支付的付款码
    private String extendInfo; //扩展参数,申请接口的值原样返回
    private String weChatAPPInfo; //微信支付信息json字符串
    private String scanPayInfo; //扫码支付信息
}
