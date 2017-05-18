package com.tqmall.sigma.client.beans.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by linjian on 17/5/12.
 */
@Getter
@Setter
public class MemberInfoQueryParam extends BaseQueryParam implements Serializable {

    private String keyword;

    private Integer memberStatus;

    private Integer memberType;
}
