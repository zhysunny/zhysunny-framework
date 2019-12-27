package com.zhysunny.framework.kafka.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 当程序终止时执行的线程(kill -9 强制终止不执行)
 * @author 章云
 * @date 2019/9/19 14:34
 */
public class ShutdownHookThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownHookThread.class);

    @Override
    public void run() {
        // 执行一些程序终止前的操作
        LOGGER.info("########################## Kafka Client Shutdown #############################");
    }

}
