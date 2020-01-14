package com.zhysunny.framework.common.business;

import com.zhysunny.framework.common.file.FileReadWrite;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 章云
 * @date 2020/1/14 10:12
 */
public class FileTransfer<E> implements Transfer<E> {

    protected File file;
    protected FileReadWrite fileReadWrite;

    public FileTransfer(File file) {
        this.file = file;
    }

    public FileTransfer(String filepath) {
        this.file = new File(filepath);
    }

    @Override
    public List<E> input() throws IOException {
        return fileReadWrite.read();
    }

    /**
     * 文件输出默认追加方式
     * @param datas
     * @throws IOException
     */
    @Override
    public void output(List<E> datas) throws IOException {
        fileReadWrite.write(datas, true);
    }

    public void output(List<E> datas, boolean append) throws IOException {
        fileReadWrite.write(datas, append);
    }

}
