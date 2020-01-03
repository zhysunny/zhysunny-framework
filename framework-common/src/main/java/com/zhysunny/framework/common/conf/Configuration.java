package com.zhysunny.framework.common.conf;

import com.zhysunny.framework.common.properties.PropertiesReader;
import com.zhysunny.framework.common.util.UnitUtils;
import com.zhysunny.framework.common.xml.XmlReader;
import com.zhysunny.framework.common.xml.XmlToProperties;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * 可以读写xml和properties文件配置，分为默认配置与最终配置
 * @author 章云
 * @date 2019/7/30 8:55
 */
public class Configuration {

    private ArrayList<Object> defaultResources = new ArrayList<Object>();
    private ArrayList<Object> finalResources = new ArrayList<Object>();
    /**
     * 配置集合
     */
    private Properties props;

    private static class Inner {

        public static final Configuration INSTANCE = new Configuration();

    }

    /**
     * 单例
     */
    private Configuration() {
    }

    public static Configuration getInstance() {
        return Inner.INSTANCE;
    }

    public Configuration addDefaultResource(String name) {
        addResource(defaultResources, name);
        return this;
    }

    public Configuration addDefaultResource(File file) {
        addResource(defaultResources, file);
        return this;
    }

    public Configuration addDefaultResource(URL url) {
        addResource(defaultResources, url);
        return this;
    }

    public Configuration addDefaultResource(InputStream is) {
        addResource(defaultResources, is);
        return this;
    }

    public Configuration addFinalResource(String name) {
        addResource(finalResources, name);
        return this;
    }

    public Configuration addFinalResource(File file) {
        addResource(finalResources, file);
        return this;
    }

    public Configuration addFinalResource(URL url) {
        addResource(finalResources, url);
        return this;
    }

    public Configuration addFinalResource(InputStream is) {
        addResource(finalResources, is);
        return this;
    }

    /**
     * 添加资源并初始化配置集合
     * @param resources
     * @param resource
     */
    private synchronized void addResource(ArrayList<Object> resources, Object resource) {
        resources.add(resource);
        props = null;
    }

    private Configuration builder() {
        props = new Properties();
        loadResources(props, defaultResources);
        loadResources(props, finalResources);
        props = new PropertiesReader(props).translate().getProps();
        return this;
    }

    /**
     * 加载xml配置到Properties
     * @param props
     * @param resources
     */
    private void loadResources(Properties props, ArrayList<Object> resources) {
        for (Object obj : resources) {
            if (obj.toString().endsWith(".xml")) {
                try {
                    Properties prop = (Properties)new XmlReader(obj).read(new XmlToProperties());
                    props.putAll(prop);
                } catch (Exception e) {
                    throw new RuntimeException("配置文件加载异常：" + obj);
                }
            } else if (obj.toString().endsWith(".properties")) {
                try {
                    Properties prop = new PropertiesReader(obj).builder().getProps();
                    props.putAll(prop);
                } catch (Exception e) {
                    throw new RuntimeException("配置文件加载异常：" + obj);
                }
            } else {
                throw new RuntimeException("资源配置文件只支持xml和properties");
            }
        }
    }

    /************************* Object *******************/

    public Object get(String name) {
        return getProps().get(name);
    }

    public Object get(String name, Object defaultValue) {
        Object value = get(name);
        return value == null ? defaultValue : value;
    }

    /************************* Capacity *******************/

    public long getCapacity(String name, String defaultValue) {
        String value = getString(name, defaultValue);
        return UnitUtils.getCapacity(value).longValue();
    }

    public long getCapacity(String name) {
        return getCapacity(name, "0");
    }

    /************************* String *******************/

    public void set(String name, String value) {
        setString(name, value);
    }

    public void setString(String name, String value) {
        getProps().setProperty(name, value);
    }

    public String getString(String name) {
        return getProps().getProperty(name);
    }

    public String getString(String name, String defaultValue) {
        return getProps().getProperty(name, defaultValue);
    }

    /************************* byte *******************/

    public byte getByte(String name) {
        return getByte(name, (byte)0);
    }

