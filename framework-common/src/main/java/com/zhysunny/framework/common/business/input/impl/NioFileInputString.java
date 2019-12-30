package com.zhysunny.framework.common.business.input.impl;

import com.zhysunny.framework.common.business.input.FileInput;
import com.zhysunny.framework.common.file.NioFileReadWrite;
import java.io.File;

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
