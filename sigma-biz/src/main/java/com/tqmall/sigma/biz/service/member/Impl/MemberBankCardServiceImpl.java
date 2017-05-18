package com.tqmall.sigma.biz.service.member.Impl;

import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.core.common.exception.BusinessProcessFailException;
import com.tqmall.sigma.biz.beans.constant.SigmaConstant;
import com.tqmall.sigma.biz.beans.encrypt.DES;
import com.tqmall.sigma.biz.beans.param.ApplyBindBankCardParam;
import com.tqmall.sigma.biz.beans.param.ConfirmBindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;
import com.tqmall.sigma.biz.beans.tonglian.param.BindBankCardParam;
import com.tqmall.sigma.biz.beans.tonglian.result.BindBankCardResult;
import com.tqmall.sigma.biz.beans.tonglian.result.BoundCardResult;
import com.tqmall.sigma.biz.beans.tonglian.result.CardBinInfoResult;
import com.tqmall.sigma.biz.bizenum.BankBindStatusEnum;
import com.tqmall.sigma.biz.bizenum.IdentityTypeEnum;
import com.tqmall.sigma.biz.service.member.BankCardService;
import com.tqmall.sigma.biz.service.member.MemberBankCardService;
import com.tqmall.sigma.biz.service.member.MemberInfoService;
import com.tqmall.sigma.biz.service.tonglian.member.TlMemberBankCardService;
import com.tqmall.sigma.component.utils.BdUtil;
import com.tqmall.sigma.component.utils.JsonUtils;
import com.tqmall.sigma.dao.MemberBankCardMapper;
import com.tqmall.sigma.entity.MemberBankCardDO;
import com.tqmall.sigma.entity.MemberInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by linjian on 17/5/18.
 */
@Slf4j
@Service
public class MemberBankCardServiceImpl implements MemberBankCardService {

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private MemberBankCardMapper memberBankCardMapper;

    @Autowired
    private TlMemberBankCardService tlMemberBankCardService;

    @Autowired
    private BankCardService bankCardService;


