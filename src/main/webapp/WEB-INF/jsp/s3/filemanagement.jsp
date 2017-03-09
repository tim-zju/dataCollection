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

			<h2 class="sub-header">Wecome to use the file Management</h2>
			<!--up-->
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">dataObject upload</h3>
				</div>
				<div class="panel-body">
					<form role="form-horizontal" id="uploadform" action="uploadobject.do"
							method="post" enctype="multipart/form-data">
							<input name="username" type="hidden" id="username">
							<div class="form-group has-info has-feedback">
								<label for="name"
									style="align: center; float: left; height: 30px">用户名：</label>

								<div class="col-sm-16">
									<input class="easyui-combobox" id="userkeyselect"
										name="userkey" style="width: 150px; height: 30px;"
										data-options="
										url:'${pageContext.request.contextPath}/ec2/getuserkeylist.do',
										method:'get',
										valueField:'userkey',
										textField:'username',
										multiple:false,
										panelHeight:'auto',
										onSelect:function(rec){
										$('#username').val(rec.username);
										$('#bucketname').combobox('reload','${pageContext.request.contextPath}/s3/getbucketnames.do?username='+rec.username);
										}
									">
								</div>
							</div>

							<div class="form-group has-info has-feedback">
								<label for="name" style="float: left">存储桶：</label>

								<div class="col-sm-16">
									<input class="easyui-combobox" id="bucketname"
										name="bucketname"
										data-options="method:'get',multiple:false,
										panelHeight:'auto',valueField:'name',textField:'name'"
										style="width: 150px; height: 30px;">
								</div>
							</div>

							<div class="form-group has-info has-feedback">
								<label for="name" style="float: left">选择文件:</label> <input
									class="btn btn-info" type="file" name="uploadfile" value="上传文件" id="filename"></input>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-10 col-sm-10">
									<input type="button" class="btn btn-lg btn-info" value="上传" id="upbutton">
								</div>
							</div>

						</form>

				</div>
			</div>

			<!-- down -->
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">dataObject download</h3>
				</div>
				<div class="panel-body">
					<form id="downform"  method="post" action="downloadobject.do"
							enctype="multipart/form-data">
							
							<input name="username" type="hidden" id="usernamedown">
							<div class="form-group has-info has-feedback">
								<label
									style="align: center; float: left; height: 30px">用户名：</label>

								<div class="col-sm-16">
									<input class="easyui-combobox" id="userkeydown"
										name="userkey" style="width: 150px; height: 30px;"
										data-options="
										url:'${pageContext.request.contextPath}/ec2/getuserkeylist.do',
										method:'get',
										valueField:'userkey',
										textField:'username',
										multiple:false,
										panelHeight:'auto',
										onSelect:function(rec){
										$('#usernamedown').val(rec.username);
										$('#bucketnamedown').combobox('reload','${pageContext.request.contextPath}/s3/getbucketnames.do?username='+rec.username);
										}
									">
								</div>
							</div>
							
							<div class="form-group has-info has-feedback">
								<label style="float: left">存储桶：</label>

								<div class="col-sm-16">
									<input class="easyui-combobox" id="bucketnamedown"
										name="bucketName"
										data-options="
										method:'get',
										multiple:false,
										panelHeight:'auto',
										valueField:'name',
										textField:'name'"
										style="width: 150px; height: 30px;">
								</div>
							</div>

							<div class="form-group has-info has-feedback">
								<label for="name"
									style="align: center; float: left; height: 30px">文件名：</label>

								<div class="col-sm-16">
									<input type="text" class="form-control" id="filenamedown"
										placeholder="请输入文件名称" name="objectName"></input>
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-offset-10 col-sm-10">
									<input type="button" class="btn btn-lg btn-info" value="下载" id="downbutton">
								</div>
							</div>
						</form>

				</div>
			</div>
			
			<!-- delete -->
			<div class="panel panel-info">
				<div class="panel-heading">
					<h3 class="panel-title">dataObject Delete</h3>
				</div>
				<div class="panel-body">
					<form id="deleteform"  method="post" action="deleteobject.do"
							enctype="multipart/form-data">
							
							<input name="username" type="hidden" id="usernamedelete">
							<div class="form-group has-info has-feedback">
								<label
									style="align: center; float: left; height: 30px">用户名：</label>

								<div class="col-sm-16">
									<input class="easyui-combobox" id="userkeydelete"
										name="userkey" style="width: 150px; height: 30px;"
										data-options="
										url:'${pageContext.request.contextPath}/ec2/getuserkeylist.do',
										method:'get',
										valueField:'userkey',
										textField:'username',
										multiple:false,
										panelHeight:'auto',
										onSelect:function(rec){
										$('#usernamedelete').val(rec.username);
										$('#bucketnamedelete').combobox('reload','${pageContext.request.contextPath}/s3/getbucketnames.do?username='+rec.username);
										}
									">
								</div>
							</div>
							
							<div class="form-group has-info has-feedback">
								<label style="float: left">存储桶：</label>

								<div class="col-sm-16">
									<input class="easyui-combobox" id="bucketnamedelete"
										name="bucketName"
										data-options="
										method:'get',
										multiple:false,
										panelHeight:'auto',
										valueField:'name',
										textField:'name',
										onSelect:function(rec){
										var username = $('#userkeydelete').combobox('getText');
										$('#objectdelete').combobox('reload','${pageContext.request.contextPath}/s3/getobjectnames.do?username='+username+'&bucketName='+rec.name);
										}"
										style="width: 150px; height: 30px;">
								</div>
							</div>

							<div class="form-group has-info has-feedback">
								<label for="name"
									style="align: center; float: left; height: 30px">文件名：</label>

								<div class="col-sm-16">
									<input class="easyui-combobox" id="objectdelete"
										name="objectName"
										data-options="
										method:'get',
										multiple:false,
										panelHeight:'auto',
										valueField:'name',
										textField:'name'
										"
										style="width: 150px; height: 30px;">
								</div>
							</div>
							
							<div class="form-group">
								<div class="col-sm-offset-10 col-sm-10">
									<input type="button" class="btn btn-lg btn-info" value="删除" id="delbutton">
								</div>
							</div>
						</form>

				</div>
			</div>
			
		</div>
	</div>
</div>

<script type="text/javascript">
		$(document).ready(function() {
			var a = '<%=request.getAttribute("result")%>' ;
			
			if(a!="null"){
				$.messager.show({
					title:'操作结果',
					msg:a,
					timeout:5000,
					showType:'slide'
				});
			}
			
			///////表格提交判断 
			$('#upbutton').click(function(){
				var cuser = $('#userkeyselect').combobox('getValue');
				var cid = $('#bucketname').combobox('getValue');
				var ckey = $('#filename').val();
				if(cuser==""||cid==""||ckey==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#uploadform').submit();
				}							
			});
			
			$('#downbutton').click(function(){
				var cuser = $('#userkeydown').combobox('getValue');
				var cid = $('#bucketnamedown').combobox('getValue');
				var ckey = $('#filenamedown').val();
				if(cuser==""||cid==""||ckey==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#downform').submit();
				}				
			});
			
			$('#delbutton').click(function(){
				var cuser = $('#userkeydelete').combobox('getValue');
				var cid = $('#bucketnamedelete').combobox('getValue');
				var ckey = $('#objectdelete').combobox('getValue');
				if(cuser==""||cid==""||ckey==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#deleteform').submit();
				}				
			});
		});
	</script>

</body>
</html>

