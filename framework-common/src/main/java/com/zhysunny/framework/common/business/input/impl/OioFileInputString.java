package com.zhysunny.framework.common.business.input.impl;

import com.zhysunny.framework.common.business.input.FileInput;
import com.zhysunny.framework.common.file.OioFileReadWrite;
import java.io.File;

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
