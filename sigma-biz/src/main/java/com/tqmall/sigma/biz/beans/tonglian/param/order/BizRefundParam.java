package com.tqmall.sigma.biz.beans.tonglian.param.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by gaorongyu on 17/4/10.
 */
@Getter
@Setter
public class BizRefundParam {
    private String orderSn;
    private String oriOrderSn;
    private BigDecimal amount;
    private Integer shopId;
}

