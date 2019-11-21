package com.zero.check.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description:
 * @author: wei.wang
 * @since: 2019/11/21 22:18
 * @history: 1.2019/11/21 created by wei.wang
 * 跨域配置
 */
@Configuration
public class CrosConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 所有的当前站点的请求地址，都支持跨域访问。
                .addMapping("/**")
                // 当前站点支持的跨域请求类型是什么。
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 是否支持跨域用户凭证
                .allowCredentials(true)
                // 所有的外部域都可跨域访问。 如果是localhost则很难配置，因为在跨域请求的时候，外部域的解析可能是localhost、127.0.0.1、主机名
                .allowedOrigins("*")
                // 超时时长设置为60秒。 时间单位是秒。
                .maxAge(60);
    }

}