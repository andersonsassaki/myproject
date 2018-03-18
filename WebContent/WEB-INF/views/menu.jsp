<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/jquery-ui-1.8.16.smoothness.css" />" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/jqGrid/css/ui.jqgrid.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/menu.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/usuario.css" />" />

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

<title>Treinamentos Constran</title>

</head>

<body onload="adjustSubMenu();">

	<!-- Seção para Logo e Menu -->
 
	<div class="logoContainer">
		<div class="logoDiv">
			<img class="logo" src="<c:url value="resources/images/logo.png"/>" />  
		</div>
		
		<div class="textLogoDiv">
			<label class="textLogoLabel">Treinamentos</label>
		</div>
		
		<nav style="height:5px">
		<ul class="menuBar">
		<c:forEach var="menuItem" items="${menus}" >
			 
			<c:if test="${menuItem.idMenuPai == '0'}">
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


<script type="text/javascript">

/***** GRID INÍCIO *****/
jq(function() {
	
	/******************************/
	/***** GRID MENU - INÍCIO *****/
	/******************************/
	jq("#grid").jqGrid({
		url:'listaGridMenu',
		datatype: 'json',
		mtype: 'GET',
		colNames:['Id', 'Título', 'Descrição', 'Página', 'Menu Pai', 'Menu Pai', 'Ordem', ''],
		colModel:[
			{name:'id', index:'id', width:60, editable:true, editoptions:{readonly:true,size:10}, hidden:true},
			{name:'tituloMenu', index:'tituloMenu', width:150, sorttype:"string", editable:true},
			{name:'descricao', index:'descricao', width:150, sorttype:"string", editable:true, editoptions:{style:"width:300px"}},
			{name:'pagina', index:'pagina',  sorttype:"string", editable:true},
			{name:'tituloMenuPai', index:'tituloMenuPai', sorttype:"string", editable:false, editoptions:{readonly:true}},
			{name:'idMenuPai',index:'idMenuPai', width:150, sorttype:"string", editable:true, edittype:'select', hidden:true,
				editoptions:{
					dataUrl: "listaDropDownMenu", 
				    style:"width:auto; height:28px; border: solid 1px #888" 
				}
			},
			{name:'ordem',tituloMenu:'ordem', sorttype:"int", editable:true},
			{name:'btnUsuarios', width:57, formatter: function(cellvalue, options, rowobject){
						      return '<button type="button" class="btnRelacionamentos" onclick=openGrid(this);></button>';
						  }
			}
		],
	    postData: {},
	    rowNum:8,
	    rowList:[7,14,21],
	    height: "auto",
	    autowidth: false,
	    rownumbers: true,
	    pager: '#pager',
	    sortname: 'id',
	    viewrecords: true,
	    sortorder: "asc",
	    caption: "Menu",
	    emptyrecords: "Empty records",
	    loadonce: false,
	    hidegrid: false,
	    loadComplete: function() {},
	    jsonReader : {
	        root: "rows",
	        page: "page",
	        total: "total",
	        records: "records",
	        repeatitems: false,
	        cell: "cell",
	        id: "id"
	    }
	});
  
	
	jq("#grid").jqGrid('navGrid', '#pager',
		{edit:false,add:false,del:false,search:true},
    	{ },
        { },
        { }, 
    	{sopt:['eq', 'ne', 'lt', 'gt', 'cn', 'bw', 'ew'],
         closeOnEscape: true, 
         multipleSearch: true, 
         closeAfterSearch: true
        }
	);

	
	jq("#grid").jqGrid('inlineNav', '#pager', {
        edit: false,
        editicon: "ui-icon-pencil",
        add: false,
        addicon: "ui-icon-plus",
        save: false,
        saveicon: "ui-icon-disk",
        cancel: false,
        cancelicon: "ui-icon-cancel",
        addParams: { },
        editParams: { }
    });

  
	jq("#grid").navButtonAdd('#pager', {
		caption:"", 
	   	buttonicon:"ui-icon-plus", 
		onClickButton: addRow,
		position: "last", 
		title:"", 
		cursor: "pointer"
	});
	
  
	jq("#grid").navButtonAdd('#pager', {  
		caption:"", 
		buttonicon:"ui-icon-pencil", 
		onClickButton: editRow,
		position: "last", 
		title:"", 
		cursor: "pointer"
    });
	
  
	jq("#grid").navButtonAdd('#pager', {
		caption:"", 
		buttonicon:"ui-icon-trash", 
		onClickButton: deleteRow,
		position: "last", 
		title:"", 
		cursor: "pointer"
	});
	
	/***************************/
	/***** GRID MENU - FIM *****/
	/***************************/
	
	
	/********************************************/
	/***** GRID MENU GRUPO USUÁRIO - INÍCIO *****/
	/********************************************/

	jq("#gridGrupoUsuario").jqGrid({
		url:'listaMenusGruposUsuarios',
		datatype: 'json',
		mtype: 'GET',
		colNames:['id', 'idMenu', 'Titulo Menu', 'Grupo Usuário', 'Grupo Usuário'],
		colModel:[
			{name:'id',index:'id', width:60, editable:true, key: true, editoptions:{readonly:true,size:10}, hidden:true},
			{name:'idMenu',index:'idMenu', width:60, sorttype:"string", editable:true, hidden:true},
			{name:'tituloMenu', index:'tituloMenu', width:150, sorttype:"string", editable:true, editoptions:{readonly:true}, hidden:true},
			{name:'nomeGrupo',index:'nomeGrupo', width:450, sorttype:"string", editable:true, hidden:false},
			{name:'grupoUsuario',index:'grupoUsuario', width:450, sorttype:"string", editable:true, edittype:'select', hidden:true,
				editoptions:{
					dataUrl: "listaMenuGrupoUsuario", 					
				    style:"width:auto; height:28px; border: solid 1px #888" 
				}
			}
		],
	    postData: {id: jq("#grid").jqGrid ('getGridParam', 'selrow')},
	    rowNum:10,
	    rowList:[7,14,21],
	    height: "auto",
	    autowidth: false,
	    rownumbers: true,
	    pager: '#pagerGrupoUsuario',
	    sortname: 'id',
	    viewrecords: true,
	    sortorder: "asc",
	    caption:"Menus / Grupos Usuários",
	    emptyrecords: "Empty records",
	    loadonce: false,
	    hidegrid: false,
	    loadComplete: function() {},
	    jsonReader : {
	        root: "rows",
	        page: "page",
	        total: "total",
	        records: "records",
	        repeatitems: false,
	        cell: "cell",
	        id: "id"
	    }
	});
  
	
	jq("#gridGrupoUsuario").jqGrid('navGrid', '#pagerGrupoUsuario',
		{edit:false,add:false,del:false,search:true},
    	{ },
        { },
        { }, 
    	{sopt:['eq', 'ne', 'lt', 'gt', 'cn', 'bw', 'ew'],
         closeOnEscape: true, 
         multipleSearch: true, 
         closeAfterSearch: true
        }
	);

	
	jq("#gridGrupoUsuario").jqGrid('inlineNav', '#pagerGrupoUsuario', {
        edit: false,
        editicon: "ui-icon-pencil",
        add: false,
        addicon: "ui-icon-plus",
        save: false,
        saveicon: "ui-icon-disk",
        cancel: false,
        cancelicon: "ui-icon-cancel",
        addParams: { },
        editParams: { }
    });

  
	jq("#gridGrupoUsuario").navButtonAdd('#pagerGrupoUsuario', {
		caption:"", 
	   	buttonicon:"ui-icon-plus", 
		onClickButton: addRowGrupoUsuario,
		position: "last", 
		title:"", 
		cursor: "pointer"
	});
	
  
	jq("#gridGrupoUsuario").navButtonAdd('#pagerGrupoUsuario', {  
		caption:"", 
		buttonicon:"ui-icon-pencil", 
		onClickButton: editRowGrupoUsuario,
		position: "last", 
		title:"", 
		cursor: "pointer"
    });
	
  
	jq("#gridGrupoUsuario").navButtonAdd('#pagerGrupoUsuario', {
		caption:"", 
		buttonicon:"ui-icon-trash", 
		onClickButton: deleteRowGrupoUsuario,
		position: "last", 
		title:"", 
		cursor: "pointer"
	});
	
	
	/*****************************************/
	/***** GRID MENU GRUPO USUÁRIO - FIM *****/
	/*****************************************/

});
</script>
  

