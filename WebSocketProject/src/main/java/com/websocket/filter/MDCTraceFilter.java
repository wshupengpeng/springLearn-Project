package com.websocket.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 80005308
 */
@Component
public class MDCTraceFilter implements Filter {

    public static final String TRACE_ID = "traceId";

    public MDCTraceFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            this.doFilterMdc((HttpServletRequest) request, (HttpServletResponse) response, chain);
        } finally {
            MDC.remove(TRACE_ID);
        }

    }

    @Override
    public void destroy() {

    }

    private void doFilterMdc(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = request.getHeader(TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUID.randomUUID().toString().trim().toLowerCase().replaceAll("-", "");
            MDC.put(TRACE_ID, traceId);
        }
        response.setHeader(TRACE_ID, traceId);
        filterChain.doFilter(request, response);
    }
}
