package com.zhysunny.framework.example.kafka.kafka.message;

import com.zhysunny.framework.common.business.OutputManage;
import com.zhysunny.framework.common.business.impl.ConsoleOutput;
import com.zhysunny.framework.common.thread.HeartbeatThread;
import com.zhysunny.framework.common.thread.ShutdownHookThread;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.kafka.consumer.persist.Persist;
import com.zhysunny.framework.kafka.consumer.persist.impl.NioFilePersistString;
import com.zhysunny.framework.kafka.consumer.service.KafkaConsumerService;
import com.zhysunny.framework.kafka.thread.KafkaConsumerThread;
import com.zhysunny.framework.kafka.thread.KafkaHeartbeat;

/**
 * 消费者测试
 * @author 章云
 * @date 2019/12/27 16:24
 */
public class MessageConsumerMain {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
        new HeartbeatThread(10000, new KafkaHeartbeat()).start();
        int threadNum = 3;
        String name = "message";
        OutputManage<String> output = new OutputManage<>(new ConsoleOutput());
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        for (int i = 0; i < threadNum; i++) {
            KafkaConsumerService<String, String> kafkaConsumerService = new KafkaConsumerMessageServiceImpl();
            kafkaConsumerService.setCommit(false);
            Persist<String> persist = new NioFilePersistString(name + i);
            threadPools.addThread(new KafkaConsumerThread(name + i, kafkaConsumerService, persist, output));
        }
        threadPools.shutdown();
    }

}
