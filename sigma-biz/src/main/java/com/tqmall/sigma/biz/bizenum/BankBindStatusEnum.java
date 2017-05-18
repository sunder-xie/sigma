package com.tqmall.sigma.biz.bizenum;

/**
 * Created by gaorongyu on 17/5/18.
 */
public enum BankBindStatusEnum {
    UNBOUND(0, "未绑定"),
    BOUND(1, "已绑定"),
    UNBIND(2, "解除绑定")
    ;

    BankBindStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private Integer status;
    private String desc;

    public Integer getStatus() {
        return status;
    }
}
