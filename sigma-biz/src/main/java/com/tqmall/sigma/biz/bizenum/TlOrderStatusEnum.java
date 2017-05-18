package com.tqmall.sigma.biz.bizenum;

import lombok.Getter;

/**
 *
 * 通联订单状态
 *
 * Created by huangzhangting on 17/4/7.
 */
@Getter
public enum TlOrderStatusEnum {
    NOT_PAY(1, "未支付"),
    PAY_FAILED(3, "交易失败"),
    PAY_SUCCESS(4, "交易成功"),
    SUCCESS_BUT_REFUND(5, "交易成功-发生退款"),
    CLOSED(6, "关闭"),
    PAYING(99, "进行中");

    private Integer code;
    private String name;

    TlOrderStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String codeName(Integer code){
        if(code==null){
            return null;
        }
        for(TlOrderStatusEnum statusEnum : values()){
            if(statusEnum.getCode().equals(code)){
                return statusEnum.getName();
            }
        }
        return null;
    }

}
