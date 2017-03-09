<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ddcollection-s3mainui</title>
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
<style type="text/css">
* {
	margin: 0px;
	padding: 0px;
}

/* body {
	background: transparent url('/jscss/demoimg/201102/bg.gif') repeat
		scroll left top;
	font-size: 14px;
} */
ul, li, p, h1, h2, h3, div, label, input, form {
	list-style: none;
	padding: 0px;
	margin: 0px;
}

#header {
	width: 600px;
	margin: 20px auto 0;
	color: #ffffff;
	background: transparent url('/jscss/demoimg/201102/header1.jpg')
		repeat-x scroll left top;
	height: 90px;
	line-height: 90px;
	font-size: 20px;
	overflow: hidden;
}

#header p {
	padding-left: 15px;
	font-weight: bold;
}

#header p span.date {
	float: right;
	padding-right: 15px;
	font-size: 14px;
	padding-top: 10px;
	font-weight: normal;
}

.content {
	width: 596px;
	_height: 430px;
	min-height: 430px;
	margin: 0 auto;
	border: 2px solid #AAAA00;
	border-top: none;
	background-color: #FFFFFF;
	overflow: hidden;
}

.toggle {
	width: 450px;
	margin: 20px auto;
}

.toggle dl dt {
	background: #F4FFF4 url('/jscss/demoimg/201102/bg_toggle_up.gif')
		no-repeat scroll 8px 14px;
	height: 40px;
	width: 450px;
	line-height: 40px;
	font-size: 16px;
	font-weight: bold;
	color: #006600;
	cursor: pointer;
	margin: 8px 0;
	padding-left: 25px;
	display: block;
}

.toggle dl dt.current {
	background: #F4FFF4 url('/jscss/demoimg/201102/bg_toggle_down.gif')
		no-repeat scroll 8px 14px;
}

.toggle dl dd {
	padding-left: 10px;
	line-height: 24px;
}

.toggle dl dd h2 {
	font-size: 15px;
}

.toggle dl dd ul {
	padding-bottom: 12px;
}

.toggle dl dd ul li {
	list-style: decimal inside none;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#getname").click(function() {
			/* var keys = $("#s3select").combobox('getValues').toString(); */
			document.listbucket.action = "listbucket.do";

		});
	});
	$(function() {
		$(".toggle dl dd").hide();
		$(".toggle dl dt").click(function() {
			$(".toggle dl dd").not($(this).next()).hide();
			$(".toggle dl dt").not($(this).next()).removeClass("current");
			$(this).next().slideToggle(500);
			$(this).toggleClass("current");
		});
	});
</script>
</head>
<body>
	<form name="listbucket" action="" method="post">
		<div style="margin: 20px 0"></div>
		<input class="easyui-combobox" id="s3select" name="userkey"
			data-options="
					url:'${pageContext.request.contextPath}/s3/getuserkeylist.do',
					method:'get',
					valueField:'userkey',
					textField:'username',
					multiple:true,
					panelHeight:'auto'
			">
		<button id="getname">get</button>
		<div id="header">
			<p>
				<span class="date">Buckets列表</span>
		</div>
		<div class="content">
			<div class="toggle">

				<dl>
					<c:forEach items="${mybuckets }" var="mybucket">
						<dt>${mybucket.name }</dt>
						<dd>
							<table>
								<tr>
									<td>object名字</td>
									<td>object描述信息</td>
								</tr>
								<c:forEach items="${mybucket.myobjects }" var="myobject">
									<tr>
										<td>${myobject.name }</td>
										<td>${myobject.details }</td>
									</tr>
								</c:forEach>
							</table>
						</dd>
					</c:forEach>
				</dl>
			</div>
		</div>
	</form>
</body>
</html>