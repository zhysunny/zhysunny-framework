package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.FileTransfer;
import java.io.File;

/**
 * @author 章云
 * @date 2020/1/14 10:25
 */
public class NioTransferString extends FileTransfer<String> {

    public NioTransferString(File file) {
        super(file);
        this.fileReadWrite = new NioFileReadWrite(file);
    }

    public NioTransferString(String filepath) {
        this(new File(filepath));
    }

}
