package com.zq.shiroweb.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Archar on 2018/1/1.
 */
@Slf4j
@RestControllerAdvice
public class SpringExceptionResolver {

    @ExceptionHandler(value = Exception.class)
    public JsonData defaultException(HttpServletRequest request, Exception ex) {
        String url = request.getRequestURL().toString();
        log.error("exception url:{}, message: {}", url, ex.getMessage());
        JsonData result = JsonData.fail(ex.getMessage());
        return result;
    }
}
