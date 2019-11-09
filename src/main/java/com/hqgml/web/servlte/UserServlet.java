package com.hqgml.web.servlte;


import com.hqgml.domain.*;
import com.hqgml.service.Impl.OtherActionService;
import com.hqgml.service.Impl.UserServiceImpl;
import com.hqgml.service.UserService;
import com.hqgml.utlis.*;
import com.sun.java.swing.plaf.windows.WindowsBorders;
import org.apache.commons.beanutils.BeanUtils;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * 判断用户登录的方法
 */
@SuppressWarnings("all")
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    jsonString jsonString = new jsonString();
    UserService us = new UserServiceImpl();
    private static Properties properties;
    private static String WebUrl;
    //保存时间的方法
    OtherActionService oac = new OtherActionService();
    HttpSession session = null;

    //静态代码块加载配置文件
    static {
        try {
            properties = new Properties();
            properties.load(new InputStreamReader(MsgUtils.class.getClassLoader().getResourceAsStream("Msg.properties"), "GBK"));
            WebUrl = properties.getProperty("WebUrl");
            if (WebUrl == null) {
                WebUrl = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //验证码
    public void Verification(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        //session的获取必须在res之前 不然会报 Cannot create a session after the response has been committed 异常
        session = request.getSession();
        response.setContentType("image/png");
        String s = Getver.outputVerifyImage(115, 35, response.getOutputStream(), 4);
        //看看验证码是啥
        System.out.println(s);
        //存入结果
        session.setAttribute("res", s);
//      System.out.println(s);
    }

    //登录方法
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ResultInfo uf = new ResultInfo();//创建用来存放各种错误信息的对象
//        判断验证码
        String check = request.getParameter("check");
        session = request.getSession();
        String res = (String) session.getAttribute("res");
        session.removeAttribute("res");
//        if (res == null || !res.equalsIgnoreCase(check)) {
//            System.out.println("验证码不正确失败");
//            uf.setFlag(false);
//            uf.setErrorMsg("验证码输入错误");
//            writeValue(uf, response);
//            return;//验证码验证失败存入然后响应到页面
//        }
//        //判断异常信息
//        if (request.getParameter("username") == "") {
//            uf.setFlag(false);
//            uf.setErrorMsg("用户名不能为空");
//            writeValue(uf, response);
//            return;
//        }
//        if (request.getParameter("password") == "") {
//            uf.setFlag(false);
//            uf.setErrorMsg("请填写密码");
//            writeValue(uf, response);
//            return;
//        }
//        if (request.getParameter("verifycode") == "") {
//            uf.setFlag(false);
//            uf.setErrorMsg("验证码不能为空");
//            writeValue(uf, response);
//            return;
//        }
        System.out.println("haha");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5password=null;
        //md5加密
        if (password!=null){
            md5password = Md5Util.encodeByMd5(password);
        }
//        System.out.println(username);
//        System.out.println(password);
        SurperUser surperUser = us.GetSurper(username, md5password);
        ManagerUser managerUser = us.GetManagerUser(username, md5password);
        if (surperUser != null) {
            //超管
            session.setAttribute("usrper_user", surperUser);
            uf.setFlag(true);
            uf.setUser(0);
            //更新最后登录的参数
            oac.Save("surperuser", "lasttime", Timeutils.Gettime(new Date()), "name", surperUser.getName());
            oac.Save("surperuser", "address", AddressUtils.GetAddress(request), "name", surperUser.getName());
            String isremove = request.getParameter("isremove");
            if (isremove != null) {
                String Cookieuuid = UuidUtil.getUuid();
                //            更新cookie
                oac.Save("surperuser", "cookie", Cookieuuid, "name", surperUser.getName());
                Cookie cookie = new Cookie("wulala", Cookieuuid);
                cookie.setMaxAge(60 * 60 * 60 * 24 * 7);
                //设置到根 目录防止cookie有的有 有的没有
                cookie.setPath("/");
                response.addCookie(cookie);
                removeCookie(surperUser.getName(), Cookieuuid, 60 * 60 * 60 * 24 * 7);
            }
            writeValue(uf, response);
            writeValueAsString(uf);
            return;
        } else if (managerUser != null) {
            //铺管
            session.setAttribute("manager", managerUser);
            System.out.println("添加的sess++++++" + session.getAttribute("manager"));
            uf.setFlag(true);
            uf.setUser(1);
            oac.Save("manageruser", "lasttime", Timeutils.Gettime(new Date()), "name", managerUser.getName());
            oac.Save("manageruser", "address", AddressUtils.GetAddress(request), "name", managerUser.getName());
//               Map<String, String[]> parameterMap = request.getParameterMap();
            String isremove = request.getParameter("isremove");
            if (isremove != null) {
                String Cookieuuid = UuidUtil.getUuid();
                //更新cookie
                oac.Save("manageruser", "cookie", Cookieuuid, "name", managerUser.getName());
                Cookie cookie = new Cookie("wulala", Cookieuuid);
                cookie.setMaxAge(60 * 60 * 60 * 24 * 7);
//                cookie.setMaxAge(60);
                //设置到根 目录防止cookie有的有 有的没有
                cookie.setPath("/");
                response.addCookie(cookie);
                removeCookie(managerUser.getName(), Cookieuuid, 60 * 60 * 60 * 24 * 7 * 1000);
//                removeCookie(managerUser.getName(),Cookieuuid,60*1000);

            }
            LogUtlis.setlog(session, request, "登录成功");
            writeValue(uf, response);
            writeValueAsString("返回的json " + uf);

            return;
        } else {
            uf.setFlag(false);
            uf.setErrorMsg("账号或者密码错误");
            writeValue(uf, response);
        }

    }

    //修改密码
    public void chancepswd(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("修改密码服务");
        String _oldpassword = request.getParameter("oldpassword");
        String _newpassword = request.getParameter("newpassword");
        String newpassword2 = request.getParameter("newpassword2");
        String oldpassword = Md5Util.encodeByMd5(_oldpassword);
        String md5newpassword = Md5Util.encodeByMd5(_newpassword);
        if (_newpassword.equals("") || _newpassword == null || newpassword2 == null || newpassword2.equals("") || !_newpassword.equals(newpassword2)) {
            jsonString.setInformation("两次密码不一致,或者新密码输入为空");
            writeValue(jsonString, response);
            return;
        }

        ManagerUser manager = (ManagerUser) session.getAttribute("manager");
        SurperUser usrper_user = (SurperUser) session.getAttribute("usrper_user");
        SurperUser surperUser=null;
        ManagerUser managerUser=null;
//        查询账号密码对不对
        if (usrper_user!=null){
            surperUser = us.GetSurper(usrper_user.getName(), oldpassword);
        }
        if (manager !=null){
             managerUser = us.GetManagerUser(manager.getName(), oldpassword);
        }
        if (surperUser != null) {
            surperUser.setPswd(md5newpassword);
            boolean b = us.upDateS(surperUser);
            if (b) {
//                LogUtlis.setlog(session, request, "修改密码");
                jsonString.setInformation("密码修改成功");
                writeValue(jsonString, response);
                String wulala = getCoookie(request, "wulala");
                if (wulala != null) {
                    us.DelectSCookie(managerUser.getName(), wulala);
                    session.removeAttribute("usrper_user");
                }
            } else {
                LogUtlis.setlog(session, request, "修改密码，但是失败了");
                jsonString.setInformation("密码修改失败,咱也不知道为啥,反正这个情况一般不会出现");
                writeValue(jsonString, response);
            }
        } else if (managerUser != null) {
            //铺管
            //这里需要从session域里面获取对象 并且将新密码添加到session域里面的manger，并传过去
            manager.setPswd(md5newpassword);
            boolean updata = us.Updata(manager);
            if (updata) {
                LogUtlis.setlog(session, request, "修改密码");
                jsonString.setInformation("密码修改成功");
                writeValue(jsonString, response);
                String wulala = getCoookie(request, "wulala");
                if (wulala != null) {
                    us.DelectCookie(managerUser.getName(), wulala);
                    session.removeAttribute("manager");
                }
            } else {
                LogUtlis.setlog(session, request, "修改密码，但是失败了");
                jsonString.setInformation("密码修改失败,咱也不知道为啥,反正这个情况一般不会出现");
                writeValue(jsonString, response);
            }
        } else {
            jsonString.setInformation("密码输入错误");
            writeValue(jsonString, response);
        }

    }

    public void exits(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referer = request.getHeader("referer");
        String isCookie = null;
        if (referer != null) {
            if (referer.contains("managers")) {
                //获取cookie
                isCookie = getCoookie(request, "wulala");
                ManagerUser manager = (ManagerUser) session.getAttribute("manager");
                if (manager != null) {
                    us.DelectCookie(manager.getName(), isCookie);
                    session.removeAttribute("manager");
                }
            }
            if (referer.contains("surper")) {
                //获取cookie
                isCookie = getCoookie(request, "wulala");
                SurperUser usrper_user = (SurperUser) session.getAttribute("usrper_user");
                if (usrper_user != null) {
                    us.DelectSCookie(usrper_user.getName(), isCookie);
                    session.removeAttribute("usrper_user");
                }
            }
        }
        response.getWriter().write("<script>window.location.href ='../index.jsp';</script>");
//        response.sendRedirect(request.getContextPath() + "../index.jsp");

    }

    public void sendMessgae(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //防止瞎几把调用接口
        String referer = request.getHeader("referer");
        System.out.println("短信接口的的调用位置" + referer);
        //根据请求头的参数 只有来自register.jsp才可以调用
        if (referer != null) {
            if (referer.contains(WebUrl + "register.jsp")) {
                //短信接口
                String phone = request.getParameter("phone");
                String vcode = MsgUtils.vcode();
                MsgUtils.SetRegister(phone, vcode);
                session = request.getSession();
                session.setAttribute("phonevcode", vcode);
                removeAttrbute(session, "phonevcode", 5 * 60 * 1000);
            }
        } else {
            System.out.println("非法调用接口");
            response.getWriter().write("<script>alert('当短信端口不花钱的吗？？？');window.history.back(-1);</script>");
        }
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String check = request.getParameter("check");
        if (check != null && name != null) {
            //防止重复用户
            boolean HaveMUser = us.FindUserByName(name);
            boolean HaveSurper = us.FindMUserByName(name);
            if (!HaveMUser && !HaveSurper) {
                String phonevcode = (String) session.getAttribute("phonevcode");
                //判断验证码
                if (phonevcode.equals(check)) {
                    //注册方法
                    Map<String, String[]> parameterMap = request.getParameterMap();
                    ManagerUser managerUser = new ManagerUser();
                    String pswd = request.getParameter("pswd");
                    String md5Pswd = Md5Util.encodeByMd5(pswd);
                    try {
                        BeanUtils.populate(managerUser, parameterMap);
                        managerUser.setPswd(md5Pswd);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    us.SaveUser(managerUser);
                    jsonString.setInformation("1");
                    writeValue(jsonString, response);
                } else {
                    jsonString.setInformation("0");
                    writeValue(jsonString, response);
                }
            } else {
                jsonString.setInformation("3");
                writeValue(jsonString, response);
            }
        }

    }

    public void findAllUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String currentPage = request.getParameter("currentPage");//当前页码
//      String rows =request.getParameter("rows");//每页的条数
        String rows = "10";// 每页显示10条写死
        //获取所有的参数，参数用来联想查询的
        Map<String, String[]> parameterMap = request.getParameterMap();
        PageBean<ManagerUser> managerUserPageBean = us.FindUserByPage(currentPage, rows, parameterMap);
        writeValue(managerUserPageBean, response);
    }


    /**
     * 设置5分钟后删除session中的验证码
     *
     * @param session
     * @param attrName
     */
    private void removeAttrbute(HttpSession session, String attrName, int time) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 删除session中存的验证码
                session.removeAttribute(attrName);
                timer.cancel();
            }
        }, time);
    }

    /**
     * 定时删除coookie
     *
     * @param userName
     * @param time
     */
    private void removeCookie(String userName, String cookie, int time) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("数据库删除cookie");
                us.DelectCookie(userName, cookie);
                us.DelectSCookie(userName, cookie);
            }
        }, time);
    }

    private String getCoookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }


}
