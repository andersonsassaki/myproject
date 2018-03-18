<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">



<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/jquery-ui-1.8.16.smoothness.css" />" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/jqGrid/css/ui.jqgrid.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/menu.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/pesquisa.css" />" />

<script type="text/javascript" src="<c:url value="/resources/jscript/jquery-1.4.4.min.js" />"></script>

<script type="text/javascript">
    var jq = jQuery.noConflict();
</script>

<script type="text/javascript" src="<c:url value="/resources/jscript/jquery-ui-1.8.16.custom.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jqGrid/js/grid.locale-pt-br.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/jqGrid/js/jquery.jqGrid.min.js" />"></script>

<script language="JavaScript" src="<c:url value="/resources/jscript/jquery-1.9.1.js" />"></script>
<script language="JavaScript" src="<c:url value="/resources/jscript/jquery-ui-1.10.2.js" />"></script>
<script language="javascript" src="<c:url value="/resources/jscript/menu.js" />"></script>
<script language="javascript" src="<c:url value="/resources/jscript/pesquisa.js" />"></script>
<!-- 
<script type="text/javascript">
    var jq = jQuery.noConflict();
</script>

<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/jquery-ui-1.8.16.smoothness.css" />" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/jqGrid/css/ui.jqgrid.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/menu.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/pesquisa.css" />" />

<script language="JavaScript" src="<c:url value="/resources/jscript/jquery-1.9.1.js" />"></script>
<script language="JavaScript" src="<c:url value="/resources/jscript/jquery-ui-1.10.2.js" />"></script>
<script language="javascript" src="<c:url value="/resources/jscript/menu.js" />"></script>
<script language="javascript" src="<c:url value="/resources/jscript/pesquisa.js" />"></script>
-->

<title>Treinamentos Constran</title>
</head>
<body onload="adjustSubMenu();">
	<!-- Seção para Logo e Menu -->
	<div class="logoContainer">
		<div class="logoDiv">
			<img class="logo" src="<c:url value="/resources/images/logo.png"/>" />  
		</div>
		
		<div class="textLogoDiv">
			<label class="textLogoLabel">Treinamentos</label>
		</div>
		
		<nav style="height:5px">
		<ul class="menuBar">
		<c:forEach var="menuItem" items="${menus}" >
			 
			<c:if test="${menuItem.idMenuPai == '0'}">
				<!-- <li><a id="menu_${menuItem.id}" <c:if test="${menuAtivo eq menuItem.tituloMenu}">class="active"</c:if> href="#" onclick="showSubMenu(this)">${menuItem.tituloMenu}</a> -->
				<li><a id="menu_${menuItem.id}" href="${menuItem.pagina}" onclick="showSubMenu(this)">${menuItem.tituloMenu}</a>
			</c:if>
			
			<ul id="submenu_${menuItem.id}" class="subMenu">
			<c:forEach var="subMenuItem" items="${menus}" begin="0">
				<c:if test="${subMenuItem.idMenuPai != '0' && subMenuItem.idMenuPai eq menuItem.id}">
					<li><a href="${subMenuItem.pagina}" disabled>${subMenuItem.tituloMenu}</a></li>
				</c:if>
			</c:forEach>
			</ul>
			
			<c:if test="${menuItem.idMenuPai == '0'}">
				</li>
			</c:if>
		</c:forEach> 
		</ul>
		</nav>

	</div>
	
	<!-- Seção para pesquisa -->
	<div id="jqgrid" class="divGrid">
	    <div class="ui-jqgrid ui-widget ui-widget-content ui-corner-all" id="gbox_grid" dir="ltr" style="width: 665px;">

	    	
	    	<div class="ui-jqgrid-view" id="gview_grid" style="width: 665px;">
	    		<div class="ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">
	    			<span class="ui-jqgrid-title">Pesquisa Colaboradores - Treinamentos</span>
	    		</div>
	    	</div>
	    	<div class="ui-state-default ui-jqgrid-hdiv" style="width: 665px;">
	    	
		    	<div class="ui-jqgrid-hbox">

		    		<input id="inputSearch" name="inputSearch" type="text" class="inputSearch" onkeypress="submitEnter(this, event);"/>
		    		<img class="imgSearch" src="<c:url value="/resources/images/search-grey.png"/>" onclick="searchTreinamento()"/>
		    	</div>
		    </div>
	    </div>
	</div>
    
	
	<!-- Seção para resultado da pesquisa -->
	<div class="containerResultDiv">	
		<div id="resultPesquisa">

		</div>
	</div>
	
	<div id="dialog" title="Feature not supported" style="display:none">
	    <p>That feature is not supported.</p>
	</div>

</body>
</html>