package com.zhysunny.framework.common.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.*;

/**
 * NIO读写
 * @author 章云
 * @date 2019/12/27 20:48
 */
public class NioFileReadWriteJson implements FileReadWrite<JSONObject> {

    private File file;

    public NioFileReadWriteJson(File file) {
        this.file = file;
    }

    public NioFileReadWriteJson(String filepath) {
        this.file = new File(filepath);
    }

    @Override
    public List<JSONObject> read() throws IOException {
        final List<JSONObject> datas = new ArrayList<>();
        if (!file.exists()) {
            return datas;
        }
        try {
            datas.addAll(Files.lines(Paths.get(file.getAbsolutePath())).map(str -> JSON.parseObject(str)).collect(toList()));
        } catch (IOException e) {
            throw e;
        }
        return datas;
    }

    @Override
    public void write(List<JSONObject> datas) throws IOException {
        try {
            Files.write(Paths.get(file.getAbsolutePath()), datas.stream().map(json -> json.toJSONString()).collect(toList()));
        } catch (IOException e) {
            throw e;
        }
    }
}
