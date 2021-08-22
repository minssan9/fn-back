package com.core.common.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
        ClientHttpRequestExecution execution) throws IOException {
        requestLog(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        responseLog(response);

        return response;
    }

    private void requestLog(HttpRequest request, byte[] body) {
        String form =
            "\n====================================REST TEMPLATE REQUEST BEGIN====================================\n"
                + "URI : {}\n"
                + "METHOD : {}\n"
                + "HEADERS : {}\n"
                + "BODY : {}\n"
                + "====================================REST TEMPLATE REQUEST END====================================";

        log.info(form, request.getURI(), request.getMethod(), request.getHeaders(),
            new String(body, StandardCharsets.UTF_8));
    }

    private void responseLog(ClientHttpResponse response) throws IOException {
        String form =
            "\n====================================REST TEMPLATE RESPONSE BEGIN====================================\n"
                + "STATUS CODE : {}\n"
                + "STAUTS TEXT : {}\n"
                + "HEADERS : {}\n"
                + "BODY : {}\n"
                + "====================================REST TEMPLATE RESPONSE END====================================";

        log.info(form, response.getStatusCode(), response.getStatusText(), response.getHeaders(),
            StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
    }
}
