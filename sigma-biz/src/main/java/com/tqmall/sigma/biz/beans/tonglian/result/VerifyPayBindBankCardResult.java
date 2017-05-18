package com.tqmall.sigma.biz.beans.tonglian.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/12.
 */
@Getter
@Setter
public class VerifyPayBindBankCardResult {

    private String tranceNum;

    private Integer cardType;

    private String bankCode;
}
