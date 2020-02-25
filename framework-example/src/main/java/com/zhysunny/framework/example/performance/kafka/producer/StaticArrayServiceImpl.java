package com.zhysunny.framework.example.performance.kafka.producer;

import com.zhysunny.framework.common.properties.PropertiesReader;
import com.zhysunny.framework.kafka.service.KafkaProducerService;
import com.zhysunny.framework.kafka.service.KafkaTopicService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author 章云
 * @date 2020/2/14 9:53
 */
public class StaticArrayServiceImpl extends KafkaProducerService<String, byte[]> {

    public StaticArrayServiceImpl() {
        this.name = "performance";
    }

    @Override
    public void createProducer() {
        Properties props = null;
        try {
            props = new PropertiesReader("conf/kafka/servers.properties",
            "conf/kafka/producer/performance.properties")
            .builder()
            .getProps();
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer = new KafkaProducer<>(props);
        topic = props.getProperty("topic.name");
        // 创建topic
        String zkUrl = props.getProperty("zk.connection.servers");
        KafkaTopicService topicService = new KafkaTopicService(zkUrl);
        if (!topicService.existTopic(topic)) {
            topicService.createTopic(topic, 10, 1);
        }
        topicService.close();
    }

    public void sendAsync(ProducerRecord<String, byte[]> record) {
        producer.send(record);
    }

    public void sendSync(ProducerRecord<String, byte[]> record) {
        try {
            producer.send(record).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