<script type="text/javascript">

function openGrid(oImg) {
	var selectedRowId, menuId;
	
    selectedRowId = $(oImg).closest('tr').attr("id");
    
    menuId = jq("#grid").jqGrid('getCell', selectedRowId, 'id');
    
    
	jq("#gridGrupoUsuario").setGridParam({
		postData: {id:menuId}
    }).trigger("reloadGrid");
	
	$("#jqgridGrupoUsuario").css({visibility:"visible"});
	
}

function addRow() {

	// Obtém a linha selecionada
    jq("#grid").jqGrid('editGridRow','new', {
    	url: "addMenu", 
     	editData: {},
       	recreateForm: true,
       	resize: false,
       	beforeShowForm: function(form) {},
       	afterShowForm: function(form) {
       		$("#TblGrid_grid").find("input").each(function(index, element) {
    			$(element).attr({"autocomplete": "off"});
    		});
       		
       		$("#editmodgrid").css({width:"auto"});
       		
       		$("#tr_idMenuPai").css({display: "table-row"});
       	},
    	closeAfterAdd: true,
    	reloadAfterSubmit: true,
    	afterSubmit : function(response, postdata) { 
        	var result = eval('(' + response.responseText + ')');
     		var errors = "";
     
            if (result.success == false) {
      			for (var i = 0; i < result.message.length; i++) {
       				errors +=  result.message[i] + "<br/>";
      			}
           } else {
           		jq("#dialog").text('O item de menu foi incluído com sucesso.');
      
           		jq("#dialog").dialog({ 
           			title: 'Alerta',
			        modal: true,
			        resizable: false,
         			buttons: {
         				"Ok": function() {
         					jq(this).dialog("close");
         				} 
         			}
        		});
            }
            
        	var new_id = null;
        
     		return [result.success, errors, new_id];
    	}
	});
}

