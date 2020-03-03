package com.zhysunny.framework.common.file;

import com.zhysunny.framework.common.util.FileUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

/**
 * 读写基础框架
 * @author 章云
 * @date 2019/12/27 20:48
 */
public abstract class BaseFileReadWrite<E> implements FileReadWrite<E> {

    private final File file;
    private BufferedReader br;
    private FileOutputStream fos;
    private final boolean append;

    public BaseFileReadWrite(File file, boolean append) {
        this.file = file;
        this.append = append;
    }

    public BaseFileReadWrite(File file) {
        this(file, false);
    }

    public BaseFileReadWrite(String filepath) {
        this(new File(filepath), false);
    }

    public BaseFileReadWrite(String filepath, boolean append) {
        this(new File(filepath), append);
    }

    @Override
    public List<E> read() throws IOException {
        final List<E> datas = new ArrayList<>();
        if (!file.exists()) {
            return datas;
        }
        if (br == null) {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        }
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                datas.add(toAny(line));
            }
        } catch (IOException e) {
            throw e;
        } finally {
            close();
        }
        return datas;
    }

    @Override
    public E readLine() throws IOException {
        if (!file.exists()) {
            return null;
        }
        if (br == null) {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        }
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                return toAny(line);
            }
        } catch (IOException e) {
            close();
            throw e;
        }
        return null;
    }

    @Override
    public Object write(List<E> datas) throws IOException {
        if (fos == null) {
            fos = new FileOutputStream(file, append);
        }
        try {
            for (E data : datas) {
                if (data == null) {
                    continue;
                }
                fos.write((toString(data) + '\n').getBytes());
            }
        } catch (IOException e) {
            close();
            throw e;
        }
        return datas.size();
    }

    @Override
    public Object write(Map<String, E> datas) throws IOException {
        List<E> collect = datas.entrySet().stream().map(entry -> entry.getValue()).collect(toList());
        return write(collect);
    }

    @Override
    public Object write(E data) throws IOException {
        if (fos == null) {
            fos = new FileOutputStream(file, append);
        }
        if (data == null) {
            return 0;
        }
        try {
            fos.write((toString(data) + '\n').getBytes());
        } catch (IOException e) {
            close();
            throw e;
        }
        return 1;
    }

    @Override
    public void flush() throws IOException {
        fos.flush();
    }

    @Override
    public void reset() {
        close();
        delete();
        fos = null;
    }

    @Override
    public File getFile() {
        return this.file;
    }

    @Override
    public void close() {
        FileUtils.close(br, fos);
        br = null;
        fos = null;
    }

    /**
     * 删除文件
     */
    public final void delete() {
        FileUtils.deleteAllFile(file);
    }

    /**
     * 回滚文件
     * @param num
     */
    public final void rollback(int num) {
        FileUtils.rollback(file, num);
    }

    /**
     * 读数据用于将String转为需要的泛型
     * @param data
     * @return
     */
    protected abstract E toAny(String data);

    /**
     * 写数据时用于将泛型转成String
     * @param e
     * @return
     */
    protected abstract String toString(E e);

}
