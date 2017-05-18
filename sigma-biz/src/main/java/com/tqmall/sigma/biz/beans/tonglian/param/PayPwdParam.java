package com.tqmall.sigma.biz.beans.tonglian.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/7.
 */
@Getter
@Setter
public class PayPwdParam {

    private String bizUserId;

    private String phone;

    private String name;

    private Integer identityType;

    private String identityNo;

    private String jumpUrl;

    private String backUrl;
}
