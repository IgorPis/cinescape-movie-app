<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Filter genre</title>
</head>
<body>
	<form action="/Movie/obican/getMG" method="get">
		Izaberi: <select name="idGenre">
			<c:forEach items="${genres }" var="g">
				<option value="${g.idGenre }">
					${g.name}
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
					<th>Zanr</th>
				</tr>
				<c:forEach items="${movies }" var="m">
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