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
 * ES写线程
 * @author 章云
 * @date 2019/9/19 21:17
 */
public class ElasticsearchWriteThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchWriteThread.class);

    private ElasticsearchService esWriteService;
    private Input input;

    public ElasticsearchWriteThread(String name, ElasticsearchService esWriteService, Input input) {
        this.setName(name);
        this.esWriteService = esWriteService;
        this.input = input;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("############# {}启动ES写线程 #############", this.getName());
            TransportClient client = EsClientPoolUtils.getClient();
            List<?> input = this.input.input();
            List<JSONObject> conversion = esWriteService.conversion(input);
            List<JSONObject> filter = esWriteService.filter(conversion);
            BulkRequestBuilder bulkRequestBuilder = esWriteService.buildBulkRequest(client, filter);
            esWriteService.request(bulkRequestBuilder);
            EsClientPoolUtils.close(client);
            LOGGER.info("############# {} ES写线程结束 #############", this.getName());
        } catch (Throwable e) {
            LOGGER.error("{} ES写线程异常退出", this.getName(), e);
        }
    }

}
