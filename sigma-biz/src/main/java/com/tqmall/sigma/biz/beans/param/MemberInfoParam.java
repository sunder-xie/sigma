package com.tqmall.sigma.biz.beans.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/5/11.
 */
@Setter
@Getter
public class MemberInfoParam extends BaseParam {
    //身份证号码
    private String identityNo;
    //真实姓名
    private String name;
    //手机号
    private String phone;
    /**
     * 个人用户绑定手机号参数
     */
    //验证码
    private String verificationCode;
    /**
     * 企业用户注册参数
     */
    //企业名称
    private String companyName;
    //营业执照号
    private String businessLicense;
    //企业对公账户
    private String accountNo;
    //开户银行名称
    private String parentBankName;
    /**
     * 业务参数
     */
    private Integer memberId;
}
