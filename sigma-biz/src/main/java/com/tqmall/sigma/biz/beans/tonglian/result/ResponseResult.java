package com.tqmall.sigma.biz.beans.tonglian.result;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Created by linjian on 17/4/8.
 */
@Getter
@Setter
public class ResponseResult {

    private String service;

    private String method;

    private Map<String, String> returnValue;

    private String status;

    private String errorCode;

    private String message;
}
