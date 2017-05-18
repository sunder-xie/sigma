package com.tqmall.sigma.biz.service.member.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.core.common.exception.BusinessProcessFailException;
import com.tqmall.sigma.biz.beans.constant.SigmaConstant;
import com.tqmall.sigma.biz.beans.encrypt.DES;
import com.tqmall.sigma.biz.beans.param.*;
import com.tqmall.sigma.biz.beans.result.IdentityInfoBO;
import com.tqmall.sigma.biz.beans.result.LoginTypeBO;
import com.tqmall.sigma.biz.beans.result.MemberInfoBO;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;
import com.tqmall.sigma.biz.beans.tonglian.param.ChangeBindPhoneParam;
import com.tqmall.sigma.biz.beans.tonglian.param.CompanyBasicInfoParam;
import com.tqmall.sigma.biz.beans.tonglian.param.SetCompanyInfoParam;
import com.tqmall.sigma.biz.beans.tonglian.param.SetRealNameParam;
import com.tqmall.sigma.biz.beans.tonglian.result.CreateMemberResult;
import com.tqmall.sigma.biz.beans.tonglian.result.QueryBankCardResult;
import com.tqmall.sigma.biz.beans.tonglian.result.order.QueryBalanceResult;
import com.tqmall.sigma.biz.bizenum.*;
import com.tqmall.sigma.biz.service.amount.AmountService;
import com.tqmall.sigma.biz.service.member.CompanyInfoService;
import com.tqmall.sigma.biz.service.member.MemberInfoService;
import com.tqmall.sigma.biz.service.member.PersonalInfoService;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberBankCardService;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberService;
import com.tqmall.sigma.biz.service.tonglian.order.TlOrderBiz;
import com.tqmall.sigma.component.utils.BdUtil;
import com.tqmall.sigma.component.utils.DateUtils;
import com.tqmall.sigma.dao.MemberInfoMapper;
import com.tqmall.sigma.entity.CompanyInfoDO;
import com.tqmall.sigma.entity.MemberInfoDO;
import com.tqmall.sigma.entity.PersonalInfoDO;
import com.tqmall.ucenter.object.result.shop.ShopDTO;
import com.tqmall.ucenter.service.shop.RpcShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by gaorongyu on 17/5/11.
 */
@Slf4j
@Service
public class MemberInfoServiceImpl implements MemberInfoService {

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Autowired
    private PersonalInfoService personalInfoService;

    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private TlMemberService tlMemberService;

    @Autowired
    private RpcShopService rpcShopService;

    @Autowired
    private TlOrderBiz tlOrderBiz;

    @Autowired
    private AmountService amountService;

    @Autowired
    private TlMemberBankCardService tlMemberBankCardService;

    @Override
    public PagingResult<MemberInfoDO> getMemberInfoList(BizMemberInfoQueryParam param) {
        Map<String, Object> searchParams = Maps.newHashMap();
        searchParams.put("isDeleted", "N");
        searchParams.put("keyword", param.getKeyword());
        searchParams.put("memberStatus", param.getMemberStatus());
        searchParams.put("memberType", param.getMemberType());
        searchParams.put("start", param.getStart());
        searchParams.put("limit", param.getLimit());
        searchParams.put("orderStr", "id");
        searchParams.put("orderStyle", "desc");
        List<MemberInfoDO> list = Lists.newArrayList();
        int total = memberInfoMapper.countByBaseCondition(searchParams);
        if (total == 0) {
            return PagingResult.wrapSuccessfulResult(list, 0);
        }
        list = memberInfoMapper.selectByBaseConditionPageable(searchParams);
        return PagingResult.wrapSuccessfulResult(list, total);
    }

    @Override
    public MemberInfoDO getMemberInfoById(Integer memberId) {
        MemberInfoDO memberInfo = memberInfoMapper.selectByPrimaryKey(memberId);
        if (memberInfo == null) {
            throw new BusinessCheckFailException(SigmaError.MEMBER_NULL);
        }
        return memberInfo;
    }

    @Override
    public MemberInfoDO getEffectiveMemberInfoById(Integer memberId) {
        MemberInfoDO memberInfo = memberInfoMapper.selectByPrimaryKey(memberId);
        if (memberInfo == null) {
            throw new BusinessCheckFailException(SigmaError.MEMBER_NULL);
        }
        if (memberInfo.getMemberStatus().equals(MemberStatusEnum.FROZEN.getId())) {
            throw new BusinessProcessFailException(SigmaError.MEMBER_FROZEN);
        }
        return memberInfo;
    }

