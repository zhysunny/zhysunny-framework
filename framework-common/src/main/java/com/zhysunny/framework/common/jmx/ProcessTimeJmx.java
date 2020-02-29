package com.zhysunny.framework.common.jmx;

import com.zhysunny.framework.common.util.DateUtils;
import java.text.ParseException;

/**
 * @author 章云
 * @date 2020/2/29 18:43
 */
public class ProcessTimeJmx implements ProcessTimeJmxMBean {

    private final String startTime;
    private String runTime;

    public ProcessTimeJmx() {
        this.startTime = DateUtils.getCurrentDateTime();
    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public String getRunTime() throws ParseException {
        long start = DateUtils.getLongOfString(startTime, DateUtils.DATE_FORMAT_DATETIME);
        long run = System.currentTimeMillis() - start;
        return DateUtils.getLengthOfTime(run / 1000);
    }

}
