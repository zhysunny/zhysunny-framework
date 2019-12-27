package com.zhysunny.framework.kafka.consumer.persist;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.List;

/**
 * 持久化kafka数据
 * @author 章云
 * @date 2019/12/5 8:45
 */
public interface Persist<E> {

    /**
     * 读取持久化数据
     * @return
     * @throws IOException
     */
    List<E> read() throws IOException;

    /**
     * 写入持久化数据
     * @param datas 数据
     * @throws IOException
     */
    void write(List<E> datas) throws IOException;

    /**
     * 对持久化数据删除或者回滚
     * @throws IOException
     */
    void delete() throws IOException;

}
