package com.zhysunny.framework.elasticsearch.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.elasticsearch.EsExceptionType;
import com.zhysunny.framework.elasticsearch.handler.FailuresHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

/**
 * invalid_index_name_exception异常处理
 * @author 章云
 * @date 2019/12/30 16:06
 */
public class InvalidIndexNameExceptionHandler extends FailuresHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidIndexNameExceptionHandler.class);

    public InvalidIndexNameExceptionHandler() {
        super(EsExceptionType.INVALID_INDEX_NAME_EXCEPTION);
    }

    @Override
    public void handler(JSONObject failure, Map<String, JSONObject> datas) throws Exception {
        LOGGER.error("更新删除不可以使用通配符索引名：" + failure.getString("index"));
        System.exit(1);
    }

}
