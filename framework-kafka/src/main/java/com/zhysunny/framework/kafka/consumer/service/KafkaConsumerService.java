package com.zhysunny.framework.kafka.consumer.service;

import com.zhysunny.framework.common.business.output.Output;
import com.zhysunny.framework.common.util.FileUtils;
import com.zhysunny.framework.kafka.constant.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * kafka消费者接口
 * @author 章云
 * @date 2019/9/19 15:10
 */
public abstract class KafkaConsumerService<K, V> {

    private static final long LEASE_TIME = Duration.ofSeconds(60).toMillis();
    private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();
    protected KafkaConsumer<K, V> consumer;
    protected String name;
    protected Properties props;

    /**
     * 用于单元测试
     * @param consumer
     */
    public void setConsumer(KafkaConsumer consumer) {
        this.consumer = consumer;
    }

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
        consumer.commitSync();
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

    /**
     * 消费者的输出
     * @param datas
     * @param outputs
     */
    public final void output(List<?> datas, Output[] outputs) {
        for (Output output : outputs) {
            try {
                output.output(datas);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
