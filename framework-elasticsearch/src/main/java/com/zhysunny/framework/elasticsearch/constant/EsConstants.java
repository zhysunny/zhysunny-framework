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

    public static final String[] ES_SERVER_HOSTS = CONF.getStrings("es.server.hosts");
    public static final String ES_CLUSTER_NAME = CONF.get("es.cluster.name");
    public static final int ES_HTTP_IMPORT_PORT = CONF.getInt("es.http.import.port", 9300);
    public static final int ES_HTTP_QUERY_PORT = CONF.getInt("es.http.query.port", 9200);
    public static final String ES_HISTORY_INDEX_NAME = CONF.get("es.history.index.name");
    public static final String ES_HISTORY_INDEX_TYPE = CONF.get("es.history.index.type");

}
