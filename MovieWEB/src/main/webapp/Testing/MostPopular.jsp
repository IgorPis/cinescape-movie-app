<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Most Popular</title>
</head>
<body>
	<c:if test="${!empty top10Movies }">
		<table border="1">
			<tr>
				<th>Naziv</th>
				<th>Trajanje</th>
				<th>Zanr</th>
			</tr>
			<c:forEach items="${top10Movies }" var="m">
				<tr>
					<td>${m.title }</td>
					<td>${m.duration }</td>
					<td>${m.genre.name }</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>