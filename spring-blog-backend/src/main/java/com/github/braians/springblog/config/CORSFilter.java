package com.github.braians.springblog.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

    public CORSFilter(){}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
       HttpServletResponse res = (HttpServletResponse) response;
       HttpServletRequest req = (HttpServletRequest) request;
       res.setHeader("Access-Control-Allow-Origin", "*");
       res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
       res.setHeader("Access-Control-Max-Age", "3600");
       res.setHeader("Access-Control-Allow-Header","access_token, authorization,content-type");

       if("OPTIONS".equalsIgnoreCase(req.getMethod())){
           res.setStatus(HttpServletResponse.SC_OK);
       } else {
           chain.doFilter(req, res);
       }
        
    }
    
}
