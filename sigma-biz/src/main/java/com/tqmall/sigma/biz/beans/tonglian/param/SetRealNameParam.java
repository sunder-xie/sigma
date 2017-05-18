package com.tqmall.sigma.biz.beans.tonglian.param;

import lombok.Data;

/**
 * Created by linjian on 17/4/5.
 */
@Data
public class SetRealNameParam {

    private String bizUserId;

    private Boolean isAuth = Boolean.TRUE;

    private String name;

    private Integer identityType;

    private String identityNo;
}
