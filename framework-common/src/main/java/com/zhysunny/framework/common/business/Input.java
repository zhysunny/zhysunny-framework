package com.zhysunny.framework.common.business;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * 输入接口
 * @author 章云
 * @date 2019/12/27 15:46
 */
public interface Input<E> extends Closeable {

    /**
     * 输入
     * @return
     * @throws IOException
     */
    List<E> input() throws IOException;

    /**
     * 获取业务名称
     * @return
     */
    String getName();

}
