package com.zhysunny.framework.kafka.business.input.impl;

import com.zhysunny.framework.kafka.business.input.Input;
import com.zhysunny.framework.kafka.business.output.impl.NioFileOutputString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取文件
 * @author 章云
 * @date 2019/12/27 15:47
 */
public class OioFileInputString implements Input<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NioFileOutputString.class);

    private File file;

    public OioFileInputString(File file) {
        this.file = file;
    }

    @Override
    public List<String> input() {
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
            LOGGER.error("读取{}持久化文件异常", file.getAbsolutePath(), e);
        }
        return datas;
    }

}
