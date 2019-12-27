package com.zhysunny.framework.kafka.consumer.persist.impl;

import com.zhysunny.framework.common.file.NioFileReadWrite;
import com.zhysunny.framework.kafka.consumer.persist.Persist;

/**
 * nio文件持久化
 * @author 章云
 * @date 2019/12/5 8:49
 */
public class NioFilePersistString extends Persist<String> {

    public NioFilePersistString(String name) {
        this(name, 10);
    }

    public NioFilePersistString(String name, int number) {
        super(name, number);
        this.fileReadWrite = new NioFileReadWrite(file);
    }

}
