package com.tqmall.sigma.biz.service.amount.Impl;

import com.tqmall.sigma.biz.service.amount.AmountService;
import com.tqmall.sigma.biz.service.tonglian.order.TlOrderBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by gaorongyu on 17/5/15.
 */
@Slf4j
@Service
public class AmountServiceImpl implements AmountService{
    @Autowired
    private TlOrderBiz tlOrderBiz;

    @Override
    public BigDecimal conversionAmount(Long count) {
        BigDecimal countBig = new BigDecimal(count);
        return countBig.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
    }
}
