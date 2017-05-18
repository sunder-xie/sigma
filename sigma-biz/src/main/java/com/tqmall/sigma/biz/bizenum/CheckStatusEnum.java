package com.tqmall.sigma.biz.bizenum;

/**
 * Created by linjian on 17/5/12.
 */
public enum CheckStatusEnum {

    CHECK_PENDING(0, "待审核"),
    PASS(1, "审核通过"),
    REFUSE(2, "审核不通过");

    private Integer id;
    private String name;

    CheckStatusEnum(Integer id, String name) {
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
