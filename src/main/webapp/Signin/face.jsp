<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>多用户会议签到系统-郭照凯</title>
    <script src="../js/tracking-min.js"></script>
    <script src="../plugins/jQuery/jquery-2.2.3.min.js"></script>


    <script src="../js/face-min.js"></script>
    <script src="../js/getParameter.js"></script>
    <script src="../js/canva_moving_effect.js"></script>
    <%--    --%>
    <style>
        * {
            padding: 0;
            margin: 0;
        }

        .container {
            position: relative;
            width: 250px;
            height: 250px;
            float: left;
            margin-left: 30px;
            margin-top: 20px;
        }

        .message {
            float: left;
            position: relative;
            top: 30px;
            left: 30px;
        }

        video, #canvas {
            position: absolute;
            width: 260px;
            height: 190px;
        }

        input:-webkit-autofill,
        input:-webkit-autofill:hover,
        input:-webkit-autofill:focus,
        input:-webkit-autofill:active {
            -webkit-transition-delay: 99999s;
            -webkit-transition: color 99999s ease-out, background-color 99999s ease-out;
        }

        body {
            -webkit-perspective: 800px;
            perspective: 800px;
            height: 100vh;
            margin: 0;
            overflow: hidden;
            font-family: 'Gudea', sans-serif;
            background: #094490d6;
            /* Old browsers */
            /* FF3.6+ */
            /*   background: -webkit-gradient(linear, left top, right bottom, color-stop(0%, #EA5C54), color-stop(100%, #8c6dec));
            Chrome,Safari4+
            background: -webkit-linear-gradient(-45deg, #EA5C54 0%, #8c6dec 100%);
            Chrome10+,Safari5.1+
            Opera 11.10+
            IE10+
            background: -webkit-linear-gradient(315deg, #EA5C54 0%, #8c6dec 100%);
            background: linear-gradient(135deg, #EA5C54 0%, #8c6dec 100%); */

            /* W3C */
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#EA5C54 ', endColorstr='#8c6dec', GradientType=1);
            /* IE6-9 fallback on horizontal gradient */
        }

        body ::-webkit-input-placeholder {
            color: #4E546D;
        }

        body .authent {
            display: none;
            background: #35394a;
            /* Old browsers */
            /* FF3.6+ */
            /*  background: linear-gradient(45deg, #0f163a6e 0%,#1c518675 100%); */
            background: -webkit-gradient(linear, left bottom, right top, color-stop(0%, #0f163a6e), color-stop(100%, #1c518675));
            /* Chrome,Safari4+ */
            background: -webkit-linear-gradient(45deg, #0f163a6e 0%, #1c518675 100%);
            /* Chrome10+,Safari5.1+ */
            /* Opera 11.10+ */
            /* IE10+ */
            background: linear-gradient(45deg, #0f163a6e 0%, #1c518675 100%);
            /* W3C */
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#0f163a6e', endColorstr='#1c518675', GradientType=1);
            /* IE6-9 fallback on horizontal gradient */
            position: absolute;
            left: 0;
            right: 90px;
            margin: auto;
            width: 100px;
            color: white;
            text-transform: uppercase;
            letter-spacing: 1px;
            text-align: center;
            padding: 20px 70px;
            top: 200px;
            bottom: 0;
            height: 100px;
            opacity: 0;
            border-top: 2px solid #D8312A;
            border-bottom: 3px solid #f7296f;
            border-radius: 30px 30px 30px 30px;
            box-shadow: -10px 15px 30px #19234e;
        }

        body .authent p {
            text-align: center;
            color: white;
        }

        body .success {
            display: none;
            color: #d5d8e2;
        }

        body .success p {
            font-size: 14px;
        }

        body p {
            color: #9ca1bf;
            font-size: 10px;
            text-align: left;
        }

        body .testtwo {
            left: -320px !important;
        }

        body .test {
            box-shadow: 0px 20px 30px 3px rgba(0, 0, 0, 0.55);
            pointer-events: none;
            top: -100px !important;
            -webkit-transform: rotateX(70deg) scale(0.8) !important;
            transform: rotateX(70deg) scale(0.8) !important;
            opacity: .6 !important;
            -webkit-filter: blur(1px);
            filter: blur(1px);
        }

        body .login {
            box-shadow: -10px 15px 30px #19234e;
            opacity: 1;
            top: 20px;
            -webkit-transition-timing-function: cubic-bezier(0.68, -0.25, 0.265, 0.85);
            -webkit-transition-property: -webkit-transform, opacity, box-shadow, top, left;
            transition-property: transform, opacity, box-shadow, top, left;
            -webkit-transition-duration: .5s;
            transition-duration: .5s;
            -webkit-transform-origin: 161px 100%;
            -ms-transform-origin: 161px 100%;
            transform-origin: 161px 100%;
            -webkit-transform: rotateX(0deg);
            transform: rotateX(0deg);
            position: relative;
            width: 240px;
            border-top: 2px solid #D8312A;
            height: 300px;
            position: absolute;
            left: 0;
            right: 0;
            margin: auto;
            top: 0;
            bottom: 0;
            padding: 100px 40px 40px 40px;
            background: #35394a;
            /* Old browsers */
            /* FF3.6+ */
            background: -webkit-gradient(linear, left bottom, right top, color-stop(0%, #0f163a6e), color-stop(100%, #1c518675));
            /* Chrome,Safari4+ */
            background: -webkit-linear-gradient(45deg, #0f163a6e 0%, #1c518675 100%);
            /* Chrome10+,Safari5.1+ */
            /* Opera 11.10+ */
            /* IE10+ */

            background: linear-gradient(45deg, #0f163a6e 0%, #1c518675 100%);
            border-bottom: 8px solid #f7296f;
            border-radius: 30px 30px 50px 50px;

            /* W3C */
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#35394a30', endColorstr='#1f222e', GradientType=1);
            /* IE6-9 fallback on horizontal gradient */
        }

        body .login .validation {
            position: absolute;
            z-index: 1;
            right: 10px;
            top: 6px;
            opacity: 0;
        }

        body .login .disclaimer {
            position: absolute;
            bottom: 20px;
            left: 35px;
            width: 250px;
            text-align: center;
        }

        body .login .disclaimer p {
            text-align: center;
        }

        body .login_title {
            color: #ffffff;
            height: 40px;
            text-align: left;
            font-size: 20px;
            margin-top: -50px;
        }

        body .login_fields {
            height: 208px;
            position: absolute;
            left: 0;
        }

        body .login_fields .icon {
            position: absolute;
            z-index: 1;
            left: 36px;
            top: 8px;
            opacity: .5;
        }

        body .login_fields input[type='password'] {
            color: #DC6180 !important;
        }

        body .login_fields input[type='text'], body .login_fields input[type='password'] {
            color: #ffffff;
            width: 190px;
            margin-top: -2px;
            background: #32364a;
            left: 0;
            padding: 10px 65px;
            border-top: 2px solid #393d52;
            border-bottom: 2px solid #393d52;
            border-right: none;
            border-left: none;
            outline: none;
            font-family: 'Gudea', sans-serif;
            box-shadow: none;
        }

        body .login_fields__user, body .login_fields__password {
            position: relative;
        }

        body .login_fields__submit {
            position: relative;
            top: 35px;
            left: 0;
            width: 80%;
            right: 0;
            margin: auto;
        }

        body .login_fields__submit .forgot {
            float: right;
            font-size: 10px;
            margin-top: 11px;
            text-decoration: underline;
        }

        body .login_fields__submit .forgot a {
            color: #babdce;
        }

        body .login_fields__submit input {
            border-radius: 50px;
            background: transparent;
            padding: 10px 50px;
            border: 2px solid #f7296f;
            color: #DC6180;
            text-transform: uppercase;
            font-size: 11px;
            -webkit-transition-property: background, color;
            transition-property: background, color;
            -webkit-transition-duration: .2s;
            transition-duration: .2s;
        }

        body .login_fields__submit input:focus {
            box-shadow: none;
            outline: none;
        }

        body .login_fields__submit input:hover {
            color: white;
            background: #f7296f;
            cursor: pointer;
            -webkit-transition-property: background, color;
            transition-property: background, color;
            -webkit-transition-duration: .2s;
            transition-duration: .2s;
        }

        /* Color Schemes */
        .love {
            position: absolute;
            right: 20px;
            bottom: 0px;
            font-size: 11px;
            font-weight: normal;
        }

        .love p {
            color: white;
            font-weight: normal;
            font-family: 'Open Sans', sans-serif;
        }

        .love a {
            color: white;
            font-weight: 700;
            text-decoration: none;
        }

        .love img {
            position: relative;
            top: 3px;
            margin: 0px 4px;
            width: 10px;
        }

        .brand {
            position: absolute;
            left: 20px;
            bottom: 14px;
        }

        .brand img {
            width: 30px;
        }
    </style>
    <script>
        var mid = getParameter("mid");
        $(function () {
            var video = document.getElementById('video');
            var canvas = document.getElementById('canvas');
            var context = canvas.getContext('2d');
            var shortCut = document.getElementById('shortCut');
            var scContext = shortCut.getContext('2d');
            var time = 10000;//向后台发照片的冷却时间

            var tracker = new tracking.ObjectTracker('face');
            tracker.setInitialScale(4);
            tracker.setStepSize(2);
            tracker.setEdgesDensity(0.1);

            tracking.track('#video', tracker, {camera: true});
            var flag = true;
            tracker.on('track', function (event) {
                if (event.data.length === 0) {
                    context.clearRect(0, 0, canvas.width, canvas.height);
                } else {
                    context.clearRect(0, 0, canvas.width, canvas.height);
                    event.data.forEach(function (rect) {
                        context.strokeStyle = '#ff0000';
                        context.strokeRect(rect.x, rect.y, rect.width, rect.height);
                        context.fillStyle = "#ff0000";
                        //console.log(rect.x, rect.width, rect.y, rect.height);
                    });
                    if (flag) {
                        console.log("拍照");
                        getPhoto();
                        flag = false;
                        setTimeout(function () {
                            flag = true;
                        }, time);
                    } else {
                        //console.log("冷却中");
                    }
                }
            });

            function getPhoto() {
                scContext.drawImage(video, 0, 0, 290, 218);
                var imgStr = shortCut.toDataURL("image/png");
                imgdata = imgStr.split(',')[1];
                $.post("/video", {image: imgdata, mid: mid}, function (data) {
                    var information = data.information;
                    if (information != null) {
                        setMessageInnerHTML(information);
                    }
                })
            }
        });

        function setMessageInnerHTML(innerHTML) {
            $("#checkinMsg").html(innerHTML);

        }
    </script>

</head>
<body>

<div class='login'>


    <div class="message" style="display:none;">
        <canvas id="shortCut" width="500" height="500"></canvas>
    </div>



    <div class='login_title'>
        <span style="text-align: center;display:block;">会议签到系统-签到页面</span>
    </div>




    <div class='login_fields'>
        <div class="container">
            <video id="video" preload autoplay loop muted></video>
            <canvas id="canvas"></canvas>
            <div id="checkinMsg" style="color: white;position: absolute;width: 80%;text-align: center;left: 30px;bottom: 0;"></div>
        </div>



    </div>


    <div class='disclaimer'>
        <%--            <p style="color: #ffffff;font-size: 15px">企业资源与教育优化一体化管理平台</p>--%>

        <p>ET工作室-郭照凯</p>
    </div>

</div>


</body>

</html>
