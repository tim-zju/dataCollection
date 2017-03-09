<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>EC2 management</title>
    <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/default/easyui.css">
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="http://v3.bootcss.com/examples/dashboard/dashboard.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/myCss.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <span class="glyphicon glyphicon-asterisk">brand</span>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="http://v3.bootcss.com/examples/dashboard/#">Help</a></li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar" id="parent">
                    <li class="sidebar-menu">
                        <a href="#accountMenu" class="nav-header collapsed" data-toggle="collapse">账户管理</a>
                        <ul id="accountMenu" class="nav collapse menu-second">
                            <li><a href="${pageContext.request.contextPath}/useraccount/toaccount.do"><i class="glyphicon glyphicon-tint"></i> 秘钥对管理</a></li>
                            <li><a href="${pageContext.request.contextPath}/useraccount/toiam.do"><i class="glyphicon glyphicon-tint"></i> IAM管理</a></li>
                        </ul>
                        <a href="#EC2Meun" class="nav-header collapsed" data-toggle="collapse">EC2管理</a>
                        <ul id="EC2Meun" class="nav collapse menu-second">
                            <li><a href="${pageContext.request.contextPath}/ec2/tocreateec2.do"><i class="glyphicon glyphicon-tint"></i> EC2创建</a></li>
                            <li><a href="${pageContext.request.contextPath}/ec2/toswitchec2.do"><i class="glyphicon glyphicon-tint"></i> EC2状态切换</a></li>
                            <li><a href="${pageContext.request.contextPath}/ec2/toec2schedule.do"><i class="glyphicon glyphicon-tint"></i> EC2调度</a></li>
                        </ul>
                        <a href="#S3Menu" class="nav-header collapsed" data-toggle="collapse">S3管理</a>
                        <ul id="S3Menu" class="nav collapse menu-second">
                            <li><a href="${pageContext.request.contextPath}/s3/tobucketmanagement.do"><i class="glyphicon glyphicon-tint"></i> 存储桶管理</a></li>
                            <li><a href="${pageContext.request.contextPath}/s3/tofilemanagement.do"><i class="glyphicon glyphicon-tint"></i> 文件管理</a></li>
                        </ul>
                    </li>
                    <li><a href="${pageContext.request.contextPath}/data/todata.do">数据采集程序管理</a></li>
                </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main" style="margin-top: -20px">

            <h2 class="sub-header">Wecome to use the IAM Management</h2>
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title">增添IAM</h3>
                </div>
                <div class="panel-body">
                    <form id="iamform" action="addiam.do" method="post" enctype="multipart/form-data" role="form-horizontal">

                            <div class="form-group has-info has-feedback" style="margin-top: 30px" >
                                <label style="float:left">账户名：</label>
                                <input class="easyui-combobox" id="addiam" name="username" 
								data-options="
								url:'${pageContext.request.contextPath}/useraccount/getnamelist.do',
								method:'get',
								valueField:'name',
								textField:'name',
								multiple:false,
								panelHeight:'auto',
							">
                            </div>

                            <div class="form-group has-info has-feedback" style="margin-top: 50px">
                                <label style="align:center; float:left; height:30px">IAM号：</label>
                                <input type="text" placeholder="请输入IAM" name="iam" id="iamnum">
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-10 col-sm-10">
                                    <input type="button" class="btn btn-lg btn-info" value="确定" id="addbutton">
                                </div>
                            </div>
                    </form>

                </div>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title">修改IAM</h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <label class="col-sm-offset-1" style="align:center; float:left; height:30px;margin-right:50px">账户名：</label>
 						<input class="easyui-combobox" id="updateiam" name="username" 
						data-options="
						url:'${pageContext.request.contextPath}/useraccount/getnamelist.do',
						method:'get',
						valueField:'name',
						textField:'name',
						multiple:false,
						panelHeight:'auto',
						">
                        <div  class="col-sm-offset-6">
                            <input type="button" id="updateButton" class="btn btn-sm btn-success"   data-toggle="modal" data-target="#myModal"value="确定">
                        </div>
                    </div>

                </div>
            </div>

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title">删除IAM</h3>
                </div>
                <div class="panel-body">
                <form id="deleteform" action="deleteiam.do" method="post" enctype="multipart/form-data" role="form-horizontal">
                    <div class="row">
                        <label class="col-sm-offset-1" style="align:center; float:left; height:30px;margin-right:50px">账户名：</label>
                        <input class="easyui-combobox" id="deleteiam" name="username" 
						data-options="
						url:'${pageContext.request.contextPath}/useraccount/getnamelist.do',
						method:'get',
						valueField:'name',
						textField:'name',
						multiple:false,
						panelHeight:'auto',
						">
                        <div  class="col-sm-offset-6">
                            <input type="button" class="btn btn-sm btn-success" value="确定" id="deletebutton">
                        </div>
                    </div>
				</form>
                </div>
            </div>

        </div>
    </div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">修改信息</h4>
            </div>
            <form id="updateform" action="updateiam.do" method="post" enctype="multipart/form-data" role="form-horizontal">
            	<div class="modal-body">
            		<div class="form-group has-info has-feedback">
							<label style="align: center; float: left; height: 30px">&nbsp;账&nbsp;&nbsp;户&nbsp;&nbsp;名&nbsp;：</label>
							<input type="text" id="updatename" name="username" readonly>
					</div>
                    <div class="form-group has-info has-feedback">
                        <label style="align:center; float:left; height:30px">新的IAM号：</label>
                        <input type="text" placeholder="请输入新的IAM号" name="iam" id="newIAM">
                    </div>
            	</div>
            
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                <button type="button" class="btn btn-primary" id="save">保存</button>
	            </div>
            </form>
        </div>
    </div>
</div>

	<script type="text/javascript">
		$(document).ready(function() {
			var a = '<%=request.getAttribute("result")%>' ;
			$('#updateButton').click(function(){
				$('#updatename').val($('#updateiam').combobox('getText'));
			});
			////////////表单提交判断 
			$('#addbutton').click(function(){
				var cuser = $('#addiam').combobox('getValue');
				var cid = $('#iamnum').val();
				if(cuser==""||cid==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#iamform').submit();
				}				
			});
			
			$('#deletebutton').click(function(){
				var cuser = $('#deleteiam').combobox('getValue');
				if(cuser==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#deleteform').submit();
				}				
			});
			
			$('#save').click(function(){
				var cuser = $('#updatename').val();
				var cid = $('#newIAM').val();
				if(cuser==""||cid==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#updateform').submit();
				}				
			});
			if(a!="null"){
				$.messager.show({
					title:'操作结果',
					msg:a,
					timeout:5000,
					showType:'slide'
				});

			}
		});
	</script>

</body>
</html>