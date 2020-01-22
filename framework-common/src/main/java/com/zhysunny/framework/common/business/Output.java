package com.zhysunny.framework.common.business;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 输出接口
 * @author 章云
 * @date 2019/12/27 21:22
 */
public interface Output<E> {

    /**
     * 输出
     * @param datas
     * @return
     * @throws IOException
     */
    Object write(List<E> datas) throws IOException;

    /**
     * 输出
     * @param datas
     * @return
     * @throws IOException
     */
    Object write(Map<String, E> datas) throws IOException;

}