function editRow() {
	// Obtém a linha selecionada
	var row = jq("#grid").jqGrid('getGridParam','selrow');
 
	if( row != null ) 
		jq("#grid").jqGrid('editGridRow', row, { 
			url: "editMenu", 
			editData: {},
	        recreateForm: true,
	        resize: false,
	        beforeShowForm: function(form) {},
	        afterShowForm: function(form) {
	       		$("#TblGrid_grid").find("input").each(function(index, element) {
	    			$(element).attr({"autocomplete": "off"});
	    		});
	       		
	       		$("#editmodgrid").css({width:"auto"});
	       		
	       		$("#tr_idMenuPai").css({display: "table-row"});
	       	},
	   		closeAfterEdit: true,
	   		reloadAfterSubmit: true,
	   		afterSubmit : function(response, postdata) { 
	        	var result = eval('(' + response.responseText + ')');
	    		var errors = "";
	    
	            if (result.success == false) {
	     			for (var i = 0; i < result.message.length; i++) {
	      				errors +=  result.message[i] + "<br/>";
	     			}
	            } else {
	            	jq("#dialog").text('O item de menu foi alterado com sucesso.');
	            	
	     			jq("#dialog").dialog({
	     				title: 'Alerta',
	     				modal: true,
	     				resizable: false,
	        			buttons: {
	        				"Ok": function() {
	        					jq(this).dialog("close");
	        				} 
	        			}
	       			});
				}
	          
	    		return [result.success, errors, null];
	   		}
	});	
	else jq( "#dialogSelectRow" ).dialog();
}

