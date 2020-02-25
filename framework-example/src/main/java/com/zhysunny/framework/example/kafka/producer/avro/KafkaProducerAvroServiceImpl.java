package com.zhysunny.framework.example.kafka.producer.avro;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.properties.PropertiesReader;
import com.zhysunny.framework.kafka.service.KafkaProducerService;
import com.zhysunny.framework.kafka.service.KafkaTopicService;
import org.apache.kafka.clients.producer.KafkaProducer;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * kafka生产者接口
 * @author 章云
 * @date 2019/9/19 15:10
 */
public class KafkaProducerAvroServiceImpl extends KafkaProducerService<String, JSONObject> {

    public KafkaProducerAvroServiceImpl() {
        this.name = "avro";
    }

    @Override
    public void createProducer() {
        Properties props = null;
        try {
            props = new PropertiesReader("conf/kafka/servers.properties",
            "conf/kafka/producer/avro.properties").builder().getProps();
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

    @Override
    public Object write(List<JSONObject> datas) {
        try {
            sendSync(datas);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
