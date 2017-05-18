package com.tqmall.sigma.biz.service.member;

import com.tqmall.sigma.entity.CompanyInfoDO;

/**
 * Created by linjian on 17/5/12.
 */
public interface CompanyInfoService {

    CompanyInfoDO getCompanyInfoNotNull(Integer memberId);

    CompanyInfoDO getCompanyInfo(Integer memberId);

    Integer insertCompanyInfo(CompanyInfoDO companyInfo);

    Integer updateCompanyInfo(CompanyInfoDO companyInfo);
}
