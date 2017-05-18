package com.tqmall.sigma.biz.service.tonglian.order.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tqmall.sigma.biz.beans.tonglian.constant.TlPayConstants;
import com.tqmall.sigma.biz.beans.tonglian.manager.BaseTlPayManager;
import com.tqmall.sigma.biz.beans.tonglian.param.order.*;
import com.tqmall.sigma.biz.beans.tonglian.result.order.*;
import com.tqmall.sigma.biz.bizenum.PaymentEnum;
import com.tqmall.sigma.biz.service.tonglian.InterfaceInvokeBiz;
import com.tqmall.sigma.biz.service.tonglian.order.TlOrderPayBiz;
import com.tqmall.sigma.component.utils.BdUtil;
import com.tqmall.sigma.component.utils.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaorongyu on 17/4/5.
 */
@Slf4j
@Service
public class TlOrderPayBizImpl implements TlOrderPayBiz{
    @Value("${tl.account.set.no}")
    private String accountSetNo; //账户集编号

    @Value("${sigma.domain}")
    private String sigmaDomain;

    private final String SERVICE = "OrderService"; //通联那边的服务对象

    @Autowired
    private InterfaceInvokeBiz interfaceInvokeBiz;
    @Autowired
    private BaseTlPayManager baseTlPayManager;

