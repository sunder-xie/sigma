package com.tqmall.sigma.biz.bizenum;

/**
 * Created by linjian on 17/4/5.
 */
public enum VerificationCodeTypeEnum {
    UNBIND_PHONE(6, "解绑手机"),
    BIND_PHONE(9, "绑定手机");

    private Integer id;
    private String name;

    VerificationCodeTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
