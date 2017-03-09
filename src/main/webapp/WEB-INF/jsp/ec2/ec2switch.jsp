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
                <!--<h1 class="page-header"></h1>-->
                <!--<div class="row placeholders"></div>-->
                <h2 class="sub-header">Wecome to use the EC2 Management</h2>
                <div div class="panel panel-info">
                    <div class="panel-heading">
                        <h3 class="panel-title">EC2 状态管理</h3>
                    </div>
                    <div class="panel-body" >
                        <form id="switchform" action="switchstate.do" method="post" enctype="multipart/form-data" style="margin-left: 400px" role="form-horizontal">

                                <div class="form-group has-info has-feedback"  style="margin-top: 20px">
                                    <label style="align:center; float:left; height:30px;font-size: larger">账&nbsp;户&nbsp;名：</label>
                                    <div class="col-sm-16">
                                        <input
										class="easyui-combobox" id="username" name="userkey" id="username" 
										data-options="
												url:'${pageContext.request.contextPath}/ec2/getuserkeylist.do',
												method:'get',
												valueField:'userkey',
												textField:'username',
												multiple:false,
												
										">
                                    </div>
                                </div>

                                <div class="form-group has-info has-feedback"  style="margin-top: 20px">
                                    <label style="float:left;font-size: larger">区&nbsp;&nbsp;&nbsp;&nbsp;域&nbsp;&nbsp;：</label>

                                    <div class="col-sm-16" >
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
                                
                                <div class="form-group has-info has-feedback"  style="margin-top: 20px">
                                    <label style="float:left;font-size: larger">EC2&nbsp;&nbsp;名&nbsp;：</label>
                                    <div class="col-sm-16">
                                        <input class="easyui-combobox" id="ec2name" name="ec2id" data-options="
                                        valueField:'ec2id',
										textField:'ec2name',
										multiple:false,
										panelHeight:'auto'"
										>
                                    </div>
                                </div>
                                
                                <div class="form-group has-info has-feedback" style="margin-top: 20px">
                                    <label style="float:left;font-size: larger">当前状态：</label>
                                    <h3 id="state">&nbsp;</h3>
                                </div>
                                
                                <div class="form-group has-info has-feedback"  style="margin-top: 20px">
                                    <label style="float:left;font-size: larger">切换至状态：</label>
                                    <div class="col-sm-16">
                                        <select class="selectpicker" name="option" id="option" style="width:150px; height:30px;" >
                                            <option value="start">启动</option>
                                            <option value="restart">重启</option>
                                            <option value="stop">停止</option>
                                            <option value="destroy">销毁</option>
                                        </select>

                                    </div>
                                </div>

                                <div class="form-group">
                                    <input type="button" id="switchbutton" class="btn btn-lg btn-info" value="确定">
                                    <label style="margin-left: 45px;margin-right: 45px"></label>
                                    <input type="reset" class="btn btn-lg btn-info" value="重置" id="reset">
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
		
		$("#ec2region").combobox({
			onSelect: function(regionParam){
				var username = $('#username').combobox('getText');
				var region = $('#ec2region').combobox('getValue');
				$("#ec2name").combobox('reload','${pageContext.request.contextPath}/ec2/getec2namelist.do?username='
						+username+'&region='+region);
			}
		});
		
		$("#ec2name").combobox({
			onSelect: function(param){
				var state = param.state;
				$('#state').html(state);
				if(state=="运行"){
					$("#option").empty();
			///		$("#option").append("<option value='start'>启动</option>");
					$("#option").append("<option value='restart'>重启</option>");
					$("#option").append("<option value='stop'>停止</option>");
					$("#option").append("<option value='destroy'>销毁</option>");
				}else if(state=="停止"){
					$("#option").empty();
					$("#option").append("<option value='start'>启动</option>");
			///		$("#option").append("<option value='restart'>重启</option>");
			///		$("#option").append("<option value='stop'>停止</option>");
					$("#option").append("<option value='destroy'>销毁</option>");
				}else if(state=="销毁"){
					$("#option").empty();
					$("#option").append("<option value='nothing'>销毁状态无法操作</option>");
				}else{}
			}
		});
		
		////////////////form表提交判断 
		$("#switchbutton").click(function() {
			var cid = $('#state').html().trim();
			if(cid=="销毁"){ 
				$.messager.alert('警告','抱歉，当前为销毁状态，无法进行任何操作 ');
			} else if(cid!="&nbsp;"){
				$('#switchform').submit();
			} else {
				$.messager.alert('警告','提交信息不能为空，请认真填写');
			}				
			
		});
		$('#reset').click(function(){
			window.location.reload();//刷新当前页面
		});
		
		
	});
</script>
    
</body>
</html>