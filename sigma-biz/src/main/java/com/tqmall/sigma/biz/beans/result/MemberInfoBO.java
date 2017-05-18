package com.tqmall.sigma.biz.beans.result;

import com.tqmall.sigma.biz.beans.tonglian.result.BoundCardResult;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by gaorongyu on 17/5/11.
 */
@Setter
@Getter
public class MemberInfoBO {
    //名称
    private String name;
    //邮箱
    private String email;
    //上次登录时间
    private String lastLoginTime;
    //可用余额
    private BigDecimal balance;
    //冻结金额
    private BigDecimal frozenAmount;
    //用户已绑定银行卡列表
    private List<BoundCardResult> bankCardList;
}
