package com.zhysunny.framework.kafka.consumer.persist.impl;

import com.zhysunny.framework.common.util.FileUtils;
import com.zhysunny.framework.kafka.consumer.persist.Persist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件持久化
 * @author 章云
 * @date 2019/12/5 8:49
 */
public class OioFilePersistString implements Persist<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OioFilePersistString.class);

    private String name;
    private int number;

    public OioFilePersistString(String name) {
        this.name = name;
        this.number = 10;
    }

    public OioFilePersistString(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public List<String> read() throws IOException {
        File file = getFile();
        final List<String> datas = new ArrayList<>(1000);
        if (!file.exists()) {
            return datas;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                datas.add(line);
            }
        } catch (IOException e) {
            LOGGER.error("读取{}持久化文件异常", name, e);
        }
        return datas;
    }

    @Override
    public void write(List<String> datas) {
        File file = getFile();
        try (FileOutputStream fos = new FileOutputStream(file);) {
            datas.forEach(str -> {
                try {
                    fos.write((str + "\n").getBytes());
                } catch (IOException e) {
                    LOGGER.error("{}数据写入{}持久化文件异常", str, name, e);
                }
            });
        } catch (IOException e) {
            LOGGER.error("写入{}持久化文件异常", name, e);
            Thread.currentThread().interrupt();
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
