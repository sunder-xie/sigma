package com.tqmall.sigma.biz.beans.tonglian.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/6.
 */
@Getter
@Setter
public class BoundCardResult {

    private String bankCardNo;

    private String bankName;

    private String bindTime;

    private Integer cardType;

    private Integer bindState;

    private String phone;

    private Boolean isSafeCard;
}
