package com.tqmall.sigma.biz.beans.tonglian.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/5.
 */
@Getter
@Setter
public class ChangeBindPhoneResult extends BaseResult {

    private String oldPhone;

    private String newPhone;
}
