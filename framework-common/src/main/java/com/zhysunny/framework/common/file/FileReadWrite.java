package com.zhysunny.framework.common.file;

import java.io.IOException;
import java.util.List;

/**
 * 文件读写
 * @author 章云
 * @date 2019/12/27 20:48
 */
public interface FileReadWrite<E> {

    /**
     * 文件读
     * @return
     */
    List<E> read() throws IOException;

    /**
     * 文件写，默认不追加
     * @param datas
     * @throws IOException
     */
    default void write(List<E> datas) throws IOException {
        write(datas, false);
    }

    /**
     * 是否追加写
     * @param datas
     * @param append
     * @throws IOException
     */
    void write(List<E> datas, boolean append) throws IOException;

}
