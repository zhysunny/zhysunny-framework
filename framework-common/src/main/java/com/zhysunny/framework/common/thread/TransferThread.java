package com.zhysunny.framework.common.thread;

import com.zhysunny.framework.common.business.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 章云
 * @date 2020/1/22 15:05
 */
public class TransferThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferThread.class);

    private Transfer transfer;

    public TransferThread(Transfer transfer) {
        this.transfer = transfer;
    }

    public TransferThread(String name, Transfer transfer) {
        this.setName(name);
        this.transfer = transfer;
    }

    @Override
    public void run() {
        LOGGER.info("############### {} 线程启动 ###############", this.getName());
        try {
            transfer.transfer();
        } catch (Throwable e) {
            LOGGER.error("{} 线程异常退出", this.getName(), e);
        }
    }

}
