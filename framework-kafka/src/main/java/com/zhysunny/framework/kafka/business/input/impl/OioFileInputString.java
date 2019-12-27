package com.zhysunny.framework.kafka.business.input.impl;

import com.zhysunny.framework.common.file.OioFileReadWrite;
import com.zhysunny.framework.kafka.business.input.FileInput;
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
public class OioFileInputString extends FileInput<String> {

    public OioFileInputString(File file) {
        super(file);
        this.fileReadWrite = new OioFileReadWrite(file);
    }

    public OioFileInputString(String filepath) {
        super(filepath);
        this.fileReadWrite = new OioFileReadWrite(file);
    }

}
