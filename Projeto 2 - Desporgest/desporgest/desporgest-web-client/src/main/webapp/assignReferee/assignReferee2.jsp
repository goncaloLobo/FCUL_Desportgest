<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="model"
	class="presentation.web.model.NewAssignRefereeModel" scope="request" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resources/app.css">
<title>DesporGest: Associar arbitro a encontro</title>
</head>
<body>
	<h2>Associar arbitro a encontro</h2>
	<form action="associarArbitro" method="post">
		<div align="center">
			<table border="1" cellpadding="5">
				<caption>
					<h2>Lista de Jogos:</h2>
				</caption>
				<tr>
					<th>Numero de Encontro</th>
					<th>Data</th>
					<th>Participante 1</th>
					<th>Participante 2</th>
				</tr>
				<c:forEach var="jogo" items="${model.sport}">
					<tr>
						<td><c:out value="${jogo.number}" /></td>
						<td><fmt:formatDate var="data" pattern="dd MMM yyyy"
								value="${jogo.matchDate.time}" /> <c:out value="${data}" /></td>
						<td><c:out value="${jogo.part1.name}" /></td>
						<td><c:out value="${jogo.part2.name}" /></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<p> </p>
		<div class="mandatory_field">
			<label for="numberJogo">Numero do Encontro:</label> <input type="text"
				name="numberJogo" size="50" value="${model.number}" />
		</div>
		<p> </p>
		<div class="mandatory_field">
			<label for="numberArbitro">Numero do arbitro:</label> <input
				type="text" name="numberArbitro" size="50"
				value="${model.numeroArbitro}" />
		</div>
		<div class="button" align="right">
			<input type="submit" value="Associar Arbitro">
		</div>
	</form>
	<c:if test="${model.hasMessages}">
		<p>Mensagens</p>
		<ul>
			<c:forEach var="mensagem" items="${model.messages}">
				<li>${mensagem}
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>