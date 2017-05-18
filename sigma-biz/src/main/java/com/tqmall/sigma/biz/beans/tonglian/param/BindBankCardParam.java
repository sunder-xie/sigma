package com.tqmall.sigma.biz.beans.tonglian.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/6.
 */
@Getter
@Setter
public class BindBankCardParam {

    private String bizUserId;

    private String tranceNum;

    private String transDate;

    private String phone;

    private String verificationCode;
}
