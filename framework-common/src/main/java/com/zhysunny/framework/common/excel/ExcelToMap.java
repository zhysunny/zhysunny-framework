package com.zhysunny.framework.common.excel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel转成Map(内存消耗较大)
 * @author 章云
 * @date 2019/7/26 15:02
 */
public class ExcelToMap {

    private ExcelReader reader;

    public ExcelToMap(ExcelReader reader) {
        this.reader = reader;
    }

    @Deprecated
    public Map<String, List<Map<String, String>>> read() throws Exception {
        // 转成map对象，数据异常值忽略
        Map<String, List<Map<String, String>>> dataMap = new LinkedHashMap<>(reader.getSheetCount());
        for (int i = 0; i < reader.getSheetCount(); i++) {
            try {
                reader.readTitle(i);
                String sheetName = reader.getSheetName();
                List<Map<String, String>> dataList = new ArrayList<>();
                Map<String, String> map = null;
                while ((map = reader.readLine()) != null) {
                    if (map.size() == 0) {
                        continue;
                    }
                    dataList.add(map);
                }
                if (!dataList.isEmpty()) {
                    dataMap.put(sheetName, dataList);
                }
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
        reader.close();
        return dataMap;
    }

    public Map<String, Map<String, List<String>>> readLess() throws Exception {
        // 转成map对象
        Map<String, Map<String, List<String>>> dataMap = new LinkedHashMap<>(reader.getSheetCount());
        for (int i = 0; i < reader.getSheetCount(); i++) {
            try {
                reader.readTitle(i);
                String sheetName = reader.getSheetName();
                Map<String, List<String>> childMap = new LinkedHashMap<>();
                Map<String, String> map = null;
                String key = null;
                String value = null;
                while ((map = reader.readLine()) != null) {
                    if (map.size() == 0) {
                        continue;
                    }
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        key = entry.getKey();
                        if (key == null || key.length() == 0) {
                            // 标题为空的列不读取
                            continue;
                        }
                        if (childMap.get(key) == null) {
                            childMap.put(key, new ArrayList<>());
                        }
                        value = entry.getValue();
                        if (value == null) {
                            value = "";
                        }
                        childMap.get(key).add(value);
                    }
                }
                if (childMap.size() > 0) {
                    dataMap.put(sheetName, childMap);
                }
            } catch (Exception e) {
                // 数据异常值忽略
                throw new Exception(e);
            }
        }
        reader.close();
        return dataMap;
    }

}
