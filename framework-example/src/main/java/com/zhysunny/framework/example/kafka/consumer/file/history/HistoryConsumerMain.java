package com.zhysunny.framework.example.kafka.consumer.file.history;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.Output;
import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.business.impl.FileOutput;
import com.zhysunny.framework.common.business.impl.ListToListTransfer;
import com.zhysunny.framework.common.file.FileReadWrite;
import com.zhysunny.framework.common.file.FileReadWriteString;
import com.zhysunny.framework.common.thread.HeartbeatThread;
import com.zhysunny.framework.common.thread.ShutdownHookThread;
import com.zhysunny.framework.common.thread.TransferThread;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.example.kafka.KafkaHeartbeat;
import com.zhysunny.framework.kafka.service.KafkaConsumerService;

/**
 * 消费者测试
 * @author 章云
 * @date 2019/12/27 16:24
 */
public class HistoryConsumerMain {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
        new HeartbeatThread(10000, new KafkaHeartbeat()).start();
        int threadNum = 10;
        String name = "history";
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        for (int i = 0; i < threadNum; i++) {
            KafkaConsumerService<String, JSONObject> kafkaConsumerService = new KafkaConsumerHistoryServiceImpl();
            FileReadWrite<String> fileReadWrite = new FileReadWriteString(name + i);
            Output<JSONObject> output = new FileOutput<>(fileReadWrite);
            Transfer transfer = new ListToListTransfer(kafkaConsumerService, output);
            threadPools.addThread(new TransferThread(name + i, transfer));
        }
        threadPools.shutdown();
    }

}
