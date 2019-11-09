package com.hqgml.web.servlte;


import com.hqgml.domain.*;
import com.hqgml.service.Impl.LogFindServletImpl;
import com.hqgml.service.LogFindServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@SuppressWarnings("all")
@WebServlet("/log/*")
public class LogServlet extends BaseServlet {
    LogFindServlet logFindServlet = new LogFindServletImpl();

    public void manager_log(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ManagerUser manager = (ManagerUser) req.getSession().getAttribute("manager");
        Object usrper_user = req.getSession().getAttribute("usrper_user");
        String id = req.getParameter("id");
        if (usrper_user == null) {
            if (manager == null || id == null) {
                resp.getWriter().write("你没有查看权限");
                return;
            }else if (!manager.getId().toString().equals(id)){
                resp.getWriter().write("你没有查看权限");
                return;
            }
        }
        String currentPage = req.getParameter("currentPage");//当前页码
//      String rows =request.getParameter("rows");//每页的条数
        String rows = "10";// 每页显示10条写死
        //获取所有的参数，参数用来联想查询的
        Map<String, String[]> parameterMap = req.getParameterMap();
        PageBean<ManagerUser_log> managerUser_logPageBean = logFindServlet.FindlogByPage(currentPage, rows, parameterMap);
        writeValue(managerUser_logPageBean, resp);
    }

    public void Surper_log(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SurperUser usrper_user = (SurperUser) req.getSession().getAttribute("usrper_user");
        if (usrper_user == null) {
            resp.getWriter().write("你没有查看权限");
            return;
        }
        String currentPage = req.getParameter("currentPage");//当前页码
//      String rows =request.getParameter("rows");//每页的条数
        String rows = "10";// 每页显示10条写死
        //获取所有的参数，参数用来联想查询的
        Map<String, String[]> parameterMap = req.getParameterMap();
        PageBean<SurperUser_log> surperUser_logPageBean = logFindServlet.FindSlogByPage(currentPage, rows, parameterMap);
        writeValue(surperUser_logPageBean, resp);
    }
}
