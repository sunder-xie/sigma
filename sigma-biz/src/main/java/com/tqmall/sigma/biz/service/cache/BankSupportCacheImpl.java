package com.tqmall.sigma.biz.service.cache;

import com.google.common.collect.Maps;
import com.tqmall.sigma.biz.bizenum.BankBizTypeEnum;
import com.tqmall.sigma.biz.bizenum.CardTypeEnum;
import com.tqmall.sigma.biz.redis.RedisClient;
import com.tqmall.sigma.biz.redis.RedisKeys;
import com.tqmall.sigma.dao.BankSupportMapper;
import com.tqmall.sigma.entity.BankSupportDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by gaorongyu on 17/5/17.
 */
@Component
public class BankSupportCacheImpl implements BankSupportCache{
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private BankSupportMapper bankSupportMapper;

    @Override
    public Boolean isBankSupport(String bankCode, Integer cardType, String bizType) {
        String redisKey = getRedisKey(cardType,bizType);
        List<BankSupportDO> bankSupportDOList = getBankCardList(redisKey, cardType, bizType);
        Map<String,Object> bankCodeMap = Maps.newHashMap();
        for (BankSupportDO bankSupportDO : bankSupportDOList){
            bankCodeMap.put(bankCode, bankSupportDO);
        }
        return bankCodeMap.containsKey(bankCode);
    }

    private String getRedisKey(Integer cardType, String bizType){
        if (bizType.equals(BankBizTypeEnum.BIND.getBizType()) && cardType.equals(CardTypeEnum.JJK.getId()))
            return RedisKeys.BIND_BANK_DEBIT;
        if (bizType.equals(BankBizTypeEnum.BIND.getBizType()) && cardType.equals(CardTypeEnum.XYK.getId()))
            return RedisKeys.BIND_BANK_CREDIT;
        return "";
    }

    private List<BankSupportDO> getBankCardList(String redisKey, Integer cardType, String bizType){
        List<BankSupportDO> bankSupportDOList = redisClient.lazyGetList(redisKey, BankSupportDO.class);
        if (bankSupportDOList.isEmpty()){
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("bizType", bizType);
            if (cardType.equals(CardTypeEnum.JJK.getId())){
                paramMap.put("isSupportDebit", 1);
            }else if (cardType.equals(CardTypeEnum.XYK.getId())){
                paramMap.put("isSupportCredit", 1);
            }
            bankSupportDOList = bankSupportMapper.selectByBaseConditionPageable(paramMap);
            redisClient.lazySet(redisKey,bankSupportDOList, RedisKeys.EXPIRE_HALF_HOUR);
        }
        return bankSupportDOList;
    }
}
