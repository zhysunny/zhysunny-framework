package com.zhysunny.framework.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.Input;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import java.io.IOException;
import java.util.List;

/**
 * @author 章云
 * @date 2020/1/20 15:07
 */
public class ElasticsearchScrollQueryService implements Input<JSONObject> {

    protected SearchRequestBuilder builder;
    private TransportClient client;
    private SearchResponse searchResponse;
    private String scrollId;
    protected String name;

    public ElasticsearchScrollQueryService(TransportClient client, String index) {
        this.client = client;
        this.builder = client.prepareSearch(index)
        .setSearchType(SearchType.QUERY_THEN_FETCH)
        .setFrom(0)
        .setSize(1000)
        // 排序
        .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
        // 设置滚动查询
        .setScroll(new TimeValue(60000))
        .setExplain(true);
    }

    public void scroll(int num) {
        for (int i = 0; i < num; i++) {
            searchResponse = client.prepareSearchScroll(this.scrollId).setScroll(new TimeValue(60000)).execute().actionGet();
        }
    }

    public SearchHits getHits() {
        if (searchResponse == null) {
            searchResponse = builder.get();
            this.scrollId = searchResponse.getScrollId();
        }
        if (searchResponse.getHits().getHits().length != 0) {
            SearchHits hits = searchResponse.getHits();
            scroll(1);
            return hits;
        }
        return null;
    }

    @Override
    public List<JSONObject> input() throws IOException {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

}
