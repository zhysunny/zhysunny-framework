package com.zhysunny.framework.common.properties;

import org.junit.*;
import java.util.Properties;

/**
 * PropertiesWriter Test.
 * @author 章云
 * @date 2019/7/27 14:21
 */
public class PropertiesWriterTest {

    private String input;
    private String output;

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test PropertiesWriter Class Start...");
    }

    @Before
    public void before() throws Exception {
        this.input = "src/test/resources/properties/input.properties";
        this.output = "src/test/resources/properties/output.properties";
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test PropertiesWriter Class End...");
    }

    /**
     * Method: write(Properties prop, String comment)
     */
    @Test
    public void testWriteForPropComment() throws Exception {
        Properties props = new PropertiesReader(input).builder().getProps();
        System.out.println(props);
        PropertiesWriter writer = new PropertiesWriter(output);
        writer.write(props, "props test");
    }

}
