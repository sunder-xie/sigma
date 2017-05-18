package com.tqmall.sigma.biz.service.common;

import com.tqmall.sigma.biz.beans.param.SendVerificationCodeParam;

/**
 * Created by gaorongyu on 17/5/9.
 */
public interface VerificationCodeService {
    String getVerificationCode(String uniqueKey);

    String getNumVerificationCode(String uniqueKey);

    Boolean checkVerificationCode(String uniqueKey, String verificationCode ,String RedisKey);

    Boolean sendTlVerificationCode(SendVerificationCodeParam param);
}