function deleteRow() {
 	// Obtém a linha selecionada
    var row = jq("#grid").jqGrid('getGridParam','selrow');

 	
    // Um pop-up será exibido para confirmar a seleção da operação
 	if( row != null ) 
  		jq("#grid").jqGrid( 'delGridRow', row, {
  			url: 'deleteMenu', 
  			recreateForm: true,
            beforeShowForm: function(form) {
            	// Muda o título
                jq(".delmsg").replaceWith('<span style="white-space: pre;">' +
                   'Confirma a exclusão do item de menu?' + '</span>');
                 
       			 // Esconde setas
                 jq('#pData').hide();  
                 jq('#nData').hide();  
            },
            reloadAfterSubmit:false,
            closeAfterDelete: true,
            afterSubmit : function(response, postdata) { 
            	var result = eval('(' + response.responseText + ')');
       			var errors = "";
       
                if (result.success == false) {
        			for (var i = 0; i < result.message.length; i++) {
         				errors +=  result.message[i] + "<br/>";
        			}
                } else {
                	jq("#dialog").text('O item de menu foi excluído com sucesso.');
        
                	jq("#dialog").dialog({
                		title: 'Alerta',
                		modal: true,
                		resizable: false,
			            buttons: {
			            	"Ok": function()  {
			            		jq(this).dialog("close");
			            	} 
			            }
			        });
                }
                 
                // Usado somente para adicionar novos registros
                var new_id = null;
                   
       			return [result.success, errors, new_id];
      		}
	});
	else jq( "#dialogSelectRow" ).dialog();
 	
}
 


function addRowGrupoUsuario() {
	
	var selectedRowId, idMenu, tituloMenu;
		
	selectedRowId = jq("#grid").jqGrid ('getGridParam', 'selrow');
	    		
	idMenu = jq("#grid").jqGrid('getCell', selectedRowId, 'id');
	tituloMenu = jq("#grid").jqGrid('getCell', selectedRowId, 'tituloMenu');
	
	jq("#gridGrupoUsuario").jqGrid('setColProp','grupoUsuario',{editoptions:{dataUrl:'listaMenuGrupoUsuario?idMenu=' + idMenu + "&tituloMenu=" + tituloMenu}});

	// Obtém a linha selecionada
    jq("#gridGrupoUsuario").jqGrid('editGridRow','new', {
    	url: "addMenuGrupoUsuario", 
     	editData: {},
       	recreateForm: true,
       	resize: false,
       	beforeShowForm: function(form) {    		  		
  		    $("#idMenu").val(idMenu);
  		    $("#tituloMenu").val(tituloMenu);
       	},
       	afterShowForm: function(form) {
       		$("#TblGrid_grid").find("input").each(function(index, element) {
    			$(element).attr({"autocomplete": "off"});
    		});
       		
       		$("#editmodgridGrupoUsuario").css({width:"auto"});
       		
       		$("#tr_grupoUsuario").css({"display":"block"});
       		$("#tr_nomeGrupo").css({"display":"none"});
       		     		
       	},
    	closeAfterAdd: true,
    	reloadAfterSubmit: true,
    	afterSubmit : function(response, postdata) { 
        	var result = eval('(' + response.responseText + ')');
     		var errors = "";
     
            if (result.success == false) {
      			for (var i = 0; i < result.message.length; i++) {
       				errors +=  result.message[i] + "<br/>";
      			}
           } else {
        	    $("#divGridGrupoUsuario").css({visibility:"hidden"});
        	   
           		jq("#dialog").text('O grupo de usuário foi associado ao menu com sucesso.');
      
           		jq("#dialog").dialog({ 
           			title: 'Alerta',
			        modal: true,
			        resizable: false,
         			buttons: {
         				"Ok": function() {
         					jq(this).dialog("close");
         				} 
         			}
        		});
            }
            
        	var new_id = null;
        
     		return [result.success, errors, new_id];
    	}
	});
}


