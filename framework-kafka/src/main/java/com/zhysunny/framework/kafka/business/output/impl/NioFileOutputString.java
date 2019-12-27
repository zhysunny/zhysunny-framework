package com.zhysunny.framework.kafka.business.output.impl;

import com.zhysunny.framework.kafka.business.output.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import static java.util.stream.Collectors.*;

/**
 * 输出到文件
 * @author 章云
 * @date 2019/12/27 15:03
 */
public class NioFileOutputString implements Output<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NioFileOutputString.class);

    private File file;

    public NioFileOutputString(File file) {
        this.file = file;
    }

    @Override
    public void output(List<String> datas) {
        try {
            Files.write(Paths.get(file.getAbsolutePath()), datas.stream().collect(toList()));
        } catch (IOException e) {
            LOGGER.error("写入{}持久化文件异常", file.getAbsolutePath(), e);
        }
    }

}
