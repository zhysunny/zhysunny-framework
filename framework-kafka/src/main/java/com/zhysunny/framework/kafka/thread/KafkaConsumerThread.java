package com.zhysunny.framework.kafka.thread;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.OutputManage;
import com.zhysunny.framework.kafka.consumer.persist.Persist;
import com.zhysunny.framework.kafka.consumer.service.KafkaConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * 消费者线程
 * @author 章云
 * @date 2019/9/19 21:17
 */
public class KafkaConsumerThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerThread.class);

    private KafkaConsumerService kafkaConsumerService;
    private OutputManage output;
    private Persist persist;

    public KafkaConsumerThread(String name, KafkaConsumerService kafkaConsumerService, Persist persist, OutputManage output) {
        this.setName(name);
        this.kafkaConsumerService = kafkaConsumerService;
        this.output = output;
        this.persist = persist;
    }

    public KafkaConsumerThread(String name, KafkaConsumerService kafkaConsumerService, OutputManage output) {
        this(name, kafkaConsumerService, null, output);
    }

    @Override
    public void run() {
        try {
            LOGGER.info("############# {}启动消费者线程 #############", this.getName());
            kafkaConsumerService.createConsumer();
            List<JSONObject> datas = null;
            // 持久化实现类，目前以文件的方式写死，后续有其他方式可以改成可配置
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                if (persist != null) {
                    // 读取持久化的数据，只有初次加载时会有数据，正常情况下返回空集合
                    datas = persist.read();
                }
                if (datas == null || datas.isEmpty()) {
                    // 拉取kafka数据
                    datas = kafkaConsumerService.poll();
                    if (datas.isEmpty()) {
                        continue;
                    }
                    if (persist != null) {
                        // 持久化数据
                        persist.write(datas);
                    }
                    // 提交offset
                    kafkaConsumerService.commit();
                }
                output.start(datas);
                if (persist != null) {
                    // 持久化文件回滚，相当于删除持久化数据
                    persist.delete();
                }
                datas.clear();
            }
        } catch (Throwable e) {
            LOGGER.error("{} kafka消费线程异常退出", this.getName(), e);
        }
    }

}
