package com.zhysunny.framework.kafka.consumer.persist.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.util.FileUtils;
import com.zhysunny.framework.kafka.consumer.persist.Persist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.*;

/**
 * nio文件持久化
 * @author 章云
 * @date 2019/12/5 8:49
 */
public class NioFilePersistJson implements Persist<JSONObject> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NioFilePersistJson.class);

    private String name;
    private int number;

    public NioFilePersistJson(String name) {
        this.name = name;
        this.number = 10;
    }

    public NioFilePersistJson(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public List<JSONObject> read() throws IOException {
        File file = getFile();
        final List<JSONObject> datas = new ArrayList<>(1000);
        if (!file.exists()) {
            return datas;
        }
        try {
            datas.addAll(Files.lines(Paths.get(file.getAbsolutePath())).map(str -> {
                JSONObject json = JSON.parseObject(str);
                json.forEach((key, value) -> {
                    if (value instanceof BigDecimal) {
                        json.put(key, ((BigDecimal)value).doubleValue());
                    }
                });
                return json;
            }).collect(toList()));
        } catch (IOException e) {
            LOGGER.error("读取{}持久化文件异常", name, e);
        }
        return datas;
    }

    @Override
    public void write(List<JSONObject> datas) {
        File file = getFile();
        try {
            Files.write(Paths.get(file.getAbsolutePath()), datas.stream().map(json -> json.toJSONString()).collect(toList()));
        } catch (IOException e) {
            LOGGER.error("写入{}持久化文件异常", name, e);
        }
    }

    @Override
    public void delete() {
        File file = getFile();
        FileUtils.rollback(file, number);
    }

    /**
     * 获取持久化文件
     * @return
     */
    private File getFile() {
        File dir = new File("tmp", name);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, name + ".txt");
    }

}
