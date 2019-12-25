package com.zhysunny.framework.common.conf.xml;

import com.zhysunny.framework.common.conf.BaseWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Properties;

/**
 * 实体类写入xml
 * @author 章云
 * @date 2019/7/25 11:14
 */
public class XmlWriter extends BaseWriter {

    /**
     * 输出流
     */
    private Writer out;

    public XmlWriter(String path) {
        super(path);
    }

    public XmlWriter(File file) {
        super(file);
    }

    public void write(BaseAnyToXml baseAnyToXml, Object... params) throws Exception {
        out = new OutputStreamWriter(new FileOutputStream(file));
        baseAnyToXml.write(out, params);
        out.close();
    }

    public void write(Properties props) throws Exception {
        write(new PropertiesToXml(), props);
    }

    public void write(XmlBean bean, String encoding) throws Exception {
        write(new BeanToXml(), bean, encoding);
    }

    public void write(XmlBean bean) throws Exception {
        write(bean, ENCODING);
    }

}
