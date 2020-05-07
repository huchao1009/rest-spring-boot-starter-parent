package com.opensource.component.rest.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * RestTemplate拦截，日志打印请求、响应信息
 */
@Slf4j
public class LoggingClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        tranceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void tranceRequest(HttpRequest request, byte[] body) throws UnsupportedEncodingException {
        log.info("request uri : {}", request.getURI());
        log.info("request headers : {}", request.getHeaders());
        log.info("request body : {}", new String(body, "UTF-8"));
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        log.info("response status code : {}", response.getStatusCode());
        log.info("response body: {}", StreamUtils.copyToString(response.getBody(), Charset.forName("UTF-8")));
    }
}
