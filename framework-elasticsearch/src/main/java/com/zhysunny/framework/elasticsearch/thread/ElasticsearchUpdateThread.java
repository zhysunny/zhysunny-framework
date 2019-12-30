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
 * ES更新线程
 * @author 章云
 * @date 2019/9/19 21:17
 */
public class ElasticsearchUpdateThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUpdateThread.class);

    private ElasticsearchService esUpdateService;
    private Input input;

    public ElasticsearchUpdateThread(String name, ElasticsearchService esUpdateService, Input input) {
        this.setName(name);
        this.esUpdateService = esUpdateService;
        this.input = input;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("############# {}启动ES更新线程 #############", this.getName());
            TransportClient client = EsClientPoolUtils.getClient();
            List<?> input = this.input.input();
            List<JSONObject> conversion = esUpdateService.conversion(input);
            BulkRequestBuilder bulkRequestBuilder = esUpdateService.buildBulkRequest(client, conversion);
            esUpdateService.request(bulkRequestBuilder);
            EsClientPoolUtils.close(client);
            LOGGER.info("############# {} ES更新线程结束 #############", this.getName());
        } catch (Throwable e) {
            LOGGER.error("{} ES更新线程异常退出", this.getName(), e);
        }
    }

}
