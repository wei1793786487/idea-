<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>会议签到系统注册界面</title>
    <link rel="stylesheet" href="./plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="./plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="./plugins/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="./plugins/adminLTE/css/AdminLTE.css">
    <link rel="stylesheet" href="./plugins/iCheck/square/blue.css">
</head>

<body class="hold-transition register-page">
<div class="register-box">
    <div class="register-logo">


        <a href="index.jsp">多用户会议签到系统</a>
        <a>（注册页面）</a>


    </div>

    <div class="register-box-body">
        <p class="login-box-msg">新用户注册</p>

        <form id="regeist" action="" method="post">
            <div class="form-group has-feedback">
                <input name="name" type="text" class="form-control" placeholder="全名" autocomplete="off">
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>

            <div class="form-group has-feedback">
                <input name="pswd" type="password" class="form-control" placeholder="密码" autocomplete="off">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input name="pswdt" type="password" class="form-control" placeholder="确认密码" autocomplete="off">
                <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input name="phone" type="text" class="form-control" id="putphone" placeholder="请输入你的手机号" autocomplete="off">
                <span class="glyphicon glyphicon-earphone form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input name="check" type="text" class="form-control" placeholder="请输入短信验证码" autocomplete="off"
                       style="float:left;width:170px;">
                <%--                <button id="sendcheck"  value="点击发送验证码" style="float:right;height:35px;width:120px"--%>
                <%--                        class="btn btn-danger">--%>
                <%--                </button>--%>
                <input id="sendcheck" type="button" value="发送短信" style="float:right;height:35px;width:120px"
                       class="btn btn-primary btn-block btn-flat"/></p>

            </div>
            <div class="row">
                <div class="col-xs-8">
                    <div class="checkbox icheck">
                        <label>

                        </label>
                    </div>
                </div>
                <!-- /.col -->

                <div class="col-xs-4">
                    <br>

                    <input type="button" id="zhuce" class="btn btn-primary btn-block btn-flat" value="注册"></button>
                </div>
                <!-- /.col -->
            </div>
        </form>

        <!--<div class="social-auth-links text-center">
            <p>- 或者 -</p>
            <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-qq"></i> 腾讯QQ用户登录</a>
            <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-weixin"></i> 微信用户登录</a>
        </div>-->

        <a href="index.jsp" class="text-center">我有账号，现在就去登录</a>
    </div>
    <!-- /.form-box -->
</div>


<script src="plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="plugins/iCheck/icheck.min.js"></script>
<script>


    $(document).keypress(function (e) {
        // 回车键事件
        if (e.which === 13) {
            $("#zhuce").click();

        }
    });

    $(function () {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
    });

    // 手机号
    function isPhone(phone) {
        var myreg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/;
        if (!myreg.test(phone)) {
            return false;
        } else {
            return true;
        }
    }

    $("#sendcheck").click(function () {
        var phone = $('input[name="phone"]').val();
        var isphone = isPhone(phone);
        if (!isphone) {
            alert("手机号码有误，请重填");
        } else {
            //调用发送短信方法
            sendmessage(this, 30);
            $("#putphone")
            $.post("/user/sendMessgae", {phone: phone}, function (data) {
            })
        }
    })

    $('#zhuce').click(function () {
        var name = $('input[name="name"]').val();
        var pswd = $('input[name="pswd"]').val();
        var pswd2 = $('input[name="pswdt"]').val();
        var code = $('input[name="check"]').val();
        if (name === '') {
            alert('请输入您的账号');
        } else if (pswd === '') {
            alert('请输入密码');
        } else if (pswd2 === '') {
            alert('请输入重复密码');
        } else if (code === '' || code.length !== 6) {
            alert('请输入六位验证码');
        } else if (pswd2 !== pswd) {
            alert("两次密码不一致")
        } else {
            // 0代表验证码失败
            // 1代表注册成功
            // 3代表用户存在
            $.post("/user/register", $("#regeist").serialize(), function (data) {
                if (data.information === "3") {
                    alert("该用户已经存在")
                } else if (data.information === "0") {
                    alert("验证码输入错误");
                } else if (data.information === "1") {
                    alert("注册成功");
                    // var r=confirm("是否前往登录");
                    window.location.href = "index.jsp";
                }
            })
        }
    })

    //发送短信
    function sendmessage(obj, second) {
        if (sendmessage) {
            countDown(obj, second)
        } else {
            alert("错误，虽然永远走不到这里！");
        }
    }

    function countDown(obj, second) {
        // 如果秒数还是大于0，则表示倒计时还没结束
        if (second >= 0) {
            // 获取默认按钮上的文字
            if (typeof buttonDefaultValue === 'undefined') {
                buttonDefaultValue = obj.defaultValue;
            }
            // 按钮置为不可点击状态
            obj.disabled = true;
            // 按钮里的内容呈现倒计时状态
            obj.value = buttonDefaultValue + '(' + second + ')';
            // 时间减一
            second--;
            // 一秒后重复执行
            setTimeout(function () {
                countDown(obj, second);
            }, 1000);
            // 否则，按钮重置为初始状态
        } else {
            // 按钮置未可点击状态
            obj.disabled = false;
            // 按钮里的内容恢复初始状态
            obj.value = buttonDefaultValue;
        }
    }


</script>
</body>

</html>