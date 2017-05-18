package com.tqmall.sigma.biz.service.tonglian.order;

import com.tqmall.sigma.biz.beans.tonglian.param.order.FreezeMoneyParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.QueryInExpDetailParam;
import com.tqmall.sigma.biz.beans.tonglian.param.order.UnfreezeMoneyParam;
import com.tqmall.sigma.biz.beans.tonglian.result.order.*;

/**
 *
 * 通联订单相关业务
 *
 * Created by huangzhangting on 17/3/30.
 */
public interface TlOrderBiz {
    /**
     * 查询余额
     * @param bizUserId 用户id
     * @return
     */
    QueryBalanceResult queryBalance(String bizUserId);

    /**
     * 查询订单状态
     * @param bizUserId 用户id
     * @param orderNo 订单号（sigma系统生成的订单号）
     * @return
     */
    OrderDetailResult getOrderDetail(String bizUserId, String orderNo);

    /**
     * 查询账户收支明细
     * @param param
     * @return
     */
    QueryInExpDetailResult queryInExpDetail(QueryInExpDetailParam param);

    /**
     * 冻结金额
     * @param param
     * @return
     */
    FreezeMoneyResult freezeMoney(FreezeMoneyParam param);

    /**
     * 解冻金额
     * @param param
     * @return
     */
    UnfreezeMoneyResult unfreezeMoney(UnfreezeMoneyParam param);

}
