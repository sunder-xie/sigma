package com.tqmall.sigma.biz.service.member.Impl;

import com.tqmall.sigma.biz.beans.tonglian.result.CardBinInfoResult;
import com.tqmall.sigma.biz.bizenum.BankBizTypeEnum;
import com.tqmall.sigma.biz.service.cache.BankSupportCache;
import com.tqmall.sigma.biz.service.member.BankCardService;
import com.tqmall.sigma.biz.service.member.MemberInfoService;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberBankCardService;
import com.tqmall.sigma.dao.MemberBankCardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by gaorongyu on 17/5/16.
 */
@Slf4j
@Service
public class BankCardServiceImpl implements BankCardService{
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private TlMemberBankCardService tlMemberBankCardService;
    @Autowired
    private BankSupportCache bankSupportCache;
    @Autowired
    private MemberBankCardMapper memberBankCardMapper;

    @Override
    public CardBinInfoResult getBankCardBin(String cardNo) {
        Assert.hasLength(cardNo, "cardNo不能为空");
        return tlMemberBankCardService.getBankCardBin(cardNo).getCardBinInfo();
    }

    @Override
    public CardBinInfoResult getBankCardBinForBind(String cardNo) {
        CardBinInfoResult cardBinInfoResult = getBankCardBin(cardNo);
        //判断该银行卡是否支持四要素绑卡
        Boolean isSupport = bankSupportCache.isBankSupport(cardBinInfoResult.getBankCode(),cardBinInfoResult.getCardType(), BankBizTypeEnum.BIND.getBizType());
        cardBinInfoResult.setSupport(isSupport);
        return cardBinInfoResult;
    }
}
