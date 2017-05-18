package com.tqmall.sigma.biz.beans.tonglian.result.order;

import lombok.Data;

/**
 *
 * 查询余额，返回结果
 *
 * Created by huangzhangting on 17/3/30.
 */
@Data
public class QueryBalanceResult {
    private Long freezenAmount; //冻结额 单位:分
    private Long allAmount; //总额 单位:分
}
