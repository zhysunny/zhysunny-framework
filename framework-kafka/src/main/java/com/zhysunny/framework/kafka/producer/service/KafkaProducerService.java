package com.zhysunny.framework.kafka.producer.service;

import com.zhysunny.framework.common.util.FileUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * kafka生产者接口
 * @author 章云
 * @date 2019/9/19 15:10
 */
public abstract class KafkaProducerService<K, V> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();
    protected KafkaProducer<K, V> producer;
    protected String name;
    protected Properties props;
    protected String topic;

    public KafkaProducer getProducer() {
        return producer;
    }

    public String getName() {
        return name;
    }

    /**
     * 创建生产者
     */
    protected void createProducer(String resource) {
        loadConfig(resource);
        producer = new KafkaProducer<>(props);
        topic = props.getProperty("topic.name");
    }

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

    /**
     * 读取配置
     * @param resource
     */
    protected final void loadConfig(String resource) {
        props = new Properties();
        InputStream is = null;
        try {
            is = CLASS_LOADER.getResourceAsStream(resource);
            props.load(is);
        } catch (IOException e) {
        } finally {
            FileUtils.close(is);
        }
    }

}
