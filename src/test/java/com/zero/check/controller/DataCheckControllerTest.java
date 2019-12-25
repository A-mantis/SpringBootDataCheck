package com.zero.check.controller;

import com.zero.check.DateCheckServiceApplicationTests;
import com.zero.check.filter.ChannelFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * @Description:
 * @author: wei.wang
 * @since: 2019/12/26 0:11
 * @history: 1.2019/12/26 created by wei.wang
 */
@Slf4j
public class DataCheckControllerTest extends DateCheckServiceApplicationTests {

    private MockMvc mockMvc;

    @Autowired
    private DataCheckController dataCheckController;


    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(dataCheckController).addFilter(new ChannelFilter()).build();
    }

    @Test
    public void userValidPost() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/check/userValidPost")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(String.valueOf("{\n" +
                        "    \"id\": \"1\",\n" +
                        "    \"roleId\": 2,\n" +
                        "    \"userList\": [\n" +
                        "        {\n" +
                        "            \"userId\": \"1\",\n" +
                        "            \"userName\": \"2\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))// 预期返回值的媒体类型text/plain;charset=UTF-8
                .andReturn();
    }

    @Test
    public void userValidGet() {
    }

    @Test
    public void testAspectQueryUserPost() {
    }

    @Test
    public void testInputStream() throws Exception {
        String str = "1234567890";
        //ByteArrayInputStream是把一个byte数组转换成一个字节流
        InputStream inputStream = new FileInputStream("src/main/resources/data/demo.txt");

        //调用这个方法,会影响到下次读取，下次再调用这个方法，读取的起始点会后移5个byte
        inputStream.read(new byte[5]);

        //ByteArrayOutputStream生成对象的时候，是生成一个100大小的byte的缓冲区，写入的时候，是把内容写入内存中的一个缓冲区
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(100);

        int i = 0;
        byte[] b = new byte[100];
        while ((i = inputStream.read(b)) != -1) {
            byteOutput.write(b, 0, i);
        }
        System.out.println(new String(byteOutput.toByteArray()));
        inputStream.close();
    }

    @Test
    public void testRequestInputStream() throws Exception {
        MockHttpServletRequest request = getRequest();
        request.setCharacterEncoding("UTF-8");
        request.setRequestURI("/ts/post");
        request.setMethod("POST");
        request.setContent("1234567890".getBytes());
        InputStream inputStream = request.getInputStream();

        //调用这个方法,会影响到下次读取，下次再调用这个方法，读取的起始点会后移6个byte
        inputStream.read(new byte[6]);
        inputStream.reset();
        //ByteArrayOutputStream生成对象的时候，是生成一个100大小的byte的缓冲区，写入的时候，是把内容写入内存中的一个缓冲区
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(100);

        int i = 0;
        byte[] b = new byte[100];
        while ((i = inputStream.read(b)) != -1) {
            byteOutput.write(b, 0, i);
        }
        System.out.println(new String(byteOutput.toByteArray()));
        inputStream.close();
    }
}