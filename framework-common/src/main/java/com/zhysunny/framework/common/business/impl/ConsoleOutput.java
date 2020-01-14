package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.Transfer;
import java.util.List;

/**
 * 输出到控制台
 * @author 章云
 * @date 2019/12/27 15:03
 */
public class ConsoleOutput implements Transfer<Object> {

    @Override
    public List<Object> input() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void output(List<Object> datas) {
        datas.forEach(System.out::println);
    }

}
