package com.zhysunny.framework.example.kafka;

import com.zhysunny.framework.common.business.Heartbeat;
import com.zhysunny.framework.kafka.constant.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 章云
 * @date 2020/1/22 15:26
 */
public class KafkaHeartbeat implements Heartbeat {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaHeartbeat.class);

    @Override
    public void execute() {
        LOGGER.info("已消费：{}", KafkaConstants.TOTAL.get());
    }

}
