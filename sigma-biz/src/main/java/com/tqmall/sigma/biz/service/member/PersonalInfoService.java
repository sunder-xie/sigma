package com.tqmall.sigma.biz.service.member;

import com.tqmall.sigma.entity.PersonalInfoDO;

/**
 * Created by linjian on 17/5/12.
 */
public interface PersonalInfoService {

    PersonalInfoDO getPersonalInfoNotNull(Integer memberId);

    PersonalInfoDO getPersonalInfo(Integer memberId);

    Integer insertPersonalInfo(PersonalInfoDO personalInfo);

    Integer updatePersonalInfo(PersonalInfoDO personalInfo);
}
