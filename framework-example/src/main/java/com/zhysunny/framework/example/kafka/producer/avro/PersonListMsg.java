package com.zhysunny.framework.example.kafka.producer.avro;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.Input;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 章云
 * @date 2020/2/25 10:38
 */
public class PersonListMsg implements Input<JSONObject> {

    @Override
    public List<JSONObject> read() {
        JSONObject notifyMsg = new JSONObject();
        notifyMsg.put("msg_type", "fss-BlackListChange-n-project-v1-2-production");
        notifyMsg.put("table_name", "person_list_data_n_project_v1_test1");
        notifyMsg.put("primary_id", 1);
        notifyMsg.put("reference_id", null);
        long currentTime = System.currentTimeMillis();
        Date timeDate = new Date(currentTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = sdf.format(timeDate);
        notifyMsg.put("send_time", timeStr);
        List<JSONObject> list = new ArrayList<>();
        list.add(notifyMsg);
        return list;
    }

    @Override
    public String getName() {
        return "avro";
    }

    @Override
    public void close() {
    }

}
