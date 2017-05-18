package com.tqmall.sigma.biz.service.tonglian.member;

import com.tqmall.sigma.biz.beans.tonglian.param.ChangeBindPhoneParam;
import com.tqmall.sigma.biz.beans.tonglian.param.SetCompanyInfoParam;
import com.tqmall.sigma.biz.beans.tonglian.param.SetPersonalInfoParam;
import com.tqmall.sigma.biz.beans.tonglian.param.SetRealNameParam;
import com.tqmall.sigma.biz.beans.tonglian.result.*;

/**
 * Created by linjian on 17/4/1.
 */
public interface TlMemberService {

    CreateMemberResult createMember(String bizUserId, Integer memberType, Integer source);

    MemberPhoneResult sendVerificationCode(String bizUserId, String phone, Integer verificationCodeType);

    MemberPhoneResult bindPhone(String bizUserId, String phone, String verificationCode);

    ChangeBindPhoneResult changeBindPhone(ChangeBindPhoneParam param);

    SetRealNameResult setRealName(SetRealNameParam param);

    BaseResult setCompanyInfo(SetCompanyInfoParam param);

    BaseResult setPersonalInfo(SetPersonalInfoParam param);

    MemberInfoResult getMemberInfo(String bizUserId);

    CompanyBasicInfoResult getCompanyBasicInfo(String bizUserId);

    PersonalBasicInfoResult getPersonalBasicInfo(String bizUserId);

    BaseResult lockMember(String bizUserId);

    BaseResult unlockMember(String bizUserId);
}
