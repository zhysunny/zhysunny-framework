package com.zhysunny.framework.common.conf.xml;

import com.zhysunny.framework.common.conf.xml.bean.Topics;
import org.junit.*;
import java.io.File;
import java.net.URL;
import static org.junit.Assert.*;

/**
 * XmlReader Test.
 * @author 章云
 * @date 2019/7/24 16:29
 */
public class XmlReaderTest {

    private String resource;
    private String path;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test XmlReader Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.resource = "xml/input.xml";
        this.path = "src/test/resources/" + resource;
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test XmlReader Class End...");
    }

    @Test
    public void testConstructor() {
        try {
            XmlReader reader = null;
            reader = new XmlReader(path).builder();
            assertNotNull(reader.getDocument());
            File file = new File(path);
            reader = new XmlReader(file).builder();
            assertNotNull(reader.getDocument());
            URL url = XmlReaderTest.class.getClassLoader().getResource(resource);
            reader = new XmlReader(url).builder();
            assertNotNull(reader.getDocument());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method: read(Class<?> clz)
     */
    @Test
    public void testReadForBean() throws Exception {
        Topics topics = new XmlReader(path).read(Topics.class);
        assertTrue(topics.getTopics().size() > 0);
        System.out.println(topics);
    }

}
