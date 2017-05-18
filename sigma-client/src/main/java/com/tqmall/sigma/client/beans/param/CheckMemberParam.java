package com.tqmall.sigma.client.beans.param;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by linjian on 17/5/12.
 */
@Getter
@Setter
public class CheckMemberParam implements Serializable {

    private Integer memberId;

    private Integer checkStatus;

    private Integer operatorId;

    private String failReason;
}
