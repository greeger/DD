<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "x" uri = "http://java.sun.com/jsp/jstl/xml" %>
<style>
   <%@include file='bebebe.css' %>
</style>
<html> 
<head>
<title>allChats</title> 
</head> 
<body>
<H1>-DD-</H1>
<H2 class="data">${anon.name}</H2>
<table>
	<tr>
	  <td>
		<form:form method="POST" action="updateChats">
			<input type="text" hidden="true" name="anonId" value=${anon.id}>
			<input class="submit" type="submit" name="update" value="обновить">
		</form:form>
	  </td>
	  <td>
		<form:form method="POST" action="index">
			<input class="submit" type="submit" name="index" value="выйти">
		</form:form>
	  </td>
	</tr>
</table>
<table>
  <tbody>
    <tr>
      <td>
	    <p class="listName">все чаты</p>
		<c:forEach items="${allChats}" var="chat">
		<form:form method="POST" action="chat">
			<input type="text" hidden="true" name="chatId" value=${chat.id}> <input type="text" hidden="true" name="anonId" value=${anon.id}>
			<table class="chatTable">
				<tr>
				  <td class="data">${chat.name}</td>
				  <td><input class="submit" type="submit" name="setMember" value="стать участником"></td>
				  <td><input class="submit" type="submit" name="delete" value="удалить"></td>
				</tr>
			</table>
		</form:form>
		</c:forEach>
	  </td>
      <td>
	    <p class="listName">мои чаты</p>
		<c:forEach items="${chats}" var="chat">
		<form:form method="POST" action="chat">
			<input type="text" hidden="true" name="chatId" value=${chat.id}> <input type="text" hidden="true" name="anonId" value=${anon.id}>
			<table class="chatTable">
				<tr>
				  <td class="data">${chat.name}</td>
				  <td><input class="submit" type="submit" name="enter" value="войти"></td>
				  <td><input class="submit" type="submit" name="leave" value="покинуть"></td>
				  <td><input class="submit" type="submit" name="delete" value="удалить"></td>
				</tr>
			</table>
		</form:form>
		</c:forEach>
	  </td>
    </tr>
    <tr>
	  <td>
		<form:form method="POST" action="create">
			<input type="text" hidden="true" name="anonId" value=${anon.id}>
			<input class="text" name="name" type="text" />
			<input class="submit" type="submit" name="create" value="создать">
		</form:form>
	  </td>
    </tr>
  </tbody>
</table>
</body> 
</html>