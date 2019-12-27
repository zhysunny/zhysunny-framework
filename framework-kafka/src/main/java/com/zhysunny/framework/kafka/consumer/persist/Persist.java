package com.zhysunny.framework.kafka.consumer.persist;

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
public abstract class Persist<E> {

    protected String name;
    protected int number;
    protected File file;
    protected FileReadWrite fileReadWrite;

    public Persist(String name, int number) {
        this.name = name;
        this.number = number;
        this.file = getFile();
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

    /**
     * 获取持久化文件
     * @return
     */
    private final File getFile() {
        File dir = new File("tmp", name);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, name + ".txt");
    }

}
