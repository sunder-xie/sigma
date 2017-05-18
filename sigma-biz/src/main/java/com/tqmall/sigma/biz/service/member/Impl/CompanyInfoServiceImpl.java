package com.tqmall.sigma.biz.service.member.Impl;

import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.sigma.biz.beans.constant.SigmaConstant;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;
import com.tqmall.sigma.biz.service.member.CompanyInfoService;
import com.tqmall.sigma.dao.CompanyInfoMapper;
import com.tqmall.sigma.entity.CompanyInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by linjian on 17/5/12.
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    @Override
    public CompanyInfoDO getCompanyInfoNotNull(Integer memberId) {
        CompanyInfoDO companyInfoDO = companyInfoMapper.selectByMemberId(memberId);
        if (companyInfoDO == null) {
            throw new BusinessCheckFailException(SigmaError.COMPANY_INFO_NULL);
        }
        return companyInfoDO;
    }

    @Override
    public CompanyInfoDO getCompanyInfo(Integer memberId) {
        return companyInfoMapper.selectByMemberId(memberId);
    }

    @Override
    public Integer insertCompanyInfo(CompanyInfoDO companyInfo) {
        Date date = new Date();
        companyInfo.setGmtCreate(date);
        companyInfo.setGmtModified(date);
        companyInfo.setCreator(SigmaConstant.SIGMA_SYSTEM_ID);
        companyInfo.setModifier(SigmaConstant.SIGMA_SYSTEM_ID);
        return companyInfoMapper.insertSelective(companyInfo);
    }

    @Override
    public Integer updateCompanyInfo(CompanyInfoDO companyInfo) {
        Date date = new Date();
        companyInfo.setGmtModified(date);
        companyInfo.setModifier(SigmaConstant.SIGMA_SYSTEM_ID);
        return companyInfoMapper.updateByPrimaryKeySelective(companyInfo);
    }
}
