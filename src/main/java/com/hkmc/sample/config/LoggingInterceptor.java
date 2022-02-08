package com.hkmc.sample.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP통신시에 request 보기 위한 설정
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("[AFTER-COMPLETION]");
        printHttpLog(request, response);
    }
    /**
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[PRE-HANDLE]");
        printHttpLog(request, response);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("[POST-HANDLE]");
        printHttpLog(request, response);
    }**/

    private void printHttpLog(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContentCachingRequestWrapper cachingRequestWrapper = (ContentCachingRequestWrapper)request;
        ContentCachingResponseWrapper cachingResponseWrapper = (ContentCachingResponseWrapper)response;

        log.info("{} - {} {}\n" +
                        "Headers : {}\n" +
                        "Request : {}\n" +
                        "Response : {}\n",
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                getHeaders(request),
                getRequestBody(cachingRequestWrapper),
                getResponseBody(cachingResponseWrapper)
        );
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();

        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    private String getRequestBody(ContentCachingRequestWrapper wrapper) throws Exception {
        if (wrapper != null) {
            if(wrapper.getContentType() != null && wrapper.getContentType().contains("application/json")){
                if(wrapper.getContentAsByteArray() != null && wrapper.getContentAsByteArray().length !=0){
                    return String.valueOf(objectMapper.readTree(wrapper.getContentAsByteArray()));
                }
            }
        }
        return " - ";
    }

    private String getResponseBody(ContentCachingResponseWrapper wrapper) throws Exception {
        if (wrapper != null) {
            if(wrapper.getContentType() != null && wrapper.getContentType().contains("application/json")){
                if(wrapper.getContentAsByteArray() != null && wrapper.getContentAsByteArray().length !=0){
                    return String.valueOf(objectMapper.readTree(wrapper.getContentAsByteArray()));
                }
            }
        }
        return " - ";
    }
}
