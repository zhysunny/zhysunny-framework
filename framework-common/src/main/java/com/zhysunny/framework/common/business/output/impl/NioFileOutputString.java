package com.zhysunny.framework.common.business.output.impl;

import com.zhysunny.framework.common.business.output.FileOutput;
import com.zhysunny.framework.common.file.NioFileReadWrite;
import java.io.File;

/**
 * 输出到文件
 * @author 章云
 * @date 2019/12/27 15:03
 */
public class NioFileOutputString extends FileOutput<String> {

    public NioFileOutputString(File file) {
        super(file);
        this.fileReadWrite = new NioFileReadWrite(file);
    }

    public NioFileOutputString(String filepath) {
        super(filepath);
        this.fileReadWrite = new NioFileReadWrite(file);
    }

}
