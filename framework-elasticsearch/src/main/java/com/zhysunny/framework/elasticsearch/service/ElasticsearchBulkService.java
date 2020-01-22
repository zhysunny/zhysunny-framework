package com.zhysunny.framework.elasticsearch.service;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.Output;
import com.zhysunny.framework.common.util.StringUtils;
import com.zhysunny.framework.elasticsearch.constant.EsConstants;
import com.zhysunny.framework.elasticsearch.handler.FailuresHandler;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ES服务接口
 * @author 章云
 * @date 2019/12/30 10:35
 */
public class ElasticsearchBulkService<E> implements Output<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchBulkService.class);

    /**
     * 针对更新同一索引是加写锁
     */
    protected ReentrantReadWriteLock.WriteLock updateIndexLock;

    /**
     * 当所有数据操作同一个index和type是需要
     */
    protected String index;
    protected String type;
    protected TransportClient client;

    public ElasticsearchBulkService(TransportClient client, String index, String type) {
        this.client = client;
        this.index = index;
        this.type = type;
    }

    public ElasticsearchBulkService(TransportClient client) {
        // 不执行index和type，需要每个data中有index和type键值对
        this(client, null, null);
    }

    /**
     * 批量写请求封装
     * @param datas
     * @return
     */
    public BulkRequestBuilder buildBulkRequest(Map<String, JSONObject> datas) {return null;}

    /**
     * 批量写请求封装
     * @param datas
     * @return
     */
    public BulkRequestBuilder buildBulkRequest(List<E> datas) {return null;}

    /**
     * 过滤字段为空的值
     * @param datas
     * @return
     */
    public final Map<String, JSONObject> filter(Map<String, JSONObject> datas) {
        for (Map.Entry<String, JSONObject> entry : datas.entrySet()) {
            Iterator<Map.Entry<String, Object>> iterator = entry.getValue().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> json = iterator.next();
                if (StringUtils.isEmptyParamOne(json.getValue())) {
                    iterator.remove();
                }
            }
        }
        return datas;
    }

    /**
     * 过滤字段为空的值
     * @param datas
     * @return
     */
    public final List<JSONObject> filter(List<JSONObject> datas) {
        for (JSONObject data : datas) {
            Iterator<Map.Entry<String, Object>> iterator = data.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> json = iterator.next();
                if (StringUtils.isEmptyParamOne(json.getValue())) {
                    iterator.remove();
                }
            }
        }
        return datas;
    }

    /**
     * 入库请求批量发送
     * @param bulkRequest
     * @return 返回[成功数, 失败数]
     */
    public final int[] request(BulkRequestBuilder bulkRequest) {
        return request(bulkRequest, null);
    }

    /**
     * 入库请求批量发送
     * @param bulkRequest 批量请求
     * @param datas       原始数据，用于异常处理
     * @return 返回[成功数, 失败数]
     */
    public final int[] request(BulkRequestBuilder bulkRequest, Map<String, JSONObject> datas) {
        int num = bulkRequest.numberOfActions();
        int error = 0;
        if (num > 0) {
            BulkResponse bulkResponse = null;
            if (updateIndexLock != null) {
                updateIndexLock.lock();
            }
            try {
                bulkResponse = bulkRequest.get();
            } catch (NoNodeAvailableException e) {
                LOGGER.error("cluster name:{},hosts:{},port:{}",
                EsConstants.ES_CLUSTER_NAME,
                EsConstants.ES_SERVER_HOSTS,
                EsConstants.ES_HTTP_IMPORT_PORT);
                System.exit(1);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                System.exit(1);
            } finally {
                if (updateIndexLock != null) {
                    updateIndexLock.unlock();
                }
            }
            if (bulkResponse.hasFailures()) {
                error = FailuresHandler.handler(bulkResponse, datas);
            }
        }
        return new int[]{ num - error, error };
    }

    @Override
    public Object write(List<E> datas) {
        return null;
    }

    @Override
    public Object write(Map<String, E> datas) {
        return null;
    }

}