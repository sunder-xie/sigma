package com.tqmall.sigma.biz.beans.tonglian.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/6.
 */
@Getter
@Setter
public class ThreeElementsBindBankCardParam {

    private String bizUserId;

    private String name;

    private Integer identityType;

    private String identityNo;

    private String cardNo;

    private Boolean isSafeCard;
}
