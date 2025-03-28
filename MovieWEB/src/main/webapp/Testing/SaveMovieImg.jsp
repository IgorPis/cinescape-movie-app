<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="/Movie/obican/saveMovieImg" method="post" enctype="multipart/form-data">
		Title:<input type="text" name="title"> <br>
		ID genre:<input type="text" name="idGenre"><br>
		ID lang:<input type="text" name="idLang"><br>
 	   <input type="file" name="posterPath"><br>
    <button type="submit">Save movie</button>
</form>
<br>
${msg }
</body>
</html>