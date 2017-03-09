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
                <h2 class="sub-header">Wecome to use the bucket Management</h2>
                <div div class="panel panel-success">
                    <div class="panel-heading">
                        <h3 class="panel-title">bucket Create</h3>
                    </div>

                    <div class="panel-body">
                        <form role="form-horizontal" id="createbucketform" action="createbucket.do"
							method="post">
							<input type="hidden" name="username" id="username">

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
											}
									">
								</div>
							</div>
							<div class="form-group">
								<label for="name">存储桶名称：</label>
								<!--		<div class="col-sm-6"> -->
								<input type="text" class="form-control" id="bucketname"
									name="bucketname" placeholder="请输入存储桶名称"></input>
								<!-- 		</div> -->
							</div>
							<div class="form-group">
								<div class="col-sm-offset-10 col-sm-10">
									<input type="button" class="btn btn-lg btn-success" value="提交" id="createbutton">
								</div>
							</div>
						</form>
                    </div>
                </div>
                
                
                <!-- delete -->
                <div div class="panel panel-success">
                    <div class="panel-heading">
                        <h3 class="panel-title">bucket Delete</h3>
                    </div>

                    <div class="panel-body">
                        <form role="form-horizontal" id="delbucketform" action="deletebucket.do"
							method="post">
							<input type="hidden" name="username" id="delusername">

							<div class="form-group has-info has-feedback">
								<label for="name"
									style="align: center; float: left; height: 30px">用户名：</label>

								<div class="col-sm-16">
									<input class="easyui-combobox" id="deluserkeyselect"
										name="userkey" style="width: 150px; height: 30px;"
										data-options="
										url:'${pageContext.request.contextPath}/ec2/getuserkeylist.do',
										method:'get',
										valueField:'userkey',
										textField:'username',
										multiple:false,
										panelHeight:'auto',
										onSelect:function(rec){
										$('#delusername').val(rec.username);
										$('#delbucketname').combobox('reload','${pageContext.request.contextPath}/s3/getbucketnames.do?username='+rec.username);
										}
									">
								</div>
							</div>
							<div class="form-group">
								<label for="name" style="float: left">存储桶：</label>

								<div class="col-sm-16">
									<input class="easyui-combobox" id="delbucketname"
										name="bucketname"
										data-options="method:'get',multiple:false,
										panelHeight:'auto',valueField:'name',textField:'name'"
										style="width: 150px; height: 30px;">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-10 col-sm-10">
									<input type="button" class="btn btn-lg btn-success" value="提交" id="delbutton">
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
			$('#createbutton').click(function(){
				var cuser = $('#userkeyselect').combobox('getValue');
				var cid = $('#bucketname').val();
				if(cuser==""||cid==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#createbucketform').submit();
				}				
			});
			
			$('#delbutton').click(function(){
				var cuser = $('#deluserkeyselect').combobox('getValue');
				var cid = $('#delbucketname').combobox('getValue');
				if(cuser==""||cid==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#delbucketform').submit();
				}				
			});
		});
	</script>
    
</body>
</html>