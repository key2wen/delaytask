package com.key.delay.server.biz.center.exception;

/**
 * create by shuguang on 2017/11/18
 * <p>
 * 不打印异常堆栈信息,提升性能
 */
public class DelayTaskRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 13032134506771309L;

    public DelayTaskRuntimeException(String message) {
        super(message);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}