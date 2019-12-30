package com.zhysunny.framework.common.business.input;

import java.io.IOException;
import java.util.List;

/**
 * 生产者生产数据的来源接口
 * @author 章云
 * @date 2019/12/27 15:46
 */
public interface Input<E> {

    List<E> input() throws IOException;

}
