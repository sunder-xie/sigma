package com.tqmall.sigma.biz.beans.tonglian.param.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by gaorongyu on 17/4/6.
 */
@Getter
@Setter
public class PayMethodParam {
    private Integer payId;
    private String bankCardNo;
    private BigDecimal payAmount;
}
