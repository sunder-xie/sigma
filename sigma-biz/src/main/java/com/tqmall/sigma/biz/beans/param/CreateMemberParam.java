package com.tqmall.sigma.biz.beans.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/5/9.
 */
@Getter
@Setter
public class CreateMemberParam extends BaseParam {
    //邮箱
    private String email;
    //登录密码
    private String password;
    //会员类型 2:企业会员 3:个人会员
    private Integer memberType;
}
