package com.tqmall.sigma.biz.beans.tonglian.result;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by linjian on 17/4/6.
 */
@Getter
@Setter
public class MemberInfoResult extends BaseResult {

    private Integer memberType;

    private Map<String, Object> memberInfo;
}
