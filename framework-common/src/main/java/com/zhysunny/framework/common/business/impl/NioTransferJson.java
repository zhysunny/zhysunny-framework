package com.zhysunny.framework.common.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhysunny.framework.common.business.FileTransfer;
import java.io.File;

/**
 * @author 章云
 * @date 2020/1/14 10:25
 */
public class NioTransferJson extends FileTransfer<JSONObject> {

    public NioTransferJson(File file) {
        super(file);
        this.fileReadWrite = new NioFileReadWriteJson(file);
    }

    public NioTransferJson(String filepath) {
        this(new File(filepath));
    }

}
