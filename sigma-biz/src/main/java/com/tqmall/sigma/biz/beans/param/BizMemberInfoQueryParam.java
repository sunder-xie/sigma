package com.tqmall.sigma.biz.beans.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by linjian on 17/5/12.
 */
@Getter
@Setter
public class BizMemberInfoQueryParam extends BizBaseQueryParam implements Serializable {

    private String keyword;

    private Integer memberStatus;

    private Integer memberType;
}
