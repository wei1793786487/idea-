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
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
                &nbsp 会议管理系统
            </h1>
            <ol class="breadcrumb">
                <li><a href="manager.jsp"><i class="fa fa-dashboard"></i> 首页</a></li>
                <li class="active">会议管理</li>
            </ol>
        </section>
        <!-- 内容头部 /-->
        <section class="content">

            <div class="box-body">

                <!--tab页-->
                <div class="nav-tabs-custom">

                    <!--tab头-->
                    <ul class="nav nav-tabs">

                        <li class="active">
                            <a data-toggle="tab">会议添加</a>
                        </li>

                    </ul>
                    <!--tab头/-->

                    <!--tab内容-->
                    <div class="ab-common">
                        <!--基础控件-->
                        <div class="tab-pane active" id="tab-common">
                            <div class="row data-type">

                                <form id="add" action="#" method="">
                                    <div class="col-md-2 title">会议名称</div>
                                    <div class="col-md-4 data">
                                        <input type="text" id="meetingname" name="meeting_name" class="form-control">
                                    </div>

                                    <div class="col-md-2 title">会议地点</div>
                                    <div class="col-md-4 data">
                                        <input type="text" name="metting_address" class="form-control">
                                    </div>
                                    <div class="col-md-2 title">发起人联系电话</div>
                                    <div class="col-md-4 data">
                                        <input type="text" id="phone" name="meeting_phone" class="form-control">
                                    </div>

                                    <div class="col-md-2 title">会议开始时间</div>
                                    <div class="col-md-4 data">
                                        <div class="input-group date">
                                            <div class="input-group-addon">
                                                <i class="fa fa-calendar"></i>
                                            </div>
                                            <input name="times" type="text" itemid="times"
                                                   class="form-control pull-right"
                                                   id="dateTimePicker">
                                        </div>
                                    </div>
                                    <div class="col-md-2 title">会议结束时间</div>
                                    <div class="col-md-4 data">
                                        <div class="input-group date">
                                            <div class="input-group-addon">
                                                <i class="fa fa-calendar"></i>
                                            </div>
                                            <input name="times2" type="text" itemid="times"
                                                   class="form-control pull-right"
                                                   id="dateTimePicker2">
                                        </div>
                                    </div>
                                    <div class="col-md-2 title">会议人员添加</div>
                                    <div class="col-md-4 data">
                                        <input type="text" value="请在报名管理中添加人员" disabled class="form-control">
                                    </div>
                                    <%--                                      <h3>   请在报名管理中添加人员</h3>--%>
                                    <%--                                    <div class="col-md-10 data">--%>
                                    <%--                                        <a href="javascript:;" class="a-upload">--%>
                                    <%--                                            <input type="file" name="" id="">点击这里上传文件--%>
                                    <%--                                        </a>--%>
                                    <%--                                    </div>--%>

                                    <br/>

                                    <div class="col-md-10 data text-center">
                                        <button type="button" id="submit" class="btn bg-maroon"
                                                style="font-size: larger;">提交
                                        </button>
                                        <button type="button" class="btn bg-default" onclick="location.reload()">重置
                                        </button>
                                    </div>
                                </form>

                            </div>
                        </div>
                        <!--基础控件/-->

                    </div>
                    <!--tab内容/-->

                </div>
                <!--tab页/-->

            </div>

        </section>

    </div>
    <!-- 内容区域 /-->

    <!-- 底部导航 -->
    <%@include file="../layout/foot.jsp" %>
    <!-- 底部导航 /-->

</div>
<%@include file="../config/js.jsp" %>
<script>
    // 手机号
    function isPhone(phone) {
        var myreg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;

        if (!myreg.test(phone)) {
            return false;
        } else {
            return true;
        }
    }

    $(function () {
        $("#submit").click(function () {
            var phone = $("#phone").val();
            var mname = $("#meetingname").val();
            var address = $("#meeting_address").val();
            var b = isPhone(phone);
            if (b&&mname!==""&&address!=="") {
                $.post("/Meeting/addmeeting", $("#add").serialize(), function (data) {
                    alert(data.information);
                })
            } else {
                if (!b){
                    alert("手机号格式不正确")
                }else if (mname===""){
                    alert("会议名称不能为空");
                }else if (address===""){
                    alert("会议地点不能为空")
                }else {
                    alert("添加失败")
                }

            }
        })
    })


    $('#dateTimePicker').datetimepicker({
        format: "yyyy/dd/mm - hh:ii",
        autoclose: true,
        todayBtn: true,
        language: 'zh-CN'
    });
    $('#dateTimePicker2').datetimepicker({
        format: "yyyy/mm/dd - hh:ii",
        autoclose: true,
        todayBtn: true,
        language: 'zh-CN'
    });
    //Date picker
    $('#datepicker').datepicker({
        autoclose: true,
        language: 'zh-CN'
    });

    $(document).ready(function () {

        // 激活导航位置
        setSidebarActive("meeting_add");

    });


</script>
<
/body>
< /html>
