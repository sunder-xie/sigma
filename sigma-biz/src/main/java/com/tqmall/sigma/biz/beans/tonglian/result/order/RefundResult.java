package com.tqmall.sigma.biz.beans.tonglian.result.order;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/4/11.
 */
@Getter
@Setter
public class RefundResult {
    private String orderNo;
    private String bizOrderNo;
    //本次退款总金额 单位：分
    private Integer amount;
    //代金券退款金额 单位：分
    private Integer couponAmount;
    //手续费退款金额 单位：分
    private Integer feeAmount;
}
