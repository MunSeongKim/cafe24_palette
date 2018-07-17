<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h1>
		<c:choose>
		    <c:when test="${empty list }">
		        등록된 글이 없습니다.    
		    </c:when>
		    <c:when test="${abc}">
		        안녕하세요    
		    </c:when>
		    <c:otherwise>
		        ㅎㅇ
		    </c:otherwise>
		</c:choose>
	</h1>
</body>
</html>