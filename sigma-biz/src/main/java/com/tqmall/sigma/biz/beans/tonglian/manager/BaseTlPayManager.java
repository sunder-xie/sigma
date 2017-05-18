package com.tqmall.sigma.biz.beans.tonglian.manager;

import com.google.common.collect.Maps;
import com.tqmall.sigma.biz.beans.tonglian.param.order.PayMethodParam;
import com.tqmall.sigma.biz.bizenum.PaymentEnum;
import com.tqmall.sigma.biz.bizenum.SourceEnum;
import com.tqmall.sigma.biz.service.tonglian.InterfaceInvokeBiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

import static com.tqmall.sigma.biz.bizenum.PaymentEnum.getEnumById;

/**
 * Created by gaorongyu on 17/4/5.
 */
@Slf4j
@Component
public class BaseTlPayManager {
    @Autowired
    private InterfaceInvokeBiz interfaceInvokeBiz;

    public Long getCountsAmount(BigDecimal amount){
        return amount.multiply(new BigDecimal(100)).longValue();
    }

    public Integer getSource(boolean isApp){
        return isApp? SourceEnum.MOBILE.getId() : SourceEnum.PC.getId();
    }

    public Map<String,Object> getPayMethod(PayMethodParam param){
        PaymentEnum paymentEnum = getEnumById(param.getPayId());
        switch (paymentEnum){
            case QUICKPAY:
                return getQuickPayParam(param);
            case POSPAY:
                return getPosPayParam(param);
        }
        return null;
    }

    private Map<String,Object> getQuickPayParam(PayMethodParam param){
        Map<String,Object> quickPay = Maps.newHashMap();
        Map<String,Object> value = Maps.newHashMap();
        value.put("bankCardNo", interfaceInvokeBiz.encrypt(param.getBankCardNo()));
        value.put("amount", getCountsAmount(param.getPayAmount()));
        quickPay.put("QUICKPAY", value);
        return quickPay;
    }

    private Map<String,Object> getPosPayParam(PayMethodParam param){
        Map<String,Object> posPay = Maps.newHashMap();
        Map<String,Object> value = Maps.newHashMap();
        value.put("amount",getCountsAmount(param.getPayAmount()));
        posPay.put("POSPAY",value);
        return posPay;
    }
}
