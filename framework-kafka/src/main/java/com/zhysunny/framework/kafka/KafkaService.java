package com.zhysunny.framework.kafka;

import com.zhysunny.framework.common.properties.PropertiesReader;
import java.util.Properties;

/**
 * @author 章云
 * @date 2020/1/14 11:20
 */
public interface KafkaService {

    /**
     * 读取配置
     * @param resource 资源路径
     * @return
     */
    default Properties loadConfig(String resource) {
        try {
            return new PropertiesReader(resource).builder().getProps();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
