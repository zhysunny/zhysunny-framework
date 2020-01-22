package com.zhysunny.framework.example.es.es.feature;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.elasticsearch.service.ElasticsearchBulkService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.lopq.LOPQModel;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 246特征写入
 * @author 章云
 * @date 2019/12/30 11:28
 */
public class ElasticsearchDeleteFeature246ServiceImpl extends ElasticsearchBulkService<String> {

    private Random random = new Random();

    public ElasticsearchDeleteFeature246ServiceImpl(String index, String type) {
        super(index, type);
        try {
            LOPQModel.loadProto(Thread.currentThread().getContextClassLoader().getResourceAsStream("model/modelserial.dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ElasticsearchDeleteFeature246ServiceImpl() {
        this(null, null);
    }

    @Override
    public Map<String, JSONObject> conversion(List<String> datas) {
        return datas.stream()
        // 数据为空的不用
        .filter(str -> StringUtils.isNotEmpty(str))
        // 删除10分之1的数据
        .filter(str -> random.nextInt(10) == 0)
        // 字符串变为json
        .map(str -> {
            JSONObject json = new JSONObject();
            int[] predict = LOPQModel.predict(str);
            json.put("coarse_id", predict[0]);
            json.put("docid", str.substring(0, 32));
            return json;
        })
        // 组装新的集合
        .collect(Collectors.toMap(json -> (String)json.remove("docid"), json -> json, (existing, replacement) -> replacement));
    }

    @Override
    public BulkRequestBuilder buildBulkRequest(TransportClient client, Map<String, JSONObject> datas) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        datas.entrySet().forEach(entry -> {
            String docid = entry.getKey();
            JSONObject json = entry.getValue();
            StringBuilder index = new StringBuilder(this.index);
            String coarseId = json.remove("coarse_id").toString();
            index.append('-').append(coarseId);
            // 数据新增请求
            bulkRequest.add(client.prepareDelete(index.toString(), this.type, docid));
        });
        return bulkRequest;
    }

}
