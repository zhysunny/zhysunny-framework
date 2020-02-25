package com.zhysunny.framework.example.console.kafka.avro;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.Input;
import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.business.impl.ListToListTransfer;
import com.zhysunny.framework.common.thread.TransferThread;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.example.console.kafka.message.KafkaProducerMessageServiceImpl;
import com.zhysunny.framework.kafka.service.KafkaProducerService;

/**
 * 生产者测试
 * @author 章云
 * @date 2019/12/27 16:24
 */
public class AvroProducerMain {

    public static void main(String[] args) {
        int threadNum = 1;
        String name = "avro";
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        Input input = new PersonListMsg();
        KafkaProducerService<String, JSONObject> kafkaProducerService = new KafkaProducerAvroServiceImpl();
        kafkaProducerService.createProducer();
        Transfer transfer = new ListToListTransfer(input, kafkaProducerService);
        for (int i = 0; i < threadNum; i++) {
            threadPools.addThread(new TransferThread(name + i, transfer));
        }
        threadPools.shutdown();
    }

}
