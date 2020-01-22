package com.zhysunny.framework.common.thread;

import com.zhysunny.framework.common.business.ShutdownHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;

/**
 * 当程序终止时执行的线程(kill -9 强制终止不执行)
 * @author 章云
 * @date 2019/9/19 14:34
 */
public class ShutdownHookThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShutdownHookThread.class);

    private ShutdownHook[] hooks;

    public ShutdownHookThread(ShutdownHook... hooks) {
        this.hooks = hooks;
    }

    @Override
    public void run() {
        // 执行一些程序终止前的操作
        if (hooks != null && hooks.length > 0) {
            Arrays.stream(hooks).forEach(hook -> hook.shutdown());
        }
        LOGGER.info("########################## Shutdown #############################");
    }

}
