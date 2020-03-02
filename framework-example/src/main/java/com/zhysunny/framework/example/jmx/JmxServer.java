package com.zhysunny.framework.example.jmx;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.zhysunny.framework.common.jmx.ProcessTimeJmx;
import com.zhysunny.framework.metrics.Metrics;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Random;

/**
 * @author 章云
 * @date 2020/2/29 18:47
 */
public class JmxServer {

    public static void main(String[] args) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        // ProcessTimeJmx
        ObjectName object = new ObjectName("com.zhysunny:type=time,name=process");
        ProcessTimeJmx processTime = new ProcessTimeJmx();
        server.registerMBean(processTime, object);

        Metrics metrics = Metrics.getInstance();
        Meter meter = metrics.meter("kafkaConsumer");
        JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
        reporter.start();

        Random random = new Random();
        while (true) {
            meter.mark(random.nextInt(10));
            Thread.sleep(5000);
        }
        // -Djava.rmi.server.hostname=127.0.0.1 -Dcom.sun.management.jmxremote.port=1000 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
    }

}
