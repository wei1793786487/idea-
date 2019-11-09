<%--
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
    <style>
        .box-body {
            height: 150px;
        }

        .form-control-feedback {
            position: absolute;
            top: 0;
            right: 0;
            z-index: 2;
            display: block;
            width: 34px;
            height: 34px;
            line-height: 34px;
            text-align: center;
            pointer-events: auto;
        }
        .profile-user-img {
            width: 100%;
            height: 100%;
            border: 1px solid #000;
            border-radius: 50%;
            /*50%的宽度圈，他是个框，如果不想要框，请将上面border一起注销*/
        }
    </style>
</head>
<body class="hold-transition skin-purple sidebar-mini">

<div class="wrapper">
    <%--    头--%>
    <%@include file="../layout/head.jsp" %>
    <%--    侧--%>
    <%@include file="../layout/left.jsp" %>
    <%
        String id = request.getParameter("id");
        request.setAttribute("meetid", id);
        System.out.println(request.getSession().getAttribute("meetid"));
    %>

    <!-- 内容区域 -->
    <div class="content-wrapper">


        <!-- 内容头部 -->
        <!--正文区域-->
        <section class="content">

            <section class="content-header">
                <h1 id="peole_bumber">
                    &nbsp;
                    <%--                    人员管理( <div  style="color: #3CB371; font-size: large;"></div> )--%>

                </h1>
                <ol class="breadcrumb">
                    <li><a href="manager.jsp"><i class="fa fa-dashboard"></i> 首页</a></li>
                    <li class="active">人员管理</li>

                </ol>
                <br/>
                <br/>

                <div>
                    <form method="post" action="/file" id="people_add" enctype="multipart/form-data">
                        <input type="text" id="manager_id" name="manager_id" style="display:none" value="${meetid}">
                        <div>
                            <div class="col-md-2 data">
                                <div class="a-upload">
                                    <input type="file" name="person_list" id="">选择你要新增的人员图片
                                </div>
                            </div>


                            <%--                    <div class="col-md-2 data">--%>
                            <%--                        <div class="a-upload">--%>
                            <%--                            <input type="button"id="submit_button">--%>
                            <%--                        </div>--%>
                            <%--                    </div>--%>
                            <input type="submit" id="submit" value="添加人员" class="btn bg-maroon col-md-1 "
                                   style="font-size: larger;">
                            <div class="col-md-6"></div>

                    </form>
                    <form id="delectByName" method="post" action="#">
                        <div class="box-tools pull-right">
                            <form id="sousuoform">
                                <div class="has-feedback">
                                    <input type="text" id="Search" class="form-control input-sm"
                                           placeholder="请输入人员名字">
                                    <span onclick="FindNameList()" id="sousuo"
                                          class="glyphicon glyphicon-search form-control-feedback"></span>
                                </div>
                            </form>
                        </div>
                    </form>
                </div>
                <br>


                <br/>
                <br>
                <div id="listpeople">


                </div>


            </section>
            <!-- 正文区域 /-->
        </section>
    </div>
    <!-- 内容区域 /-->

    <!-- 底部导航 -->
    <%@include file="../layout/foot.jsp" %>
    <!-- 底部导航 /-->

</div>
<%@include file="../config/js.jsp" %>
<script>
    function delectpeople(id) {
        if (confirm("你确定要删除吗？此操作不可撤销。")) {
            $.get("/Meeting/delectPeople", {id: id}, function (data) {
                if (data.information != null) {
                    alert(data.information);
                    location.reload();
                }
            })
        }else {

        }

    }

    function load(name) {
        $.get("/Meeting/personlist?mid=${meetid}", {name:name}, function (data) {
            if (data.information != null) {
                alert(data.information);
                window.history.back(-1);
            } else {
                $("#peole_bumber").html("人员管理(共有" + data.length + "人)")
                var listdata = "";
                for (var i = 0; i < data.length; i++) {
                    var list = data[i];
                    var li = ' <div   class="col-md-2">\n' +
                        '                    <div class="box box-primary">\n' +
                        '                        <div class="box-body box-profile">\n' +
                        '                            <i id="delectpeople"  onclick="delectpeople(' + list.id + ')" class="fa fa-times" style=" float:right;"></i>' +
                        '                            ' +
                        '<div class="box_cen_top" style="width: 50%;\n' +
                        '    margin: 0 auto;\n' +
                        '    height: 80%;"><img  " class="profile-user-img img-responsive img-circle" src="../upload/' + list.photo_name + '"\n' +
                        '                                 alt="User profile picture">\n </div>' +
                        '                            <h4 class="profile-username text-center">' + list.person_name + '</h4>\n' +
                        '                        </div>\n' +
                        '                    </div>\n' +
                        '\n' +
                        '                </div>'
                    listdata += li;
                }
                // alert(listdat
                $("#listpeople").html(listdata);
            }


        })
    }

    function FindNameList() {
      var name = $("#Search").val();
        load(name);
    }

    $(document).ready(function () {
        //动态获取id改变表单的value参数
        //        var parameter = getParameter("id");
        //         $("#manager_id").val(parameter);
        // 提交请求
        load(null);


    });
</script>

</body>
</html>
