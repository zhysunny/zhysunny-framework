package com.zhysunny.framework.kafka.business.output.impl;

import com.zhysunny.framework.kafka.business.output.Output;
import java.util.List;

/**
 * 输出到控制台
 * @author 章云
 * @date 2019/12/27 15:03
 */
public class ConsoleOutput implements Output<Object> {

    @Override
    public void output(List<Object> datas) {
        datas.forEach(System.out::println);
    }

}
