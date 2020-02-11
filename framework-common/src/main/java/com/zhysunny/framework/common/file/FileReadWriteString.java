package com.zhysunny.framework.common.file;

import java.io.File;

/**
 * NIO读写
 * @author 章云
 * @date 2019/12/27 20:48
 */
public class FileReadWriteString extends BaseFileReadWrite<String> {

    public FileReadWriteString(File file, boolean append) {
        super(file, append);
    }

    public FileReadWriteString(File file) {
        super(file);
    }

    public FileReadWriteString(String filepath) {
        super(filepath);
    }

    public FileReadWriteString(String filepath, boolean append) {
        super(filepath, append);
    }

    @Override
    protected final String toAny(String data) {
        return data;
    }

    @Override
    protected final String toString(String data) {
        return data;
    }
}
