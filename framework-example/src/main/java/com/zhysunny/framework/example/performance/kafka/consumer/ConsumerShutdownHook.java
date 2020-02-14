package com.zhysunny.framework.example.performance.kafka.consumer;

import com.zhysunny.framework.common.business.ShutdownHook;
import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.kafka.service.KafkaConsumerService;
import java.util.List;

/**
 * @author 章云
 * @date 2020/2/14 11:39
 */
public class ConsumerShutdownHook implements ShutdownHook {

    private List<KafkaConsumerService> consumers;
    private List<Transfer> transfers;

    public ConsumerShutdownHook(List<KafkaConsumerService> consumers, List<Transfer> transfers) {
        this.consumers = consumers;
        this.transfers = transfers;
    }

    @Override
    public void shutdown() {
        transfers.forEach(transfer -> transfer.close());
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance();
        threadPools.shutdown();
        threadPools.join();
        consumers.forEach(consumer -> consumer.close());
    }
}
