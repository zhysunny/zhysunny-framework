package com.zhysunny.framework.common.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 阻塞IO读写
 * @author 章云
 * @date 2019/12/27 20:48
 */
public class OioFileReadWriteJson implements FileReadWrite<JSONObject> {

    private File file;
    private boolean append;

    public OioFileReadWriteJson(File file, boolean append) {
        this.file = file;
        this.append = append;
    }

    public OioFileReadWriteJson(File file) {
        this(file, false);
    }

    public OioFileReadWriteJson(String filepath) {
        this(new File(filepath), false);
    }

    public OioFileReadWriteJson(String filepath, boolean append) {
        this(new File(filepath), append);
    }

    @Override
    public List<JSONObject> read() throws IOException {
        final List<JSONObject> datas = new ArrayList<>();
        if (!file.exists()) {
            return datas;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                datas.add(JSON.parseObject(line));
            }
        } catch (IOException e) {
            throw e;
        }
        return datas;
    }

    @Override
    public void write(List<JSONObject> datas) throws IOException {
        try (final FileOutputStream fos = new FileOutputStream(file, append)) {
            for (JSONObject data : datas) {
                fos.write((data.toJSONString() + "\n").getBytes());
            }
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public void write(Map<String, JSONObject> datas) throws IOException {
        try (final FileOutputStream fos = new FileOutputStream(file, append)) {
            for (Map.Entry<String, JSONObject> entry : datas.entrySet()) {
                fos.write((entry.getKey() + "\t" + entry.getValue().toJSONString() + "\n").getBytes());
            }
        } catch (IOException e) {
            throw e;
        }
    }

}
