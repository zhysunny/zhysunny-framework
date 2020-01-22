package com.zhysunny.framework.common.business;

import java.util.Collection;
import java.util.Map;

/**
 * @author 章云
 * @date 2020/1/22 13:51
 */
public interface Business<T> {

    Collection<T> conversion(Collection<T> datas);

    Map<String, T> conversionToMap(Collection<T> datas);

    Collection<T> conversionToCollection(Map<String, T> datas);

    Map<String, T> conversion(Map<String, T> datas);

}
