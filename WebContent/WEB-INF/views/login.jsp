<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/login.css" />" />

<title>Página Inicial</title>


<script type="text/javascript">
/* TODO */	
</script>

</head>
<body>

	<div class="main">
	
	</div>
	<form action="home" method="post">
	<div class="logoContainer">
		<div class="credencialDiv">
			<ul>
				<li class="credencialHeader">
					<label>Login</label>
		        </li>
		    	<li style="padding-top:20px">
					<label>Usuário</label>
		        </li>
		 		<li>
		    		<input id="login" name="login" type="text" class="credencialContainer inputUsr" autocomplete="off" />
		        </li>
		        <li>
		        	<p></p>
		        </li>
		        <li>
					<label>Senha</label>
		        </li>
		 		<li>
		    		<input id="password" name="password" type="password" class="credencialContainer inputPwd" autocomplete="off" />
		        </li>
		        <li>
		        	<p></p>
		        </li>
		        <li>
		        	<label class="errorMsg"><c:if test="${param.login=='erro'}">Usuário ou senha inválida.</c:if></label>

		        	<input type="submit" class="submitButton" value="OK">
		        </li>
			</ul>
		</div>
	
		<div class="logoDiv">
			<img class="logo" src="<c:url value="/resources/images/logo.png"/>" />  
		</div>
		
		<div class="logoText">Treinamentos
		</div>
	</div>
	</form>

</body>
</html>