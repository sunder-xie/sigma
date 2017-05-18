package com.tqmall.sigma.biz.beans.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/5/17.
 */
@Getter
@Setter
public class ApplyBindBankCardParam extends BaseParam {
    //银行卡号
    private String cardNo;
    //银行预留手机号
    private String phone;

    private String name;

    private Integer cardType;

    private String identityNo;

    private String validate;//信用卡时必传

    private String cvv2;//信用卡时必传
}
