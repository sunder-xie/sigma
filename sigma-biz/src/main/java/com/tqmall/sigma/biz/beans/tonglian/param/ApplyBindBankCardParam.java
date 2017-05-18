package com.tqmall.sigma.biz.beans.tonglian.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/6.
 */
@Getter
@Setter
public class ApplyBindBankCardParam {

    private String bizUserId;

    private String cardNo;

    private String phone;

    private String name;

    private Integer cardType;

    private Integer identityType;

    private String identityNo;

    private String validate;//信用卡时必传

    private String cvv2;//信用卡时必传

    private Boolean isSafeCard;//非必传

    private String unionBank;//非必传
}
