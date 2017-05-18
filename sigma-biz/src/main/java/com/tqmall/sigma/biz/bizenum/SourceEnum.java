package com.tqmall.sigma.biz.bizenum;

/**
 * Created by linjian on 17/4/5.
 */
public enum SourceEnum {
    MOBILE(1, "Mobile"),
    PC(2, "PC");

    private Integer id;
    private String name;

    SourceEnum(Integer id, String name) {
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
