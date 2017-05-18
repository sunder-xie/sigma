package com.tqmall.sigma.biz.beans.tonglian.result.order;

import lombok.Data;

/**
 *
 * 订单详情，返回结果
 *
 * Created by huangzhangting on 17/4/7.
 */
@Data
public class OrderDetailResult {
    private String bizOrderNo; //sigma生成的订单号
    private String orderNo; //通联那边的订单号
    private Integer orderStatus; //订单状态
}
