package com.zhysunny.framework.elasticsearch.service;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;

/**
 * @author 章云
 * @date 2020/1/20 15:07
 */
public class ElasticsearchSimpleQueryService {

    protected SearchRequestBuilder builder;

    public ElasticsearchSimpleQueryService(TransportClient client, String type, String... indices) {
        this.builder = client.prepareSearch(indices)
        .setTypes(type)
        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
        .setFrom(0)
        .setSize(1000)
        .setExplain(true);
    }

    public SearchRequestBuilder setQuery(QueryBuilder queryBuilder) {
        return builder.setQuery(queryBuilder);
    }

    public SearchRequestBuilder setPostFilter(QueryBuilder queryBuilder) {
        return builder.setPostFilter(queryBuilder);
    }

    public SearchHits getHits() {
        SearchResponse response = builder.get();
        return response.getHits();
    }

}
