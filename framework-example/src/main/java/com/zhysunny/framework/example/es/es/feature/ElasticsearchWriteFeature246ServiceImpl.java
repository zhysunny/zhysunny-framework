package com.zhysunny.framework.example.es.es.feature;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.elasticsearch.write.ElasticsearchWriteService;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.lopq.LOPQModel;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import static java.util.stream.Collectors.*;

/**
 * 246特征写入
 * @author 章云
 * @date 2019/12/30 11:28
 */
public class ElasticsearchWriteFeature246ServiceImpl extends ElasticsearchWriteService<String> {

    public ElasticsearchWriteFeature246ServiceImpl(String index, String type) {
        super(index, type);
        try {
            LOPQModel.loadProto(Thread.currentThread().getContextClassLoader().getResourceAsStream("model/modelserial.dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ElasticsearchWriteFeature246ServiceImpl() {
        this(null, null);
    }

    @Override
    public List<JSONObject> conversion(List<String> datas) {
        return datas.stream()
        // 数据为空的不用
        .filter(str -> StringUtils.isNotEmpty(str))
        // 字符串变为json
        .map(str -> {
            JSONObject json = new JSONObject();
            byte[] decode = Base64.getDecoder().decode(str);
            json.put("rt_feature", decode);
            int[] predict = LOPQModel.predict(str);
            json.put("coarse_id", predict[0]);
            return json;
        }).collect(toList());
    }

    @Override
    public BulkRequestBuilder buildBulkRequest(TransportClient client, List<JSONObject> datas) {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        datas.forEach(json -> {
            StringBuilder index = new StringBuilder(this.index);
            String coarseId = json.getString("coarse_id");
            index.append('-').append(coarseId);
            // 数据新增请求
            bulkRequest.add(client.prepareIndex(index.toString(), this.type).setSource(json));
        });
        return bulkRequest;
    }

}
