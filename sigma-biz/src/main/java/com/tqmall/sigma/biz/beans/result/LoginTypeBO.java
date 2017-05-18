package com.tqmall.sigma.biz.beans.result;

import com.tqmall.sigma.biz.bizenum.LoginMemberStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/5/12.
 */
@Setter
@Getter
public class LoginTypeBO {
    //会员登录状态枚举
    private LoginMemberStatusEnum loginMemberStatusEnum;

    /*
     * 用于区分会员详情页 登录时会员类型为“未填写信息”时使用
     * 会员类型 2:企业会员 3:个人会员
     */
    private Integer memberType;

    /**
     * 审核失败原因 会员未审核通过时使用
     */
    private String failReason;
}
