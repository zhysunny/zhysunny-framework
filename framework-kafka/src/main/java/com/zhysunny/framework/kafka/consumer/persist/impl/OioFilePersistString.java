package com.zhysunny.framework.kafka.consumer.persist.impl;

import com.zhysunny.framework.common.file.OioFileReadWrite;
import com.zhysunny.framework.kafka.consumer.persist.Persist;

/**
 * 文件持久化
 * @author 章云
 * @date 2019/12/5 8:49
 */
public class OioFilePersistString extends Persist<String> {

    public OioFilePersistString(String name) {
        this(name, 10);
    }

    public OioFilePersistString(String name, int number) {
        super(name, number);
        this.fileReadWrite = new OioFileReadWrite(file);
    }

}
