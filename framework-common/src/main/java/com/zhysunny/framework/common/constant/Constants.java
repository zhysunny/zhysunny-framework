package com.zhysunny.framework.common.constant;

import com.zhysunny.framework.common.conf.Configuration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 常量类
 * @author 章云
 * @date 2019/12/25 19:45
 */
public class Constants {

    protected static final Configuration CONF = Configuration.getInstance();

    public static final AtomicInteger TOTAL = new AtomicInteger(0);

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final AtomicInteger SUCCESS_COUNT = new AtomicInteger(0);

    public static final AtomicInteger ERROR_COUNT = new AtomicInteger(0);

}
