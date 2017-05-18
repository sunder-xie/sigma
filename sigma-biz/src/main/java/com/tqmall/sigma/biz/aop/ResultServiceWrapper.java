package com.tqmall.sigma.biz.aop;

import com.tqmall.core.common.entity.Result;
import com.tqmall.core.common.exception.BusinessCheckFailException;
import com.tqmall.core.common.exception.BusinessProcessFailException;
import com.tqmall.sigma.biz.beans.tonglian.error.SigmaError;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ResultServiceWrapper implements MethodInterceptor {

    protected static final String SYSTEM_ERROR_MSG = "系统繁忙，请稍后重试";

    @Override
    public Result invoke(final MethodInvocation methodInvocation) throws Throwable {
        Result result = new Result();
        try {
            String methodName = methodInvocation.getMethod().getName();
            if (log.isDebugEnabled()) {
                log.debug("starting business logic processing.... " + methodName);
            }
            result = (Result) methodInvocation.proceed();
            if (log.isDebugEnabled()) {
                log.debug("finished business logic processing...." + methodName);
            }
        } catch (BusinessProcessFailException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            result.setCode(e.getErrorCode());

        } catch (BusinessCheckFailException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            result.setCode(e.getErrorCode());

        } catch (IllegalArgumentException e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            result.setCode(SigmaError.CODE_NULL.getCode());

        } catch (RuntimeException e) {
            setSystemError(result);
            handleException(e);
        }
        return result;
    }

    protected void setSystemError(Result result) {
        result.setSuccess(false);
        result.setCode(SigmaError.SYSTEM_ERROR.getCode());
        result.setMessage(SigmaError.SYSTEM_ERROR.getMessage());
    }

    protected void handleException(RuntimeException e) {
        log.error("系统出错", e);
    }
}
