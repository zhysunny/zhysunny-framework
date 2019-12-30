package com.zhysunny.framework.example.es.es.feature;

import com.zhysunny.framework.common.business.input.Input;
import com.zhysunny.framework.common.business.input.impl.NioFileInputString;
import com.zhysunny.framework.common.conf.Configuration;
import com.zhysunny.framework.common.util.ThreadPoolUtil;
import com.zhysunny.framework.elasticsearch.ElasticsearchService;
import com.zhysunny.framework.elasticsearch.constant.EsConstants;
import com.zhysunny.framework.elasticsearch.thread.ElasticsearchUpdateThread;
import com.zhysunny.framework.elasticsearch.thread.ElasticsearchWriteThread;
import com.zhysunny.framework.elasticsearch.thread.ShutdownHookThread;
import com.zhysunny.framework.elasticsearch.util.EsClientPoolUtils;
import java.io.File;

/**
 * 246特征写入
 * @author 章云
 * @date 2019/12/30 11:49
 */
public class Feature246UpdateMain {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHookThread());
        Configuration instance = Configuration.getInstance();
        instance.addDefaultResource(Thread.currentThread().getContextClassLoader().getResource("conf/elasticsearch.properties"));
        int threadNum = 5;
        String name = "246feature";
        ThreadPoolUtil threadPools = ThreadPoolUtil.getInstance(threadNum);
        EsClientPoolUtils.init(EsConstants.ES_CLUSTER_NAME, EsConstants.ES_SERVER_HOSTS, threadNum);
        ElasticsearchService esUpdateService = new ElasticsearchUpdateFeature246ServiceImpl(
        EsConstants.ES_HISTORY_INDEX_NAME, EsConstants.ES_HISTORY_INDEX_TYPE);
        File path = new File("E:\\feature\\feature246");
        File[] files = path.listFiles();
        int num = 0;
        for (File file : files) {
            Input<String> input = new NioFileInputString(file);
            threadPools.addThread(new ElasticsearchUpdateThread(name + num++, esUpdateService, input));
            if (num >= 5) {
                break;
            }
        }
        threadPools.shutdown();
    }

}