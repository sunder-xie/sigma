package com.tqmall.sigma.biz.beans.tonglian.param;

import lombok.Data;

/**
 * Created by linjian on 17/4/5.
 */
@Data
public class CompanyBasicInfoParam {
    //企业名称
    private String companyName;
    //企业地址（非必传）
    //private String companyAddress;
    //营业执照号
    private String businessLicense;
    //组织机构代码（非必传）
    //private String organizationCode;
    //联系电话（非必传）
    //private String telephone;
    //法人姓名
    private String legalName;
    //法人证件类型
    private Integer identityType;
    //法人证件号码
    private String legalIds;
    //法人手机号码
    private String legalPhone;
    //企业对公账户
    private String accountNo;
    //开户银行名称
    private String parentBankName;
    //开户行地区代码
    private String bankCityNo;
    //开户行支行名称（非必传）
    //private String bankName;
    //支付行号（非必传）
    //private String unionBank;
}
