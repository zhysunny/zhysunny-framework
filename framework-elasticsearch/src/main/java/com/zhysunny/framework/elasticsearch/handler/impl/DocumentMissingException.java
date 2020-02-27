package com.zhysunny.framework.elasticsearch.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.elasticsearch.handler.EsExceptionType;
import com.zhysunny.framework.elasticsearch.handler.FailuresHandler;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * document_missing_exception异常处理
 * @author 章云
 * @date 2020/2/27 15:02
 */
public class DocumentMissingException extends FailuresHandler {

    private FileOutputStream fos;

    public DocumentMissingException() {
        super(EsExceptionType.DOCUMENT_MISSING_EXCEPTION);
    }

    @Override
    public void handler(JSONObject failure, Map<String, JSONObject> datas) throws Exception {
        // 使用upsert避免这个异常，不严谨，待优化
    }

}
