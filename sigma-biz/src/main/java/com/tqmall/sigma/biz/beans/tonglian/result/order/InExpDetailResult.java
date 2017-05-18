package com.tqmall.sigma.biz.beans.tonglian.result.order;

import lombok.Data;

/**
 *
 * 账户收支明细
 *
 * Created by huangzhangting on 17/4/6.
 */
@Data
public class InExpDetailResult {
    private String tradeNo; //收支明细流水号
    private String accountSetName; //账户集名称
    private String changeTime; //变更时间，格式：yyyy-MM-dd HH:mm:ss
    private Long curAmount; //现有金额，单位：分
    private Long oriAmount; //原始金额，单位：分
    private Long chgAmount; //变更金额，单位：分
    private Long curFreezenAmount; //现有冻结金额，单位：分
    private String bizOrderNo; //商户订单号
    private String tradeType; //交易类型，充值、提现、转账、退款
    private String remark; //备注
}
