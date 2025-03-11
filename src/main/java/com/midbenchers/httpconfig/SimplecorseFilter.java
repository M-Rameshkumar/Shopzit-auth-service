package com.midbenchers.httpconfig;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SimplecorseFilter implements Filter {

    @Value("${app.client.url}")
    private String clientApiUrl = "";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        Map<String, String> map = new HashMap<>();

        // Allow requests from frontend origin
        String originHeader = request.getHeader("Origin");
        if (originHeader != null && (originHeader.equals(clientApiUrl) || originHeader.equals("http://localhost:5173"))) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
        } else {
            response.setHeader("Access-Control-Allow-Origin", "*");  // Allow all origins if not specified (for testing purposes)
        }

        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");  // Allow cookies and auth headers

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {}
}


