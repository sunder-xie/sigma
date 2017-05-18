package com.tqmall.sigma.biz.beans.tonglian.param;

import lombok.Data;

/**
 * Created by linjian on 17/4/5.
 */
@Data
public class ChangeBindPhoneParam {

    private String bizUserId;

    private String oldPhone;

    private String oldVerificationCode;

    private String newPhone;

    private String newVerificationCode;
}
