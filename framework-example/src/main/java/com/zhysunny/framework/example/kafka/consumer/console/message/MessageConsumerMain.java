package com.zhysunny.framework.example.kafka.consumer.console.message;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.Output;
import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.business.impl.ConsoleOutput;
import com.zhysunny.framework.common.business.impl.ListToListTransfer;
import com.zhysunny.framework.common.thread.HeartbeatThread;
import com.zhysunny.framework.common.thread.ShutdownHookThread;
import com.zhysunny.framework.common.thread.TransferThread;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.example.kafka.KafkaHeartbeat;
import com.zhysunny.framework.example.kafka.consumer.file.history.KafkaConsumerHistoryServiceImpl;
import com.zhysunny.framework.kafka.service.KafkaConsumerService;

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
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        for (int i = 0; i < threadNum; i++) {
            KafkaConsumerService<String, JSONObject> kafkaConsumerService = new KafkaConsumerHistoryServiceImpl();
            Output output = new ConsoleOutput();
            Transfer transfer = new ListToListTransfer(kafkaConsumerService, output);
            threadPools.addThread(new TransferThread(name + i, transfer));
        }
        threadPools.shutdown();
    }

}
