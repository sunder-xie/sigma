package com.tqmall.sigma.client.beans.result.member;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by linjian on 17/5/12.
 */
@Data
public class CompanyInfoDTO implements Serializable {

    /**
     * 会员ID*
     */
    private Integer memberId;

    /**
     * 企业名称*
     */
    private String companyName;

    /**
     * 营业执照号*
     */
    private String businessLicense;

    /**
     * 法人姓名*
     */
    private String legalName;

    /**
     * 法人证件类型（1:身份证 2:护照 3:军官证 4:回乡证 5:台胞证 6:警官证 7:士兵证 99:其它证件）*
     */
    private Integer identityType;

    /**
     * 法人证件号码*
     */
    private String legalIds;

    /**
     * 法人手机号*
     */
    private String legalPhone;

    /**
     * 企业对公账户*
     */
    private String accountNo;

    /**
     * 开户银行名称*
     */
    private String parentBankName;

    /**
     * 开户行地区代码*
     */
    private String bankCityNo;
}