function editRowGrupoUsuario() {
	
	//var selectedRowId, menuId;
	
	//selectedRowId = jq("#grid").jqGrid ('getGridParam', 'selrow');
	    		
	//menuId = jq("#grid").jqGrid('getCell', selectedRowId, 'id');
	
	//jq("#gridGrupoUsuario").jqGrid('setColProp','ccObra',{editoptions:{dataUrl:'listaObraUsuario?usuario=' + usuarioId}});
	
	// Obtém a linha selecionada
	var row = jq("#gridGrupoUsuario").jqGrid('getGridParam','selrow');
 
	if( row != null ) 
		jq("#gridGrupoUsuario").jqGrid('editGridRow', row, { 
			url: "editMenuGrupoUsuario", 
			editData: {},
	        recreateForm: true,
	        resize: false,
	        beforeShowForm: function(form) {},
	        afterShowForm: function(form) {
	       		$("#TblGrid_grid").find("input").each(function(index, element) {
	    			$(element).attr({"autocomplete": "off"});
	    		});
	       		delmodgridGrupoUsuario       		
	       		$("#editmodgridGrupoUsuario").css({width:"auto"});
	       		
	       		$("#tr_grupoUsuario").css({"display":"block"});
	       		$("#tr_nomeGrupo").css({"display":"none"});
	       	},
	   		closeAfterEdit: true,
	   		reloadAfterSubmit: true,
	   		afterSubmit : function(response, postdata) { 
	        	var result = eval('(' + response.responseText + ')');
	    		var errors = "";
	    
	            if (result.success == false) {
	     			for (var i = 0; i < result.message.length; i++) {
	      				errors +=  result.message[i] + "<br/>";
	     			}
	            } else {
	            	$("#divGridGrupoUsuario").css({visibility:"hidden"});
	            	
	            	jq("#dialog").text('O grupo de usuário foi associado ao menu com sucesso.');
	            	
	     			jq("#dialog").dialog({
	     				title: 'Alerta',
	     				modal: true,
	     				resizable: false,
	        			buttons: {
	        				"Ok": function() {
	        					jq(this).dialog("close");
	        				} 
	        			}
	       			});
				}
	          
	    		return [result.success, errors, null];
	   		}
	});	
	else jq( "#dialogSelectRow" ).dialog();
}


function deleteRowGrupoUsuario() {
 	// Obtém a linha selecionada
    var row = jq("#gridGrupoUsuario").jqGrid('getGridParam','selrow');

 	
    // Um pop-up será exibido para confirmar a seleção da operação
 	if( row != null ) 
  		jq("#gridGrupoUsuario").jqGrid( 'delGridRow', row, {
  			url: 'deleteMenuGrupoUsuario', 
  			recreateForm: true,
            beforeShowForm: function(form) {
            	// Muda o título
                jq(".delmsg").replaceWith('<span style="white-space: pre;">' +
                   'Confirma a exclusão da associação do grupo de usuário com o menu?' + '</span>');
                 
       			 // Esconde setas
                 jq('#pData').hide();  
                 jq('#nData').hide();  
            },
            afterShowForm: function(form) {      		       		
	       		$("#delmodgridGrupoUsuario").css({width:"auto"});
	       	},
            reloadAfterSubmit:false,
            closeAfterDelete: true,
            afterSubmit : function(response, postdata) { 
            	var result = eval('(' + response.responseText + ')');
       			var errors = "";
       
                if (result.success == false) {
        			for (var i = 0; i < result.message.length; i++) {
         				errors +=  result.message[i] + "<br/>";
        			}
                } else {
                	$("#divGridGrupoUsuario").css({visibility:"hidden"});
                	
                	jq("#dialog").text('A associação do grupo de usuário com o menu foi excluída com sucesso.');
        
                	jq("#dialog").dialog({
                		title: 'Alerta',
                		modal: true,
                		resizable: false,
			            buttons: {
			            	"Ok": function()  {
			            		jq(this).dialog("close");
			            	} 
			            }
			        });
                }
                 
                // Usado somente para adicionar novos registros
                var new_id = null;
                   
       			return [result.success, errors, new_id];
      		}
	});
	else jq( "#dialogSelectRow" ).dialog();
} 

</script>


<div id="jqgrid" class="divGrid">
    <table id="grid"></table>
    <div id="pager"></div>
</div>

<div id="jqgridGrupoUsuario" class="divGridGrupoUsuario">
    <table id="gridGrupoUsuario"></table>
    <div id="pagerGrupoUsuario"></div>
</div>

<div id="dialog" title="Feature not supported" style="display:none">
    <p>That feature is not supported.</p>
</div>

<div id="dialogSelectRow" title="Warning" style="display:none">
    <p>Selecione um registro.</p>
</div>

</body>

</html>