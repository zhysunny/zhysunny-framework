package com.zhysunny.framework.common.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;

/**
 * 阻塞IO读写
 * @author 章云
 * @date 2019/12/27 20:48
 */
public class FileReadWriteJson extends BaseFileReadWrite<JSONObject> {

    public FileReadWriteJson(File file, boolean append) {
        super(file, append);
    }

    public FileReadWriteJson(File file) {
        super(file);
    }

    public FileReadWriteJson(String filepath) {
        super(filepath);
    }

    public FileReadWriteJson(String filepath, boolean append) {
        super(filepath, append);
    }

    @Override
    protected JSONObject toAny(String data) {
        return JSON.parseObject(data);
    }

    @Override
    protected String toString(JSONObject json) {
        return json.toJSONString();
    }
}
