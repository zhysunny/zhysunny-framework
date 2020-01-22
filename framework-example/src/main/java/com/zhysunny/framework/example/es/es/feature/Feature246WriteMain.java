package com.zhysunny.framework.example.es.es.feature;

import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.business.impl.NioTransferString;
import com.zhysunny.framework.common.conf.Configuration;
import com.zhysunny.framework.common.thread.ShutdownHookThread;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.elasticsearch.service.ElasticsearchBulkService;
import com.zhysunny.framework.elasticsearch.constant.EsConstants;
import com.zhysunny.framework.elasticsearch.thread.ElasticsearchBulkThread;
import com.zhysunny.framework.elasticsearch.util.EsClientPoolUtils;
import java.io.File;

/**
 * 246特征写入
 * @author 章云
 * @date 2019/12/30 11:49
 */
public class Feature246WriteMain {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
        Configuration instance = Configuration.getInstance();
        instance.addDefaultResource(Thread.currentThread().getContextClassLoader().getResource("conf/es/elasticsearch.properties"));
        int threadNum = 5;
        String name = "246feature";
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        EsClientPoolUtils.init(EsConstants.ES_CLUSTER_NAME, EsConstants.ES_SERVER_HOSTS, threadNum);
        ElasticsearchBulkService esService = new ElasticsearchWriteFeature246ServiceImpl(EsConstants.ES_HISTORY_INDEX_NAME,
        EsConstants.ES_HISTORY_INDEX_TYPE);
        File path = new File("E:\\feature\\feature246");
        File[] files = path.listFiles();
        int num = 0;
        for (File file : files) {
            Transfer<String> transfer = new NioTransferString(file);
            threadPools.addThread(new ElasticsearchBulkThread(name + num++, esService, transfer));
            if (num >= 5) {
                break;
            }
        }
        threadPools.shutdown();
    }

}
