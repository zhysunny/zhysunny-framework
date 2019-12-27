package com.zhysunny.framework.kafka.business.output.impl;

import com.zhysunny.framework.kafka.business.output.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 输出到文件
 * @author 章云
 * @date 2019/12/27 15:03
 */
public class OioFileOutputString implements Output<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OioFileOutputString.class);

    private File file;

    public OioFileOutputString(File file) {
        this.file = file;
    }

    @Override
    public void output(List<String> datas) {
        try (FileOutputStream fos = new FileOutputStream(file);) {
            datas.forEach(str -> {
                try {
                    fos.write((str + "\n").getBytes());
                } catch (IOException e) {
                    LOGGER.error("{}数据写入{}文件异常", str, file.getAbsolutePath(), e);
                }
            });
        } catch (IOException e) {
            LOGGER.error("写入{}文件异常", file.getAbsolutePath(), e);
            Thread.currentThread().interrupt();
        }
    }

}
