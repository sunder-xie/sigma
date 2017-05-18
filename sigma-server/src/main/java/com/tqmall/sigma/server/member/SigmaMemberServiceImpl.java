package com.tqmall.sigma.server.member;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.sigma.biz.beans.param.BizCheckMemberParam;
import com.tqmall.sigma.biz.beans.param.BizMemberInfoQueryParam;
import com.tqmall.sigma.biz.service.member.CompanyInfoService;
import com.tqmall.sigma.biz.service.member.MemberInfoService;
import com.tqmall.sigma.biz.service.member.PersonalInfoService;
import com.tqmall.sigma.client.beans.param.CheckMemberParam;
import com.tqmall.sigma.client.beans.param.MemberInfoQueryParam;
import com.tqmall.sigma.client.beans.result.member.CompanyInfoDTO;
import com.tqmall.sigma.client.beans.result.member.MemberInfoDTO;
import com.tqmall.sigma.client.beans.result.member.PersonalInfoDTO;
import com.tqmall.sigma.client.service.member.SigmaMemberService;
import com.tqmall.sigma.component.utils.BdUtil;
import com.tqmall.sigma.entity.MemberInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gaorongyu on 17/5/11.
 */
@Service("sigmaMemberService")
public class SigmaMemberServiceImpl implements SigmaMemberService {

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private PersonalInfoService personalInfoService;

    @Autowired
    private CompanyInfoService companyInfoService;

    @Override
    public PagingResult<MemberInfoDTO> getMemberInfoList(MemberInfoQueryParam param) {
        BizMemberInfoQueryParam bizAccountPayRecordParam = BdUtil.bo2do(param, BizMemberInfoQueryParam.class);
        PagingResult<MemberInfoDO> pagingResult = memberInfoService.getMemberInfoList(bizAccountPayRecordParam);
        List<MemberInfoDTO> list = BdUtil.do2bo4List(pagingResult.getList(), MemberInfoDTO.class);
        return PagingResult.wrapSuccessfulResult(list, pagingResult.getTotal());
    }

    @Override
    public Result<MemberInfoDTO> getMemberInfo(Integer memberId) {
        return Result.wrapSuccessfulResult(BdUtil.do2bo(memberInfoService.getMemberInfoById(memberId), MemberInfoDTO.class));
    }

    @Override
    public Result<PersonalInfoDTO> getPersonalInfo(Integer memberId) {
        return Result.wrapSuccessfulResult(BdUtil.do2bo(personalInfoService.getPersonalInfo(memberId), PersonalInfoDTO.class));
    }

    @Override
    public Result<CompanyInfoDTO> getCompanyInfo(Integer memberId) {
        return Result.wrapSuccessfulResult(BdUtil.do2bo(companyInfoService.getCompanyInfoNotNull(memberId), CompanyInfoDTO.class));
    }

    @Override
    public Result<Boolean> lockMember(Integer memberId, Integer operatorId) {
        return Result.wrapSuccessfulResult(memberInfoService.lockMember(memberId, operatorId));
    }

    @Override
    public Result<Boolean> unlockMember(Integer memberId, Integer operatorId) {
        return Result.wrapSuccessfulResult(memberInfoService.unlockMember(memberId, operatorId));
    }

    @Override
    public Result<Boolean> closeMember(Integer memberId, Integer operatorId) {
        return Result.wrapSuccessfulResult(memberInfoService.closeMember(memberId, operatorId));
    }

    @Override
    public Result<Boolean> checkMember(CheckMemberParam param) {
        BizCheckMemberParam bizCheckMemberParam = BdUtil.bo2do(param, BizCheckMemberParam.class);
        return Result.wrapSuccessfulResult(memberInfoService.checkMember(bizCheckMemberParam));
    }
}
