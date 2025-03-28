<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
/* body { */
/* 	 background-image: url("${movie.posterPath }"); */
/* 	 } */
</style>
<body>
	<form action="/CineScape/obican/findMovie" method="get">
		Title: <input type="text" name="title"> <br>
		<input type="submit" value="Trazi">
	</form>
	<br>
	<c:if test="${!empty movie }">
		<table>
			<tr>
				<th>Title</th>
				<th>Poster</th>
			</tr>
			<tr>
				<td>${movie.title }</td>
				<td><img src="${movie.posterPath}" alt="Movie Poster" width="100" height="100"></td>
			</tr>
		</table>
		<br>
<!-- 		<img src="/Movie/imgMovies/thought.jpg" alt="Movie Poster"> -->

	</c:if>
</body>
</html>