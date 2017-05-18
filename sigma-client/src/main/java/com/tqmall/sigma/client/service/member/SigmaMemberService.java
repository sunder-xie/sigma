package com.tqmall.sigma.client.service.member;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.sigma.client.beans.param.CheckMemberParam;
import com.tqmall.sigma.client.beans.param.MemberInfoQueryParam;
import com.tqmall.sigma.client.beans.result.member.CompanyInfoDTO;
import com.tqmall.sigma.client.beans.result.member.MemberInfoDTO;
import com.tqmall.sigma.client.beans.result.member.PersonalInfoDTO;

/**
 * Created by gaorongyu on 17/5/11.
 */
public interface SigmaMemberService {

    PagingResult<MemberInfoDTO> getMemberInfoList(MemberInfoQueryParam param);

    Result<MemberInfoDTO> getMemberInfo(Integer memberId);

    Result<PersonalInfoDTO> getPersonalInfo(Integer memberId);

    Result<CompanyInfoDTO> getCompanyInfo(Integer memberId);

    Result<Boolean> lockMember(Integer memberId, Integer operatorId);

    Result<Boolean> unlockMember(Integer memberId, Integer operatorId);

    Result<Boolean> closeMember(Integer memberId, Integer operatorId);

    Result<Boolean> checkMember(CheckMemberParam param);
}
