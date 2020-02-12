package com.zhysunny.framework.common.file;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 文件读写
 * @author 章云
 * @date 2019/12/27 20:48
 */
public interface FileReadWrite<E> extends Closeable {

    /**
     * 文件读
     * @return
     * @throws IOException
     */
    List<E> read() throws IOException;

    /**
     * 只读一行
     * @return
     * @throws IOException
     */
    E readLine() throws IOException;

    /**
     * 文件写
     * @param datas
     * @return
     * @throws IOException
     */
    Object write(List<E> datas) throws IOException;

    /**
     * 文件写
     * @param datas
     * @return
     * @throws IOException
     */
    Object write(Map<String, E> datas) throws IOException;

    /**
     * 只写一行
     * @param data
     * @return
     * @throws IOException
     */
    Object write(E data) throws IOException;

    /**
     * 针对写数据的刷盘
     * @throws IOException
     */
    void flush() throws IOException;

    /**
     * 文件重置操作，针对追加写，到一定程度后重置文件
     * @throws IOException
     */
    void reset() throws IOException;

    /**
     * 获取当前操作文件
     * @return
     */
    File getFile();

}
