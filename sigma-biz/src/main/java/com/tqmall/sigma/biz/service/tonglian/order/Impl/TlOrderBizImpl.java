package com.tqmall.sigma.biz.service.tonglian.order.Impl;

import com.tqmall.sigma.biz.beans.tonglian.param.order.FreezeMoneyParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.QueryInExpDetailParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.UnfreezeMoneyParam;
import com.tqmall.sigma.biz.beans.tonglian.result.order.*;
import com.tqmall.sigma.biz.service.tonglian.InterfaceInvokeBiz;
import com.tqmall.sigma.biz.service.tonglian.order.TlOrderBiz;
import com.tqmall.sigma.component.utils.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzhangting on 17/3/30.
 */
@Slf4j
@Service
public class TlOrderBizImpl implements TlOrderBiz {
    private final String SERVICE = "OrderService"; //通联那边的服务对象

    @Value("${tl.account.set.no}")
    private String accountSetNo; //账户集编号

    @Autowired
    private InterfaceInvokeBiz interfaceInvokeBiz;

    //账户集编号，参数设置
    private void putAccountSetNo(Map<String, Object> map){
        map.put("accountSetNo", accountSetNo);
    }
    private <T> T invoke(Class<T> tClass, String method, Map<String, Object> param){
        return interfaceInvokeBiz.invoke(tClass, SERVICE, method, param);
    }
    @Override
    public QueryBalanceResult queryBalance(String bizUserId) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Map<String, Object> map = new HashMap<>();
        map.put("bizUserId", bizUserId);
        putAccountSetNo(map);
        return invoke(QueryBalanceResult.class, "queryBalance", map);
    }

    @Override
    public OrderDetailResult getOrderDetail(String bizUserId, String orderNo) {
        Assert.hasLength(bizUserId, "用户id不能为空");
        Assert.hasLength(orderNo, "订单号不能为空");
        Map<String, Object> map = new HashMap<>();
        map.put("bizUserId", bizUserId);
        map.put("bizOrderNo", orderNo);
        return invoke(OrderDetailResult.class, "getOrderDetail", map);
    }

    @Override
    public QueryInExpDetailResult queryInExpDetail(QueryInExpDetailParam param) {
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Map<String, Object> map = MapUtils.objectToMap(param);
        putAccountSetNo(map);
        return invoke(QueryInExpDetailResult.class, "queryInExpDetail", map);
    }

    private void checkParamForFreeze(FreezeMoneyParam param){
        Assert.notNull(param, "参数不能为空");
        Assert.hasLength(param.getBizUserId(), "用户id不能为空");
        Assert.hasLength(param.getBizFreezenNo(), "订单号不能为空");
        Long amount = param.getAmount();
        Assert.isTrue(amount!=null && amount>0, "非法金额，金额="+amount);
    }
    private Map<String, Object> getReqForFreeze(FreezeMoneyParam param){
        checkParamForFreeze(param);
        Map<String, Object> map = MapUtils.objectToMap(param);
        putAccountSetNo(map);
        return map;
    }
    @Override
    public FreezeMoneyResult freezeMoney(FreezeMoneyParam param) {
        return invoke(FreezeMoneyResult.class, "freezeMoney", getReqForFreeze(param));
    }

    @Override
    public UnfreezeMoneyResult unfreezeMoney(UnfreezeMoneyParam param) {
        return invoke(UnfreezeMoneyResult.class, "unfreezeMoney", getReqForFreeze(param));
    }

}
