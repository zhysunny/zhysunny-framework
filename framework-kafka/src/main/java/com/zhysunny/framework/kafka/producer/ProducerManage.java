package com.zhysunny.framework.kafka.producer;

import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.kafka.business.input.Input;
import com.zhysunny.framework.kafka.producer.service.KafkaProducerService;
import com.zhysunny.framework.kafka.thread.KafkaProducerThread;

/**
 * 生产者管理
 * @author 章云
 * @date 2019/12/27 15:04
 */
public class ProducerManage {

    public static void start(String name, int threadNum, KafkaProducerService kafkaProducerService, Input input) {
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        for (int i = 0; i < threadNum; i++) {
            threadPools.addThread(new KafkaProducerThread<>(name, kafkaProducerService, input));
        }
    }

}
