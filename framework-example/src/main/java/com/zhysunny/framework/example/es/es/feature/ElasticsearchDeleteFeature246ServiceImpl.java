package com.zhysunny.framework.example.es.es.feature;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.elasticsearch.ElasticsearchService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.lopq.LOPQModel;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import static java.util.stream.Collectors.*;

/**
 * 246特征写入
 * @author 章云
 * @date 2019/12/30 11:28
 */
public class ElasticsearchDeleteFeature246ServiceImpl extends ElasticsearchService<String> {

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
    public List<JSONObject> conversion(List<String> datas) {
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
        }).collect(toList());
    }

    @Override
    public BulkRequestBuilder buildBulkRequest(TransportClient client, List<JSONObject> datas) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        datas.forEach(json -> {
            StringBuilder index = new StringBuilder(this.index);
            String coarseId = json.remove("coarse_id").toString();
            String docid = json.remove("docid").toString();
            index.append('-').append(coarseId);
            // 数据新增请求
            bulkRequest.add(client.prepareDelete(index.toString(), this.type, docid));
        });
        return bulkRequest;
    }

}