    public byte getByte(String name, byte defaultValue) {
        String value = getString(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Byte.parseByte(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void setByte(String name, byte value) {
        set(name, Byte.toString(value));
    }

    /************************* short *******************/

    public short getShort(String name) {
        return getShort(name, (short)0);
    }

    public short getShort(String name, short defaultValue) {
        String value = getString(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Short.parseShort(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void setShort(String name, short value) {
        set(name, Short.toString(value));
    }

    /************************* int *******************/

    public int getInt(String name) {
        return getInt(name, 0);
    }

    public int getInt(String name, int defaultValue) {
        String value = getString(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void setInt(String name, int value) {
        set(name, Integer.toString(value));
    }

    /************************* long *******************/

    public long getLong(String name) {
        return getLong(name, 0L);
    }

    public long getLong(String name, long defaultValue) {
        String value = getString(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void setLong(String name, long value) {
        set(name, Long.toString(value));
    }

    /************************* float *******************/

    public float getFloat(String name) {
        return getFloat(name, 0.0f);
    }

    public float getFloat(String name, float defaultValue) {
        String value = getString(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void setFloat(String name, float value) {
        set(name, Float.toString(value));
    }

    /************************* double *******************/
    public double getDouble(String name) {
        return getDouble(name, 0.0d);
    }

    public double getDouble(String name, double defaultValue) {
        String value = getString(name);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void setDouble(String name, double value) {
        set(name, Double.toString(value));
    }

    /************************* boolean *******************/
    public boolean getBoolean(String name) {
        return getBoolean(name, Boolean.FALSE);
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        String value = getString(name);
        if ("true".equalsIgnoreCase(value)) {
            return Boolean.TRUE;
        } else if ("false".equalsIgnoreCase(value)) {
            return Boolean.FALSE;
        } else {
            return defaultValue;
        }
    }

    public void setBoolean(String name, boolean value) {
        set(name, Boolean.toString(value));
    }

    /************************* Array *******************/

    public String[] getStrings(String name) {
        return getStrings(name, "");
    }

    public String[] getStrings(String name, String defaultValue) {
        String value = getString(name);
        if (value == null) {
            value = defaultValue;
        }
        if (value == null || value.length() == 0) {
            return new String[0];
        }
        StringTokenizer tokenizer = new StringTokenizer(value, ", \t\n\r\f");
        List<String> values = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            values.add(tokenizer.nextToken());
        }
        return values.toArray(new String[values.size()]);
    }

    /************************* Class *******************/

    public Class getClass(String name, Class defaultValue) {
        String value = getString(name);
        if (value == null || value.length() == 0) {
            return defaultValue;
        }
        try {
            return Class.forName(value);
        } catch (ClassNotFoundException e) {
            return defaultValue;
        }
    }

    /**
     * 增加父类限制
     * @param name
     * @param defaultValue
     * @param xface
     * @return
     */
    public Class getClass(String name, Class defaultValue, Class xface) {
        try {
            Class theClass = getClass(name, defaultValue);
            // 如果xface不是theClass的超类，抛出异常
            if (theClass != null && !xface.isAssignableFrom(theClass)) {
                throw new RuntimeException(theClass + " not " + xface.getName());
            }
            return theClass;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得配置集合对象
     * @return
     */
    private synchronized Properties getProps() {
        if (props == null) {
            builder();
        }
        return props;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Configuration: \n\t");
        sb.append("defaults: ");
        toString(defaultResources, sb);
        sb.append("\n\t");
        sb.append("final: ");
        toString(finalResources, sb);
        return sb.toString();
    }

    private void toString(ArrayList<Object> resources, StringBuffer sb) {
        ListIterator<Object> i = resources.listIterator();
        while (i.hasNext()) {
            if (i.nextIndex() != 0) {
                sb.append(" , ");
            }
            Object obj = i.next();
            if (obj instanceof File) {
                sb.append(obj);
            } else {
                sb.append((String)obj);
            }
        }
    }

    /**
     * 给常量类设置默认值
     * @param clz
     */
    public void setDefaultValue(Class<?> clz) {
        // 所有公共字段
        Field[] fields = clz.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            DefaultValue annotation = field.getAnnotation(DefaultValue.class);
            if (annotation == null) {
                continue;
            }
            // 注解配置的默认值
            String defaultValue = annotation.value();
            String key = null;
            try {
                // properties配置的key值
                key = (String)field.get(clz);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            String value = props.getProperty(key);
            if (value == null || value.trim().length() == 0) {
                props.setProperty(key, defaultValue);
            }
        }
    }

}
