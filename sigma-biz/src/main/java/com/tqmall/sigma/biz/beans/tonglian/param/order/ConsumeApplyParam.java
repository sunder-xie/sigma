package com.tqmall.sigma.biz.beans.tonglian.param.order;

import lombok.Data;

/**
 *
 * 消费申请，参数对象
 *
 * Created by huangzhangting on 17/4/6.
 */
@Data
public class ConsumeApplyParam extends OrderApplyParam {
    private String payerId; //消费用户的 bizUserId
    private String recieverId; //消费商户的 bizUserId，如果是平台自身,参数值为: #yunBizUserId_B2C#

    /* 非必填字段 */
    private String frontUrl; //前台通知地址,前台交易时必填
    //private splitRule ; //分账规则json字符串
    //订单最长时效为 24 小时,默 认为最长时效。订单过期将不 能进行支付确认,订单过期后云账户自动更新订单状态为 “关闭”。
    private String goodsName; //商品名称
    private String goodsDesc; //商品描述

}
