package com.zhysunny.framework.elasticsearch.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.util.DateUtils;
import com.zhysunny.framework.common.util.FileUtils;
import com.zhysunny.framework.elasticsearch.handler.EsExceptionType;
import com.zhysunny.framework.elasticsearch.handler.FailuresHandler;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

/**
 * mapper_parsing_exception异常处理
 * @author 章云
 * @date 2019/12/5 14:43
 */
public class MapperParsingExceptionHandler extends FailuresHandler {

    private FileOutputStream fos;

    public MapperParsingExceptionHandler() {
        super(EsExceptionType.MAPPER_PARSING_EXCEPTION);
    }

    @Override
    public void handler(JSONObject failure, Map<String, JSONObject> datas) throws Exception {
        JSONObject cause = failure.getJSONObject("cause");
        JSONObject causedBy = cause.getJSONObject("caused_by");
        String type = causedBy.getString("type");
        File path = new File(exceptionDir, type);
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, type + ".txt");
        // 判断条件放在写入文件的前面，保证文件一直存在
        if (fos == null) {
            fos = new FileOutputStream(file, true);
        }
        if (file.length() > 10 * 1024 * 1024) {
            // 文件大于10M回滚
            fos.close();
            FileUtils.rollback(file, 10);
            fos = new FileOutputStream(file, true);
        }
        fos.write((DateUtils.getCurrentDateTime() + '\t' + failure.toJSONString() + "\n").getBytes());
    }

}
