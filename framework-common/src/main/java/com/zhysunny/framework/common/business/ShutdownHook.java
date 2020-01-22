package com.zhysunny.framework.common.business;

/**
 * 程序终止任务
 * @author 章云
 * @date 2020/1/20 10:03
 */
public interface ShutdownHook {

    /**
     * 停止进程前的操作
     */
    void shutdown();

}
