package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.Output;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 输出到控制台
 * @author 章云
 * @date 2019/12/27 15:03
 */
public class ConsoleOutput implements Output<Object> {

    @Override
    public Object write(List<Object> datas) {
        datas.forEach(System.out::println);
        return null;
    }

    @Override
    public Object write(Map<String, Object> datas) {
        datas.entrySet().forEach(System.out::println);
        return null;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void close() {
    }
}
