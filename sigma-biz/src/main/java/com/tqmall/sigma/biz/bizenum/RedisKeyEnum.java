package com.tqmall.sigma.biz.bizenum;

/**
 * Created by gaorongyu on 17/5/9.
 */
public enum RedisKeyEnum {
    VERIFICATION_CODE("VERIFICATION_CODE", "获取验证码"),
    VERIFICATION_CODE_NUM("VERIFICATION_CODE", "获取数字验证码"),
    ;

    private String key;
    private String desc;

    RedisKeyEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
