//package com.hqgml.web.filter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
///**
// * @data 11/4/2019 10:51 PM
// **/
//@WebFilter(urlPatterns = {"/file", "/log/*", "/Meeting/*", "/user/*", "/video","/Signin/*","/Others/*","/upload/*"})
//public class ActionFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        HttpServletResponse res = (HttpServletResponse) servletResponse;
//        HttpSession session = req.getSession();
//        String requestURI = req.getRequestURI();
//        System.out.println(requestURI);
//        if (requestURI.contains("index.jsp") || requestURI.contains("/user/login") || requestURI.contains("/user/sendMessgae") || requestURI.contains("/css/") ||
//                requestURI.contains("/img/") || requestURI.contains("/js/") || requestURI.contains("/plugins/") || requestURI.contains("/user/Verification") ||
//                requestURI.contains("../index.jsp") || requestURI.contains("/user/register")
//        ) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            Object manager = session.getAttribute("manager");
//            Object usrper_user = session.getAttribute("usrper_user");
//            if (usrper_user!=null){
//                filterChain.doFilter(servletRequest, servletResponse);
//            }else if (manager!=null){
//                if (requestURI.contains("findAllUser")){
//                   res.getWriter().write("你没有这个权限");
//                }else {
//                    //这里应该是过滤访问所有的
//                    filterChain.doFilter(servletRequest, servletResponse);
//                }
//            }
//        }
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
