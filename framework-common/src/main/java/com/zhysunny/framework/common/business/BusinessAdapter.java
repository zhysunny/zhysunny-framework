package com.zhysunny.framework.common.business;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 章云
 * @date 2020/1/22 13:54
 */
public class BusinessAdapter<T> implements Business<T> {

    @Override
    public List<T> conversion(List<T> datas) {
        return null;
    }

    @Override
    public Map<String, T> conversionToMap(List<T> datas) {
        return null;
    }

    @Override
    public List<T> conversionToList(Map<String, T> datas) {
        return null;
    }

    @Override
    public Map<String, T> conversion(Map<String, T> datas) {
        return null;
    }

}
