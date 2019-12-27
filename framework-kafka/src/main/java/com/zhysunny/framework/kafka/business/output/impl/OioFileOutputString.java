package com.zhysunny.framework.kafka.business.output.impl;

import com.zhysunny.framework.common.file.OioFileReadWrite;
import com.zhysunny.framework.kafka.business.output.FileOutput;
import java.io.File;

/**
 * 输出到文件
 * @author 章云
 * @date 2019/12/27 15:03
 */
public class OioFileOutputString extends FileOutput<String> {

    public OioFileOutputString(File file) {
        super(file);
        this.fileReadWrite = new OioFileReadWrite(file);
    }

    public OioFileOutputString(String filepath) {
        super(filepath);
        this.fileReadWrite = new OioFileReadWrite(file);
    }

}
