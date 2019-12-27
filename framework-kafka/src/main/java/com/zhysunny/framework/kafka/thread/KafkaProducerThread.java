package com.zhysunny.framework.kafka.thread;

import com.zhysunny.framework.kafka.business.input.Input;
import com.zhysunny.framework.kafka.producer.service.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * 生产者线程
 * @author 章云
 * @date 2019/9/19 21:17
 */
public class KafkaProducerThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerThread.class);

    private KafkaProducerService kafkaProducerService;
    private Input input;

    public KafkaProducerThread(String name, KafkaProducerService kafkaProducerService, Input input) {
        this.setName(name);
        this.kafkaProducerService = kafkaProducerService;
        this.input = input;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("############# {}启动生产者线程 #############", this.getName());
            List<?> datas = input.input();
            kafkaProducerService.send(datas);
        } catch (Throwable e) {
            LOGGER.error("{} kafka消费线程异常退出", this.getName(), e);
        }
    }

}
