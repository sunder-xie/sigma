package com.tqmall.sigma.biz.service.member;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.sigma.biz.beans.param.*;
import com.tqmall.sigma.biz.beans.result.IdentityInfoBO;
import com.tqmall.sigma.biz.beans.result.LoginTypeBO;
import com.tqmall.sigma.biz.beans.result.MemberInfoBO;
import com.tqmall.sigma.biz.beans.tonglian.param.ChangeBindPhoneParam;
import com.tqmall.sigma.entity.MemberInfoDO;

/**
 * Created by gaorongyu on 17/5/11.
 */
public interface MemberInfoService {

    PagingResult<MemberInfoDO> getMemberInfoList(BizMemberInfoQueryParam param);

    MemberInfoDO getMemberInfoById(Integer memberId);

    MemberInfoDO getEffectiveMemberInfoById(Integer memberId);

    MemberInfoDO getMemberInfoByBizUserIdAndType(Integer userId, Integer userType);

    LoginTypeBO getLoginMemberType(String encryptedUserId, Integer userType);

    Boolean createMember(CreateMemberParam param);

    Boolean bindPhone(BindPhoneParam param);

    Boolean setMemberInfo(MemberInfoParam param);

    MemberInfoBO getMemberInfo(BaseParam param);

    Boolean lockMember(Integer memberId, Integer operatorId);

    Boolean unlockMember(Integer memberId, Integer operatorId);

    Boolean closeMember(Integer memberId, Integer operatorId);

    Boolean checkMember(BizCheckMemberParam param);

    void checkCompanyInfoCallBack(CloudCheckCompanyParam param);

    Boolean changeBindPhone(ChangeBindPhoneParam param);

    IdentityInfoBO getIdentityInfo(BaseParam param);
}
