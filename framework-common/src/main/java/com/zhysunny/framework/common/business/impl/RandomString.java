package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.Input;
import com.zhysunny.framework.common.util.RandomUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 随机字符串
 * @author 章云
 * @date 2019/12/27 16:32
 */
public class RandomString implements Input<String> {

    private int total;
    private int size;

    public RandomString(int total, int size) {
        this.total = total;
        this.size = size;
    }

    @Override
    public List<String> input() {
        final List<String> datas = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            String str = RandomUtils.allCode(size);
            datas.add(str);
        }
        return datas;
    }

}
