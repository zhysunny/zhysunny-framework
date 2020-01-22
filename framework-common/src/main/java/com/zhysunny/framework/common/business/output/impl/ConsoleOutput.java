package com.zhysunny.framework.common.business.output.impl;

import com.zhysunny.framework.common.business.output.Output;
import java.util.List;
import java.util.Map;

/**
 * 输出到控制台
 * @author 章云
 * @date 2019/12/27 15:03
 */
public class ConsoleOutput implements Output<Object> {

    @Override
    public void write(List<Object> datas) {
        datas.forEach(System.out::println);
    }

    @Override
    public void write(Map<String, Object> datas) {
        datas.entrySet().forEach(System.out::println);
    }

}
