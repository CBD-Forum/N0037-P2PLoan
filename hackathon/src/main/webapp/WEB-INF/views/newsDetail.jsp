<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>新闻溯源系统</title>

    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
          rel="stylesheet">
    <link
            href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css"
            rel="stylesheet">
    <link
            href="${pageContext.request.contextPath}/css/dataTables/datatables.min.css"
            rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/animate.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/style.css"
          rel="stylesheet">
    <link
            href="${pageContext.request.contextPath}/css/datepicker/datepicker3.css"
            rel="stylesheet">


</head>

<body>

<div id="wrapper">

    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav metismenu" id="side-menu">
                <li class="nav-header"><shiro:user>
                    <div class="dropdown profile-element">
                        <span></span> <a data-toggle="dropdown" class="dropdown-toggle"
                                         href="#"> <span class="clear"> <span
                            class="block m-t-xs"> <strong class="font-bold"><shiro:principal/></strong>
									</span> <span class="text-muted text-xs block"><b class="caret"></b></span>
								</span>
                    </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="${pageContext.request.contextPath}/login/out">注销</a></li>
                            <li class="divider"></li>
                            <li><a href="${pageContext.request.contextPath}/login/out">注销</a></li>
                        </ul>
                    </div>
                </shiro:user> <shiro:guest>
							<span class="font-extra-bold font-uppercase"><a
                                    href="${pageContext.request.contextPath}/login">登录</a></span>
                </shiro:guest>


                    <div class="logo-element">FUDAN</div>
                </li>
                <li><a href="/"><i class="fa fa-th-large"></i> <span
                        class="nav-label">系统概况</span></a></li>
                <li class="active"><a
                        href="${pageContext.request.contextPath}/news"><i
                        class="fa fa-newspaper-o"></i> <span class="nav-label">新闻</span>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/site"><i
                        class="fa fa-pencil-square-o"></i> <span class="nav-label">发布者</span>
                </a></li>
                <li><a href="${pageContext.request.contextPath}/"><i
                        class="fa fa-chain"></i> <span class="nav-label">区块信息</span> </a></li>
            </ul>

        </div>
    </nav>

    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top white-bg" role="navigation"
                 style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
                       href="#"><i class="fa fa-bars"></i> </a>

                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li><a href="${pageContext.request.contextPath}/logout"><i
                            class="fa fa-sign-out"></i> 注销</a></li>
                </ul>

            </nav>
        </div>

        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>新闻详情</h2>
                <ol class="breadcrumb">
                    <li><a href="/">主页</a></li>
                    <li><a href="/news">列表</a></li>
                    <li class="active"><strong>新闻详情</strong></li>
                </ol>
            </div>
            <div class="col-lg-2"></div>
        </div>

        <div class="wrapper wrapper-content animated fadeInRight">


            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <h5>新闻详情</h5>
                        </div>

                        <div class="ibox-content">
                            <div class="form-group"><label>新闻ID</label><input class="form-control" type="text"
                                                                              value="${news.id}"></div>
                            <div class="form-group"><label>新闻标题</label><input class="form-control" type="text"
                                                                              value="${news.title}"></div>
                            <div class="form-group"><label>新闻内容</label><input class="form-control" type="text"
                                                                              value="${news.content}"></div>
                            <div class="form-group"><label>新闻作者</label><input class="form-control" type="text"
                                                                              value="${news.author}"></div>
                            <div class="form-group"><label>新闻日期</label><input class="form-control" type="text"
                                                                              value="${news.date}"></div>
                            <div class="form-group"><label>站点名称</label><input class="form-control" type="text"
                                                                              value="${news.site.name}"></div>
                            <div class="form-group"><label>新闻状态</label><input class="form-control" type="text"
                                                                              value="${news.status}"></div>
                            <div class="form-group"><label>新闻状态</label><input class="form-control" type="text"
                                                                              value="${news.status}"></div>
                            <div class="form-group"><label>交易号</label><input class="form-control" type="text"
                                                                             value="${tx.txHash}"></div>
                            <div class="form-group"><label>交易内容</label><textarea cols="80"
                                                                                 class="form-control">${tx.txPayload}</textarea>
                            </div>
                            <div class="form-group"><label>解码</label><textarea cols="80" class="form-control">${payload}"</textarea>
                            </div>
                            <div class="form-group"><label>新闻有效</label><input class="form-control" type="text"
                                                                              value="${newsValid}"></div>
                            <div class="form-group"><label>二维码</label><img
                                    src="${pageContext.request.contextPath}/qr?text=http://localhost:8080/news/${news.id}">
                            </div>


                        </div>
                    </div>
                </div>
            </div>


            <div class="row">
            </div>


        </div>


        <div class="footer">
            <div class="pull-right"></div>
            <div>
                <strong>Copyright</strong> 复旦众安区块链实验室 &copy; 2017
            </div>
        </div>

    </div>
</div>

<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script
        src="${pageContext.request.contextPath}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script
        src="${pageContext.request.contextPath}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script
        src="${pageContext.request.contextPath}/js/plugins/dataTables/datatables.min.js"></script>
<script
        src="${pageContext.request.contextPath}/js/plugins/dataTables/dataTables.editor.min.js"></script>
<!-- Custom and plugin javascript -->
<script src="${pageContext.request.contextPath}/js/inspinia.js"></script>
<script
        src="${pageContext.request.contextPath}/js/plugins/pace/pace.min.js"></script>
<script
        src="${pageContext.request.contextPath}/js/plugins/datepicker/bootstrap-datepicker.js"></script>

<script>


    $(document).ready(function () {

            $("#add_button").click(function () {
                $("#add_form").submit();
            });

            $('#data_1 .input-group.date').datepicker({
                todayBtn: "linked",
                keyboardNavigation: false,
                forceParse: false,
                autoclose: true,
                format: "yyyy-MM-dd"
            });


            $('.dataTables-news').DataTable({
                "language": {
                    "lengthMenu": "每页显示_MENU_条记录",
                    "zeroRecords": "无数据",
                    "info": "第_PAGE_页，共_PAGES_页",
                    "infoEmpty": "无数据",
                    "infoFiltered": "(共_MAX_条数据)",
                    "search": "搜索:",
                    "paginate": {
                        "first": "起始页",
                        "last": "结束页",
                        "next": "后一页",
                        "previous": "前一页"
                    },
                    buttons: {
                        copy: "复制",
                        print: "打印"
                    }
                },
                pageLength: 25,
                responsive: true,
                dom: '<"html5buttons"B>lTfgitp',
                columns: [

                    {data: "newsId"},
                    {data: "title"},
                    {data: "siteName"},
                    {data: "content"},
                    {data: "author"},
                    {data: "publishTime"},
                    {data: "status"},
                    {
                        data: "ops",
                        orderable: false
                    },
                ],
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
        }
    );
</script>

</body>

</html>
