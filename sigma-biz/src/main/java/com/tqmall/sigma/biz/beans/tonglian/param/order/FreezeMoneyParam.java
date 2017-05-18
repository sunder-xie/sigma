package com.tqmall.sigma.biz.beans.tonglian.param.order;

import lombok.Data;

/**
 *
 * 冻结金额，请求参数
 *
 * Created by huangzhangting on 17/4/11.
 */
@Data
public class FreezeMoneyParam{
    private String bizUserId;
    private String bizFreezenNo; //商户冻结金额订单号
    private Long amount; //冻结金额,单位:分
}
