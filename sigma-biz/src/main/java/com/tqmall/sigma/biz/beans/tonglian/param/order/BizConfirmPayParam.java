package com.tqmall.sigma.biz.beans.tonglian.param.order;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/4/7.
 */
@Getter
@Setter
public class BizConfirmPayParam {
    private String orderSn;
    private Integer shopId;
    private String consumerIp;
    private Integer payId;
    //交易方式为短信验证的非网关支付必传
    private String verificationCode;
    //快捷支付必传
    private String tradeNo;
    //确认支付后跳转的页面地址
    private String jumpUrl;
}
