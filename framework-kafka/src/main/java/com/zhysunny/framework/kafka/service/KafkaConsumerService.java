package com.zhysunny.framework.kafka.service;

import com.zhysunny.framework.common.business.Input;
import com.zhysunny.framework.kafka.constant.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * kafka消费者接口
 * @author 章云
 * @date 2019/9/19 15:10
 */
public abstract class KafkaConsumerService<K, V> implements Input<V> {

    private static final long LEASE_TIME = Duration.ofSeconds(60).toMillis();
    protected KafkaConsumer<K, V> consumer;
    protected String name;
    private boolean commit = true;

    public void setCommit(boolean commit) {
        this.commit = commit;
    }

    /**
     * 用于单元测试
     * @param consumer
     */
    public void setConsumer(KafkaConsumer consumer) {
        this.consumer = consumer;
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
        KafkaConstants.TOTAL.addAndGet(datas.size());
        return datas;
    }

    /**
     * 提交offset
     */
    public final void commit() {
        if (commit) {
            consumer.commitSync();
        }
    }

    @Override
    public List<V> input() throws IOException {
        if (consumer == null) {
            createConsumer();
        }
        return poll();
    }

}
