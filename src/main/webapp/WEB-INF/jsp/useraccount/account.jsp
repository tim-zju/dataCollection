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
            <div class="col-sm-3 col-md-2 sidebar" >
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

                <h2 class="sub-header">Wecome to use the Account Management</h2>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">增添账户</h3>
                    </div>
                    <div class="panel-body">
                        <form id="createuser" action="createuser.do" method="post" enctype="multipart/form-data" role="form-horizontal">

                                <div class="form-group has-info has-feedback">
                                    <label style="align:center; float:left; height:30px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;&nbsp;&nbsp;：</label>
                                    <input type="text" placeholder="请输入用户名" name="username" id="mycreateuser">
                                </div>

                                <div class="form-group has-info has-feedback">
                                    <label style="align:center; float:left; height:30px">AWSAccessKeyId：</label>
                                    <input type="text" placeholder="请输入秘钥对的KEY" name="AWSAccessKeyId" id="createAWSAccessKeyId">
                                </div>

                                <div class="form-group has-info has-feedback">
                                    <label style="align:center; float:left; height:30px">&nbsp;AWSSecretKey&nbsp;&nbsp;&nbsp;&nbsp;：</label>
                                    <input type="text" placeholder="请输入秘钥对的VALUE" name="AWSSecretKey" id="createAWSSecretKey">
                                </div>

                                <div class="col-sm-offset-10 col-sm-10">
                                    <input type="button" class="btn btn-lg btn-info" value="创建" id="createbutton">
                                </div>
                        </form>

                    </div>
                </div>

				<!-- update -->
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">修改账户</h3>
                    </div>
                    <div class="panel-body">
                        <div class="row">
                            <label class="col-sm-offset-1" style="align:center; float:left; height:30px;margin-right:50px">账户名：</label>
							<input class="easyui-combobox" id="updateusername" name="username" 
								data-options="
								url:'${pageContext.request.contextPath}/useraccount/getnamelist.do',
								method:'get',
								valueField:'name',
								textField:'name',
								multiple:false,
								panelHeight:'auto',
							">
                            <div  class="col-sm-offset-6 ">
                                <input type="button" class="btn btn-sm btn-info" id="updateButton" data-toggle="modal" data-target="#myModal"value="确定">
                            </div>
                        </div>

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
                <form id="updateaccount" action="updateaccount.do" method="post" enctype="multipart/form-data" role="form-horizontal">
                	<div class="modal-body">
                			<div class="form-group has-info has-feedback">
								<div class="row">
									<label class="col-sm-offset-1"
										style="align: center; float: left; height: 30px">&nbsp;&nbsp;&nbsp;&nbsp;用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;&nbsp;&nbsp;：</label>
									<input type="text" id="updatename" name="accountname" readonly id="upaccount">
								</div>
							</div>
                            <div class="form-group has-info has-feedback">
                                <label style="align:center; float:left; height:30px">新的AWSAccessKeyId：</label>
                                <input type="text" placeholder="请输入新的秘钥对的KEY" name="AWSAccessKeyId" id="upAWSAccessKeyId">
                            </div>

                            <div class="form-group has-info has-feedback">
                                <label style="align:center; float:left; height:30px">&nbsp;新的AWSSecretKey&nbsp;&nbsp;&nbsp;&nbsp;：</label>
                                <input type="text" placeholder="请输入秘钥对的VALUE" name="AWSSecretKey" id="upAWSSecretKey">
                            </div>
                	</div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                    <button type="button" class="btn btn-primary" id="updatebutton">保存</button>
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
				$('#updatename').val($('#updateusername').combobox('getText'));
			});
			
			///////表格提交判断 
			$('#createbutton').click(function(){
				var cuser = $('#mycreateuser').val();
				var cid = $('#createAWSAccessKeyId').val();
				var ckey = $('#createAWSSecretKey').val();
				if(cuser==""||cid==""||ckey==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#createuser').submit();
				}				
			});
			
			$('#updatebutton').click(function(){
				var cuser = $('#upaccount').val();
				var cid = $('#upAWSAccessKeyId').val();
				var ckey = $('#upAWSSecretKey').val();
				if(cuser==""||cid==""||ckey==""){
					$.messager.alert('警告','提交信息不能为空，请认真填写');
				} else {
					$('#updateaccount').submit();
				}				
			});
		});
	</script>
    
</body>
</html>