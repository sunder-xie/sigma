package com.tqmall.sigma.biz.service.tonglian.member.Impl;

import com.google.common.collect.Maps;
import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;
import com.tqmall.sigma.biz.beans.tonglian.param.ChangeBindPhoneParam;
import com.tqmall.sigma.biz.beans.tonglian.param.SetCompanyInfoParam;
import com.tqmall.sigma.biz.beans.tonglian.param.SetPersonalInfoParam;
import com.tqmall.sigma.biz.beans.tonglian.param.SetRealNameParam;
import com.tqmall.sigma.biz.beans.tonglian.result.*;
import com.tqmall.sigma.biz.bizenum.MemberTypeEnum;
import com.tqmall.sigma.biz.service.tonglian.InterfaceInvokeBiz;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberService;
import com.tqmall.sigma.component.utils.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Created by linjian on 17/4/1.
 */
@Slf4j
@Service
public class TlMemberServiceImpl implements TlMemberService {

    private static final String MEMBER_SERVICE = "MemberService";

    @Autowired
    private InterfaceInvokeBiz interfaceInvokeBiz;

    @Override
    public CreateMemberResult createMember(String bizUserId, Integer memberType, Integer source) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Assert.notNull(memberType, "会员类型不能为空");
        Assert.notNull(source, "访问终端类型不能为空");
        Map<String, Object> param = Maps.newHashMap();
        param.put("bizUserId", bizUserId);
        param.put("memberType", memberType);
        param.put("source", source);
        return interfaceInvokeBiz.invoke(CreateMemberResult.class, MEMBER_SERVICE, "createMember", param);
    }

    @Override
    public MemberPhoneResult sendVerificationCode(String bizUserId, String phone, Integer verificationCodeType) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Assert.hasLength(phone, "手机号码不能为空");
        Assert.notNull(verificationCodeType, "验证码类型不能为空");
        Map<String, Object> param = Maps.newHashMap();
        param.put("bizUserId", bizUserId);
        param.put("phone", phone);
        param.put("verificationCodeType", verificationCodeType);
        return interfaceInvokeBiz.invoke(MemberPhoneResult.class, MEMBER_SERVICE, "sendVerificationCode", param);
    }

    @Override
    public MemberPhoneResult bindPhone(String bizUserId, String phone, String verificationCode) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Assert.hasLength(phone, "手机号码不能为空");
        Assert.hasLength(verificationCode, "验证码不能为空");
        Map<String, Object> param = Maps.newHashMap();
        param.put("bizUserId", bizUserId);
        param.put("phone", phone);
        param.put("verificationCode", verificationCode);
        return interfaceInvokeBiz.invoke(MemberPhoneResult.class, MEMBER_SERVICE, "bindPhone", param);
    }

    @Override
    public ChangeBindPhoneResult changeBindPhone(ChangeBindPhoneParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getOldPhone(), "原手机号码不能为空");
        Assert.hasLength(param.getOldVerificationCode(), "原手机验证码不能为空");
        Assert.hasLength(param.getNewPhone(), "新手机号码不能为空");
        Assert.hasLength(param.getNewVerificationCode(), "新手机验证码不能为空");
        return interfaceInvokeBiz.invoke(ChangeBindPhoneResult.class, MEMBER_SERVICE, "changeBindPhone", MapUtils.objectToMap(param));
    }

    @Override
    public SetRealNameResult setRealName(SetRealNameParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getName(), "姓名不能为空");
        Assert.notNull(param.getIdentityType(), "证件类型不能为空");
        Assert.hasLength(param.getIdentityNo(), "证件号码不能为空");
        param.setIdentityNo(interfaceInvokeBiz.encrypt(param.getIdentityNo()));
        return interfaceInvokeBiz.invoke(SetRealNameResult.class, MEMBER_SERVICE, "setRealName", MapUtils.objectToMap(param));
    }

    @Override
    public BaseResult setCompanyInfo(SetCompanyInfoParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.notNull(param.getCompanyBasicInfo(), "企业基本信息不能为空");
        Assert.hasLength(param.getCompanyBasicInfo().getCompanyName(), "企业名称不能为空");
        Assert.hasLength(param.getCompanyBasicInfo().getBusinessLicense(), "营业执照号不能为空");
        Assert.hasLength(param.getCompanyBasicInfo().getLegalName(), "法人姓名不能为空");
        Assert.notNull(param.getCompanyBasicInfo().getIdentityType(), "法人证件类型不能为空");
        Assert.hasLength(param.getCompanyBasicInfo().getLegalIds(), "法人证件号码不能为空");
        Assert.hasLength(param.getCompanyBasicInfo().getLegalPhone(), "法人手机号码不能为空");
        Assert.hasLength(param.getCompanyBasicInfo().getAccountNo(), "企业对公账户不能为空");
        Assert.hasLength(param.getCompanyBasicInfo().getParentBankName(), "开户银行名称不能为空");
        Assert.hasLength(param.getCompanyBasicInfo().getBankCityNo(), "开户银行地区代码不能为空");
        param.getCompanyBasicInfo().setLegalIds(interfaceInvokeBiz.encrypt(param.getCompanyBasicInfo().getLegalIds()));
        param.getCompanyBasicInfo().setAccountNo(interfaceInvokeBiz.encrypt(param.getCompanyBasicInfo().getAccountNo()));
        return interfaceInvokeBiz.invoke(BaseResult.class, MEMBER_SERVICE, "setCompanyInfo", MapUtils.objectToMap(param));
    }

    @Override
    public BaseResult setPersonalInfo(SetPersonalInfoParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.notNull(param.getUserInfo(), "基本信息不能为空");
        Assert.hasLength(param.getUserInfo().getName(), "姓名不能为空");
        return interfaceInvokeBiz.invoke(BaseResult.class, MEMBER_SERVICE, "setPersonalMemberInfo", MapUtils.objectToMap(param));
    }

    @Override
    public MemberInfoResult getMemberInfo(String bizUserId) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Map<String, Object> param = Maps.newHashMap();
        param.put("bizUserId", bizUserId);
        return interfaceInvokeBiz.invoke(MemberInfoResult.class, MEMBER_SERVICE, "getMemberInfo", param);
    }

    @Override
    public CompanyBasicInfoResult getCompanyBasicInfo(String bizUserId) {
        MemberInfoResult result = this.getMemberInfo(bizUserId);
        if (!result.getMemberType().equals(MemberTypeEnum.COMPANY.getId())) {
            throw new BusinessCheckFailException(SigmaError.MEMBER_TYPE_ERROR);
        }
        return MapUtils.mapToObject(result.getMemberInfo(), CompanyBasicInfoResult.class);
    }

    @Override
    public PersonalBasicInfoResult getPersonalBasicInfo(String bizUserId) {
        MemberInfoResult result = this.getMemberInfo(bizUserId);
        if (!result.getMemberType().equals(MemberTypeEnum.PERSONAL.getId())) {
            throw new BusinessCheckFailException(SigmaError.MEMBER_TYPE_ERROR);
        }
        return MapUtils.mapToObject(result.getMemberInfo(), PersonalBasicInfoResult.class);
    }

    @Override
    public BaseResult lockMember(String bizUserId) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Map<String, Object> param = Maps.newHashMap();
        param.put("bizUserId", bizUserId);
        return interfaceInvokeBiz.invoke(BaseResult.class, MEMBER_SERVICE, "lockMember", param);
    }

    @Override
    public BaseResult unlockMember(String bizUserId) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Map<String, Object> param = Maps.newHashMap();
        param.put("bizUserId", bizUserId);
        return interfaceInvokeBiz.invoke(BaseResult.class, MEMBER_SERVICE, "unlockMember", param);
    }
}
