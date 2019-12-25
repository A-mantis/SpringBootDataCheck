package com.zero.check;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.beans.Transient;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DateCheckServiceApplicationTests {

    //声明request变量
    private MockHttpServletRequest request;

    @Before
    public void init() throws IllegalAccessException, NoSuchFieldException {
        System.out.println("开始测试-----------------");
        request = new MockHttpServletRequest();
    }

    @Test
    public void test() {

    }

    public MockHttpServletRequest getRequest() {
        return request;
    }
}
