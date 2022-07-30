<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "x" uri = "http://java.sun.com/jsp/jstl/xml" %>
<style>
   <%@include file='bebebe.css' %>
</style>
<html> 
<head>
<title>login</title> 
</head> 
<body>
<form:form method="POST" action="login">
<H1>-DD-</H1>
<table>
  <tbody>
    <tr>
      <td><p class="fieldName">имя</p></td>
      <td><p class="fieldName">пароль</p></td>
    </tr>
    <tr>
      <td><input class="text" name="login" type="text" /></td>
      <td><input class="text" name="password" type="password" /></td>
    </tr>
    <tr>
		<td><input class="submit" type="submit" name="logIn" value="войти">
		<input class="submit" type="submit" name="signUp" value="зарегистрироваться"></td>
		<td></td>
    </tr>
  </tbody>
</table>
</form:form>
</body> 
</html>