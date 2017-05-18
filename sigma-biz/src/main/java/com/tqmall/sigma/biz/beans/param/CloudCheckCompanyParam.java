package com.tqmall.sigma.biz.beans.param;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by linjian on 17/5/12.
 */
@Getter
@Setter
public class CloudCheckCompanyParam {

    private Integer memberId;

    private Integer cloudCheckStatus;

    private Date cloudCheckTime;

    private String cloudFailReason;
}
