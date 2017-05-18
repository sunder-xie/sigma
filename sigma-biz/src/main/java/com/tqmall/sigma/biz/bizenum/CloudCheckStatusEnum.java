package com.tqmall.sigma.biz.bizenum;

/**
 * Created by linjian on 17/5/16.
 */
public enum CloudCheckStatusEnum {

    CHECK_PENDING(0, "待审核"),
    SUCCESS(2, "审核成功"),
    FAIL(3, "审核失败");

    private Integer id;
    private String name;

    CloudCheckStatusEnum(Integer id, String name) {
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
