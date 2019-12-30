package com.zhysunny.framework.common.business.input.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.input.FileInput;
import com.zhysunny.framework.common.file.NioFileReadWriteJson;
import java.io.File;

/**
 * 读取文件
 * @author 章云
 * @date 2019/12/27 15:47
 */
public class NioFileInputJson extends FileInput<JSONObject> {

    public NioFileInputJson(File file) {
        super(file);
        this.fileReadWrite = new NioFileReadWriteJson(file);
    }

    public NioFileInputJson(String filepath) {
        super(filepath);
        this.fileReadWrite = new NioFileReadWriteJson(file);
    }
}
