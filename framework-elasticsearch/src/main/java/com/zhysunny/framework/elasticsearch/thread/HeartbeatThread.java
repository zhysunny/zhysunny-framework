package com.zhysunny.framework.elasticsearch.thread;

import com.zhysunny.framework.elasticsearch.constant.EsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 心跳线程，用于检测程序运行状态
 * @author 章云
 * @date 2019/9/19 14:25
 */
public class HeartbeatThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatThread.class);

    /**
     * 心跳时间，单位毫秒
     */
    private long interval;

    public HeartbeatThread(long interval) {
        // 设置守护线程，当非守护线程线程全部结束时，该线程自动结束
        this.setDaemon(true);
        this.setName(this.getClass().getSimpleName());
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                LOGGER.error("{}中断异常", this.getName());
                Thread.currentThread().interrupt();
            }
            LOGGER.info("已消费：{}", EsConstants.TOTAL.get());
        }
    }

}
