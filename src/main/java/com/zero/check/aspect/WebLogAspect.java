package com.zero.check.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import com.zero.check.exception.UserInfoException;
import com.zero.check.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @Description:
 * @author: wei.wang
 * @since: 2019/11/21 13:47
 * @history: 1.2019/11/21 created by wei.wang
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

    private final String REQUEST_GET = "GET";

    private final String REQUEST_POST = "POST";


    /**
     * 定义切点，切点为com.zero.check.controller包和子包里任意方法的执行
     */
    @Pointcut("execution(* com.zero.check.controller..*(..))")
    public void webLog() {

    }

    /**
     * 前置通知，在切点之前执行的通知
     *
     * @param joinPoint 切点
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        //logger.info("Info : " + joinPoint.getSignature().toString());
        //获取请求参数
        try {
            String reqBody = this.getReqBody();
            log.info("REQUEST: {}", reqBody);
        } catch (Exception ex) {
            log.info("get Request Error: {}", ex.getMessage());
        }

    }

//    /**
//     * 前置通知，在切点之前执行的通知
//     *
//     * @param joinPoint 切点
//     */
//    @Before("webLog() &&args(..,bindingResult)")
//    public void doBefore(JoinPoint joinPoint, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            FieldError error = bindingResult.getFieldError();
//            throw new UserInfoException(Response.error(error.getDefaultMessage()).setData(error));
//        }
//        //获取请求参数
//        try {
//            String reqBody = this.getReqBody();
//            logger.info("REQUEST: " + reqBody);
//        } catch (Exception ex) {
//            logger.info("get Request Error: " + ex.getMessage());
//        }
//
//    }

    /**
     * 后置通知，切点后执行
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        //处理完请求，返回内容
        try {
            log.info("RESPONSE: {}", JSON.toJSONString(ret));
        } catch (Exception ex) {
            log.info("get Response Error: {}", ex.getMessage());
        }

    }

    /**
     * 返回调用参数
     *
     * @return ReqBody
     */
    private String getReqBody() {
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = this.getHttpServletRequest();
        //获取请求方法GET/POST
        String method = request.getMethod();
        Optional.ofNullable(method).orElse("UNKNOWN");
        if (REQUEST_POST.equals(method)) {
            return this.getPostReqBody(request);
        } else if (REQUEST_GET.equals(method)) {
            return this.getGetReqBody(request);
        }
        return "get Request Parameter Error";
    }

    /**
     * 获取request
     * Spring对一些（如RequestContextHolder、TransactionSynchronizationManager、LocaleContextHolder等）中非线程安全状态的bean采用ThreadLocal进行处理
     * 让它们也成为线程安全的状态
     *
     * @return
     */
    private HttpServletRequest getHttpServletRequest() {
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    }

    /**
     * 获取GET请求数据
     *
     * @param request
     * @return
     */
    private String getGetReqBody(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, String> parameterMap = new HashMap<>(16);
        while (enumeration.hasMoreElements()) {
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter, request.getParameter(parameter));
        }
        return parameterMap.toString();
    }

    /**
     * 获取POST请求数据
     *
     * @param request
     * @return 返回POST参数
     */
    private String getPostReqBody(HttpServletRequest request) {
        try (InputStream inputStream = request.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, IOUtils.UTF8))) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            log.info("Post Request Parameter Error : {}", e.getMessage());
        }
        return "Post Request Parameter Error";
    }
}
