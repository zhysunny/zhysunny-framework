package com.zhysunny.framework.common.file;

import java.io.File;
import java.io.IOException;

/**
 * 持久化kafka数据
 * @author 章云
 * @date 2019/12/5 8:45
 */
public class PersistJson extends FileReadWriteJson {

    protected int number;

    public PersistJson(int number, File file, boolean append) {
        super(file, append);
        this.number = number;
    }

    public PersistJson(File file, boolean append) {
        this(10, file, append);
    }

    public PersistJson(File file) {
        this(10, file, false);
    }

    /**
     * 对持久化数据删除或者回滚
     * @throws IOException
     */
    public final void rollback() {
        super.rollback(number);
    }

}
