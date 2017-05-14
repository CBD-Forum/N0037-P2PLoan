<%--
  Created by IntelliJ IDEA.
  User: bintan
  Date: 17-4-13
  Time: 下午5:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>FUDAN</title>

    <link href="${pageContext.request.contextPath}css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}css/style.css" rel="stylesheet">

</head>

<body>

<div id="wrapper">

    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav metismenu" id="side-menu">
                <li class="nav-header">

                    <shiro:user>
                        <div class="dropdown profile-element"><span></span>
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear"> <span class="block m-t-xs"> <strong
                                    class="font-bold"><shiro:principal/></strong>
                             </span> <span class="text-muted text-xs block"><b class="caret"></b></span> </span> </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a href="${pageContext.request.contextPath}/logout">注销</a></li>
                                <li class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/logout">注销</a></li>
                            </ul>
                        </div>
                    </shiro:user>
                    <shiro:guest><span class="font-extra-bold font-uppercase"><a
                            href="${pageContext.request.contextPath}/login">登录</a></span>
                    </shiro:guest>


                    <div class="logo-element">
                        FUDAN
                    </div>
                </li>
                <li class="active">
                    <a href="/"><i class="fa fa-th-large"></i> <span class="nav-label">系统概况</span></a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/user"><i class="fa fa-pencil-square-o"></i> <span
                            class="nav-label">User</span>
                    </a>
                </li>
            </ul>

        </div>
    </nav>

    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i>
                    </a>

                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <a href="${pageContext.request.contextPath}/logout"><i class="fa fa-sign-out"></i> 注销</a>
                    </li>
                </ul>

            </nav>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>FUDAN</h5>
                        </div>

                        <div class="ibox-content">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="footer">
    <div class="pull-right">
    </div>
    <div>
        <strong>Copyright</strong> fudan &copy; 2017
    </div>
</div>

</div>
</div>

<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${pageContext.request.contextPath}js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${pageContext.request.contextPath}js/inspinia.js"></script>
<script src="${pageContext.request.contextPath}js/plugins/pace/pace.min.js"></script>


</body>

</html>
