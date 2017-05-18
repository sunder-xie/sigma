package com.tqmall.sigma.biz.beans.tonglian.error;

import com.tqmall.core.common.errors.ServiceErrors;

public enum SigmaError implements ServiceErrors {
    //3位系统码(110)＋1位异常等级码(1严重,2错误,3警告)＋4位具体异常定义码
    SYSTEM_ERROR("11010001", "系统内部错误，请稍后再试！"),
    CODE_NULL("11010002", "参数错误，非法请求！"),
    MEMBER_TYPE_ERROR("11010003", "会员类型错误"),
    PAY_ID_ERROR("11010004","支付类型错误"),
    INVOKE_ERROR("11010005","调用通联接口失败"),
    PARAM_ERROR("11010006","参数错误"),
    IDENTIFY_CODE_OUTTIME("11010007","验证码超时"),
    MEMBER_RENEW("11010008","会员重复创建"),
    MEMBER_NULL("11010009","查无会员"),
    TL_ERROR("11010010","通联统一错误"),
    DESCRY_ERROR("11010011","解密失败"),
    PERSONAL_INFO_NULL("11020012","查无个人会员详情"),
    COMPANY_INFO_NULL("11020013","查无企业会员详情"),
    SHOP_INFO_ERROR("11020014", "门店详情获取失败"),
    MEMBER_FROZEN("11020015", "账户冻结"),
    DES_ENCRYPT_ERROR("11020016","加密失败"),
    DES_DECRYPT_ERROR("11020017","解密失败"),
    EMAIL_REPEAT("11020018","邮箱重复"),
    ;

    private final String code;

    private final String message;

    SigmaError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getErrorMsg(String code) {
        for (SigmaError shopError : values()) {
            if (code.equals(shopError.code)) return shopError.message;
        }
        return "";
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
