package com.zhysunny.framework.kafka.deserializer;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.Map;

/**
 * String转JSONObject 反序列化
 * @author 章云
 * @date 2019/10/22 14:57
 */
public class StringToJSONObjectDeserializer implements Deserializer<JSONObject> {

    private static final StringDeserializer STRING_DESERIALIZER = new StringDeserializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        STRING_DESERIALIZER.configure(configs, isKey);
    }

    @Override
    public JSONObject deserialize(String topic, byte[] record) {
        String deserialize = STRING_DESERIALIZER.deserialize(topic, record);
        return JSONObject.parseObject(deserialize);
    }

    @Override
    public void close() {
    }

}
