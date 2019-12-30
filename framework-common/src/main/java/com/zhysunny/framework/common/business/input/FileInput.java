package com.zhysunny.framework.common.business.input;

import com.zhysunny.framework.common.file.FileReadWrite;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 文件输入
 * @author 章云
 * @date 2019/12/27 21:27
 */
public class FileInput<E> implements Input<E> {

    protected File file;
    protected FileReadWrite fileReadWrite;

    public FileInput(File file) {
        this.file = file;
    }

    public FileInput(String filepath) {
        this.file = new File(filepath);
    }

    @Override
    public final List<E> input() throws IOException {
        return fileReadWrite.read();
    }
}
