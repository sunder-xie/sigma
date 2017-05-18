package com.tqmall.sigma.biz.service.member.Impl;

import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.sigma.biz.beans.constant.SigmaConstant;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;
import com.tqmall.sigma.biz.service.member.PersonalInfoService;
import com.tqmall.sigma.dao.PersonalInfoMapper;
import com.tqmall.sigma.entity.PersonalInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by linjian on 17/5/12.
 */
@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

    @Autowired
    private PersonalInfoMapper personalInfoMapper;

    @Override
    public PersonalInfoDO getPersonalInfoNotNull(Integer memberId) {
        PersonalInfoDO personalInfoDO = personalInfoMapper.selectByMemberId(memberId);
        if (personalInfoDO == null){
            throw new BusinessCheckFailException(SigmaError.PERSONAL_INFO_NULL);
        }
        return personalInfoDO;
    }

    @Override
    public PersonalInfoDO getPersonalInfo(Integer memberId) {
        return personalInfoMapper.selectByMemberId(memberId);
    }

    @Override
    public Integer insertPersonalInfo(PersonalInfoDO personalInfo) {
        Date date = new Date();
        personalInfo.setGmtCreate(date);
        personalInfo.setGmtModified(date);
        personalInfo.setCreator(SigmaConstant.SIGMA_SYSTEM_ID);
        personalInfo.setModifier(SigmaConstant.SIGMA_SYSTEM_ID);
        return personalInfoMapper.insertSelective(personalInfo);
    }

    @Override
    public Integer updatePersonalInfo(PersonalInfoDO personalInfo) {
        Date date = new Date();
        personalInfo.setGmtModified(date);
        personalInfo.setModifier(SigmaConstant.SIGMA_SYSTEM_ID);
        return personalInfoMapper.updateByPrimaryKeySelective(personalInfo);
    }
}
