package com.tqmall.sigma.test.biz;

import com.tqmall.sigma.biz.beans.tonglian.param.order.BizConfirmPayParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.BizOrderPayApplyParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.BizRefundParam;
import com.tqmall.sigma.biz.bizenum.PaymentEnum;
import com.tqmall.sigma.biz.bizenum.ValidateTypeEnum;
import com.tqmall.sigma.biz.service.tonglian.order.TlOrderPayBiz;
import com.tqmall.sigma.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by gaorongyu on 17/4/7.
 */
public class TlOrderPayTest extends BaseTest{
    @Autowired
    private TlOrderPayBiz tlOrderPayBiz;

    @Test
    public void test_depositApply(){
        BizOrderPayApplyParam applyParam = new BizOrderPayApplyParam();
        applyParam.setOrderSn("hms20170512001");
        applyParam.setPayId(PaymentEnum.QUICKPAY.getPayId());
        applyParam.setPayAmount(new BigDecimal("10"));
        applyParam.setBankCardNo("6228480402637874214");
        applyParam.setShopId(88888);
        applyParam.setValidateType(ValidateTypeEnum.PAY_PASSWORD.getId());
        tlOrderPayBiz.depositApply(applyParam);
    }

    @Test
    public void test_confirmPay(){
        BizConfirmPayParam payParam = new BizConfirmPayParam();
        payParam.setOrderSn("hms20170505002");
        payParam.setPayId(PaymentEnum.QUICKPAY.getPayId());
        payParam.setShopId(88888);
        payParam.setConsumerIp("10.0.0.126");
        payParam.setTradeNo("860333500589424640D1");
        payParam.setVerificationCode("661823");
        tlOrderPayBiz.confirmPayForServer(payParam);
    }

    @Test
    public void test_confirmPayByPwd(){
        BizConfirmPayParam payParam = new BizConfirmPayParam();
        payParam.setOrderSn("hms20170410001");
        payParam.setPayId(PaymentEnum.QUICKPAY.getPayId());
        payParam.setShopId(66666);
        payParam.setConsumerIp("10.0.0.126");
        payParam.setJumpUrl("https://www.tqmall.com");
        String url = tlOrderPayBiz.confirmPayByPassword(payParam);
        System.out.println(url);
    }

    @Test
    public void test_refund(){
        BizRefundParam refundParam = new BizRefundParam();
        refundParam.setOrderSn("test_refund001");
        refundParam.setOriOrderSn("consumeApply_order_4");
        refundParam.setAmount(new BigDecimal("0.01"));
        refundParam.setShopId(66666);
        tlOrderPayBiz.refund(refundParam);
    }

    @Test
    public void test_agentCollectApply(){
        BizOrderPayApplyParam applyParam = new BizOrderPayApplyParam();
        applyParam.setOrderSn("hms20170505002");
        applyParam.setPayId(PaymentEnum.QUICKPAY.getPayId());
        applyParam.setPayAmount(new BigDecimal("0.1"));
        applyParam.setBankCardNo("6228480402637874214");
        applyParam.setShopId(88888);
        applyParam.setReceiveId(66666);
        applyParam.setValidateType(ValidateTypeEnum.MESSAGE_CODE.getId());
        tlOrderPayBiz.agentCollectApply(applyParam);
    }

    @Test
    public void test_signalAgentPay(){
        BizOrderPayApplyParam applyParam = new BizOrderPayApplyParam();
        applyParam.setOrderSn("hms20170505002");
        applyParam.setPayAmount(new BigDecimal("0.08"));
        applyParam.setReceiveId(66666);
        tlOrderPayBiz.signalAgentPay(applyParam);
    }
}
