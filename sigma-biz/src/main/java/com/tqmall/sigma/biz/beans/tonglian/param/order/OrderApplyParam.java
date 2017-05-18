package com.tqmall.sigma.biz.beans.tonglian.param.order;

import lombok.Data;

import java.util.Map;

/**
 *
 * 订单申请，参数对象
 *
 * Created by huangzhangting on 17/4/6.
 */
@Data
public class OrderApplyParam {
    private String bizOrderNo; //商户订单号
    private Long amount; //订单金额 包含手续费,单位:分
    private Long fee = 0l; //手续费(平台手续费收入),单位:分 内扣,如果不存在,则填 0
    private String backUrl; //后台通知地址
    private Map<String, Object> payMethod; //支付方式 内部对象，也使用map组装
    private String industryCode = "1910"; //行业代码
    private String industryName = "其他"; //行业名称
    private Integer source; //访问终端类型

    /* 非必填字段 */
    private Integer validateType; //交易验证方式,如不填,默认为短信验证码 1:短信验证码 2:支付密码
    //订单最长时效为 24 小时,默 认为最长时效。订单过期将不 能进行支付确认,订单过期后云账户自动更新订单状态为 “关闭”。
    private String ordErexpireDatetime; //订单过期时间(yyyy-MM-dd HH:mm:ss)
    private String summary; //交易内容摘要 最多 20 个字符
    private String extendInfo; //扩展参数, 回调时原样返回

}
