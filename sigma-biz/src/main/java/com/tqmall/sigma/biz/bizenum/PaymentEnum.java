package com.tqmall.sigma.biz.bizenum;

import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;

/**
 * Created by gaorongyu on 17/4/6.
 */
public enum PaymentEnum {
    QUICKPAY(0,"QUICKPAY","通联快捷支付"),
    POSPAY(1,"POSPAY","通联pos支付")
    ;

    private Integer payId;
    private String code;
    private String desc;

    PaymentEnum(Integer payId, String code, String desc) {
        this.payId = payId;
        this.code = code;
        this.desc = desc;
    }

    public Integer getPayId() {
        return payId;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static PaymentEnum getEnumById(Integer payId) {
        for (PaymentEnum paymentEnum : values()){
            if (paymentEnum.getPayId().equals(payId))
                return paymentEnum;
        }
        throw new BusinessCheckFailException(SigmaError.PAY_ID_ERROR);
    }
}
