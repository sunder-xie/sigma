package com.tqmall.sigma.biz.beans.tonglian.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/7.
 */
@Getter
@Setter
public class UpdatePhoneParam {

    private String bizUserId;

    private String name;

    private Integer identityType;

    private String identityNo;

    private String oldPhone;

    private String jumpUrl;//非必传

    private String backUrl;
}
