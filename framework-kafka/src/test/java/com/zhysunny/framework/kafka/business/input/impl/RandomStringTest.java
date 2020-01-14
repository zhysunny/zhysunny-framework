package com.zhysunny.framework.kafka.business.input.impl;

import com.zhysunny.framework.common.business.Transfer;
import com.zhysunny.framework.common.business.impl.RandomStringInput;
import org.junit.*;
import java.util.List;
import static org.junit.Assert.*;

/**
 * RandomString Test.
 * @author 章云
 * @date 2019/12/27 16:37
 */
public class RandomStringTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        System.out.println("Test RandomString Class Start...");
    }

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @AfterClass
    public static void afterClass() throws Exception {
        System.out.println("Test RandomString Class End...");
    }

    /**
     * Method: input()
     */
    @Test
    public void testInput() throws Exception {
        int total = 10;
        int size = 1024;
        Transfer<String> input = new RandomStringInput(total, size);
        List<String> datas = input.input();
        assertEquals(datas.size(), total);
        datas.forEach(str -> assertEquals(str.length(), size));
    }

} 
