<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />

    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-js.jspf" />
    <title>My Home Page</title>
    <style>
        html,body{
            margin:0;padding: 0;
            width:100%;height:100%;

        }
        .container-lg{
            width:100%;height:100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .userbox{
            width:400px;
            height:200px;
            margin:0 auto;
        }
        .item{
            width:100%;
            height:40px;
            line-height: 40px;
            display: flex;
            position: relative;
            border-top:1px solid #999;

        }
        .namebox{
            width:100px;
            height:40px;
            line-height: 40px;
            text-align: right;
            border-right:1px solid #999;
            border-left:1px solid #999;
            border-bottom:1px solid #999;
        }
        .noticebox{
            width:200px;
            height:40px;
            line-height: 40px;
            text-align: right;
            padding-right: 10px;
            color:red;
            font-size:10px;
            position:absolute;left:-200px;top:0

        }
        .valuebox{
            width:calc(100% - 100px);
            height:40px;
            line-height: 40px;
            border-right:1px solid #999;
            border-bottom:1px solid #999;
            overflow: hidden;
        }
        .textbox{
            width: 100%;
            height:40px;
            line-height: 40px;
            border: none;

            outline: 0;
            text-indent: 10px;

        }
        .submit{
            padding: 0;
            width:200px;
            height:40px;
            line-height: 40px;
            margin: 30px auto;
            background:green;
            color:#fff;
            text-align: center;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<div class="container-lg">
    <div class="userbox">

        <form action="/user/saveUser" method="post" id="mebox">
            <div class="item">
                <div class="namebox" >
                    用户名：
                </div>
                <div class="valuebox">
                    <input type="text"  name="name" class="textbox name" placeholder="请输入姓名"/>
                </div>
                <div class="noticebox">

                </div>
            </div>
            <div class="item">
                <div class="namebox">
                    密码：
                </div>
                <div class="valuebox">
                    <input type="password" name="password" class="textbox password1" placeholder="请输入密码"/>
                </div>
                <div class="noticebox">

                </div>
            </div>
            <div class="item">
                <div class="namebox">
                    确认密码：
                </div>
                <div class="valuebox">
                    <input type="password" class="textbox password2" placeholder="请输入再次输入密码"/>
                </div>
                <div class="noticebox">

                </div>
            </div>
            <div class="item">
                <div class="namebox">
                    邮箱：
                </div>
                <div class="valuebox">
                    <input type="email"  name="email" class="textbox email" placeholder="请输入邮箱"/>
                </div>
                <div class="noticebox">

                </div>
            </div>
            <div class="item">
                <div class="namebox">
                    手机号：
                </div>
                <div class="valuebox">
                    <input type="number" maxlength="11" name="phoneNumber" class="textbox phoneNumber" placeholder="请输入手机号"/>
                </div>
                <div class="noticebox">

                </div>
            </div>
            <div class="submit" onclick="saveUser()">
                提交
            </div>
        </form>

    </div>
</div>
<script>
    console.log($)
    function saveUser(){
        var name = $(".name").val();
        var password1 = $(".password1").val();
        var password2 = $(".password2").val();
        var email = $(".email").val();
        var phoneNumber = $(".phoneNumber").val();
        $(".noticebox").html("")
        if(name == null || name == undefined ||name.trim() == ''){
            $(".noticebox").eq(0).html("请输入姓名");
            return false;
        }
        if(password1 == null || password1 == undefined ||password1.trim() == ''){
            $(".noticebox").eq(1).html("请输入密码");
            return false;
        }
        if(password2 == null || password2 == undefined ||password2.trim() == ''){
            $(".noticebox").eq(2).html("请输入确认密码");
            return false;
        }
        if(password2 != password1){
            $(".noticebox").eq(2).html("两次输入的密码不一致");
            return false;
        }
        if(email == null || email == undefined ||email.trim() == ''){
            $(".noticebox").eq(3).html("请输入邮箱");
            return false;
        }
        if(phoneNumber == null || phoneNumber == undefined ||phoneNumber.trim() == ''){
            $(".noticebox").eq(4).html("请输入手机号");
            return false;
        }

        $("#mebox").submit();
    }
</script>
</body>

