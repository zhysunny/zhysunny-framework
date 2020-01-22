package com.zhysunny.framework.common.business;

import java.util.Collection;
import java.util.Map;

/**
 * @author 章云
 * @date 2020/1/22 13:54
 */
public class AbstractBusiness<T> implements Business<T> {

    @Override
    public Collection<T> conversion(Collection<T> datas) {
        return null;
    }

    @Override
    public Map<String, T> conversionToMap(Collection<T> datas) {
        return null;
    }

    @Override
    public Collection<T> conversionToCollection(Map<String, T> datas) {
        return null;
    }

    @Override
    public Map<String, T> conversion(Map<String, T> datas) {
        return null;
    }

}
