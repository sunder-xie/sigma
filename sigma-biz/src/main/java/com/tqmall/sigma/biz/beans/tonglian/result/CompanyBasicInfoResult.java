package com.tqmall.sigma.biz.beans.tonglian.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/6.
 */
@Getter
@Setter
public class CompanyBasicInfoResult {
    //企业名称
    private String companyName;
    //企业地址
    private String companyAddress;
    //营业执照号
    private String businessLicense;
    //组织机构代码
    private String organizationCode;
    //联系电话
    private String telephone;
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
    //开户行支行名称
    private String bankName;
    //审核状态
    private Integer status;
    //审核时间
    private String checkTime;
    //备注
    private String remark;
    //审核失败原因
    private String failReason;
    //是否已设置支付密码
    private Boolean isSetPayPwd;
}
