package com.zhysunny.framework.common.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

/**
 * 阻塞IO读写
 * @author 章云
 * @date 2019/12/27 20:48
 */
public class OioFileReadWrite implements FileReadWrite<String> {

    private File file;
    private boolean append;

    public OioFileReadWrite(File file, boolean append) {
        this.file = file;
        this.append = append;
    }

    public OioFileReadWrite(File file) {
        this(file, false);
    }

    public OioFileReadWrite(String filepath) {
        this(new File(filepath), false);
    }

    public OioFileReadWrite(String filepath, boolean append) {
        this(new File(filepath), append);
    }

    @Override
    public List<String> read() throws IOException {
        final List<String> datas = new ArrayList<>();
        if (!file.exists()) {
            return datas;
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                    continue;
                }
                datas.add(line);
            }
        } catch (IOException e) {
            throw e;
        }
        return datas;
    }

    @Override
    public void write(List<String> datas) throws IOException {
        try (final FileOutputStream fos = new FileOutputStream(file, append)) {
            for (String data : datas) {
                fos.write((data + "\n").getBytes());
            }
        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public void write(Map<String, String> datas) throws IOException {
        List<String> collect = datas.entrySet().stream().map(entry -> entry.getKey() + "\t" + entry.getValue()).collect(toList());
        write(collect);
    }

}
