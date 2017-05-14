<%--
  Created by IntelliJ IDEA.
  User: bintan
  Date: 17-4-13
  Time: 上午9:45
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>INSPINIA | Login</title>

    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <div>

            <h1 class="logo-name">IN+</h1>

        </div>
        <h3>Welcome to IN+</h3>
        <p>Perfectly designed and precisely prepared admin theme with over 50 pages with extra new web app views.
            <!--Continually expanded and constantly improved Inspinia Admin Them (IN+)-->
        </p>
        <p>Login in. To see it in action.</p>

        <form class="m-t" role="form" action="${pageContext.request.contextPath}/login/loginDo"
              method="post">

            <div class="form-group"><label>name</label> <input type="text" id="content" name="username"
                                                               placeholder="" class="form-control">
            </div>
            <div class="form-group"><label>passwd</label> <input type="password" id="author"
                                                                 name="password" placeholder=""
                                                                 class="form-control"></div>
            <button type="submit" class="btn btn-primary block full-width m-b">Login</button>
            <a href="#">
                <small>Forgot password?</small>
            </a>
            <p class="text-muted text-center">
                <small>Do not have an account?</small>
            </p>
            <a class="btn btn-sm btn-white btn-block" href="${pageContext.request.contextPath}/register">Create an
                account</a>
        </form>
        <p class="m-t">
            <small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small>
        </p>
    </div>
</div>

<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function () {
        $("#add_button").click(function () {
            $("#add_form").submit();
        });
    });
</script>

</body>

</html>
