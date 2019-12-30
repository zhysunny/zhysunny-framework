package com.zhysunny.framework.common.business.output;

import java.io.IOException;
import java.util.List;

/**
 * 消费者数据输出接口
 * @author 章云
 * @date 2019/12/27 21:22
 */
public interface Output<E> {

    /**
     * 输出
     * @param datas
     * @throws IOException
     */
    void output(List<E> datas) throws IOException;

}
