package com.zhysunny.framework.kafka.consumer.persist.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.kafka.consumer.persist.Persist;

/**
 * nio文件持久化
 * @author 章云
 * @date 2019/12/5 8:49
 */
public class NioFilePersistJson extends Persist<JSONObject> {

    public NioFilePersistJson(String name) {
        this(name, 10);
    }

    public NioFilePersistJson(String name, int number) {
        super(name, number);
        this.fileReadWrite = new NioFileReadWriteJson(file);
    }

}
