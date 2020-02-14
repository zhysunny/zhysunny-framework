package com.zhysunny.framework.kafka.service;

import kafka.admin.AdminUtils;
import kafka.admin.TopicCommand;
import kafka.utils.ZkUtils;
import org.apache.kafka.common.security.JaasUtils;
import scala.collection.JavaConversions;
import scala.collection.Seq;
import java.io.Closeable;
import java.util.List;
import java.util.Properties;

/**
 * kafka-topics.sh脚本，针对topic的增删改查
 * @author 章云
 * @date 2019/10/18 10:03
 */
public class KafkaTopicService implements Closeable {

    private ZkUtils zkUtils;

    public KafkaTopicService(ZkUtils zkUtils) {
        this.zkUtils = zkUtils;
    }

    public KafkaTopicService(String zkUrl) {
        this.zkUtils = ZkUtils.apply(zkUrl, 30000, 30000, JaasUtils.isZkSecurityEnabled());
    }

    /**
     * 获取所有topic
     * @return
     */
    public List<String> listTopic() {
        Seq<String> allTopics = zkUtils.getAllTopics();
        return JavaConversions.seqAsJavaList(allTopics);
    }

    /**
     * 判断topic是否存在
     * @param topicName
     * @return
     */
    public boolean existTopic(String topicName) {
        return AdminUtils.topicExists(zkUtils, topicName);
    }

    /**
     * 创建topic
     * @param topicName  topic名
     * @param partitions 分区数
     * @param replicas   副本数
     */
    public void createTopic(String topicName, int partitions, int replicas) {
        Properties configs = new Properties();
        TopicCommand.warnOnMaxMessagesChange(configs, replicas);
        AdminUtils.createTopic(zkUtils, topicName, partitions, replicas, configs);
    }

    /**
     * 修改topic分区数，分区数只能增加不能减少
     * @param topicName
     * @param partitions
     */
    public void alertTopic(String topicName, int partitions) {
        AdminUtils.addPartitions(zkUtils, topicName, partitions, "", true);
    }

    /**
     * 获取topic信息
     * @param topicName
     */
    public void describeTopic(String topicName) {
        String[] args = new String[]{ "describe", "--topic", topicName };
        TopicCommand.TopicCommandOptions opts = new TopicCommand.TopicCommandOptions(args);
        TopicCommand.describeTopic(zkUtils, opts);
    }

    /**
     * 删除topic
     * @param topicName
     */
    public void deleteTopic(String topicName) {
        String[] args = new String[]{ "delete", "--topic", topicName };
        TopicCommand.TopicCommandOptions opts = new TopicCommand.TopicCommandOptions(args);
        TopicCommand.deleteTopic(zkUtils, opts);
    }

    @Override
    public void close() {
        zkUtils.close();
    }
}
