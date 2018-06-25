<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style>
     	<%@ include file="chart.css"%>
	</style>
<title>Programme TV - Liste des jours</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<p><%= request.getAttribute("channelsList") %></p>
</body>
</html>