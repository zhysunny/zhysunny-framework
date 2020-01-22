package com.zhysunny.framework.elasticsearch.thread;

import com.zhysunny.framework.common.thread.Hearbeat;
import com.zhysunny.framework.elasticsearch.constant.EsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 章云
 * @date 2020/1/20 10:21
 */
public class ElasticsearchHeartbeat implements Hearbeat {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchHeartbeat.class);

    @Override
    public void execute() {
        LOGGER.info("已完成：{}", EsConstants.TOTAL.get());
    }

}
