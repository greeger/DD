<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "x" uri = "http://java.sun.com/jsp/jstl/xml" %>
<style>
   <%@include file='bebebe.css' %>
</style>
<html> 
<head>
<title>chat</title> 
</head> 
<body>
<H1>-DD-</H1>
<H2 class="data">${chat.name}</H2>
<table>
	<tr>
	  <td>
		<form:form method="POST" action="updateChat">
			<input type="text" hidden="true" name="chatId" value=${chat.id}>
			<input type="text" hidden="true" name="anonId" value=${anon.id}>
			<input class="submit" type="submit" name="update" value="обновить">
		</form:form>
	  </td>
	  <td>
		<form:form method="POST" action="chats">
			<input type="text" hidden="true" name="anonId" value=${anon.id}>
			<input class="submit" type="submit" name="chats" value="список чатов">
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
	    <p class="listName">создатель чата:</p>
		<form:form method="POST" action="change">
			<input type="text" hidden="true" name="chatId" value=${chat.id}>
			<input type="text" hidden="true" name="anonId" value=${anon.id}>
			<input type="text" hidden="true" name="memberId" value=${creator.id}>
			<table class="anonTable">
				<tr>
				  <td class="data">${creator.name}</td>
				  <td><input class="submit" type="submit" name="deleteAnon" value="удалить"></td>
				</tr>
			</table>
		</form:form>
	    <p class="listName">модераторы:</p>
		<c:forEach items="${moders}" var="moder">
		<form:form method="POST" action="change">
			<input type="text" hidden="true" name="chatId" value=${chat.id}>
			<input type="text" hidden="true" name="anonId" value=${anon.id}>
			<input type="text" hidden="true" name="memberId" value=${moder.id}>
			<table class="anonTable">
				<tr>
				  <td class="data">${moder.name}</td>
				  <td><input class="submit" type="submit" name="setUnModerates" value="сделать участником"></td>
				  <td><input class="submit" type="submit" name="deleteAnon" value="удалить"></td>
				  <td><input class="submit" type="submit" name="ban" value="забанить"></td>
				</tr>
			</table>
		</form:form>
		</c:forEach>
	    <p class="listName">участники:</p>
		<c:forEach items="${members}" var="member">
		<form:form method="POST" action="change">
			<input type="text" hidden="true" name="chatId" value=${chat.id}>
			<input type="text" hidden="true" name="anonId" value=${anon.id}>
			<input type="text" hidden="true" name="memberId" value=${member.id}>
			<table class="anonTable">
				<tr class="data">
				  <td class="data">${member.name}</td>
				  <td><input class="submit" type="submit" name="setModerates" value="сделать модератором"></td>
				  <td><input class="submit" type="submit" name="deleteAnon" value="удалить"></td>
				  <td><input class="submit" type="submit" name="ban" value="забанить"></td>
				</tr>
			</table>
		</form:form>
		</c:forEach>
	    <p class="listName">забанены:</p>
		<c:forEach items="${banned}" var="banned">
		<form:form method="POST" action="change">
			<input type="text" hidden="true" name="chatId" value=${chat.id}>
			<input type="text" hidden="true" name="anonId" value=${anon.id}>
			<input type="text" hidden="true" name="memberId" value=${banned.id}>
			<table class="anonTable">
				<tr>
				  <td class="data">${banned.name}</td>
				  <td><input class="submit" type="submit" name="deleteAnon" value="удалить"></td>
				  <td><input class="submit" type="submit" name="unBan" value="разбанить"></td>
				</tr>
			</table>
		</form:form>
		</c:forEach>
	  </td>
	  <td class="border">
		<form:form method="POST" action="write">
		<input type="text" hidden="true" name="chatId" value=${chat.id}>
		<input type="text" hidden="true" name="anonId" value=${anon.id}>
	    <table>
			<tr>
				<td><input class="text" name="text" type="text" /></td>
				<td><input class="submit" type="submit" name="write" value="отправить"></td>
			</tr>
		</table>
		</form:form>
		<c:forEach items="${msgs}" var="msg">
		<form:form method="POST" action="deleteMsg">
			<input type="text" hidden="true" name="chatId" value=${chat.id}>
			<input type="text" hidden="true" name="anonId" value=${anon.id}>
			<input type="text" hidden="true" name="msgId" value=${msg.id}>
			<table class="msgTable">
				<tr>
				  <td class="data">${msg.author}: ${msg.text}</td>
				  <td><input class="submit" type="submit" name="deleteMsg" value="удалить"></td>
				</tr>
				<tr>
				  <td class="time">${msg.time}</td>
				</tr>
			</table>
		</form:form>
		</c:forEach>
	  </td>
    </tr>
  </tbody>
</table>
</body> 
</html>