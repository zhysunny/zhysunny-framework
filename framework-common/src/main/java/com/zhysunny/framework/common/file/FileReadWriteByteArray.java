package com.zhysunny.framework.common.file;

import java.io.File;

/**
 * NIO读写
 * @author 章云
 * @date 2019/12/27 20:48
 */
public class FileReadWriteByteArray extends BaseFileReadWrite<byte[]> {

    public FileReadWriteByteArray(File file, boolean append) {
        super(file, append);
    }

    public FileReadWriteByteArray(File file) {
        super(file);
    }

    public FileReadWriteByteArray(String filepath) {
        super(filepath);
    }

    public FileReadWriteByteArray(String filepath, boolean append) {
        super(filepath, append);
    }

    @Override
    protected final byte[] toAny(String data) {
        return data.getBytes();
    }

    @Override
    protected final String toString(byte[] data) {
        return new String(data);
    }
}
