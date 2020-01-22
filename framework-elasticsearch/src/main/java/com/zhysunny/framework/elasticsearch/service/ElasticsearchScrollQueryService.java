package com.zhysunny.framework.elasticsearch.service;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * @author 章云
 * @date 2020/1/14 15:13
 */
public class ElasticsearchScrollQueryService {

    private TransportClient client;
    private String index;
    private SearchResponse searchResponse;
    private int batch = 1000;
    private String scrollId;

    public ElasticsearchScrollQueryService(TransportClient client, String index) {
        this.client = client;
        this.index = index;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getScrollId() {
        return scrollId;
    }

    /**
     * 获取滚动查询
     * @return
     */
    public void createSearchResponse() {
        this.searchResponse = client.prepareSearch(index)
        // 排序
        .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
        // 设置滚动查询
        .setScroll(new TimeValue(60000))
        // 设置滚动大小
        .setSize(batch)
        .get();
        this.scrollId = searchResponse.getScrollId();
    }

    public void scroll(int num) {
        for (int i = 0; i < num; i++) {
            searchResponse = client.prepareSearchScroll(this.scrollId).setScroll(new TimeValue(60000)).execute().actionGet();
        }
    }

    public SearchHit[] query() {
        if (searchResponse.getHits().getHits().length != 0) {
            SearchHit[] hits = searchResponse.getHits().getHits();
            scroll(1);
            return hits;
        }
        return null;
    }

}
