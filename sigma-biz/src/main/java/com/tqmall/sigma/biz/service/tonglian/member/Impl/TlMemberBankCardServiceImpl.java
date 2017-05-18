package com.tqmall.sigma.biz.service.tonglian.member.Impl;

import com.google.common.collect.Maps;
import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;
import com.tqmall.sigma.biz.beans.tonglian.param.ApplyBindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.param.BindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.param.ThreeElementsBindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.result.*;
import com.tqmall.sigma.biz.bizenum.CardTypeEnum;
import com.tqmall.sigma.biz.service.tonglian.InterfaceInvokeBiz;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberBankCardService;
import com.tqmall.sigma.component.utils.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by linjian on 17/4/6.
 */
@Slf4j
@Service
public class TlMemberBankCardServiceImpl implements TlMemberBankCardService {

    private static final String MEMBER_SERVICE = "MemberService";

    @Autowired
    private InterfaceInvokeBiz interfaceInvokeBiz;

    @Override
    public GetBankCardBinResult getBankCardBin(String cardNo) {
        Assert.hasLength(cardNo, "银行卡号不能为空");
        Map<String, Object> param = Maps.newHashMap();
        param.put("cardNo", interfaceInvokeBiz.encrypt(cardNo));
        return interfaceInvokeBiz.invoke(GetBankCardBinResult.class, MEMBER_SERVICE, "getBankCardBin", param);
    }

    @Override
    public BindBankCardResult applyBindBankCard(ApplyBindBankCardParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getCardNo(), "银行卡号不能为空");
        Assert.hasLength(param.getPhone(), "银行预留手机不能为空");
        Assert.hasLength(param.getName(), "姓名不能为空");
        Assert.notNull(param.getCardType(), "卡类型不能为空");
        Assert.notNull(param.getIdentityType(), "证件类型不能为空");
        Assert.hasLength(param.getIdentityNo(), "证件号码不能为空");
        if (param.getCardType().equals(CardTypeEnum.XYK.getId())) {
            Assert.hasLength(param.getValidate(), "有效期不能为空");
            Assert.hasLength(param.getCvv2(), "CVV2不能为空");
            Assert.isNull(param.getIsSafeCard(), "信用卡时不能填写");
            param.setValidate(interfaceInvokeBiz.encrypt(param.getValidate()));
            param.setCvv2(interfaceInvokeBiz.encrypt(param.getCvv2()));
        }
        param.setCardNo(interfaceInvokeBiz.encrypt(param.getCardNo()));
        param.setIdentityNo(interfaceInvokeBiz.encrypt(param.getIdentityNo()));
        return interfaceInvokeBiz.invoke(BindBankCardResult.class, MEMBER_SERVICE, "applyBindBankCard", MapUtils.objectToMap(param));
    }

    @Override
    public BindBankCardResult bindBankCard(BindBankCardParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getTranceNum(), "流水号不能为空");
        Assert.hasLength(param.getTransDate(), "申请时间不能为空");
        Assert.hasLength(param.getPhone(), "银行预留手机不能为空");
        Assert.hasLength(param.getVerificationCode(), "短信验证码不能为空");
        return interfaceInvokeBiz.invoke(BindBankCardResult.class, MEMBER_SERVICE, "bindBankCard", MapUtils.objectToMap(param));
    }

    @Override
    public VerifyPayBindBankCardResult applyVerifyPayBindBankCard(ApplyBindBankCardParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getCardNo(), "银行卡号不能为空");
        Assert.hasLength(param.getPhone(), "银行预留手机不能为空");
        Assert.hasLength(param.getName(), "姓名不能为空");
        Assert.notNull(param.getIdentityType(), "证件类型不能为空");
        Assert.hasLength(param.getIdentityNo(), "证件号码不能为空");
        GetBankCardBinResult result = getBankCardBin(param.getCardNo());
        if (result.getCardBinInfo().getCardType().equals(CardTypeEnum.XYK.getId()) && null != param.getIsSafeCard()) {
            log.error("信用卡时不能填写");
            throw new BusinessCheckFailException(SigmaError.PARAM_ERROR);
        }
        param.setCardNo(interfaceInvokeBiz.encrypt(param.getCardNo()));
        param.setIdentityNo(interfaceInvokeBiz.encrypt(param.getIdentityNo()));
        return interfaceInvokeBiz.invoke(VerifyPayBindBankCardResult.class, MEMBER_SERVICE, "applyVerifyPayBindBankCard", MapUtils.objectToMap(param));
    }

    @Override
    public VerifyPayBindBankCardResult affirmVerifyPayBindCard(BindBankCardParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getTranceNum(), "流水号不能为空");
        Assert.hasLength(param.getVerificationCode(), "短信验证码不能为空");
        return interfaceInvokeBiz.invoke(VerifyPayBindBankCardResult.class, MEMBER_SERVICE, "affirmVerifyPayBindCard", MapUtils.objectToMap(param));
    }

    @Override
    public BindBankCardResult threeElementsBindBankCard(ThreeElementsBindBankCardParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getName(), "姓名不能为空");
        Assert.notNull(param.getIdentityType(), "证件类型不能为空");
        Assert.hasLength(param.getIdentityNo(), "证件号码不能为空");
        Assert.hasLength(param.getCardNo(), "银行卡号不能为空");
        param.setCardNo(interfaceInvokeBiz.encrypt(param.getCardNo()));
        param.setIdentityNo(interfaceInvokeBiz.encrypt(param.getIdentityNo()));
        return interfaceInvokeBiz.invoke(BindBankCardResult.class, MEMBER_SERVICE, "threeElementsBindBankCard", MapUtils.objectToMap(param));
    }

    @Override
    public SetSafeCardResult setSafeCard(String bizUserId, String cardNo) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Assert.hasLength(cardNo, "银行卡号不能为空");
        Map<String, Object> param = Maps.newHashMap();
        param.put("bizUserId", bizUserId);
        param.put("cardNo", interfaceInvokeBiz.encrypt(cardNo));
        return interfaceInvokeBiz.invoke(SetSafeCardResult.class, MEMBER_SERVICE, "setSafeCard", param);
    }

    @Override
    public QueryBankCardResult queryBankCard(String bizUserId, String cardNo) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Map<String, Object> param = Maps.newHashMap();
        param.put("bizUserId", bizUserId);
        if (null != cardNo) param.put("cardNo", interfaceInvokeBiz.encrypt(cardNo));
        return interfaceInvokeBiz.invoke(QueryBankCardResult.class, MEMBER_SERVICE, "queryBankCard", param);
    }

    @Override
    public UnbindBankCardResult unbindBankCard(String bizUserId, String cardNo) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Assert.hasLength(cardNo, "银行卡号不能为空");
        Map<String, Object> param = Maps.newHashMap();
        param.put("bizUserId", bizUserId);
        param.put("cardNo", interfaceInvokeBiz.encrypt(cardNo));
        return interfaceInvokeBiz.invoke(UnbindBankCardResult.class, MEMBER_SERVICE, "unbindBankCard", param);
    }
}
