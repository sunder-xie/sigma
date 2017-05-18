package com.tqmall.sigma.biz.beans.tonglian.param;

import lombok.Data;

/**
 * Created by linjian on 17/4/5.
 */
@Data
public class SetCompanyInfoParam {

    private String bizUserId;

    private String backUrl;//非必传

    private CompanyBasicInfoParam companyBasicInfo;
}
