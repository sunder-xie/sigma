package com.tqmall.sigma.biz.beans.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/5/10.
 */
@Getter
@Setter
public class BindPhoneParam extends BaseParam {
    //手机号
    private String phone;
    //验证码
    private String verificationCode;
}
