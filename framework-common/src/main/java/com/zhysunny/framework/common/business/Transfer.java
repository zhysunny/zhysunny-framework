package com.zhysunny.framework.common.business;

import java.io.IOException;
import java.util.List;

/**
 * 数据传输
 * @author 章云
 * @date 2020/1/14 10:11
 */
public interface Transfer<E> {

    /**
     * 数据输入
     * @return
     * @throws IOException
     */
    List<E> input() throws IOException;

    /**
     * 数据输出
     * @param datas
     * @throws IOException
     */
    void output(List<E> datas) throws IOException;

}
