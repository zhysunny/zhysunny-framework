package com.zhysunny.framework.elasticsearch.thread;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.input.Input;
import com.zhysunny.framework.elasticsearch.ElasticsearchService;
import com.zhysunny.framework.elasticsearch.util.EsClientPoolUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * ES删除线程
 * @author 章云
 * @date 2019/9/19 21:17
 */
public class ElasticsearchDeleteThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchDeleteThread.class);

    private ElasticsearchService esDeleteService;
    private Input input;

    public ElasticsearchDeleteThread(String name, ElasticsearchService esDeleteService, Input input) {
        this.setName(name);
        this.esDeleteService = esDeleteService;
        this.input = input;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("############# {}启动ES删除线程 #############", this.getName());
            TransportClient client = EsClientPoolUtils.getClient();
            List<?> input = this.input.input();
            List<JSONObject> conversion = esDeleteService.conversion(input);
            BulkRequestBuilder bulkRequestBuilder = esDeleteService.buildBulkRequest(client, conversion);
            esDeleteService.request(bulkRequestBuilder);
            EsClientPoolUtils.close(client);
            LOGGER.info("############# {} ES删除线程结束 #############", this.getName());
        } catch (Throwable e) {
            LOGGER.error("{} ES删除线程异常退出", this.getName(), e);
        }
    }

}
