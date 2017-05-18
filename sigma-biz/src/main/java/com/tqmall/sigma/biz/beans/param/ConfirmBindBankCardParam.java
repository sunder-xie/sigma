package com.tqmall.sigma.biz.beans.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/5/18.
 */
@Getter
@Setter
public class ConfirmBindBankCardParam extends BaseParam {
    //银行卡
    private String cardNo;
    //验证码
    private String verificationCode;
}
