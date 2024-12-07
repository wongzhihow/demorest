package com.example.demorest.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;

import static java.util.Collections.list;

@Component
public class LoggingFilterBean extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilterBean.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = requestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = responseWrapper(response);

        chain.doFilter(requestWrapper, responseWrapper);

        logRequest(requestWrapper);
        logResponse(responseWrapper);
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(headersToString(list(request.getHeaderNames()), request::getHeader));
        boolean isRequestQueryStringExist = request.getQueryString() == null;

        stringBuilder.append(request.getMethod()).append(" ");
        stringBuilder.append(request.getContentType()).append(" ");
        stringBuilder.append(request.getRequestURL());
        stringBuilder.append(isRequestQueryStringExist ? "" : "?");
        stringBuilder.append(isRequestQueryStringExist ? "" : request.getQueryString());
        stringBuilder.append("\n");
        stringBuilder.append(new String(request.getContentAsByteArray()));
        logger.info("Request: {}", stringBuilder);
    }

    private void logResponse(ContentCachingResponseWrapper response) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(headersToString(response.getHeaderNames(), response::getHeader));

        stringBuilder.append(HttpStatus.valueOf(response.getStatus()));
        stringBuilder.append("\n");
        stringBuilder.append(new String(response.getContentAsByteArray()));
        logger.info("Response: {}", stringBuilder);
        response.copyBodyToResponse();
    }

    private String headersToString(Collection<String> headersNames, Function<String, String> headerValueResolver) {
        StringBuilder stringBuilder = new StringBuilder();

        headersNames.forEach((headerName) -> {
            stringBuilder.append(
                    "%s=%s ".formatted(headerName, headerValueResolver.apply(headerName)));
        });

        return stringBuilder.toString();
    }

    private ContentCachingRequestWrapper requestWrapper(ServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper requestWrapper) {
            return requestWrapper;
        }
        return new ContentCachingRequestWrapper((HttpServletRequest) request);
    }

    private ContentCachingResponseWrapper responseWrapper(ServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper responseWrapper) {
            return responseWrapper;
        }
        return new ContentCachingResponseWrapper((HttpServletResponse) response);
    }
}
