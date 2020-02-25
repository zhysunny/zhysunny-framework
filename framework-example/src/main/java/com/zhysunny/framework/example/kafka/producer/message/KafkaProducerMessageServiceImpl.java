package com.zhysunny.framework.example.kafka.producer.message;

import com.zhysunny.framework.common.properties.PropertiesReader;
import com.zhysunny.framework.kafka.service.KafkaProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import java.util.Properties;

/**
 * kafka生产者接口
 * @author 章云
 * @date 2019/9/19 15:10
 */
public class KafkaProducerMessageServiceImpl extends KafkaProducerService<String, String> {

    public KafkaProducerMessageServiceImpl() {
        this.name = "message";
    }

    @Override
    public void createProducer() {
        Properties props = null;
        try {
            props = new PropertiesReader("conf/kafka/servers.properties",
            "conf/kafka/producer/message.properties").builder().getProps();
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer = new KafkaProducer<>(props);
        topic = props.getProperty("topic.name");
    }

}
