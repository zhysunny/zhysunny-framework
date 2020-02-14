package com.zhysunny.framework.example.performance.kafka.consumer;

import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Slf4jReporter;
import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.thread.ShutdownHookThread;
import com.zhysunny.framework.common.thread.TransferThread;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.example.kafka.KafkaConsumerPerformanceServiceImpl;
import com.zhysunny.framework.kafka.service.KafkaConsumerService;
import com.zhysunny.framework.metrics.Metrics;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * kafka消费者性能测试
 * @author 章云
 * @date 2020/2/14 10:37
 */
public class PerformanceConsumerMain {
    public static void main(String[] args) {
        // 读取参数
        int threadNum = 10;
        if (args != null && args.length > 0) {
            threadNum = Integer.parseInt(args[0]);
        }
        // 设置Metrics监听
        Metrics metrics = Metrics.getInstance();
        String name = "performance.consumer";
        Meter meter = metrics.meter(name);
        File dir = new File("metrics");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        metrics.addReporter(CsvReporter.forRegistry(metrics).build(dir));
        metrics.addReporter(Slf4jReporter.forRegistry(metrics).build());
        metrics.reporter(10, TimeUnit.SECONDS);
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        List<KafkaConsumerService> consumers = new ArrayList<>(threadNum);
        List<Transfer> transfers = new ArrayList<>(threadNum);
        for (int i = 0; i < threadNum; i++) {
            KafkaConsumerService kafkaConsumerService = new KafkaConsumerPerformanceServiceImpl();
            kafkaConsumerService.setCommit(false);
            Transfer transfer = new ConsumerTransfer(kafkaConsumerService, meter);
            threadPools.addThread(new TransferThread(name + i, transfer));
            consumers.add(kafkaConsumerService);
            transfers.add(transfer);
        }
        // 设置宕机处理
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread(new ConsumerShutdownHook(consumers, transfers)));
    }
}
