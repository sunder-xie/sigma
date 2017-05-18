package com.tqmall.sigma.biz.beans.tonglian.param;

import lombok.Data;

/**
 * Created by linjian on 17/4/6.
 */
@Data
public class SetPersonalInfoParam {

    private String bizUserId;

    private PersonalBasicInfoParam userInfo;
}
