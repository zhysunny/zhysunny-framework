package com.zhysunny.framework.example.es.kafka.feature;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.conf.Configuration;
import com.zhysunny.framework.elasticsearch.constant.EsConstants;
import com.zhysunny.framework.elasticsearch.util.EsClientPoolUtils;
import org.elasticsearch.client.transport.TransportClient;
import java.util.List;

/**
 * @author 章云
 * @date 2020/1/14 15:25
 */
public class ElasticsearchQueryScrollMain {

    public static void main(String[] args) {
        Configuration instance = Configuration.getInstance();
        instance.addDefaultResource(Thread.currentThread().getContextClassLoader().getResource("conf/es/elasticsearch.properties"));
        EsClientPoolUtils.init(EsConstants.ES_CLUSTER_NAME, EsConstants.ES_SERVER_HOSTS, 1);
        TransportClient client = EsClientPoolUtils.getClient();
        ElasticsearchHistoryService query = new ElasticsearchHistoryService(client, EsConstants.ES_HISTORY_INDEX_NAME + "-*");
        query.setBatch(999);
        query.createSearchResponse();
        System.out.println(query.getScrollId());
        // scrollId是一样的，重要的是滚动次数
        query.scroll(4);
        List<JSONObject> input;
        while ((input = query.getQueryResult()).size() > 0) {
            System.out.println(input.size());
        }
    }

}
