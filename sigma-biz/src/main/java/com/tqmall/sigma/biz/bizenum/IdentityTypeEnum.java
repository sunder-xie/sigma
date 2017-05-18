package com.tqmall.sigma.biz.bizenum;

/**
 * Created by linjian on 17/4/5.
 */
public enum IdentityTypeEnum {
    ID_CARD(1, "身份证"),
    PASSPORT(2, "护照"),
    OFFICIAL_CARD(3, "军官证");

    private Integer id;
    private String name;

    IdentityTypeEnum(Integer id, String name) {
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
