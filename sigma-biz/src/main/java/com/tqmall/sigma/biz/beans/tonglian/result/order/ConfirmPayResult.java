package com.tqmall.sigma.biz.beans.tonglian.result.order;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gaorongyu on 17/4/7.
 */
@Getter
@Setter
public class ConfirmPayResult {
    private String payStatus;
    private String payFailMessage;
    private String bizUserId;
    private String bizOrderNo;
}
