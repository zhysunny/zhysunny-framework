package com.zhysunny.framework.kafka.business.output.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.file.NioFileReadWriteJson;
import com.zhysunny.framework.kafka.business.output.FileOutput;
import java.io.File;

/**
 * 输出到文件
 * @author 章云
 * @date 2019/12/27 15:03
 */
public class NioFileOutputJson extends FileOutput<JSONObject> {

    public NioFileOutputJson(File file) {
        super(file);
        this.fileReadWrite = new NioFileReadWriteJson(file);
    }

    public NioFileOutputJson(String filepath) {
        super(filepath);
        this.fileReadWrite = new NioFileReadWriteJson(file);
    }

}
