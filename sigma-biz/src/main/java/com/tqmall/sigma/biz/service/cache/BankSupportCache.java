package com.tqmall.sigma.biz.service.cache;

/**
 * Created by gaorongyu on 17/5/17.
 */
public interface BankSupportCache {
    public Boolean isBankSupport(String bankCode, Integer cardType, String bizType);
}
