package com.zhysunny.framework.common.jmx;

import java.text.ParseException;

/**
 * @author 章云
 * @date 2020/2/29 18:44
 */
public interface ProcessTimeJmxMBean {

    /**
     * 程序启动时间
     * @return
     */
    String getStartTime();

    /**
     * 程序运行时间
     * @return
     * @throws ParseException
     */
    String getRunTime() throws ParseException;
}
