package com.tqmall.sigma.biz.bizenum;

/**
 * 登录时会员类型枚举
 * <p/>
 * Created by gaorongyu on 17/5/10.
 */
public enum LoginMemberStatusEnum {
    UN_SIGN_UP(0, "未注册"),
    UN_MESSAGE(1, "未填写信息"),
    CHECK_PENDING(2, "审核中"),
    PASS(3, "审核通过已生效"),
    REFUSE(4, "审核未通过");

    private Integer id;
    private String name;

    LoginMemberStatusEnum(Integer type, String desc) {
        this.id = type;
        this.name = desc;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
