package com.zhysunny.framework.kafka.business.input.impl;

import com.zhysunny.framework.common.file.NioFileReadWrite;
import com.zhysunny.framework.kafka.business.input.FileInput;
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
public class NioFileInputString extends FileInput<String> {

    public NioFileInputString(File file) {
        super(file);
        this.fileReadWrite = new NioFileReadWrite(file);
    }

    public NioFileInputString(String filepath) {
        super(filepath);
        this.fileReadWrite = new NioFileReadWrite(file);
    }

}
