package com.zhysunny.framework.common.business.impl;

import com.zhysunny.framework.common.business.Output;
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
    public Object write(List<E> datas) throws IOException {
        return fileReadWrite.write(datas);
    }

    @Override
    public Object write(Map<String, E> datas) throws IOException {
        return fileReadWrite.write(datas);
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

}
