package com.zhysunny.framework.elasticsearch.constant;

import com.zhysunny.framework.common.conf.Configuration;
import com.zhysunny.framework.common.constant.Constants;

/**
 * kafka常量类
 * @author 章云
 * @date 2019/12/27 10:07
 */
public class EsConstants extends Constants {

    private static final Configuration CONF = Configuration.getInstance();

    public static final String ES_SERVER_HOSTS_KEY = "es.server.hosts";
    public static final String ES_SERVER_HOSTS_DOC = "es集群列表，IP或者域名，多个用逗号分隔";
    public static final String[] ES_SERVER_HOSTS_VALUE = CONF.getStrings(ES_SERVER_HOSTS_KEY);

    public static final String ES_CLUSTER_NAME_KEY = "es.cluster.name";
    public static final String ES_CLUSTER_NAME_DOC = "es集群名";
    public static final String ES_CLUSTER_NAME_VALUE = CONF.getString(ES_CLUSTER_NAME_KEY);

    public static final String ES_HTTP_IMPORT_PORT_KEY = "es.http.import.port";
    public static final String ES_HTTP_IMPORT_PORT_DOC = "es入库用的端口号，默认9300";
    public static final int ES_HTTP_IMPORT_PORT_VALUE = CONF.getInt(ES_HTTP_IMPORT_PORT_KEY, 9300);

    public static final String ES_HTTP_QUERY_PORT_KEY = "es.http.query.port";
    public static final String ES_HTTP_QUERY_PORT_DOC = "es查询用的端口号，默认9200";
    public static final int ES_HTTP_QUERY_PORT_VALUE = CONF.getInt(ES_HTTP_QUERY_PORT_KEY, 9200);

}
