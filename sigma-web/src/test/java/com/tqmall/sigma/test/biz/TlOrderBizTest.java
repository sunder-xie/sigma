package com.tqmall.sigma.test.biz;

import com.tqmall.sigma.biz.beans.tonglian.param.order.*;
import com.tqmall.sigma.biz.beans.tonglian.result.order.*;
import com.tqmall.sigma.biz.bizenum.SourceEnum;
import com.tqmall.sigma.biz.bizenum.TlOrderStatusEnum;
import com.tqmall.sigma.biz.service.tonglian.InterfaceInvokeBiz;
import com.tqmall.sigma.biz.service.tonglian.order.TlOrderBiz;
import com.tqmall.sigma.biz.service.tonglian.order.TlOrderPayBiz;
import com.tqmall.sigma.biz.redis.lock.RedisLock;
import com.tqmall.sigma.biz.redis.lock.RedisLockCallback;
import com.tqmall.sigma.component.utils.DateUtils;
import com.tqmall.sigma.component.utils.JsonUtils;
import com.tqmall.sigma.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzhangting on 17/3/30.
 */
public class TlOrderBizTest extends BaseTest {
    private final String bizUserId = "88888"; //商户系统用户标识,商户系统中唯一编号（必须是String类型）

    //88888：创建会员成功
    //{"userId":"ff6e1ec9-2d96-4149-9f4e-c9f95680149f","bizUserId":"88888"}

    private final String bizUserId2 = "66666";
    //{"bizUserId":"hzt_9999","userId":"934d843a-b1e7-4c79-b156-69ce93166065"}

    private final String backUrl = "http://mana.yipei360.com/test/testLock";

    @Autowired
    private InterfaceInvokeBiz interfaceInvokeBiz;
    @Autowired
    private TlOrderBiz tlOrderBiz;
    @Autowired
    private TlOrderPayBiz tlOrderPayBiz;

    @Autowired
    private RedisLock redisLock;


    @Test
    public void test_queryBalance(){
        QueryBalanceResult result = tlOrderBiz.queryBalance(bizUserId2);
        System.out.println(JsonUtils.toJson(result));
    }

    @Test
    public void test_getOrderDetail(){
//        final String orderNo = "hzt_test_order_1";
//        final String orderNo = "txsq_order_1";
//        final String orderNo = "consumeApply_order_1";
//        final String orderNo = "withdrawApply_order_1";
//        final String orderNo = "withdrawApply_order_2";
        final String orderNo = "freezeMoney_order_1";

        OrderDetailResult result = redisLock.execute(orderNo, new RedisLockCallback<OrderDetailResult>() {
            @Override
            public OrderDetailResult callback() {
                return tlOrderBiz.getOrderDetail(bizUserId, orderNo);
            }
        });

        System.out.println("订单："+result);
        System.out.println("状态："+ TlOrderStatusEnum.codeName(result.getOrderStatus()));
    }


    @Test
    public void test_queryInExpDetail(){
        QueryInExpDetailParam param = new QueryInExpDetailParam();
        param.setBizUserId(bizUserId);
//        param.setDateStart("2017-04-05");
        param.setDateEnd(DateUtils.dateToString(new Date(), DateUtils.yyyy_MM_dd));
        QueryInExpDetailResult result = tlOrderBiz.queryInExpDetail(param);
        System.out.println("收支明细测试："+ JsonUtils.toJson(result));
    }


    @Test
    public void test_consumeApply() throws Exception{
        Map<String, Object> payMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        map.put("amount", 1);
        map.put("bankCardNo", interfaceInvokeBiz.encrypt("6228480329277138775"));
        payMap.put("QUICKPAY", map); //快捷支付
//        payMap.put("POSPAY", map); //订单 POS

        String bizOrderNo = "consumeApply_order_4";

        ConsumeApplyParam param = new ConsumeApplyParam();
        param.setPayerId(bizUserId2);
        param.setRecieverId(bizUserId);
        param.setBizOrderNo(bizOrderNo);
        param.setAmount(1l);
        param.setBackUrl(backUrl);
        param.setPayMethod(payMap);
        param.setSource(SourceEnum.PC.getId());

        ConsumeApplyResult result = tlOrderPayBiz.consumeApply(param);
        System.out.println("消费申请结果："+JsonUtils.toJson(result));
    }

    @Test
    public void test_withdrawApply(){
        String bizOrderNo = "withdrawApply_order_2";
        WithdrawApplyParam param = new WithdrawApplyParam();
        param.setBizUserId(bizUserId);
        param.setBankCardNo(interfaceInvokeBiz.encrypt("6228480402637874214"));
        param.setBizOrderNo(bizOrderNo);
        param.setAmount(1l);
        param.setBackUrl(backUrl);
        param.setSource(SourceEnum.PC.getId());
        param.setExtendInfo("扩展信息测试一哈哈。。。");
        WithdrawApplyResult result = tlOrderPayBiz.withdrawApply(param);
        System.out.println(JsonUtils.toJson(result));
    }


    private String freezenNo = "freezeMoney_order_3";

    @Test
    public void test_freezeMoney(){
        FreezeMoneyParam param = new FreezeMoneyParam();
        param.setBizUserId(bizUserId);
        param.setBizFreezenNo(freezenNo);
        param.setAmount(1l);
        FreezeMoneyResult result = tlOrderBiz.freezeMoney(param);
        System.out.println("冻结金额："+JsonUtils.toJson(result));
    }

    @Test
    public void test_unfreezeMoney(){
        UnfreezeMoneyParam param = new UnfreezeMoneyParam();
        param.setBizUserId(bizUserId);
        param.setBizFreezenNo(freezenNo);
        param.setAmount(1l);
        UnfreezeMoneyResult result = tlOrderBiz.unfreezeMoney(param);
        System.out.println("解冻金额："+JsonUtils.toJson(result));
    }

}
