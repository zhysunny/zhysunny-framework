package com.zhysunny.framework.example.kafka.consumer.file.history;

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
public class KafkaConsumerHistoryServiceImpl extends KafkaConsumerService<String, JSONObject> {

    public KafkaConsumerHistoryServiceImpl() {
        this.name = "history";
    }

    @Override
    public void createConsumer() {
        Properties props = null;
        try {
            props = new PropertiesReader("conf/kafka/servers.properties",
            "conf/kafka/consumer/history.properties").builder().getProps();
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
