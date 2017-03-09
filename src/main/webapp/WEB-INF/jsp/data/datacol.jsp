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
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
				style="margin-top: -20px">
				<!--<h1 class="page-header"></h1>-->
				<!--<div class="row placeholders"></div>-->
				<h2 class="sub-header">Wecome to use the DataCollector
					Management</h2>

				<div div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">添加数据采集程序</h3>
					</div>
					<div class="panel-body">
						<form id="addform" method="post" action="addCollector.do"
							enctype="multipart/form-data">

							<div class="form-group has-info has-feedback">
								<label style="align: center; float: left; height: 30px">数据采集程序名：</label>
								<input type="text" placeholder="请输入程序名" name="datacollectorname"
									id="datacollectorname">
							</div>
							
							<div class="form-group has-info has-feedback">
                                        <label style="float:left">描述信息:</label>
                                        <input type="text" name="description" class="form-control" placeholder="请输入描述信息">
                                    </div>

							<div class="form-group has-info has-feedback">
								<label style="float: left">请选择程序文件：</label> <input
									class="btn btn-info" type="file" name="uploadfile" value="上传文件" id="filename">
							</div>

							<div class="form-group">
								<div class="col-sm-offset-10 col-sm-10">
									<input type="button" class="btn btn-lg btn-info"
										value="一键上传至S3" id="ups3">
								</div>
							</div>
						</form>
					</div>
				</div>

				<div div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">删除数据采集程序</h3>
					</div>
					<div class="panel-body">
						<form id="delform" method="post" action="delCollector.do"
							enctype="multipart/form-data" role="form-horizontal">
								<div class="form-group has-info has-feedback">
									<div class="row">
										<label class="col-sm-offset-1"
											style="align: center; float: left; height: 30px;margin-right:50px">数据采集程序名：</label>
										<input
										class="easyui-combobox " id="dataname" name="dataname"
										data-options="
												url:'${pageContext.request.contextPath}/data/getdatalist.do',
												method:'get',
												valueField:'name',
												textField:'name',
												multiple:false
										">
										<div class="col-sm-offset-6">
											<input type="button" class="btn btn-sm btn-info" value="确定" id="delbutton">
										</div>
									</div>
								</div>
						</form>
					</div>
				</div>

				<div div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">修改数据采集程序</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<label class="col-sm-offset-1"
								style="align: center; float: left; height: 30px;margin-right:50px">数据采集程序名：</label>
							<input
							class="easyui-combobox " name="dataname" id="choosename"
							data-options="
									url:'${pageContext.request.contextPath}/data/getdatalist.do',
									method:'get',
									valueField:'name',
									textField:'name',
									multiple:false
							">
							<div class="col-sm-offset-6">
								<input type="submit" class="btn btn-sm btn-info"
									data-toggle="modal" data-target="#myModal" id="updateButton" value="确定">
							</div>
						</div>
					</div>
				</div>

				<div div class="panel panel-info">
					<div class="panel-heading" class="row">
						<h3 class="panel-title col-md-3">查询数据采集程序</h3>
						<input type="button" class="btn btn-sm btn-success" id="query" value="查询">
					</div>
					<div class="panel-body">
						<table id="datatable" class="easyui-datagrid"  
				        	data-options="
				        	url:'getdatacol.do',
				        	fitColumns:true,
				        	singleSelect:true,
				        	title:'数据采集程序',
				        	pagination:true,
				        	queryParams:{query:'false'}
				        ">
						    <thead>   
						        <tr>   
						            <th data-options="field:'name',width:100,align:'center'">名称</th>   
						            <th data-options="field:'filename',width:100,align:'center'">存储s3位置</th>   
						            <th data-options="field:'description',width:100,align:'center'">描述信息</th>
						            <th data-options="field:'datasources',width:100,align:'center'">所属账户</th>   
						        </tr>   
						    </thead>   
						</table>  
					</div>
				</div>
				
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">后台下载模块</h3>
					</div>
					<div class="panel-body">
						<form id="addform" method="post" action="downloadbg.do"
							enctype="multipart/form-data">

							<div class="form-group has-info has-feedback">
								<label style="align: center; float: center; height: 30px" class="col-sm-offset-3">下载间隔：</label>
								<select class="easyui-combobox " name="option" id="option" style="width:150px; height:20px;margin-left:50px" >
                                    <option value="3">3分钟</option>
                                    <option value="5">5分钟</option>
                                    <option value="10">10分钟</option>
                                    <option value="20">20分钟</option>
                                </select>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-10 col-sm-10">
									<input type="submit" class="btn btn-lg btn-info"
										value="开始" id="start">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改信息</h4>
				</div>
				<form id="updatedataform" method="post" role="form-horizontal" action="updatedata.do"
						enctype="multipart/form-data">
					<div class="modal-body">
					
							<div class="form-group has-info has-feedback">
								<div class="row">
									<label class="col-sm-offset-1"
										style="align: center; float: left; height: 30px">数据采集程序名：</label>
									<input type="text" id="showname" name="dataname" readonly>
								</div>
							</div>
							<div class="form-group has-info has-feedback">
								<div class="row">
									<label class="col-sm-offset-1"
										style="align: center; float: left; height: 30px">描&nbsp;&nbsp;&nbsp;述&nbsp;&nbsp;&nbsp;信&nbsp;&nbsp;&nbsp;息&nbsp;&nbsp;：</label>
									<input type="text" placeholder="请输入描述信息" name="description" id="description">
								</div>
							</div>
					
					</div>
					<div class="modal-footer">
						<div class="form-group has-info has-feedback">
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
							<button type="button" class="btn btn-primary" id="save">保存</button>
						</div>
					</div>
				</form>
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
			
			$('#updateButton').click(function(){
				$('#showname').val($('#choosename').combobox('getText'));
			});
			
			$('#query').click(function(){
				$('#datatable').datagrid('load',{query:'true'});
			});
			
			////////表单提交验证
			$('#ups3').click(function(){
				var cuser = $('#datacollectorname').val();
				var cid = $('#filename').val();
				if(cuser==""||cid==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#addform').submit();
				}				
			});
			
			$('#delbutton').click(function(){
				var cuser = $('#dataname').combobox('getValue');
				if(cuser==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#delform').submit();
				}				
			});
			
			$('#save').click(function(){
				var cuser = $('#showname').val();
				if(cuser==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#updatedataform').submit();
				}				
			});
			
		});
	</script>
	
</body>
</html>