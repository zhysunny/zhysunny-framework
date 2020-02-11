package com.zhysunny.framework.hbase.constant;

import com.zhysunny.framework.common.constant.Constants;

/**
 * kafka常量类
 * @author 章云
 * @date 2019/12/27 10:07
 */
public class HbaseConstants extends Constants {

    public static final String HBASE_ZOOKEEPER_QUORUM_KEY = "hbase.zookeeper.quorum";
    public static final String HBASE_ZOOKEEPER_QUORUM_DOC = "hbase连接的zookeeper节点，不需要端口";
    public static final String HBASE_ZOOKEEPER_QUORUM_VALUE = CONF.getString(HBASE_ZOOKEEPER_QUORUM_KEY);

    public static final String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT_KEY = "hbase.zookeeper.property.clientPort";
    public static final String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT_DOC = "zookeeper端口";
    public static final String HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT_VALUE = CONF.getString(HBASE_ZOOKEEPER_PROPERTY_CLIENTPORT_KEY);

}
