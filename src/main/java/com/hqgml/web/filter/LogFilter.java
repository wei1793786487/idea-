package com.hqgml.web.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hqgml.domain.ManagerUser;
import com.hqgml.domain.ResultInfo;
import com.hqgml.domain.SurperUser;
import com.hqgml.service.Impl.OtherActionService;
import com.hqgml.service.Impl.UserServiceImpl;
import com.hqgml.service.UserService;
import com.hqgml.utlis.AddressUtils;
import com.hqgml.utlis.LogUtlis;
import com.hqgml.utlis.Timeutils;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;

/**
 * @data 10/30/2019 11:30 AM
 **/
@SuppressWarnings("all")
@WebFilter(urlPatterns = {"/user/login"})
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
//        System.out.println("我被访问了");
        boolean havecookie = false;
        String logincookie = "";
        //request.setCharacterEncoding（）是设置从request中取得的值或从数据库中取出的值
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if ("wulala".equals(cookie.getName())) {
                havecookie = true;
            logincookie = cookie.getValue();
        }
        }
        if (havecookie) {
            UserService us = new UserServiceImpl();
            ResultInfo uf = new ResultInfo();//创建用来存放各种错误信息的对象
            SurperUser surperUser = us.FindSByCookie(logincookie);
            ManagerUser managerUser = us.FindMByCookie(logincookie);
            OtherActionService oac = new OtherActionService();
            ObjectMapper mapper = new ObjectMapper();
            if (surperUser != null) {
                //超管
                session.setAttribute("usrper_user", surperUser);
                uf.setFlag(true);
                uf.setUser(0);
                //更新最后登录的参数
                oac.Save("surperuser", "lasttime", Timeutils.Gettime(new Date()), "name", surperUser.getName());
                oac.Save("surperuser", "address", AddressUtils.GetAddress(req), "name", surperUser.getName());
                res.setContentType("application/json;charset=utf-8");
                mapper.writeValue(res.getOutputStream(), uf);
                LogUtlis.setlog(session, req, "登录成功");
                return;
            } else if (managerUser != null) {
                //铺管
                System.out.println("通过ccokie登录了");
                session.setAttribute("manager", managerUser);
                uf.setFlag(true);
                uf.setUser(1);
                oac.Save("manageruser", "lasttime", Timeutils.Gettime(new Date()), "name", managerUser.getName());
                oac.Save("manageruser", "address", AddressUtils.GetAddress(req), "name", managerUser.getName());
                res.setContentType("application/json;charset=utf-8");
                mapper.writeValue(res.getOutputStream(), uf);
                LogUtlis.setlog(session, req, "登录成功");
                return;
            } else {
                System.out.println("放型");
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
