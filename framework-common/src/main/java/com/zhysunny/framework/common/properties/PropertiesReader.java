package com.zhysunny.framework.common.properties;

import com.zhysunny.framework.common.conf.BaseReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Properties文件读取类
 * @author 章云
 * @date 2019/7/27 11:02
 */
public class PropertiesReader extends BaseReader {

    private Properties props;

    public PropertiesReader(Object... resources) {
        super(resources);
    }

    public PropertiesReader(List<Object> resources) {
        super(resources);
    }

    public PropertiesReader(Properties props) {
        this.props = props;
    }

    @Override
    public PropertiesReader builder() throws Exception {
        try {
            props = new Properties();
            for (Object resource : resources) {
                if (resource instanceof URL) {
                    URL url = (URL)resource;
                    props.load(url.openStream());
                } else if (resource instanceof File) {
                    File file = (File)resource;
                    props.load(new FileInputStream(file));
                } else if (resource instanceof String) {
                    File file = new File((String)resource);
                    URL url = Thread.currentThread().getContextClassLoader().getResource((String)resource);
                    if (url != null) {
                        props.load(url.openStream());
                    } else {
                        props.load(new FileInputStream(file));
                    }
                } else if (resource instanceof InputStream) {
                    InputStream is = (InputStream)resource;
                    props.load(is);
                } else {
                    throw new RuntimeException("不支持的资源配置类型：" + resource.getClass());
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        translate();
        return this;
    }

    private static final Pattern PATTERN = Pattern.compile("\\$\\{([^\\}]+)\\}");

    public PropertiesReader translate() {
        Matcher matcher = null;
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) {
                continue;
            }
            String value = entry.getValue().toString();
            matcher = PATTERN.matcher(value);
            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                String matcherKey = matcher.group();
                matcherKey = matcherKey.substring(2, matcherKey.length() - 1);
                String matchervalue = props.getProperty(matcherKey);
                if (matchervalue != null) {
                    matcher.appendReplacement(buffer, matchervalue);
                }
            }
            matcher.appendTail(buffer);
            props.setProperty(entry.getKey().toString(), buffer.toString());
        }
        return this;
    }

    public Properties getProps() {
        return props;
    }

}
