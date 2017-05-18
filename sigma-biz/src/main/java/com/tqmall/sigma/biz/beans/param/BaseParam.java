package com.tqmall.sigma.biz.beans.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/5/11.
 */
@Getter
@Setter
public class BaseParam {
    //加密后的门店Id或卖家Id
    private String userId;
    //1:门店 2:商家
    private Integer userType;
}
