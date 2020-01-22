package com.zhysunny.framework.common.business.output.impl;

import com.zhysunny.framework.common.business.output.Output;
import com.zhysunny.framework.common.file.FileReadWrite;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 文件输出
 * @author 章云
 * @date 2019/12/27 15:02
 */
public class FileOutput<E> implements Output<E> {

    protected FileReadWrite fileReadWrite;

    public FileOutput(FileReadWrite fileReadWrite) {
        this.fileReadWrite = fileReadWrite;
    }

    @Override
    public void write(List<E> datas) throws IOException {
        fileReadWrite.write(datas);
    }

    @Override
    public void write(Map<String, E> datas) throws IOException {
        fileReadWrite.write(datas);
    }

}
