package com.tqmall.sigma.web;

import com.tqmall.core.common.entity.Result;
import com.tqmall.sigma.biz.beans.tonglian.param.order.BizConfirmPayParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.BizOrderPayApplyParam;
import com.tqmall.sigma.biz.bizenum.PaymentEnum;
import com.tqmall.sigma.biz.bizenum.ValidateTypeEnum;
import com.tqmall.sigma.biz.service.member.MemberInfoService;
import com.tqmall.sigma.biz.service.tonglian.order.TlOrderPayBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * Created by huangzhangting on 17/3/30.
 */
@Controller
@RequestMapping("test")
public class TestController {

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private TlOrderPayBiz tlOrderPayBiz;

    @RequestMapping("member/detail")
    @ResponseBody
    public Result getMemberInfo(@RequestParam("id") Integer id) {
        return Result.wrapSuccessfulResult(memberInfoService.getMemberInfoById(id));
    }

    @RequestMapping("depositApply")
    @ResponseBody
    public Boolean depositApply(){
        BizOrderPayApplyParam applyParam = new BizOrderPayApplyParam();
        applyParam.setOrderSn("hms20170414003");
        applyParam.setPayId(PaymentEnum.QUICKPAY.getPayId());
        applyParam.setPayAmount(new BigDecimal("10"));
        applyParam.setBankCardNo("6228480402637874214");
        applyParam.setShopId(88888);
        applyParam.setValidateType(ValidateTypeEnum.MESSAGE_CODE.getId());
        return tlOrderPayBiz.depositApply(applyParam);
    }

    @RequestMapping("confirmPay")
    @ResponseBody
    public Boolean confirmPay(){
        BizConfirmPayParam payParam = new BizConfirmPayParam();
        payParam.setOrderSn("hms20170414003");
        payParam.setPayId(PaymentEnum.QUICKPAY.getPayId());
        payParam.setShopId(88888);
        payParam.setConsumerIp("10.0.0.126");
        payParam.setTradeNo("852806893242957824D1");
        payParam.setVerificationCode("198712");
        return tlOrderPayBiz.confirmPayForServer(payParam);
    }

}
