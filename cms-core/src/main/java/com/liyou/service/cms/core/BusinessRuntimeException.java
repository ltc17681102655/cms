package com.liyou.service.cms.core;

import com.liyou.framework.base.AbstractBusinessRuntimeException;

/***
 *
 *   @author linxiaohui
 *   @date 2018/5/7
 ***/
public class BusinessRuntimeException extends AbstractBusinessRuntimeException {
    public BusinessRuntimeException() {
    }

    public BusinessRuntimeException(String message) {
        super(message);
    }

    public BusinessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessRuntimeException(Throwable cause) {
        super(cause);
    }

    public BusinessRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * 获取 code
     *
     * @return
     */
    @Override
    public int getCode() {
        return 0;
    }
}