    @Override
    public MemberInfoDO getMemberInfoByBizUserIdAndType(Integer userId, Integer userType) {
        MemberInfoDO memberInfo = memberInfoMapper.selectByBizUserIdAndType(userId, userType);
        if (memberInfo == null) {
            throw new BusinessCheckFailException(SigmaError.MEMBER_NULL);
        }
        return memberInfo;
    }

    @Override
    public LoginTypeBO getLoginMemberType(String encryptedUserId, Integer userType) {
        LoginTypeBO loginTypeBO = new LoginTypeBO();
        Integer userId = Integer.parseInt(DES.decryptDES(encryptedUserId));
        MemberInfoDO member = memberInfoMapper.selectByBizUserIdAndType(userId, userType);
        //未注册
        if (member == null) {
            loginTypeBO.setLoginMemberStatusEnum(LoginMemberStatusEnum.UN_SIGN_UP);
            return loginTypeBO;
        }
        //会员已生效 可直接登录首页
        if (member.getMemberStatus().equals(MemberStatusEnum.EFFICACY.getId())) {
            loginTypeBO.setLoginMemberStatusEnum(LoginMemberStatusEnum.PASS);
            return loginTypeBO;
        }
        //审核未通过
        if (member.getCheckStatus().equals(CheckStatusEnum.REFUSE.getId())) {
            loginTypeBO.setLoginMemberStatusEnum(LoginMemberStatusEnum.REFUSE);
            loginTypeBO.setFailReason(member.getFailReason());
            return loginTypeBO;
        }
        //个人会员
        if (member.getMemberType().equals(MemberTypeEnum.PERSONAL.getId())) {
            PersonalInfoDO personalInfo = personalInfoService.getPersonalInfo(member.getId());
            if (personalInfo == null) {
                loginTypeBO.setLoginMemberStatusEnum(LoginMemberStatusEnum.UN_MESSAGE);
                loginTypeBO.setMemberType(member.getMemberType());
                return loginTypeBO;
            }
        } else {//企业会员
            CompanyInfoDO companyInfoDO = companyInfoService.getCompanyInfo(member.getId());
            if (companyInfoDO == null) {
                loginTypeBO.setLoginMemberStatusEnum(LoginMemberStatusEnum.UN_MESSAGE);
                loginTypeBO.setMemberType(member.getMemberType());
                return loginTypeBO;
            }
        }
        //其余为审核中
        loginTypeBO.setLoginMemberStatusEnum(LoginMemberStatusEnum.CHECK_PENDING);
        return loginTypeBO;
    }

    @Override
    public Boolean createMember(CreateMemberParam param) {
        Assert.notNull(param.getUserId(), "userId不能为空");
        Assert.notNull(param.getUserType(), "userType不能为空");
        Assert.notNull(param.getEmail(), "email不能为空");
        Assert.notNull(param.getPassword(), "password不能为空");
        Assert.notNull(param.getMemberType(), "memberType不能为空");
        MemberInfoDO checkEmail = memberInfoMapper.selectByEmail(param.getEmail());
        if (checkEmail != null) {
            log.error("邮箱：" + param.getEmail() + " 已存在");
            throw new BusinessCheckFailException(SigmaError.EMAIL_REPEAT);
        }
        //本地创建会员
        Integer userId = DES.decrypyDESToID(param.getUserId());
        MemberInfoDO member = memberInfoMapper.selectByBizUserIdAndType(userId, param.getUserType());
        if (member != null) {
            log.error("会员已存在,userId:{},userType:{}", userId, param.getUserType());
            throw new BusinessProcessFailException(SigmaError.MEMBER_RENEW);
        }
        Result<ShopDTO> shopDTOResult = rpcShopService.getShopByUserIdIncludeDeleted("SIGMA", userId);
        if (!shopDTOResult.isSuccess() || shopDTOResult.getData() == null) {
            log.error("调UC接口获取门店详情失败,门店id:" + userId);
            throw new BusinessProcessFailException(SigmaError.SHOP_INFO_ERROR);
        }
        member = new MemberInfoDO();
        Date date = new Date();
        member.setBizUserId(userId);
        member.setBizUserType(param.getUserType());
        member.setMemberEmail(param.getEmail());
        member.setMemberPassword(DES.encryptDESDB(param.getPassword()));
        member.setMemberType(param.getMemberType());
        member.setMemberName(shopDTOResult.getData().getCompanyName());
        member.setGmtCreate(date);
        member.setGmtModified(date);
        member.setCreator(SigmaConstant.SIGMA_SYSTEM_ID);
        member.setModifier(SigmaConstant.SIGMA_SYSTEM_ID);
        member.setVisitSource(SourceEnum.PC.getId());
        memberInfoMapper.insertSelective(member);

        //调通联创建会员
        //TODO 容灾
        CreateMemberResult tlResult = tlMemberService.createMember(member.getId().toString(), param.getMemberType(), SourceEnum.PC.getId());
        //更新云账户id
        MemberInfoDO memberUpdate = new MemberInfoDO();
        memberUpdate.setId(member.getId());
        memberUpdate.setCloudUserId(tlResult.getUserId());
        memberInfoMapper.updateByPrimaryKeySelective(memberUpdate);
        return Boolean.TRUE;
    }

