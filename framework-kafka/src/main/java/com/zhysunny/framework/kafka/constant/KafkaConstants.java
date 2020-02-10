package com.zhysunny.framework.kafka.constant;

import com.zhysunny.framework.common.constant.Constants;

/**
 * kafka常量类
 * @author 章云
 * @date 2019/12/27 10:07
 */
public class KafkaConstants extends Constants {

    public static final String BOOTSTRAP_SERVERS_KEY = "bootstrap.servers";
    public static final String BOOTSTRAP_SERVERS_DOC = "kafka集群节点列表，多个用逗号分隔";
    public static final String BOOTSTRAP_SERVERS_VALUE = CONF.getString(BOOTSTRAP_SERVERS_KEY);

    /******************************* 生产者 ******************************/

    public static final String KEY_SERIALIZER_KEY = "key.serializer";
    public static final String KEY_SERIALIZER_DOC = "kafka生产者key序列化方式";
    public static final String KEY_SERIALIZER_VALUE = CONF.getString(KEY_SERIALIZER_KEY);

    public static final String VALUE_SERIALIZER_KEY = "value.serializer";
    public static final String VALUE_SERIALIZER_DOC = "kafka生产者value序列化方式";
    public static final String VALUE_SERIALIZER_VALUE = CONF.getString(VALUE_SERIALIZER_KEY);

    /******************************* 消费者 ******************************/

    public static final String KEY_DESERIALIZER_KEY = "key.deserializer";
    public static final String KEY_DESERIALIZER_DOC = "kafka生产者key反序列化方式";
    public static final String KEY_DESERIALIZER_VALUE = CONF.getString(KEY_DESERIALIZER_KEY);

    public static final String VALUE_DESERIALIZER_KEY = "value.deserializer";
    public static final String VALUE_DESERIALIZER_DOC = "kafka生产者value反序列化方式";
    public static final String VALUE_DESERIALIZER_VALUE = CONF.getString(VALUE_DESERIALIZER_KEY);

    public static final String ENABLE_AUTO_COMMIT_KEY = "enable.auto.commit";
    public static final String ENABLE_AUTO_COMMIT_DOC = "消费者是否自动提交offset，默认不提交";
    public static final String ENABLE_AUTO_COMMIT_VALUE = CONF.getString(ENABLE_AUTO_COMMIT_KEY, "false");

    public static final String HEARTBEAT_INTERVAL_MS_KEY = "heartbeat.interval.ms";
    public static final String HEARTBEAT_INTERVAL_MS_DOC = "心跳时间间隔，毫秒，默认10秒";
    public static final String HEARTBEAT_INTERVAL_MS_VALUE = CONF.getString(HEARTBEAT_INTERVAL_MS_KEY, "10000");

    public static final String AUTO_OFFSET_RESET_KEY = "auto.offset.reset";
    public static final String AUTO_OFFSET_RESET_DOC = "offset重置策略，默认从头开始";
    public static final String AUTO_OFFSET_RESET_VALUE = CONF.getString(AUTO_OFFSET_RESET_KEY, "earliest");

    public static final String MAX_PARTITION_FETCH_BYTES_KEY = "max.partition.fetch.bytes";
    public static final String MAX_PARTITION_FETCH_BYTES_DOC = "消费者拉取数据每个分区最大拉取的容量大小，默认1兆";
    public static final String MAX_PARTITION_FETCH_BYTES_VALUE = Long.toString(CONF.getCapacity(MAX_PARTITION_FETCH_BYTES_KEY, "1M"));

    /******************************* 序列化和反序列化 ******************************/

    public static final String KAFKA_JSON_DESERIALIZER_KEY = "kafka.json.deserializer";
    public static final String KAFKA_JSON_DESERIALIZER_DOC = "在StringDeserializer基础上将String转Json";
    public static final String KAFKA_JSON_DESERIALIZER_VALUE = CONF
    .getString(KAFKA_JSON_DESERIALIZER_KEY, "com.znv.fss.kafka.deserializer.StringToJsonDeserializer");

    public static final String KAFKA_STRING_DESERIALIZER_KEY = "kafka.string.deserializer";
    public static final String KAFKA_STRING_DESERIALIZER_DOC = "kafka提供的字符串反序列化方式";
    public static final String KAFKA_STRING_DESERIALIZER_VALUE = CONF
    .getString(KAFKA_STRING_DESERIALIZER_KEY, "org.apache.kafka.common.serialization.StringDeserializer");

    public static final String KAFKA_AVRO_DESERIALIZER_KEY = "kafka.avro.deserializer";
    public static final String KAFKA_AVRO_DESERIALIZER_DOC = "自定义avro反序列化方式";
    public static final String KAFKA_AVRO_DESERIALIZER_VALUE = CONF
    .getString(KAFKA_AVRO_DESERIALIZER_KEY, "com.znv.fss.kafka.deserializer.KafkaAvroDeserializer");

    public static final String KAFKA_STRING_SERIALIZER_KEY = "kafka.string.serializer";
    public static final String KAFKA_STRING_SERIALIZER_DOC = "kafka提供的字符串序列化方式";
    public static final String KAFKA_STRING_SERIALIZER_VALUE = CONF
    .getString(KAFKA_STRING_SERIALIZER_KEY, "org.apache.kafka.common.serialization.StringSerializer");

    public static final String KAFKA_AVRO_SERIALIZER_KEY = "kafka.avro.serializer";
    public static final String KAFKA_AVRO_SERIALIZER_DOC = "自定义avro序列化方式";
    public static final String KAFKA_AVRO_SERIALIZER_VALUE = CONF
    .getString(KAFKA_AVRO_SERIALIZER_KEY, "com.znv.fss.kafka.serializer.KafkaAvroSerializer");

}
