package com.tqmall.sigma.client.beans.result.member;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by linjian on 17/5/12.
 */
@Data
public class PersonalInfoDTO implements Serializable {

    /**
     * 会员ID*
     */
    private Integer memberId;

    /**
     * 真实姓名*
     */
    private String realName;

    /**
     * 证件类型（1:身份证 2:护照 3:军官证 4:回乡证 5:台胞证 6:警官证 7:士兵证 99:其它证件）*
     */
    private Integer identityType;

    /**
     * 证件号码*
     */
    private String identityNo;
}
