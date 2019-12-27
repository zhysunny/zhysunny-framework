package com.zhysunny.framework.kafka.consumer;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.kafka.business.output.Output;
import com.zhysunny.framework.kafka.consumer.persist.Persist;
import com.zhysunny.framework.kafka.consumer.service.KafkaConsumerService;
import com.zhysunny.framework.kafka.thread.KafkaConsumerThread;
import java.util.List;

/**
 * 消费者管理
 * @author 章云
 * @date 2019/12/27 15:04
 */
public class ConsumerManage<K, V> {

    public void start(String name, int threadNum, KafkaConsumerService<K, V> kafkaConsumerService, Output<V>[] outputs, Persist<V> persist) {
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        for (int i = 0; i < threadNum; i++) {
            threadPools.addThread(new KafkaConsumerThread(name, kafkaConsumerService, outputs, persist));
        }
    }

    /**
     * 消费者的输出
     * @param datas
     * @param outputs
     */
    public void output(List<V> datas, Output<V>[] outputs) {
        for (Output output : outputs) {
            output.output(datas);
        }
    }

}