    @Override
    public Boolean applyBindBankCard(ApplyBindBankCardParam param) {
        Integer userId = DES.decrypyDESToID(param.getUserId());
        MemberInfoDO member = memberInfoService.getMemberInfoByBizUserIdAndType(userId, param.getUserType());
        com.tqmall.sigma.biz.beans.tonglian.param.ApplyBindBankCardParam tlApplyParam = BdUtil.bo2do(param, com.tqmall.sigma.biz.beans.tonglian.param.ApplyBindBankCardParam.class);
        tlApplyParam.setBizUserId(member.getId().toString());
        tlApplyParam.setIdentityType(IdentityTypeEnum.ID_CARD.getId());
        BindBankCardResult bankCardResult = tlMemberBankCardService.applyBindBankCard(tlApplyParam);
        MemberBankCardDO memberBankCardDO = getByMemberIdAndCardNo(member.getId(), param.getCardNo());
        if (memberBankCardDO == null){
            memberBankCardDO = new MemberBankCardDO();
            memberBankCardDO.setMemberId(member.getId());
            memberBankCardDO.setBankCardNo(DES.encryptDES(param.getCardNo()));
            memberBankCardDO.setBankPhone(param.getPhone());
            //银行卡信息
            CardBinInfoResult cardBinResult = bankCardService.getBankCardBin(param.getCardNo());
            memberBankCardDO.setCardBin(cardBinResult.getCardBin());
            memberBankCardDO.setBankCode(cardBinResult.getBankCode());
            memberBankCardDO.setBankName(cardBinResult.getBankName());
            memberBankCardDO.setCardType(cardBinResult.getCardType());
            memberBankCardDO.setCardState(cardBinResult.getCardState());
            //确认绑卡时需要提供的业务参数
            memberBankCardDO.setApplyTime(bankCardResult.getTransDate());
            memberBankCardDO.setTranceNum(bankCardResult.getTranceNum());
            insertSelective(memberBankCardDO);
        }else {
            MemberBankCardDO updateDO = new MemberBankCardDO();
            memberBankCardDO.setId(memberBankCardDO.getId());
            memberBankCardDO.setBankPhone(param.getPhone());
            memberBankCardDO.setApplyTime(bankCardResult.getTransDate());
            memberBankCardDO.setTranceNum(bankCardResult.getTranceNum());
            updateByPrimaryKeySelective(updateDO);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean confirmBindBankCard(ConfirmBindBankCardParam param) {
        Integer userId = DES.decrypyDESToID(param.getUserId());
        MemberInfoDO member = memberInfoService.getMemberInfoByBizUserIdAndType(userId, param.getUserType());
        MemberBankCardDO memberBankCardDO = getByMemberIdAndCardNo(member.getId(), param.getCardNo());
        if (memberBankCardDO == null){
            log.error("查无会员银行卡信息，memberId:" + member.getId() + ",银行卡号：" + param.getCardNo());
            throw new BusinessProcessFailException(SigmaError.CODE_NULL);
        }
        BindBankCardParam tlBindBankCardParam = new BindBankCardParam();
        tlBindBankCardParam.setBizUserId(member.getId().toString());
        tlBindBankCardParam.setTranceNum(memberBankCardDO.getTranceNum());
        tlBindBankCardParam.setTransDate(memberBankCardDO.getApplyTime());
        tlBindBankCardParam.setPhone(memberBankCardDO.getBankPhone());
        tlBindBankCardParam.setVerificationCode(param.getVerificationCode());
        tlMemberBankCardService.bindBankCard(tlBindBankCardParam);
        MemberBankCardDO updateDO = new MemberBankCardDO();
        updateDO.setId(memberBankCardDO.getId());
        updateDO.setBindState(BankBindStatusEnum.BOUND.getStatus());
        updateDO.setBindTime(new Date());
        updateByPrimaryKeySelective(updateDO);
        return Boolean.TRUE;
    }

    @Override
    public BoundCardResult queryBoundBankCardInfo(String cardNo) {
        //TODO
        Integer memberId = null;
        return tlMemberBankCardService.queryBankCard(memberId.toString(), cardNo).getBindCardList().get(0);
    }

    @Override
    public List<BoundCardResult> queryBoundBankCardInfo() {
        //TODO
        Integer memberId = null;
        return tlMemberBankCardService.queryBankCard(memberId.toString(), null).getBindCardList();
    }

    @Override
    public Boolean unbindBankCard(String cardNo) {
        //TODO
        Integer memberId = null;
        tlMemberBankCardService.unbindBankCard(memberId.toString(), cardNo);
        MemberBankCardDO memberBankCardDO = getByMemberIdAndCardNo(memberId, cardNo);
        if (memberBankCardDO == null){
            log.error("查无会员银行卡信息，memberId:" + memberId + ",银行卡号：" + cardNo);
            throw new BusinessProcessFailException(SigmaError.CODE_NULL);
        }
        MemberBankCardDO updateDO = new MemberBankCardDO();
        updateDO.setId(memberBankCardDO.getId());
        updateDO.setBindState(BankBindStatusEnum.UNBIND.getStatus());
        updateDO.setUnbindTime(new Date());
        updateByPrimaryKeySelective(updateDO);
        return Boolean.TRUE;
    }

    private void insertSelective(MemberBankCardDO memberBankCardDO){
        memberBankCardDO.setGmtCreate(new Date());
        memberBankCardDO.setGmtModified(new Date());
        memberBankCardDO.setCreator(SigmaConstant.SIGMA_SYSTEM_ID);
        memberBankCardDO.setModifier(SigmaConstant.SIGMA_SYSTEM_ID);
        int num = memberBankCardMapper.insertSelective(memberBankCardDO);
        if (num < 1){
            log.error("本地数据存储失败，" + JsonUtils.toJson(memberBankCardDO));
            throw new BusinessCheckFailException(SigmaError.SYSTEM_ERROR);
        }
    }

    private MemberBankCardDO getByMemberIdAndCardNo(Integer memberId, String cardNo){
        return memberBankCardMapper.selectByMemberIdAndCardNo(memberId,DES.encryptDESDB(cardNo));
    }

    private void updateByPrimaryKeySelective(MemberBankCardDO memberBankCardDO){
        memberBankCardDO.setGmtModified(new Date());
        int num = memberBankCardMapper.updateByPrimaryKeySelective(memberBankCardDO);
        if (num < 1){
            log.error("本地数据更新失败，" + JsonUtils.toJson(memberBankCardDO));
            throw new BusinessCheckFailException(SigmaError.SYSTEM_ERROR);
        }
    }
}
