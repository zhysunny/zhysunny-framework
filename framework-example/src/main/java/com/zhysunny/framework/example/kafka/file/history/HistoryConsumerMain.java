package com.zhysunny.framework.example.kafka.file.history;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.OutputManage;
import com.zhysunny.framework.common.business.impl.NioTransferJson;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.kafka.consumer.persist.Persist;
import com.zhysunny.framework.kafka.consumer.persist.impl.NioFilePersistString;
import com.zhysunny.framework.kafka.consumer.service.KafkaConsumerService;
import com.zhysunny.framework.kafka.thread.HeartbeatThread;
import com.zhysunny.framework.kafka.thread.KafkaConsumerThread;
import com.zhysunny.framework.kafka.thread.ShutdownHookThread;

/**
 * 消费者测试
 * @author 章云
 * @date 2019/12/27 16:24
 */
public class HistoryConsumerMain {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
        new HeartbeatThread(10000).start();
        int threadNum = 10;
        String name = "history";
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        for (int i = 0; i < threadNum; i++) {
            KafkaConsumerService<String, JSONObject> kafkaConsumerService = new KafkaConsumerHistoryServiceImpl();
            kafkaConsumerService.setCommit(false);
            Persist<String> persist = new NioFilePersistString(name + i);
            OutputManage<String> output = new OutputManage<>(new NioTransferJson("history" + i + ".txt"));
            threadPools.addThread(new KafkaConsumerThread(name + i, kafkaConsumerService, persist, output));
        }
        threadPools.shutdown();
    }

}
