package com.zhysunny.framework.common.business;

import java.io.IOException;
import java.util.List;

/**
 * 输入接口
 * @author 章云
 * @date 2019/12/27 15:46
 */
public interface Input<E> {

    /**
     * 输入
     * @return
     * @throws IOException
     */
    List<E> input() throws IOException;

}