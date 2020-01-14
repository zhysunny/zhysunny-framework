package com.zhysunny.framework.kafka.producer.service;

import com.zhysunny.framework.kafka.KafkaService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * kafka生产者接口
 * @author 章云
 * @date 2019/9/19 15:10
 */
public abstract class KafkaProducerService<K, V> implements KafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    protected KafkaProducer<K, V> producer;
    protected String name;
    protected String topic;

    public String getName() {
        return name;
    }

    /**
     * 创建消费者
     */
    public abstract void createProducer();

    /**
     * 发送数据到kafka
     */
    public final void send(List<V> datas) {
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
     * 关闭生产者
     */
    public final void close() {
        producer.close();
    }

}
