package com.zhysunny.framework.hbase.util;

import com.zhysunny.framework.common.util.ClientPoolUtils;
import com.zhysunny.framework.hbase.constant.HbaseConstants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import java.io.IOException;

/**
 * @author 章云
 * @date 2020/1/22 9:42
 */
public class HbaseConnUtils implements ClientPoolUtils.Client<Connection> {

    private String zookeeperQuorum;
    private String zookeeperPort;

    public HbaseConnUtils(String zookeeperQuorum, String zookeeperPort) {
        this.zookeeperQuorum = zookeeperQuorum;
        this.zookeeperPort = zookeeperPort;
    }

    public HbaseConnUtils() {
        this(HbaseConstants.HBASE_ZOOKEEPER_QUORUM_VALUE, HbaseConstants.HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT_VALUE);
    }

    @Override
    public Connection getClient() {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zookeeperQuorum);
        // 与hbase/conf/hbase-site.xml中hbase.zookeeper.property.clientPort配置的值相同
        conf.set("hbase.zookeeper.property.clientPort", zookeeperPort);
        try {
            return ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
        }
        return null;
    }

}
