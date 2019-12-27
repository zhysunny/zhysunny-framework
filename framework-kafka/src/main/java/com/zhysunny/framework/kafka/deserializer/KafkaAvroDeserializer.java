package com.zhysunny.framework.kafka.deserializer;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.kafka.ZnvSchema;
import com.zhysunny.framework.kafka.error.DeserializationException;
import org.apache.avro.Schema;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.util.Utf8;
import org.apache.kafka.common.serialization.Deserializer;
import java.util.HashMap;
import java.util.Map;

/**
 * Kafka Avro 反序列化
 * @author 章云
 * @date 2019/10/22 14:57
 */
public class KafkaAvroDeserializer implements Deserializer<JSONObject> {

    private static final DecoderFactory DECODER_FACTORY = DecoderFactory.get();
    private static final Schema PARSE = ZnvSchema.Map.schema();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public JSONObject deserialize(String topic, byte[] record) {
        BinaryDecoder decoder = DECODER_FACTORY.binaryDecoder(record, null);
        final JSONObject json = new JSONObject(true);
        SpecificDatumReader<HashMap<Utf8, Object>> reader = new SpecificDatumReader<>(PARSE);
        try {
            Map<Utf8, Object> read = reader.read(new HashMap<>(), decoder);
            read.forEach((key, value) -> json.put(key.toString(), value == null ? null : value instanceof Utf8 ? value.toString() : value));
        } catch (Exception e) {
            throw new DeserializationException("Error deserialize avro message", e);
        }
        return json;
    }

    @Override
    public void close() {
    }

}
