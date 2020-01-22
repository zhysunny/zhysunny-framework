package com.zhysunny.framework.elasticsearch.thread;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.elasticsearch.service.ElasticsearchBulkService;
import com.zhysunny.framework.elasticsearch.util.EsClientPoolUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

/**
 * ES写线程
 * @author 章云
 * @date 2019/9/19 21:17
 */
public class ElasticsearchBulkThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchBulkThread.class);

    private ElasticsearchBulkService esService;
    private Transfer transfer;

    public ElasticsearchBulkThread(String name, ElasticsearchBulkService esService, Transfer transfer) {
        this.setName(name);
        this.esService = esService;
        this.transfer = transfer;
    }

    @Override
    public void run() {
        try {
            LOGGER.info("############# {}启动ES写线程 #############", this.getName());
            TransportClient client = EsClientPoolUtils.getClient();
            List<?> input = this.transfer.input();
            Map<String, JSONObject> datas = esService.conversion(input);
            datas = esService.filter(datas);
            BulkRequestBuilder bulkRequestBuilder = esService.buildBulkRequest(client, datas);
            esService.request(bulkRequestBuilder, datas);
            EsClientPoolUtils.close(client);
            LOGGER.info("############# {} ES写线程结束 #############", this.getName());
        } catch (Throwable e) {
            LOGGER.error("{} ES写线程异常退出", this.getName(), e);
        }
    }

}
