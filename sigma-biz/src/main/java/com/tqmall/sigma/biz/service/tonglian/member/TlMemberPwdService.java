package com.tqmall.sigma.biz.service.tonglian.member;

import com.tqmall.sigma.biz.beans.tonglian.param.PayPwdParam;
import com.tqmall.sigma.biz.beans.tonglian.param.UpdatePhoneParam;

/**
 * Created by linjian on 17/4/7.
 */
public interface TlMemberPwdService {

    String getSetPayPwdUrl(PayPwdParam param);

    String getUpdatePayPwdUrl(PayPwdParam param);

    String getResetPayPwdUrl(PayPwdParam param);

    String getUpdatePhoneByPayPwdUrl(UpdatePhoneParam param);
}
