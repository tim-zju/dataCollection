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
                <h2 class="sub-header">Wecome to use the EC2 Management</h2>
                <div class="panel panel-info">
                    <div class="panel-heading" >
                        <h3 class="panel-title">EC2 Create</h3>
                    </div>
                    <div class="panel-body">
                        <form id="ec2createaction" action="launchinstance.do" method="post" enctype="multipart/form-data" role="form-horizontal">
								
								<div class="form-group has-info has-feedback" style=";margin-top: 10px">
									<div style="margin: 20px 0"></div>
									<!-- 获取username and accesskey -->
									<input type="hidden" name="username" id="username" value=""> 
								</div>
								
                                <div class="form-group has-info has-feedback" style=";margin-top: 10px">
                                    <label style="align:center; float:left; height:30px" >账户名：</label>
                                    <div class="col-sm-16">
                                        <input
										class="easyui-combobox" id="ec2select" name="userkey" id="ec2select" 
										data-options="
												url:'${pageContext.request.contextPath}/ec2/getuserkeylist.do',
												method:'get',
												valueField:'userkey',
												textField:'username',
												multiple:false,
												panelHeight:'auto'
										">
                                    </div>
                                </div>


                                <div class="form-group has-info has-feedback" >
                                    <label style="float:left">区&nbsp;&nbsp;&nbsp;域&nbsp;：</label>

                                    <div class="col-sm-16">
                                        <input class="easyui-combobox" id="ec2region" name="region"
										data-options="
												url:'${pageContext.request.contextPath}/ec2/getregionlist.do',
												method:'get',
												valueField:'value',
												textField:'name',
												multiple:false,
												panelHeight:'auto'
										">

                                    </div>
                                </div>

                                <a class="btn btn-primary" role="button" data-toggle="collapse" href="#advance" aria-expanded="false"
                                   aria-controls="collapseExample" style="margin-bottom: 10px">高级设置</a>
                                <div class="collapse" id="advance">
                                    <div class="form-group has-info has-feedback">
                                        <label style="float:left">脚本文件上传:</label>
                                        <input class="btn btn-info" type="file" name="uploadShell" value="上传文件" >
                                    </div>
                                    <div class="form-group has-info has-feedback" style="margin-top: 20px">
                                        <label style="float:left">EC2&nbsp;&nbsp;实例名&nbsp;&nbsp;:</label>
                                        <input type="text" name="ec2name" placeholder="请输入需要创建的EC2实例名">
                                    </div>
                                    <div class="form-group has-info has-feedback" style="margin-top: 20px">
                                        <label style="float:left">数据采集程序:</label>
                                        <div class="col-sm-16">
                                        	<input class="easyui-combobox" id="ec2DataCollctor" name="dataCollector"
											data-options="
													url:'${pageContext.request.contextPath}/ec2/getDataCollectorlist.do',
													method:'get',
													valueField:'name',
													textField:'name',
													multiple:false,
													panelHeight:'auto'
											">
                                        </div>
                                    </div>
                                    <div class="form-group has-info has-feedback">
                                        <label style="float:left">描述信息:</label>
                                        <input type="text" name="description" class="form-control" placeholder="请输入EC2实例的描述信息">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-sm-offset-10 col-sm-10">
                                        <input type="button" id="createbutton" class="btn btn-lg btn-info" value="创建">
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
		$("#createbutton").click(function() {
			
			var cuser = $('#ec2select').combobox('getValue');
			var cid = $('#ec2region').combobox('getValue');
			if(cuser==""||cid==""){
				$.messager.alert('警告','用户和地区不能为空，请认真填写'); 
			} else {
				var name = $("#ec2select").combobox('getText').toString();
				$("#username").val(name);
				$('#ec2createaction').submit();
			}				
			
		});
		var a = '<%=request.getAttribute("result")%>' ;
		
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