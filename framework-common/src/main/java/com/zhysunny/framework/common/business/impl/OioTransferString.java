package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.FileTransfer;
import com.zhysunny.framework.common.file.OioFileReadWrite;
import java.io.File;

/**
 * @author 章云
 * @date 2020/1/14 10:23
 */
public class OioTransferString extends FileTransfer<String> {

    public OioTransferString(File file) {
        super(file);
        this.fileReadWrite = new OioFileReadWrite(file);
    }

    public OioTransferString(String filepath) {
        this(new File(filepath));
    }

}
