package com.zhysunny.framework.example.metrics;

import com.codahale.metrics.*;
import com.zhysunny.framework.metrics.Metrics;
import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author 章云
 * @date 2020/2/12 15:52
 */
public class MetricsTest {
    public static void main(String[] args) {
        Metrics instance = Metrics.getInstance();
        // 度量器
        // 计数器，只打印数值
        Counter counter = instance.counter("counter");
        // 计数器，可以打印速率
        Meter meter = instance.meter("meter");
        // 数据的分布情况
        Histogram histogram = instance.histogram("histogram");
        // 基于Histograms和Meters
        Timer timer = instance.timer("timer");

        // 报告
        // 控制台
        instance.addReporter(ConsoleReporter.forRegistry(instance).build());
        // slf4j
        instance.addReporter(Slf4jReporter.forRegistry(instance).build());
        // csv
        File dir = new File("tmp");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        instance.addReporter(CsvReporter.forRegistry(instance).build(dir));
        // jmx
        // -Djava.rmi.server.hostname=127.0.0.1 -Dcom.sun.management.jmxremote.port=1000 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
        instance.addReporter(JmxReporter.forRegistry(instance).build());

        // 开始报告
        instance.reporter(5, TimeUnit.SECONDS);
        Random random = new Random();
        while (true) {
            int num = random.nextInt(32);
            counter.inc(num);
            meter.mark(num);
            histogram.update(num);
            timer.update(num, TimeUnit.MILLISECONDS);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
