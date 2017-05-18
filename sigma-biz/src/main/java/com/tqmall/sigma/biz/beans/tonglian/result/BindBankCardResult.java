package com.tqmall.sigma.biz.beans.tonglian.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/6.
 */
@Getter
@Setter
public class BindBankCardResult extends BaseResult {

    private String tranceNum;

    private String transDate;

    private String bankName;

    private String bankCode;

    private Integer cardType;
}
