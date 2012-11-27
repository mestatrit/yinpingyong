<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.lang.Exception" %>
<%@ page import="java.io.PrintWriter" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FMIS</title>
</head>
<body>
	系统出现异常了...
	<%Exception ex=(Exception)request.getAttribute("exception");%>
	<H2>Exception:</H2>
	<%ex.printStackTrace(new PrintWriter(out));%>
</body>
</html>