package com.zhysunny.framework.common.thread;

import com.zhysunny.framework.common.business.Heartbeat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

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
    /**
     * 心跳任务
     */
    private Heartbeat[] hearbeats;

    public HeartbeatThread(long interval, Heartbeat... hearbeats) {
        // 设置守护线程，当非守护线程线程全部结束时，该线程自动结束
        this.setDaemon(true);
        this.hearbeats = hearbeats;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            if (hearbeats == null || hearbeats.length == 0) {
                break;
            }
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                LOGGER.error("{}中断异常", this.getName());
                Thread.currentThread().interrupt();
            }
            Arrays.stream(hearbeats).forEach(hearbeat -> hearbeat.execute());
        }
    }

}
