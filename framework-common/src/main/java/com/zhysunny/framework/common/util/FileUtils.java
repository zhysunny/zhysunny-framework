package com.zhysunny.framework.common.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 文件类操作工具
 * @author 章云
 * @date 2019/7/27 20:52
 */
public class FileUtils {

    private static final int LENGTH = 8192;

    /**
     * 递归删除文件
     * @param file
     * @throws IOException
     */
    public static void deleteAllFile(File file) {
        if (file.isFile()) {
            try {
                Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
            } catch (IOException e) {
            }
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.exists()) {
                        deleteAllFile(f);
                    }
                }
                try {
                    Files.deleteIfExists(Paths.get(file.getAbsolutePath()));
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 扫描多级目录下的文件
     * @param path    主目录
     * @param files   文件集合
     * @param suffixs 文件后缀（可以写多个）
     */
    public static void getFiles(File path, List<String> files, String... suffixs) {
        if (path.isFile()) {
            for (String suffix : suffixs) {
                if (path.getName().endsWith(suffix)) {
                    files.add(path.getAbsolutePath());
                    break;
                }
            }
        } else {
            String[] names = path.list();
            File file = null;
            for (String name : names) {
                file = new File(path, name);
                getFiles(file, files, suffixs);
            }
        }
    }

    public static void rollback(File file, int number) {
        number = number < 2 ? 10 : number;
        int i = number - 1;
        File rollbackFile = new File(file.getAbsolutePath() + "." + i);
        if (rollbackFile.delete()) {
        }
        for (i = number - 2; i >= 0; i--) {
            rollbackFile = new File(file.getAbsolutePath() + "." + i);
            if (rollbackFile.renameTo(new File(file.getAbsolutePath() + "." + (i + 1)))) {
            }
        }
        if (file.renameTo(new File(file.getAbsolutePath() + ".0"))) {
        }
    }

    /* ################流操作#################### */

    /**
     * 关闭流
     * @param streams
     */
    public static void close(Closeable... streams) {
        for (Closeable stream : streams) {
            try {
                if (null != stream) {
                    stream.close();
                }
            } catch (IOException e) {
            }
        }
    }

}
