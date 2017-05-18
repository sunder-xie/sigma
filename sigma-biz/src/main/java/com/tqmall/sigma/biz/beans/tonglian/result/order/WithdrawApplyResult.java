package com.tqmall.sigma.biz.beans.tonglian.result.order;

import lombok.Data;

/**
 *
 * 提现申请，返回结果
 *
 * Created by huangzhangting on 17/4/6.
 */
@Data
public class WithdrawApplyResult {
    private String orderNo; //云账户订单号
    private String bizOrderNo; //商户订单号

    /* 非必传字段 */
    private String extendInfo; //扩展参数,申请接口的值原样返回
}