    @Override
    public Boolean depositApply(BizOrderPayApplyParam param) {
        //充值订单自动创建
        Assert.notNull(param.getOrderSn(),"orderSn不能为空");
        Assert.notNull(param.getPayAmount(),"payAmount不能为空");
        Assert.notNull(param.getShopId(),"shopId不能为空");
        Assert.notNull(param.getPayId(),"payId不能为空");
        //TODO id和type获取唯一id
        Integer bizUserId = param.getShopId();
        Map<String,Object> map = new HashMap<>();
        map.put("bizOrderNo",param.getOrderSn());
        map.put("bizUserId",bizUserId.toString());
        map.put("accountSetNo",accountSetNo);
        map.put("amount",baseTlPayManager.getCountsAmount(param.getPayAmount()));
        map.put("fee",0L);
        //TODO 快捷支付不可用密码支付
        map.put("validateType", param.getValidateType());
        map.put("backUrl",sigmaDomain + TlPayConstants.PAY_NOTIFY_URL);
        PayMethodParam payMethodParam = BdUtil.do2bo(param,PayMethodParam.class);
        map.put("payMethod",baseTlPayManager.getPayMethod(payMethodParam));
        map.put("industryCode", TlPayConstants.INDUSTRY_CODE);
        map.put("industryName",TlPayConstants.INDUSTRY_NAME);
        map.put("source",baseTlPayManager.getSource(param.isApp()));
        ConsumeApplyResult result =  interfaceInvokeBiz.invoke(ConsumeApplyResult.class,SERVICE,"depositApply",map);
        //TODO 记录云订单号 快捷记录tradeNo
        if (param.getPayId().equals(PaymentEnum.QUICKPAY.getPayId())){

        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean confirmPayForServer(BizConfirmPayParam param) {
        Assert.notNull(param.getOrderSn(),"orderSn不能为空");
        Assert.notNull(param.getShopId(),"shopId不能为空");
        Assert.notNull(param.getPayId(),"payId不能为空");
        Assert.notNull(param.getConsumerIp(), "consumerIp不能为空");
        Map<String,Object> map = new HashMap<>();
        //TODO id和type获取唯一id
        Integer bizUserId = param.getShopId();
        map.put("bizOrderNo", param.getOrderSn());
        map.put("bizUserId", bizUserId.toString());
        //TODO 快捷获取tradeNo
        if (param.getPayId().equals(PaymentEnum.QUICKPAY.getPayId())){
            map.put("tradeNo", param.getTradeNo());
        }
        //TODO 交易方式为短信验证的非网关支付必传
        map.put("verificationCode",param.getVerificationCode());
        map.put("consumerIp",param.getConsumerIp());
        ConfirmPayResult result = interfaceInvokeBiz.invoke(ConfirmPayResult.class,SERVICE,"pay",map);
        //TODO 记录失败状态及原因
        return Boolean.TRUE;
    }

    @Override
    public String confirmPayByPassword(BizConfirmPayParam param) {
        Assert.notNull(param.getOrderSn(),"orderSn不能为空");
        Assert.notNull(param.getShopId(),"shopId不能为空");
        Assert.notNull(param.getPayId(),"payId不能为空");
        Assert.notNull(param.getConsumerIp(),"consumerIp不能为空");
        //TODO id和type获取唯一id
        Integer bizUserId = param.getShopId();
        Map<String,Object> map = new HashMap<>();
        map.put("bizOrderNo", param.getOrderSn());
        map.put("bizUserId", bizUserId.toString());
        map.put("consumerIp", param.getConsumerIp());
        //非网关支付jumpUrl
        map.put("jumpUrl", param.getJumpUrl());
        map = interfaceInvokeBiz.getReq(SERVICE, "pay", map);
        String paramStr = interfaceInvokeBiz.getRequestParamStr(map, false);
        return TlPayConstants.ORDER_PAY_PWD_URL +  paramStr;
    }

    @Override
    public Boolean refund(BizRefundParam param) {
        //TODO 在线支付取消时自动创建
        Assert.notNull(param.getOrderSn(),"orderSn不能为空");
        Assert.notNull(param.getOriOrderSn(),"oriOrderSn不能为空");
        Assert.notNull(param.getShopId(),"shopId不能为空");
        Assert.notNull(param.getAmount(),"amount不能为空");
        //TODO id和type获取唯一id
        Integer bizUserId = param.getShopId();
        Map<String,Object> map = new HashMap<>();
        map.put("bizOrderNo", param.getOrderSn());
        map.put("oriBizOrderNo", param.getOriOrderSn());
        map.put("bizUserId", bizUserId.toString());
        map.put("amount", baseTlPayManager.getCountsAmount(param.getAmount()));
        RefundResult result = interfaceInvokeBiz.invoke(RefundResult.class, SERVICE, "refund", map);
        //TODO 处理退款结果
        return Boolean.TRUE;
    }

    @Override
    public ConsumeApplyResult consumeApply(ConsumeApplyParam param) {
        Assert.notNull(param, "参数不能为空");
        Map<String, Object> map = MapUtils.objectToMap(param);
        return interfaceInvokeBiz.invoke(ConsumeApplyResult.class, SERVICE, "consumeApply", map);
    }

    @Override
    public WithdrawApplyResult withdrawApply(WithdrawApplyParam param) {
        Assert.notNull(param, "参数不能为空");
        Map<String, Object> map = MapUtils.objectToMap(param);
        map.put("accountSetNo", accountSetNo);
        return interfaceInvokeBiz.invoke(WithdrawApplyResult.class, SERVICE, "withdrawApply", map);
    }

    @Override
    public Boolean agentCollectApply(BizOrderPayApplyParam param) {
        Assert.notNull(param.getOrderSn(),"orderSn不能为空");
        Assert.notNull(param.getPayAmount(),"payAmount不能为空");
        Assert.notNull(param.getShopId(),"shopId不能为空");
        Assert.notNull(param.getPayId(),"payId不能为空");
        Assert.notNull(param.getReceiveId(),"receiveId不能为空");
        //TODO id和type获取唯一id
        Integer bizUserId = param.getShopId();
        Integer receiverId = param.getReceiveId();
        ArrayList<Object> recieverList = Lists.newArrayList();
        Map<String,Object> receiveMap = Maps.newHashMap();
        receiveMap.put("bizUserId", receiverId.toString());
        receiveMap.put("amount", baseTlPayManager.getCountsAmount(param.getPayAmount()));
        recieverList.add(receiveMap);
        Map<String,Object> map = new HashMap<>();
        map.put("bizOrderNo",param.getOrderSn());
        map.put("payerId",bizUserId.toString());
        map.put("recieverList",recieverList);
        map.put("tradeCode",TlPayConstants.AGENT_COLLECT_TRADE_CODE);
        map.put("amount",baseTlPayManager.getCountsAmount(param.getPayAmount()));
        map.put("fee",0L);
        //TODO 快捷支付不可用密码支付
        map.put("validateType", param.getValidateType());
        map.put("backUrl",sigmaDomain + TlPayConstants.PAY_NOTIFY_URL);
        PayMethodParam payMethodParam = BdUtil.do2bo(param,PayMethodParam.class);
        map.put("payMethod",baseTlPayManager.getPayMethod(payMethodParam));
        map.put("industryCode", TlPayConstants.INDUSTRY_CODE);
        map.put("industryName",TlPayConstants.INDUSTRY_NAME);
        map.put("source",baseTlPayManager.getSource(param.isApp()));
        ConsumeApplyResult result = interfaceInvokeBiz.invoke(ConsumeApplyResult.class, SERVICE, "agentCollectApply", map);
        return Boolean.TRUE;
    }

    @Override
    public Boolean signalAgentPay(BizOrderPayApplyParam param) {
        Assert.notNull(param.getOrderSn(),"orderSn不能为空");
        Assert.notNull(param.getPayAmount(),"payAmount不能为空");
        Assert.notNull(param.getReceiveId(),"receiveId不能为空");
        //TODO id和type获取唯一id
        Integer bizUserId = param.getReceiveId();
        Map<String,Object> map = new HashMap<>();
        map.put("bizOrderNo","DS" + param.getOrderSn());
        map.put("bizUserId",bizUserId.toString());
        ArrayList<Object> collectPayList = Lists.newArrayList();
        Map<String,Object> collectPayMap = Maps.newHashMap();
        collectPayMap.put("bizOrderNo",param.getOrderSn());
        collectPayMap.put("amount",baseTlPayManager.getCountsAmount(param.getPayAmount()));
        collectPayList.add(collectPayMap);
        map.put("collectPayList",collectPayList);
        map.put("accountSetNo",accountSetNo);
        map.put("tradeCode",TlPayConstants.AGENT_PAY_TRADE_CODE);
        map.put("amount",baseTlPayManager.getCountsAmount(param.getPayAmount()));
        map.put("fee",0L);
        map.put("backUrl",sigmaDomain + TlPayConstants.PAY_NOTIFY_URL);
        SignalAgentPayResult result = interfaceInvokeBiz.invoke(SignalAgentPayResult.class, SERVICE, "signalAgentPay", map);
        //TODO 处理分账结果
        return Boolean.TRUE;
    }
}
