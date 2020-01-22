package com.zhysunny.framework.example.console.kafka.message;

import com.zhysunny.framework.common.business.Input;
import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.business.impl.ListToListTransfer;
import com.zhysunny.framework.common.business.impl.RandomString;
import com.zhysunny.framework.common.thread.ShutdownHookThread;
import com.zhysunny.framework.common.thread.TransferThread;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.kafka.service.KafkaProducerService;

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
        Input input = new RandomString(1000, 1024);
        KafkaProducerService<String, String> kafkaProducerService = new KafkaProducerMessageServiceImpl();
        Transfer transfer = new ListToListTransfer(input, kafkaProducerService);
        for (int i = 0; i < threadNum; i++) {
            threadPools.addThread(new TransferThread(name + i, transfer));
        }
        threadPools.shutdown();
    }

}
