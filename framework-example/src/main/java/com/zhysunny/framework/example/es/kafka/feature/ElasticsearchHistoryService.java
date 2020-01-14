package com.zhysunny.framework.example.es.kafka.feature;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.elasticsearch.ElasticsearchQueryService;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 章云
 * @date 2020/1/14 15:13
 */
public class ElasticsearchHistoryService extends ElasticsearchQueryService {

    public ElasticsearchHistoryService(TransportClient client, String index) {
        super(client, index);
    }

    public List<JSONObject> getQueryResult() {
        SearchHit[] hits = super.query();
        if (hits != null) {
            List<JSONObject> datas = new ArrayList<>(hits.length);
            JSONObject json = null;
            for (SearchHit hit : hits) {
                json = new JSONObject(hit.getSource());
                datas.add(json);
            }
            return datas;
        } else {
            return new ArrayList<>(0);
        }
    }

}
