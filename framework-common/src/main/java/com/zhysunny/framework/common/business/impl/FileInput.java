package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.Input;
import com.zhysunny.framework.common.file.FileReadWrite;
import java.io.IOException;
import java.util.List;

/**
 * 文件输入
 * @author 章云
 * @date 2019/12/27 21:27
 */
public class FileInput<E> implements Input<E> {

    protected FileReadWrite fileReadWrite;

    public FileInput(FileReadWrite fileReadWrite) {
        this.fileReadWrite = fileReadWrite;
    }

    @Override
    public final List<E> input() throws IOException {
        return fileReadWrite.read();
    }

}
