package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.Output;
import java.util.List;
import java.util.Map;

/**
 * @author 章云
 * @date 2020/2/14 9:46
 */
public class NoOperationOutput implements Output<Object> {
    @Override
    public Object write(List<Object> datas) {
        return null;
    }

    @Override
    public Object write(Map<String, Object> datas) {
        return null;
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
}
