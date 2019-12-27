package com.zhysunny.framework.kafka.business.input.impl;

import com.zhysunny.framework.kafka.business.input.Input;
import com.zhysunny.framework.kafka.business.output.impl.NioFileOutputString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.*;

/**
 * 读取文件
 * @author 章云
 * @date 2019/12/27 15:47
 */
public class NioFileInputString implements Input<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NioFileOutputString.class);

    private File file;

    public NioFileInputString(File file) {
        this.file = file;
    }

    @Override
    public List<String> input() {
        final List<String> datas = new ArrayList<>(1000);
        if (!file.exists()) {
            return datas;
        }
        try {
            datas.addAll(Files.lines(Paths.get(file.getAbsolutePath())).collect(toList()));
        } catch (IOException e) {
            LOGGER.error("读取{}持久化文件异常", file.getAbsolutePath(), e);
        }
        return datas;
    }

}
