package com.zhysunny.framework.kafka.business.input.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.kafka.business.input.Input;
import java.io.File;
import java.util.List;
import static java.util.stream.Collectors.*;

/**
 * 读取文件
 * @author 章云
 * @date 2019/12/27 15:47
 */
public class NioFileInputJson implements Input<JSONObject> {

    private NioFileInputString input;

    public NioFileInputJson(File file) {
        input = new NioFileInputString(file);
    }

    @Override
    public List<JSONObject> input() {
        return input.input().stream().map(str -> JSON.parseObject(str)).collect(toList());
    }

}
