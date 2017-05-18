package com.tqmall.sigma.client.beans.result.member;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by linjian on 17/5/12.
 */
@Data
public class MemberInfoDTO implements Serializable {

    private Integer id;

    /**
     * 淘汽业务用户ID*
     */
    private Integer bizUserId;

    /**
     * 淘汽业务用户类型（1:门店 2:商家）*
     */
    private Integer bizUserType;

    /**
     * 会员名称*
     */
    private String memberName;

    /**
     * 已绑定手机号码*
     */
    private String memberPhone;

    /**
     * 是否绑定手机（0:否 1:是）*
     */
    private Integer isPhoneChecked;

    /**
     * 会员类型（2:企业会员 3:个人会员）*
     */
    private Integer memberType;

    /**
     * 访问终端类型（1:Mobile 2:PC）*
     */
    private Integer visitSource;

    /**
     * 会员状态（0:待生效 1:已生效 2:关闭 3:冻结）*
     */
    private Integer memberStatus;

    /**
     * 会员登录邮箱*
     */
    private String memberEmail;

    /**
     * 上次登录时间*
     */
    private Date lastLoginTime;

    /**
     * 上次登录IP*
     */
    private String lastLoginIp;

    /**
     * 淘汽审核状态（0:待审核 1:审核通过 2:审核不通过）*
     */
    private Integer checkStatus;

    /**
     * 失败原因*
     */
    private String failReason;
}
