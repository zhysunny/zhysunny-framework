package com.zhysunny.framework.example.kafka.console.avro;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.properties.PropertiesReader;
import com.zhysunny.framework.kafka.service.KafkaConsumerService;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * kafka 消费者
 * @author 章云
 * @date 2019/9/19 15:07
 */
public class KafkaConsumerAvroServiceImpl extends KafkaConsumerService<String, JSONObject> {

    public KafkaConsumerAvroServiceImpl() {
        this.name = "avro";
    }

    @Override
    public void createConsumer() {
        Properties props = null;
        try {
            props = new PropertiesReader("conf/kafka/servers.properties",
            "conf/kafka/consumer/avro.properties").builder().getProps();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 消费组
        consumer = new KafkaConsumer(props);
        // 绑定topic
        List<String> topics = new ArrayList<>();
        topics.add(props.getProperty("topic.name"));
        consumer.subscribe(topics);
    }

}
