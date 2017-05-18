package com.tqmall.sigma.biz.beans.tonglian.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/6.
 */
@Getter
@Setter
public class PersonalBasicInfoResult {
    //姓名
    private String name;
    //用户状态
    private Integer userState;
    //会员类型
    private Integer memberType;
    //云账户用户Id
    private String userId;
    //国家
    private String country;
    //省份
    private String province;
    //县市
    private String area;
    //地址
    private String address;
    //手机号码
    private String phone;
    //身份证号码，RSA加密
    private String identityCardNo;
    //是否绑定手机
    private Boolean isPhoneChecked;
    //创建时间
    private String registerTime;
    //创建IP
    private String registerIp;
    //支付失败次数
    private Integer payFailAmount;
    //是否进行实名认证
    private Boolean isIdentityChecked;
    //实名认证时间
    private String realNameTime;
    //备注
    private String remark;
    //访问终端类型
    private Integer source;
    //是否已设置支付密码
    private Boolean isSetPayPwd;
    //商户系统用户标识，商户系统中唯一编号
    private String bizUserId;
}
