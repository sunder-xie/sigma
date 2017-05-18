package com.tqmall.sigma.biz.beans.tonglian.param.order;

import lombok.Data;

/**
 *
 * 提现申请，参数对象
 *
 * Created by huangzhangting on 17/4/6.
 */
@Data
public class WithdrawApplyParam extends OrderApplyParam {
    //payMethod 支付方式可以不传 如不传,则默认为通联通代付

    private String bizUserId;
    private String bankCardNo; //银行卡号/账号 绑定的银行卡号/账号 RAS 加密

    //accountSetNo 账户集编号

    /* 非必填字段 */
    private Long bankCardPro; //银行卡/账户属性 0:个人银行卡 1:企业对公账户 如果不传默认为 0
    private String withdrawType; //提现方式 T0:T+0 到账 T1:T+1 到账 默认为 T0

}
