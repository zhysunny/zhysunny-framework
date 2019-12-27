package com.zhysunny.framework.kafka.main;

import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.kafka.business.input.Input;
import com.zhysunny.framework.kafka.business.input.impl.RandomString;
import com.zhysunny.framework.kafka.producer.service.KafkaProducerService;
import com.zhysunny.framework.kafka.producer.service.impl.KafkaProducerMessageServiceImpl;
import com.zhysunny.framework.kafka.thread.KafkaProducerThread;

/**
 * 生产者测试
 * @author 章云
 * @date 2019/12/27 16:24
 */
public class MessageProducer {

    public static void main(String[] args) {
        int threadNum = 10;
        String name = "message";
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        KafkaProducerService<String, String> kafkaProducerService = new KafkaProducerMessageServiceImpl();
        Input<String> input = new RandomString(1000, 1024);
        for (int i = 0; i < threadNum; i++) {
            threadPools.addThread(new KafkaProducerThread<>(name, kafkaProducerService, input));
        }
        threadPools.shutdown();
    }

}
