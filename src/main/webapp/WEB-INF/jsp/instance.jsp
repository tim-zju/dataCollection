<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="uploadobject.do" method="post" enctype="multipart/form-data">
		<input type="file" name="uploadfile" value="请上传">
		<p>
		<label>请输入文件名称：</label> 
		<input type="text" name="filename">
		<input type="submit" value="上传">
	</form>
</body>
</html>