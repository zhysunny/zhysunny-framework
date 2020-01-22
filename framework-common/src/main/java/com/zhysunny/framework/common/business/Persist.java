package com.zhysunny.framework.common.business;

import com.zhysunny.framework.common.file.FileReadWrite;
import com.zhysunny.framework.common.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 持久化kafka数据
 * @author 章云
 * @date 2019/12/5 8:45
 */
public class Persist<E> {

    protected int number;
    private File file;
    protected FileReadWrite fileReadWrite;

    public Persist(int number, FileReadWrite fileReadWrite) {
        this.number = number;
        this.fileReadWrite = fileReadWrite;
        this.file = fileReadWrite.getFile();
    }

    public Persist(FileReadWrite fileReadWrite) {
        this(10, fileReadWrite);
    }

    /**
     * 读取持久化数据
     * @return
     * @throws IOException
     */
    public final List<E> read() throws IOException {
        return fileReadWrite.read();
    }

    /**
     * 写入持久化数据
     * @param datas 数据
     * @throws IOException
     */
    public final void write(List<E> datas) throws IOException {
        fileReadWrite.write(datas);
    }

    /**
     * 对持久化数据删除或者回滚
     * @throws IOException
     */
    public final void delete() {
        FileUtils.rollback(file, number);
    }

}
