package com.tqmall.sigma.biz.beans.tonglian.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linjian on 17/4/5.
 */
@Getter
@Setter
public class SetRealNameResult extends BaseResult {

    private String name;

    private Integer identityType;

    private String identityNo;
}
