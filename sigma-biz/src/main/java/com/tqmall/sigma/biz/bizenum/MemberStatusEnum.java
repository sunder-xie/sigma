package com.tqmall.sigma.biz.bizenum;

/**
 * Created by linjian on 17/5/12.
 */
public enum MemberStatusEnum {

    PENDING_EFFECT(0, "待生效"),
    EFFICACY(1, "已生效"),
    CLOSE(2, "关闭"),
    FROZEN(3, "冻结");

    private Integer id;
    private String name;

    MemberStatusEnum(Integer id, String name) {
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
