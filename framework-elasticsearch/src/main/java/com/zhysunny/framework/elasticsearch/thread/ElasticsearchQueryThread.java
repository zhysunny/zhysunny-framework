package com.zhysunny.framework.elasticsearch.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ES查询线程
 * @author 章云
 * @date 2019/9/19 21:17
 */
public class ElasticsearchQueryThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchQueryThread.class);

    public ElasticsearchQueryThread(String name) {
        this.setName(name);
    }

    @Override
    public void run() {
        try {
            LOGGER.info("############# {}启动ES查询线程 #############", this.getName());
        } catch (Throwable e) {
            LOGGER.error("{} ES查询线程异常退出", this.getName(), e);
        }
    }

}
