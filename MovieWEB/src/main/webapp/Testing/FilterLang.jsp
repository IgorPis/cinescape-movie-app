<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Filter lang</title>
</head>
<body>
	<form action="/Movie/obican/getML" method="get">
		Izaberi: <select name="idLang">
			<c:forEach items="${langs }" var="l">
				<option value="${l.idLanguage }">
					${l.name }
				</option>
			</c:forEach>
		</select>
		<br>
		<input type="submit" value="Prikazi">
	</form>
	<br>
		<c:if test="${!empty movies }">
			<table border="1">
				<tr>
					<th>Naziv</th>
					<th>Trajanje</th>
				</tr>
				<c:forEach items="${movies }" var="m">
					<tr>
						<td>${m.title }</td>
						<td>${m.duration }</td>
					</tr>
				</c:forEach>
			</table>
	</c:if>
</body>
</html>