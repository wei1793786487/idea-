<%@ page import="com.hqgml.domain.ManagerUser" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/10/2
  Time: 1:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../config/css.jsp" %>
</head>
<body class="hold-transition skin-purple sidebar-mini">

<div class="wrapper">
    <%--    头--%>
    <%@include file="../layout/head.jsp" %>
    <%--    侧--%>
    <%@include file="../layout/left.jsp" %>

    <!-- 内容区域 -->
    <div class="content-wrapper">


        <!-- 内容头部 -->
        <section class="content-header">
            <h1>
                管理面板
                <small>欢迎界面</small>
            </h1>

        </section>
        <!-- 内容头部 /-->

        <!-- 正文区域 -->
        <section class="content">
            <a></a><br/>
            <a></a><br/>
            <div class="alert alert-block ">

                <%--                欢迎使用--%>
                <%--                <strong>--%>
                <%--                    多用户会议签到系统-网站后台--%>
                <%--                    <small>(v1.0)</small>--%>
                <%--                </strong>--%>
                <c:if test="${manager.address!=null}">
                    <h3> 欢迎使用 <strong>多用户会议签到系统-网站后台</strong>，您上次登录的地点是<strong>${manager.address}</strong>。</h3>
                </c:if>
                <c:if test="${manager.address==null}">
                    <h3> 欢迎使用 <strong>多用户会议签到系统-网站后台</strong>，欢迎您初次登陆本系统</strong>。</h3>
                </c:if>

                <%--                <%--%>
                <%--                    ManagerUser manager = (ManagerUser) request.getSession().getAttribute("manager");--%>
                <%--                    System.out.println(manager.getAddress());--%>
                <%--                %>--%>

            </div>
            <%--       <h3 style="">会议签到进入</h3>--%>

        </section>
        <!-- 正文区域 /-->


    </div>
    <!-- 内容区域 /-->

    <!-- 底部导航 -->
    <%@include file="../layout/foot.jsp" %>
    <!-- 底部导航 /-->

</div>
<%@include file="../config/js.jsp" %>
<script>


</script>
</body>
</html>
