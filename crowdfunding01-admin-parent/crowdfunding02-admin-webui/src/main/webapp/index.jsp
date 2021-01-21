<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript">
	$(function() {
		$("#btn1").click(function() {
			$.ajax({
				"url": "send/array/one.html",
				"type": "post",
				"data": {
					"array": [1,2,3,4,5,6]
				},
				"dataType": "text",
				"success": function(data) {
					alert(data);
				},
				"error": function(data) {
					alert(data);
				}
			});
		});
		var array = [1,2,3,4,5,6];
		var arrayStr = JSON.stringify(array);
		$("#btn3").click(function() {
			$.ajax({
				"url": "send/array/three.html",
				"type": "post",
				"data": arrayStr,
				"contentType":"application/json;charset=UTF-8",
				"dataType": "text",
				"success": function(data) {
					alert(data);
				},
				"error": function(data) {
					alert(data);
				}
			});
		});
		$("#btn4").click(function() {
			layer.msg("layer的弹窗");
		});
	});
</script>
</head>
<body>
	<h1>成功了。</h1>
	<a href="test/ssm.html">测试ssm整合环境</a>
	<br/>
	<br/>
	<button id="btn1">send一个数组</button>
	<br/>
	<br/>
	<button id="btn3">send一个数组</button>
	<br/>
	<br/>
	<button id="btn4">layer的弹窗</button>
</body>
</html>