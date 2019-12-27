package com.zhysunny.framework.kafka.serializer;

import com.zhysunny.framework.common.util.FileUtils;
import com.zhysunny.framework.kafka.ZnvSchema;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * Kafka Avro 序列化
 * @author 章云
 * @date 2019/12/27 9:35
 */
public class KafkaAvroSerializer implements Serializer<Object> {

    private static final EncoderFactory ENCODER_FACTORY = EncoderFactory.get();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Object record) {
        if (record == null) {
            return null;
        }
        Schema schema = ZnvSchema.getSchema(record);
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            if (record instanceof byte[]) {
                out.write((byte[])record);
            } else {
                BinaryEncoder encoder = ENCODER_FACTORY.directBinaryEncoder(out, null);
                DatumWriter<Object> writer = null;
                if (record instanceof SpecificRecord) {
                    writer = new SpecificDatumWriter<>(schema);
                } else {
                    writer = new GenericDatumWriter<>(schema);
                }
                writer.write(record, encoder);
                encoder.flush();
            }
            byte[] bytes = out.toByteArray();
            return bytes;
        } catch (Exception e) {
            throw new SerializationException("Error serialize avro message", e);
        } finally {
            FileUtils.close(out);
        }
    }

    @Override
    public void close() {
    }

}
