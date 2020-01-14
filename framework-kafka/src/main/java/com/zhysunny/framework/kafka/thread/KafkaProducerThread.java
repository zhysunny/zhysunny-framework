package com.zhysunny.framework.kafka.thread;

import com.zhysunny.framework.common.business.Transfer;
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
    private Transfer transfer;

    public KafkaProducerThread(String name, KafkaProducerService kafkaProducerService, Transfer transfer) {
        this.setName(name);
        this.kafkaProducerService = kafkaProducerService;
        this.transfer = transfer;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("############# {}启动生产者线程 #############", this.getName());
            kafkaProducerService.createProducer();
            List<?> datas = transfer.input();
            kafkaProducerService.send(datas);
            LOGGER.info("############# {}生产者线程结束 #############", this.getName());
        } catch (Throwable e) {
            LOGGER.error("{} kafka生产线程异常退出", this.getName(), e);
        }
    }

}
