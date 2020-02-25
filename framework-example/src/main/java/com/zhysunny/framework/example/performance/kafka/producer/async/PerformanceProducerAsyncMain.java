package com.zhysunny.framework.example.performance.kafka.producer.async;

import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Slf4jReporter;
import com.zhysunny.framework.common.business.Input;
import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.business.impl.StaticArrayInput;
import com.zhysunny.framework.common.thread.ShutdownHookThread;
import com.zhysunny.framework.common.thread.TransferThread;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.example.performance.kafka.producer.StaticArrayServiceImpl;
import com.zhysunny.framework.example.kafka.producer.ProducerShutdownHook;
import com.zhysunny.framework.kafka.service.KafkaProducerService;
import com.zhysunny.framework.metrics.Metrics;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * kafka生产者性能测试
 * @author 章云
 * @date 2020/2/14 10:37
 */
public class PerformanceProducerAsyncMain {
    public static void main(String[] args) {
        // 读取参数
        int threadNum = 10;
        if (args != null && args.length > 0) {
            threadNum = Integer.parseInt(args[0]);
        }
        int size = 1024;
        if (args != null && args.length > 1) {
            size = Integer.parseInt(args[1]);
        }
        // 设置Metrics监听
        Metrics metrics = Metrics.getInstance();
        String name = "performance.producer.async";
        Meter meter = metrics.meter(name);
        File dir = new File("metrics");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        metrics.addReporter(CsvReporter.forRegistry(metrics).build(dir));
        metrics.addReporter(Slf4jReporter.forRegistry(metrics).build());
        metrics.reporter(10, TimeUnit.SECONDS);
        // 静态数据，节省创建数据的时间
        Input input = new StaticArrayInput(size);
        KafkaProducerService<String, byte[]> kafkaProducerService = new StaticArrayServiceImpl();
        kafkaProducerService.createProducer();
        String topic = kafkaProducerService.getTopic();
        Transfer transfer = new ProducerAsyncTransfer(input, topic, meter, kafkaProducerService);
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        for (int i = 0; i < threadNum; i++) {
            threadPools.addThread(new TransferThread(name + i, transfer));
        }
        // 设置宕机处理
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread(new ProducerShutdownHook(kafkaProducerService, transfer)));
    }
}
