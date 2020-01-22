package com.zhysunny.framework.common.business;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 章云
 * @date 2020/1/22 13:51
 */
public interface Business<T> {

    List<T> conversion(List<T> datas);

    Map<String, T> conversionToMap(List<T> datas);

    List<T> conversionToList(Map<String, T> datas);

    Map<String, T> conversion(Map<String, T> datas);

}
