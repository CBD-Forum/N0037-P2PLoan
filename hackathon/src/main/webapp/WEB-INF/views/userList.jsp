<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>FUDAN</title>

    <link href="${pageContext.request.contextPath}${pageContext.request.contextPath}css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}${pageContext.request.contextPath}font-awesome/css/font-awesome.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}${pageContext.request.contextPath}css/dataTables/datatables.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}${pageContext.request.contextPath}css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}${pageContext.request.contextPath}css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}${pageContext.request.contextPath}css/datepicker/datepicker3.css"
          rel="stylesheet">


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
                <li>
                    <a href="/"><i class="fa fa-th-large"></i> <span class="nav-label">系统概况</span></a>
                </li>
                <li class="active">
                    <a href="${pageContext.request.contextPath}/user"><i class="fa fa-newspaper-o"></i> <span
                            class="nav-label">User</span>
                    </a>
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

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>User List</h2>
                <ol class="breadcrumb">
                    <li>
                        <a href="/">主页</a>
                    </li>
                    <li class="active">
                        <strong>User List</strong>
                    </li>
                </ol>
            </div>
            <div class="col-lg-2">

            </div>
        </div>

        <div class="wrapper wrapper-content animated fadeInRight">

            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>Custom responsive table </h5>
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                    <i class="fa fa-wrench"></i>
                                </a>
                                <ul class="dropdown-menu dropdown-user">
                                    <li><a href="#">Config option 1</a>
                                    </li>
                                    <li><a href="#">Config option 2</a>
                                    </li>
                                </ul>
                                <a class="close-link">
                                    <i class="fa fa-times"></i>
                                </a>
                            </div>
                        </div>

                        <div class="ibox-content">
                            <div class="input-group-btn">
                                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown" type="button">Action <span class="caret"></span></button>
                                <ul class="dropdown-menu">
                                    <li><a data-toggle="modal" data-target="#myModal4" >Action</a></li>
                                    <li><a href="">Another action</a></li>
                                    <li><a href="">Something else here</a></li>
                                    <li class="divider"></li>
                                    <li><a href="">Separated link</a></li>
                                </ul>
                            </div>


                            <div class="row">

                            <div>
                                <button type="button" class="btn btn-primary" data-toggle="modal"
                                        data-target="#myModal4">新增
                                </button>
                            </div>
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover dataTables-example">
                                    <thead>
                                    <tr>
                                        <th>userID</th>
                                        <th>userName</th>
                                        <th>userPasswd</th>

                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="user" items="${list}">
                                        <tr>
                                            <td>${user.id}</td>
                                            <td><a href="user/${user.id}">${user.username}</a></td>
                                            <td>${user.password}</td>

                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th>userID</th>
                                        <th>userName</th>
                                        <th>userPasswd</th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>

    </div>


    <div class="modal inmodal" id="myModal4" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
            <div class="modal-content animated bounceInRight">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                            class="sr-only">关闭</span></button>
                    <i class="fa fa-laptop modal-icon"></i>
                    <h4 class="modal-title">新增数据</h4>
                </div>
                <div class="modal-body">
                    <p><strong>填入相关信息</strong></p>
                    <form id="add_form" action="${pageContext.request.contextPath}/user/add">
                        <div class="form-group"><label>name</label> <input type="text" id="content" name="userName"
                                                                           placeholder="" class="form-control">
                        </div>
                        <div class="form-group"><label>passwd</label> <input type="text" id="author"
                                                                             name="userPasswd" placeholder=""
                                                                             class="form-control"></div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                    <button id="add_button" type="button" class="btn btn-primary">新增</button>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <div class="pull-right">
        </div>
        <div>
            <strong>Copyright</strong> FUDAN &copy; 2017
        </div>
    </div>

</div>
</div>

<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}${pageContext.request.contextPath}js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}${pageContext.request.contextPath}js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}${pageContext.request.contextPath}js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${pageContext.request.contextPath}${pageContext.request.contextPath}js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}${pageContext.request.contextPath}js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}${pageContext.request.contextPath}js/plugins/dataTables/dataTables.editor.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${pageContext.request.contextPath}${pageContext.request.contextPath}js/inspinia.js"></script>
<script src="${pageContext.request.contextPath}${pageContext.request.contextPath}js/plugins/pace/pace.min.js"></script>
<script src="${pageContext.request.contextPath}${pageContext.request.contextPath}js/plugins/datepicker/bootstrap-datepicker.js"></script>


<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {
        $("#add_button").click(function () {
            $("#add_form").submit();
        });
        $('.dataTables-example').DataTable({
            pageLength: 25,
            responsive: true,
            dom: '<"html5buttons"B>lTfgitp',
            buttons: [
                {extend: 'copy'},
                {extend: 'csv'},
                {extend: 'excel', title: 'ExampleFile'},
                {extend: 'pdf', title: 'ExampleFile'},

                {
                    extend: 'print',
                    customize: function (win) {
                        $(win.document.body).addClass('white-bg');
                        $(win.document.body).css('font-size', '10px');

                        $(win.document.body).find('table')
                            .addClass('compact')
                            .css('font-size', 'inherit');
                    }
                }
            ]

        });

    });

</script>

</body>

</html>
