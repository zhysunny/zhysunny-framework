package com.zhysunny.framework.kafka.error;

import org.apache.kafka.common.KafkaException;

/**
 * kafka消息反序列化异常
 * @author 章云
 * @date 2019/12/27 9:34
 */
public class DeserializationException extends KafkaException {

    private static final long serialVersionUID = 1L;

    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeserializationException(String message) {
        super(message);
    }

    public DeserializationException(Throwable cause) {
        super(cause);
    }

    public DeserializationException() {
        super();
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}