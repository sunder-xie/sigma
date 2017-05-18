package com.tqmall.sigma.biz.bizenum;

/**
 * Created by linjian on 17/4/5.
 */
public enum CardTypeEnum {
    JJK(1, "借记卡"),
    XYK(2, "信用卡");

    private Integer id;
    private String name;

    CardTypeEnum(Integer id, String name) {
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
