package com.zhysunny.framework.example.kafka.producer;

import com.zhysunny.framework.common.business.ShutdownHook;
import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.kafka.service.KafkaProducerService;

/**
 * @author 章云
 * @date 2020/2/14 11:39
 */
public class ProducerShutdownHook implements ShutdownHook {

    private KafkaProducerService producer;
    private Transfer transfer;

    public ProducerShutdownHook(KafkaProducerService producer, Transfer transfer) {
        this.producer = producer;
        this.transfer = transfer;
    }

    @Override
    public void shutdown() {
        transfer.close();
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance();
        threadPools.shutdown();
        threadPools.join();
        producer.close();
    }
}
