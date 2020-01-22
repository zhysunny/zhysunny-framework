package com.zhysunny.framework.kafka.thread;

import com.zhysunny.framework.common.thread.Hearbeat;
import com.zhysunny.framework.kafka.constant.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 章云
 * @date 2020/1/20 10:21
 */
public class KafkaHeartbeat implements Hearbeat {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaHeartbeat.class);

    @Override
    public void execute() {
        LOGGER.info("已消费：{}", KafkaConstants.TOTAL.get());
    }

}
