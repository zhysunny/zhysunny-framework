package com.zhysunny.framework.kafka.business.output;

import java.util.List;

/**
 * 消费者消费数据的输出接口
 * @author 章云
 * @date 2019/12/27 15:02
 */
public interface Output<E> {

    void output(List<E> datas);

}
