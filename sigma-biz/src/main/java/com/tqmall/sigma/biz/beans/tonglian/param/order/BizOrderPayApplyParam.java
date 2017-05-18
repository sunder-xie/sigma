package com.tqmall.sigma.biz.beans.tonglian.param.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by gaorongyu on 17/4/5.
 */
@Getter
@Setter
public class BizOrderPayApplyParam{
    private String orderSn;
    private Integer shopId;
    private Integer receiveId;
    private BigDecimal payAmount;
    private String webReturnUrl;
    private String serverReturnUrl;
    private Integer payId;
    private String bankCardNo;
    private Integer validateType;
    private boolean isApp;
}
