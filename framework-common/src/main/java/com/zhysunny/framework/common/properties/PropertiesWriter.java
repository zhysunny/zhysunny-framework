package com.zhysunny.framework.common.properties;

import com.zhysunny.framework.common.conf.BaseWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Properties文件输出类
 * @author 章云
 * @date 2019/7/27 14:08
 */
public class PropertiesWriter extends BaseWriter {

    public PropertiesWriter(String path) {
        super(path);
    }

    public PropertiesWriter(File file) {
        super(file);
    }

    /**
     * 默认不追加写入
     * @param props
     * @param comment
     * @throws Exception
     */
    public void write(Properties props, String comment) throws Exception {
        write(props, comment, false);
    }

    /**
     * 写入Properties文件
     * @param props
     * @param comment
     * @param append
     * @throws Exception
     */
    public void write(Properties props, String comment, boolean append) throws Exception {
        FileOutputStream fos = new FileOutputStream(file, append);
        props.store(fos, comment);
        fos.close();
    }

}
