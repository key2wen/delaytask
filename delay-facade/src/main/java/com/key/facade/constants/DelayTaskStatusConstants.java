package com.key.facade.constants;

/**
 * 状态：1.正常延迟中；2.消费延迟中；3.可消费状态；4.消费完成；5.超时关闭
 */
public interface DelayTaskStatusConstants {

    /**
     * 待设置等待
     */
    int PENDING = 0;

    /**
     * 正常延迟中
     */
    int NORMAL_DELAY = 1;

    /**
     * 消费延迟中
     */
    int RESERVE_DEPAY = 2;

    /**
     * 可消费状态
     */
    int READY = 3;

    /**
     * 消费完成 （完成状态）
     */
    int FINISH = 4;

    /**
     * 超时关闭
     */
    int TIME_OUT_CLOSE = 5;

}
