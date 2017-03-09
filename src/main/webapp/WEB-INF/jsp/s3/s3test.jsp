<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="uploadobject.do" method="post" enctype="multipart/form-data">
		<p>choose the file</p>
		<input type="file" name="uploadfile" value="请上传"></input>
		<br />
		<p>enter the user name</p>
		<input type="text" name="username"></input>
		<br />
		<p>choose the bucket</p>
		<select name="bucketname">
		<option value="soft">Soft</option>
		<option value="program">Program</option>
		<option value="data">Data</option>
		</select>
		<br />
		<input type="submit" value="上传">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/demo.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
/* 	$(document)
			.ready(
					function() {
						$('#username')
								.combobox(
										{
											onSelect : function() {
												$('bucketname')
														.urlcombobox('reload',
																'${pageContext.request.contextPath}/s3/getAllBucketsName.do');
											}
										});
					});  */
</script>
</head>
<body>
	<form action="uploadobject.do" method="post"
		enctype="multipart/form-data">
		<p>choose the file</p>
		<input type="file" name="uploadfile" value="请上传"></input> <br />
		<p>enter the user name</p>
		<input class="easyui-combobox" id="username" name="username"
			data-options="
					url:'${pageContext.request.contextPath}/ec2/getuserkeylist.do',
					method:'get',
					valueField:'username',
					textField:'username',
					multiple:false,
					panelHeight:'auto',
					onSelect:function(rec){
				$('#bucketname').combobox('reload','${pageContext.request.contextPath}/s3/getAllBucketsName.do?username='+rec.username)
			}
			" />
		<br />
		<p>choose the bucket</p>
		<input class="easyui-combobox" id="bucketname" name="bucketname"
			data-options="valueField:'name',textField:'name'"> <br /> <input
			type="submit" value="上传">
	</form>
</body>
</html>