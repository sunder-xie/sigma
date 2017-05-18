package com.tqmall.sigma.biz.service.tonglian.order;

import com.tqmall.sigma.biz.beans.tonglian.param.order.BizConfirmPayParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.BizOrderPayApplyParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.BizRefundParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.ConsumeApplyParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.WithdrawApplyParam;
import com.tqmall.sigma.biz.beans.tonglian.result.order.ConsumeApplyResult;
import com.tqmall.sigma.biz.beans.tonglian.result.order.WithdrawApplyResult;

/**
 * Created by gaorongyu on 17/4/5.
 */
public interface TlOrderPayBiz {
    Boolean depositApply(BizOrderPayApplyParam param);

    Boolean confirmPayForServer(BizConfirmPayParam param);

    String confirmPayByPassword(BizConfirmPayParam param);

    Boolean refund(BizRefundParam param);


    /**
     * 消费申请
     * @param param
     * @return
     */
    ConsumeApplyResult consumeApply(ConsumeApplyParam param);

    /**
     * 提现申请
     * @param param
     * @return
     */
    WithdrawApplyResult withdrawApply(WithdrawApplyParam param);

    Boolean agentCollectApply(BizOrderPayApplyParam param);

    Boolean signalAgentPay(BizOrderPayApplyParam param);
}
