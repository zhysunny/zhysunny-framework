package com.zhysunny.framework.example.kafka.kafka.message;

import com.zhysunny.framework.kafka.producer.service.KafkaProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * kafka生产者接口
 * @author 章云
 * @date 2019/9/19 15:10
 */
public class KafkaProducerMessageServiceImpl extends KafkaProducerService<String, String> {

    public KafkaProducerMessageServiceImpl() {
        this.name = "message";
        this.createProducer("conf/kafka.properties");
    }

}
