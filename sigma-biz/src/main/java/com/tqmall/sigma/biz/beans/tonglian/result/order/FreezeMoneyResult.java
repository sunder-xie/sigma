package com.tqmall.sigma.biz.beans.tonglian.result.order;

import lombok.Data;

/**
 * Created by huangzhangting on 17/4/11.
 */
@Data
public class FreezeMoneyResult {
    private String bizFreezenNo; //商户冻结金额订单号
    private Long amount; //冻结金额,单位:分
}
