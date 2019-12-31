package com.zhysunny.framework.common.business.output;

import com.zhysunny.framework.common.file.FileReadWrite;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 文件输出
 * @author 章云
 * @date 2019/12/27 15:02
 */
public abstract class FileOutput<E> implements Output<E> {

    protected File file;
    protected FileReadWrite fileReadWrite;

    public FileOutput(File file) {
        this.file = file;
    }

    public FileOutput(String filepath) {
        this.file = new File(filepath);
    }

    @Override
    public final void output(List<E> datas) throws IOException {
        fileReadWrite.write(datas, true);
    }

}
