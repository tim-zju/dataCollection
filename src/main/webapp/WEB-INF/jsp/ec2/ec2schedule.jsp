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
                        <h3 class="panel-title">EC2 Schedule</h3>
                    </div>
                    <div class="panel-body">
                        <form id="schedulecreateaction" action="schedulecreate.do" method="post" enctype="multipart/form-data" role="form-horizontal">
                            <a class="btn btn-primary" role="button" data-toggle="collapse" href="#advance" aria-expanded="false"
                               aria-controls="collapseExample" style="margin-bottom: 10px">创建EC2调度</a>
                            	<div class="collapse" id="advance">
                                
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
	                                        <input class="easyui-combobox" id="ec2region" name="regionname"
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
                                
                                    <div class="form-group has-info has-feedback" style="margin-top: 20px">
                                        <label style="float:left">EC2&nbsp;&nbsp;实例名&nbsp;&nbsp;:</label>
                                        <input type="text" name="ec2name" placeholder="请输入需要创建的EC2实例名">
                                    </div>
                                    
                                    <div class="form-group">
	                                    <div class="col-sm-offset-10 col-sm-10">
	                                        <input type="button" id="createbutton" class="btn btn-lg btn-info" value="创建">
	                                    </div>
                                	</div>
                                   
                                </div>
                                
                        </form>
                        
                        <div >
                        	<h3 class="panel-title col-md-3">查询可以调度的虚拟机</h3>
							<input type="button" class="btn btn-sm btn-success" id="query" value="查询">
							<table id="datatable" class="easyui-datagrid"  
					        	data-options="
					        	url:'getschedulelist.do',
					        	fitColumns:true,
					        	singleSelect:true,
					        	title:'调度虚拟机',
					        	pagination:true,
					        	height:300,
					        	checkOnSelect:true,
					        	rownumbers:true,
					        	showHeader:true,
					        	showFooter:true,
					        	singleSelect:false,
					        	queryParams:{query:'false'}
					        ">
							    <thead>   
							        <tr>   
							            <th data-options="field:'keyName',width:100,align:'center',checkbox:'true'">选择</th>   
							            <th data-options="field:'ec2name',width:100,align:'center'">EC2名</th>   
							            <th data-options="field:'ec2id',width:100,align:'center'">EC2ID</th>   
							            <th data-options="field:'region',width:100,align:'center'">地区</th>
							            <th data-options="field:'description',width:100,align:'center'">所属账户</th>   
							            <th data-options="field:'state',width:100,align:'center'">状态</th>   
							        </tr>   
							    </thead>   
							</table>
						  	<h3 class="panel-title col-md-3">设置调度间隔：</h3>
						  	<select class="easyui-combobox " name="option" id="option" style="width:150px; height:20px;margin-left:50px;margin-right:50px" >
	                                  <option value="1">1分钟</option>
	                                  <option value="2">2分钟</option>
	                                  <option value="5">5分钟</option>
	                                  <option value="10">10分钟</option>
	                              </select>
							<input type="button" class="btn btn-sm btn-info" id="start" value="开始调度" style="margin-left:150px">
                        </div>
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
				$('#schedulecreateaction').submit();
			}				
			
		});
		
		$('#query').click(function(){
			$('#datatable').datagrid('load',{query:'true'});
		});
		
		$('#start').click(function(){
			var selectObj = $('#datatable').datagrid('getSelections');
			var len = selectObj.length;
			$(selectObj).each(function(index,element){
				$.ajax({
					url : 'schedulestart.do',
					type : 'POST',
					data:{"userkey":selectObj[index].keyName,"ec2id":selectObj[index].ec2id,"region":selectObj[index].region,
						"state":selectObj[index].state,"index":index,"length":len,"time":$('#option').val()},
					dataType : 'text',
					async : true,
					success : function(data) {
						if(data=="EC2 schedule start"){
							$.messager.show({
								title:'操作结果',
								msg:'EC2 开始调度', 
								timeout:5000,
								showType:'slide'
							});
						}
					}
				});
			});
		});
		
	});
</script>

</body>
</html>