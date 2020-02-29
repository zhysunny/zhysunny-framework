package com.zhysunny.framework.example.jmx;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author 章云
 * @date 2020/2/29 18:49
 */
public class JmxClient {
    public static void main(String[] args) throws Exception {
        String jmxURL = "service:jmx:rmi:///jndi/rmi://127.0.0.1:1000/jmxrmi";
        JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
        JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
        MBeanServerConnection mbsc = connector.getMBeanServerConnection();
        // MBean名称
        while (true) {
            ObjectName objectName = new ObjectName("com.zhysunny:type=time,name=process");
            print(mbsc, objectName, "StartTime");
            print(mbsc, objectName, "RunTime");

            objectName = new ObjectName("metrics:name=kafkaConsumer");
            print(mbsc, objectName, "Count");
            print(mbsc, objectName, "RateUnit");
            print(mbsc, objectName, "MeanRate");
            print(mbsc, objectName, "OneMinuteRate");
            print(mbsc, objectName, "FiveMinuteRate");
            print(mbsc, objectName, "FifteenMinuteRate");

            Thread.sleep(5000);
        }
    }

    private static void print(MBeanServerConnection mbsc, ObjectName objectName, String attribute) throws Exception {
        System.out.println(attribute + " : " + mbsc.getAttribute(objectName, attribute));
    }
}
