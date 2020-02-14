package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.Input;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 随机字符串
 * @author 章云
 * @date 2019/12/27 16:32
 */
public class StaticArrayInput implements Input<byte[]> {

    private int size;

    public StaticArrayInput(int size) {
        this.size = size;
    }

    @Override
    public List<byte[]> read() {
        final List<byte[]> datas = new ArrayList<>(1);
        byte[] bytes = new byte[size];
        Arrays.fill(bytes, (byte)0);
        datas.add(bytes);
        return datas;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void close() {
    }
}
