package com.tqmall.sigma.biz.beans.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by linjian on 17/5/12.
 */
@Getter
@Setter
public class BizCheckMemberParam implements Serializable {

    private Integer memberId;

    private Integer checkStatus;

    private Integer operatorId;

    private String failReason;
}
