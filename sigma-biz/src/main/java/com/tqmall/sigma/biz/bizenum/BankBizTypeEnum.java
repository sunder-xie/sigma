package com.tqmall.sigma.biz.bizenum;

/**
 * Created by gaorongyu on 17/5/17.
 */
public enum BankBizTypeEnum {
    BIND("bind_card","四要素绑卡"),
    ;

    BankBizTypeEnum(String bizType, String desc) {
        this.bizType = bizType;
        this.desc = desc;
    }

    private String bizType;
    private String desc;

    public String getDesc() {
        return desc;
    }

    public String getBizType() {
        return bizType;
    }
}
