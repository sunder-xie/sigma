package com.tqmall.sigma.biz.bizenum;

/**
 * Created by gaorongyu on 17/4/10.
 */
public enum ValidateTypeEnum {
    MESSAGE_CODE(1, "短信验证码"),
    PAY_PASSWORD(2, "支付密码");

    ValidateTypeEnum(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    private Integer id;
    private String desc;
}