    @Override
    public Boolean bindPhone(BindPhoneParam param) {
        Assert.notNull(param.getUserId(), "用户ID不能为空");
        Assert.notNull(param.getUserType(), "用户类型不能为空");
        Integer userId = DES.decrypyDESToID(param.getUserId());
        MemberInfoDO member = getMemberInfoByBizUserIdAndType(userId, param.getUserType());
        tlMemberService.bindPhone(member.getId().toString(), param.getPhone(), param.getVerificationCode());
        MemberInfoDO memberUpdate = new MemberInfoDO();
        memberUpdate.setId(member.getId());
        memberUpdate.setMemberPhone(param.getPhone());
        memberUpdate.setIsPhoneChecked(1);
        memberInfoMapper.updateByPrimaryKeySelective(memberUpdate);
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean setMemberInfo(MemberInfoParam param) {
        Assert.notNull(param.getUserId(), "用户ID不能为空");
        Assert.notNull(param.getUserType(), "用户类型不能为空");
        Integer userId = DES.decrypyDESToID(param.getUserId());
        MemberInfoDO member = getMemberInfoByBizUserIdAndType(userId, param.getUserType());
        param.setMemberId(member.getId());
        if (member.getMemberType().equals(MemberTypeEnum.PERSONAL.getId())) {
            //幂等 已创建则不重复
            PersonalInfoDO personalInfoDO = personalInfoService.getPersonalInfo(member.getId());
            if (personalInfoDO != null) {
                return Boolean.TRUE;
            }
            setPersonalMemberInfo(param);
        } else {
            CompanyInfoDO companyInfoDO = companyInfoService.getCompanyInfo(member.getId());
            if (companyInfoDO != null) {
                return Boolean.TRUE;
            }
            setCompanyMemberInfo(param);
        }
        return Boolean.TRUE;
    }

    private void setPersonalMemberInfo(MemberInfoParam param) {
        Assert.hasLength(param.getIdentityNo(), "身份证号码不能为空");
        Assert.hasLength(param.getName(), "姓名不能为空");
        //个人用户绑定手机
        BindPhoneParam phoneParam = BdUtil.do2bo(param, BindPhoneParam.class);
        bindPhone(phoneParam);
        MemberInfoDO memberUpdate = new MemberInfoDO();
        memberUpdate.setId(param.getMemberId());
        memberUpdate.setMemberPhone(param.getPhone());
        memberUpdate.setIsPhoneChecked(1);
        memberInfoMapper.updateByPrimaryKeySelective(memberUpdate);

        //创建会员信息数据
        PersonalInfoDO personalInfoDO = new PersonalInfoDO();
        personalInfoDO.setMemberId(param.getMemberId());
        personalInfoDO.setRealName(param.getName());
        personalInfoDO.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        personalInfoDO.setIdentityNo(DES.encryptDESDB(param.getIdentityNo()));
        personalInfoService.insertPersonalInfo(personalInfoDO);
    }

    private void setCompanyMemberInfo(MemberInfoParam param) {
        Assert.hasLength(param.getIdentityNo(), "身份证号码不能为空");
        Assert.hasLength(param.getName(), "姓名不能为空");
        Assert.hasLength(param.getCompanyName(), "企业名称不能为空");
        Assert.hasLength(param.getBusinessLicense(), "营业执照号不能为空");
        Assert.hasLength(param.getName(), "法人姓名不能为空");
        Assert.hasLength(param.getIdentityNo(), "法人身份证号码不能为空");
        Assert.hasLength(param.getPhone(), "法人手机号码不能为空");
        Assert.hasLength(param.getAccountNo(), "企业对公账户不能为空");
        Assert.hasLength(param.getParentBankName(), "开户银行名称不能为空");
        CompanyInfoDO companyInfoDO = new CompanyInfoDO();
        companyInfoDO.setMemberId(param.getMemberId());
        companyInfoDO.setAccountNo(DES.encryptDESDB(param.getAccountNo()));
        companyInfoDO.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        companyInfoDO.setLegalIds(DES.encryptDESDB(param.getIdentityNo()));
        companyInfoDO.setCompanyName(param.getCompanyName());
        companyInfoDO.setLegalName(param.getName());
        companyInfoDO.setBusinessLicense(param.getBusinessLicense());
        companyInfoDO.setCompanyTelephone(param.getPhone());
        companyInfoDO.setParentBankName(param.getParentBankName());
        companyInfoService.insertCompanyInfo(companyInfoDO);
    }

    @Override
    public MemberInfoBO getMemberInfo(BaseParam param) {
        Integer userId = DES.decrypyDESToID(param.getUserId());
        MemberInfoDO member = getMemberInfoByBizUserIdAndType(userId, param.getUserType());
        MemberInfoBO memberInfoBO = new MemberInfoBO();
        memberInfoBO.setName(member.getMemberName());
        memberInfoBO.setEmail(member.getMemberEmail());
        memberInfoBO.setLastLoginTime(DateUtils.dateToString(member.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss"));
        //金额显示
        QueryBalanceResult balanceResult = tlOrderBiz.queryBalance(member.getId().toString());
        memberInfoBO.setBalance(amountService.conversionAmount(balanceResult.getAllAmount() - balanceResult.getFreezenAmount()));
        memberInfoBO.setFrozenAmount(amountService.conversionAmount(balanceResult.getFreezenAmount()));
        //绑定银行卡显示
        QueryBankCardResult bankCardResult = tlMemberBankCardService.queryBankCard(member.getId().toString(), null);
        memberInfoBO.setBankCardList(bankCardResult.getBindCardList());
        return memberInfoBO;
    }

    @Override
    public Boolean lockMember(Integer memberId, Integer operatorId) {
        Assert.notNull(memberId, "memberId不能为空");
        Assert.notNull(operatorId, "operatorId不能为空");
        tlMemberService.lockMember(memberId.toString());
        return changeMemberStatus(memberId, operatorId, MemberStatusEnum.FROZEN.getId());
    }

    @Override
    public Boolean unlockMember(Integer memberId, Integer operatorId) {
        Assert.notNull(memberId, "memberId不能为空");
        Assert.notNull(operatorId, "operatorId不能为空");
        tlMemberService.unlockMember(memberId.toString());
        return changeMemberStatus(memberId, operatorId, MemberStatusEnum.EFFICACY.getId());
    }

    @Override
    public Boolean closeMember(Integer memberId, Integer operatorId) {
        Assert.notNull(memberId, "memberId不能为空");
        Assert.notNull(operatorId, "operatorId不能为空");
        return changeMemberStatus(memberId, operatorId, MemberStatusEnum.CLOSE.getId());
    }

    @Override
    public Boolean checkMember(BizCheckMemberParam param) {
        Assert.notNull(param, "param不能为空");
        Assert.notNull(param.getMemberId(), "memberId不能为空");
        Assert.notNull(param.getOperatorId(), "operatorId不能为空");
        Assert.notNull(param.getCheckStatus(), "memberStatus不能为空");
        if (param.getCheckStatus().equals(CheckStatusEnum.REFUSE.getId())) {
            Assert.notNull(param.getFailReason(), "failReason不能为空");
        }
        MemberInfoDO memberInfo = getMemberInfoById(param.getMemberId());
        //幂等 不可重复审核通过
        if (memberInfo.getCheckStatus().equals(CheckStatusEnum.PASS.getId())) {
            return Boolean.TRUE;
        }
        if (memberInfo.getMemberType().equals(MemberTypeEnum.COMPANY.getId())) {
            return checkCompanyInfo(param);
        } else {
            return checkPersonalInfo(param);
        }
    }

    @Override
    @Transactional
    public void checkCompanyInfoCallBack(CloudCheckCompanyParam param) {
        //在企业详情表中记录云账户审核信息
        CompanyInfoDO companyInfo = companyInfoService.getCompanyInfoNotNull(param.getMemberId());
        CompanyInfoDO companyUpdate = BdUtil.bo2do(param, CompanyInfoDO.class);
        companyUpdate.setId(companyInfo.getId());
        companyInfoService.updateCompanyInfo(companyUpdate);
        //修改会员表信息
        MemberInfoDO memberUpdate = new MemberInfoDO();
        memberUpdate.setId(param.getMemberId());
        memberUpdate.setGmtModified(new Date());
        memberUpdate.setModifier(SigmaConstant.SIGMA_SYSTEM_ID);
        if (param.getCloudCheckStatus().equals(CloudCheckStatusEnum.SUCCESS.getId())) {
            memberUpdate.setMemberStatus(MemberStatusEnum.EFFICACY.getId());
        } else {
            memberUpdate.setCheckStatus(CheckStatusEnum.REFUSE.getId());
            memberUpdate.setFailReason(param.getCloudFailReason());
        }
        memberInfoMapper.updateByPrimaryKeySelective(memberUpdate);
    }

    @Override
    public Boolean changeBindPhone(ChangeBindPhoneParam param) {
        tlMemberService.changeBindPhone(param);
        MemberInfoDO memberUpdate = new MemberInfoDO();
        memberUpdate.setId(Integer.parseInt(param.getBizUserId()));
        memberUpdate.setMemberPhone(param.getNewPhone());
        memberUpdate.setGmtModified(new Date());
        Integer num = memberInfoMapper.updateByPrimaryKeySelective(memberUpdate);
        return num > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    private Boolean changeMemberStatus(Integer memberId, Integer operatorId, Integer memberStatus) {
        MemberInfoDO memberUpdate = new MemberInfoDO();
        memberUpdate.setId(memberId);
        memberUpdate.setMemberStatus(memberStatus);
        memberUpdate.setGmtModified(new Date());
        memberUpdate.setModifier(operatorId);
        Integer num = memberInfoMapper.updateByPrimaryKeySelective(memberUpdate);
        return num > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    private Boolean checkCompanyInfo(BizCheckMemberParam param) {
        //淘汽审核通过则调用通联接口设置企业信息
        if (param.getCheckStatus().equals(CheckStatusEnum.PASS.getId())) {
            CompanyInfoDO companyInfo = companyInfoService.getCompanyInfoNotNull(param.getMemberId());
            CompanyBasicInfoParam companyBasicInfoParam = BdUtil.do2bo(companyInfo, CompanyBasicInfoParam.class);
            SetCompanyInfoParam setCompanyInfoParam = new SetCompanyInfoParam();
            setCompanyInfoParam.setBizUserId(param.getMemberId().toString());
            setCompanyInfoParam.setCompanyBasicInfo(companyBasicInfoParam);
            tlMemberService.setCompanyInfo(setCompanyInfoParam);
        }
        MemberInfoDO memberUpdate = new MemberInfoDO();
        memberUpdate.setId(param.getMemberId());
        memberUpdate.setCheckStatus(param.getCheckStatus());
        memberUpdate.setFailReason(param.getFailReason());
        memberUpdate.setGmtModified(new Date());
        memberUpdate.setModifier(param.getOperatorId());
        Integer num = memberInfoMapper.updateByPrimaryKeySelective(memberUpdate);
        return num > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    private Boolean checkPersonalInfo(BizCheckMemberParam param) {
        //淘汽审核通过则调用通联接口实名认证
        if (param.getCheckStatus().equals(CheckStatusEnum.PASS.getId())) {
            PersonalInfoDO personalInfo = personalInfoService.getPersonalInfo(param.getMemberId());
            SetRealNameParam setRealNameParam = new SetRealNameParam();
            setRealNameParam.setBizUserId(param.getMemberId().toString());
            setRealNameParam.setName(personalInfo.getRealName());
            setRealNameParam.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
            setRealNameParam.setIdentityNo(DES.decryptDESDB(personalInfo.getIdentityNo()));
            tlMemberService.setRealName(setRealNameParam);
            //更新个人会员信息表
            PersonalInfoDO personalInfoUpdate = new PersonalInfoDO();
            personalInfoUpdate.setId(personalInfo.getId());
            personalInfoUpdate.setRealNameTime(new Date());
            personalInfoUpdate.setIsIdentityChecked(1);
            personalInfoService.updatePersonalInfo(personalInfo);
        }
        MemberInfoDO memberUpdate = new MemberInfoDO();
        memberUpdate.setId(param.getMemberId());
        memberUpdate.setCheckStatus(param.getCheckStatus());
        memberUpdate.setFailReason(param.getFailReason());
        memberUpdate.setGmtModified(new Date());
        memberUpdate.setModifier(param.getOperatorId());
        if (param.getCheckStatus().equals(CheckStatusEnum.PASS.getId())) {
            memberUpdate.setMemberStatus(MemberStatusEnum.EFFICACY.getId());
        }
        Integer num = memberInfoMapper.updateByPrimaryKeySelective(memberUpdate);
        return num > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public IdentityInfoBO getIdentityInfo(BaseParam param) {
        return null;
    }
}
