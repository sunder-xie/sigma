package com.tqmall.sigma.biz.redis;

/**
 *
 * redis缓存key管理
 *
 * Created by huangzhangting on 17/4/1.
 */
public interface RedisKeys {
    //key前缀
    String PREFIX = "sigma_";
    //数据库查不到数据，则向redis设置一个标识，避免短时间内无数据还重复查询数据库
    String NO_DATA = PREFIX + "no_data";
    //四要素绑定银行卡支持的信用卡
    String BIND_BANK_CREDIT = "sigma_bind_bank_credit";
    //四要素绑定银行卡支持的借记卡
    String BIND_BANK_DEBIT = "sigma_bind_bank_debit";

    /* ========== 常用的缓存时间（单位：秒） ========== */
    int EXPIRE_FOREVER_CACHE = 0; //永久缓存，实际缓存时间是大概 5 年，RedisClient中自动处理了

    int EXPIRE_ONE_MINUTE = 60;

    int EXPIRE_TEN_MINUTES = 600;

    int EXPIRE_HALF_HOUR = 1800;

    int EXPIRE_ONE_HOUR = 3600;

    int EXPIRE_ONE_DAY = 3600*24;


    /* ========== 缓存key ========== */
    String TEST_KEY = PREFIX + "test_key_hzt";



}
