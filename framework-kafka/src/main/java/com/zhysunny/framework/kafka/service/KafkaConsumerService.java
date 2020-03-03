package com.zhysunny.framework.kafka.service;

import com.zhysunny.framework.common.business.Input;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.ArrayList;
import java.util.List;

/**
 * kafka消费者接口
 * @author 章云
 * @date 2019/9/19 15:10
 */
public abstract class KafkaConsumerService<K, V> implements Input<V> {

    private static final long LEASE_TIME = 60000;
    protected KafkaConsumer<K, V> consumer;
    protected String name;
    protected boolean running = true;

    public void setConsumer(KafkaConsumer consumer) {
        this.consumer = consumer;
    }

    public KafkaConsumer<K, V> getConsumer() {
        return consumer;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * 创建消费者
     */
    public abstract void createConsumer();

    /**
     * 拉取kafka数据
     */
    public final List<V> poll() {
        final List<V> datas = new ArrayList<>();
        ConsumerRecords<K, V> records = consumer.poll(LEASE_TIME);
        records.forEach(record -> datas.add(record.value()));
        return datas;
    }

    /**
     * 提交offset
     */
    public final void commit() {
        consumer.commitSync();
    }

    @Override
    public List<V> read() {
        if (consumer == null) {
            createConsumer();
        }
        return poll();
    }

    @Override
    public void close() {
        if (consumer != null) {
            consumer.close();
        }
        running = false;
    }

}
