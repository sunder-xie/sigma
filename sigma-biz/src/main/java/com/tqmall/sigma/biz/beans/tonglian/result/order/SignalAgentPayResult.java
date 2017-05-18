package com.tqmall.sigma.biz.beans.tonglian.result.order;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/5/4.
 */
@Setter
@Getter
public class SignalAgentPayResult {
    private String payStatus;
    private String payFailMessage;
    private String orderNo;
    private String bizOrderNo;
    private String extendInfo;
}
