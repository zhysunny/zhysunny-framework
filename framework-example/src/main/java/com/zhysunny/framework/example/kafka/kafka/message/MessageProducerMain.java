package com.zhysunny.framework.example.kafka.kafka.message;

import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.business.impl.RandomStringInput;
import com.zhysunny.framework.common.thread.ShutdownHookThread;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.kafka.producer.service.KafkaProducerService;
import com.zhysunny.framework.kafka.thread.KafkaProducerThread;

/**
 * 生产者测试
 * @author 章云
 * @date 2019/12/27 16:24
 */
public class MessageProducerMain {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
        int threadNum = 10;
        String name = "message";
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        KafkaProducerService<String, String> kafkaProducerService = new KafkaProducerMessageServiceImpl();
        Transfer<String> transfer = new RandomStringInput(1000, 1024);
        for (int i = 0; i < threadNum; i++) {
            threadPools.addThread(new KafkaProducerThread(name + i, kafkaProducerService, transfer));
        }
        threadPools.shutdown();
    }

}
