package com.tqmall.sigma.biz.bizenum;

/**
 * Created by linjian on 17/4/5.
 */
public enum MemberTypeEnum {
    COMPANY(2, "企业会员"),
    PERSONAL(3, "个人会员");

    private Integer id;
    private String name;

    MemberTypeEnum(Integer id, String name) {
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
