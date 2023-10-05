//package com.zh.awe.security.handler;
//
//import com.zh.awe.security.enums.OrderType;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.security.web.access.intercept.AuthorizationFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//import java.io.IOException;
//
///**
// * @author zh 2023/7/30 13:30
// */
//@Component
//@ConditionalOnMissingBean(DynamicAuthorizationHandler.class)
//public class DynamicAuthorizationHandler extends GenericFilterBean implements CustomFilter {
//
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        if (isApplied(request)) {
//            // Ensure that filter is only applied once per request.
//            chain.doFilter(request, response);
//        } else {
//            // Do invoke this filter...
//            doFilterInternal(request, response, chain);
//            // ... and mark that it has already been applied.
//            request.setAttribute(getAlreadyFilteredAttributeName(), Boolean.TRUE);
//        }
//
//    }
//
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        chain.doFilter(request, response);
//    }
//
//    private boolean isApplied(HttpServletRequest request) {
//        return request.getAttribute(getAlreadyFilteredAttributeName()) != null;
//    }
//
//    private String getAlreadyFilteredAttributeName() {
//        String name = getFilterName();
//        if (name == null) {
//            name = getClass().getName();
//        }
//        return name + ".APPLIED";
//    }
//
//    @Override
//    public FilterProvider custom() {
//        return new FilterProvider(OrderType.Replace, AuthorizationFilter.class,this);
//    }
//}
