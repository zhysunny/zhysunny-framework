package com.zhysunny.framework.kafka.service;

import com.zhysunny.framework.common.business.Output;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * kafka生产者接口
 * @author 章云
 * @date 2019/9/19 15:10
 */
public abstract class KafkaProducerService<K, V> implements Output<V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    protected KafkaProducer<K, V> producer;
    protected String name;
    protected String topic;

    @Override
    public String getName() {
        return name;
    }

    public String getTopic() {
        return topic;
    }

    /**
     * 创建消费者
     */
    public abstract void createProducer();

    /**
     * 同步发送
     * @param datas
     */
    public final void sendSync(List<V> datas) {
        ProducerRecord<K, V> record;
        for (V value : datas) {
            record = new ProducerRecord<>(topic, value);
            try {
                producer.send(record).get();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("kafka生产者发送异常record：{}", record.toString(), e);
            }
        }
    }

    /**
     * 同步发送
     * @param datas
     */
    public final void sendSync(Map<K, V> datas) {
        ProducerRecord<K, V> record;
        for (Map.Entry<K, V> entry : datas.entrySet()) {
            record = new ProducerRecord<>(topic, entry.getKey(), entry.getValue());
            try {
                producer.send(record).get();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.error("kafka生产者发送异常record：{}", record.toString(), e);
            }
        }
    }

    /**
     * 异步发送
     * @param datas
     * @param callback
     */
    public final void sendAsync(List<V> datas, Callback callback) {
        ProducerRecord<K, V> record;
        for (V value : datas) {
            record = new ProducerRecord<>(topic, value);
            producer.send(record, callback);
        }
    }

    /**
     * 异步发送
     * @param datas
     * @param callback
     */
    public final void sendAsync(Map<K, V> datas, Callback callback) {
        ProducerRecord<K, V> record;
        for (Map.Entry<K, V> entry : datas.entrySet()) {
            record = new ProducerRecord<>(topic, entry.getKey(), entry.getValue());
            producer.send(record, callback);
        }
    }

    /**
     * 关闭生产者
     */
    public final void close() {
        producer.close();
    }

    @Override
    public Object write(Map<String, V> datas) {
        return null;
    }

    @Override
    public Object write(List<V> datas) {
        return null;
    }

}
