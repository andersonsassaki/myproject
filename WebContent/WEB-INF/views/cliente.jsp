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
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/cliente.css" />" />

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
<script language="javascript" src="<c:url value="/resources/jscript/jsTreinamento.js" />"></script>

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
	jq("#grid").jqGrid({
		url:'listaClientes',
		datatype: 'json',
		mtype: 'GET',
		colNames:['Id', 'Nome Cliente', 'CNPJ'],
		colModel:[
			{name:'id',index:'id', width:60, editable:true, editoptions:{readonly:true,size:10}, hidden:true},
			{name:'nomeCliente',index:'nomeCliente', width:400, sorttype:"string", editable:true, editoptions:{style:"width:250px"}},
			{name:'cnpj',index:'cnpj', width:200, sorttype:"string", editable:true, hidden:true}
		],
	    postData: {},
	    rowNum:20,
	    rowList:[20,40,60],
	    height: "auto",
	    autowidth: false,
	    rownumbers: true,
	    pager: '#pager',
	    sortname: 'id',
	    viewrecords: true,
	    sortorder: "asc",
	    caption:"Clientes",
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

 });
</script>
  

<script type="text/javascript">

function addRow() {

	// Obtém a linha selecionada
    jq("#grid").jqGrid('editGridRow','new', {
    	url: "addCliente", 
     	editData: {},
       	recreateForm: true,
       	resize: false,
       	beforeShowForm: function(form) {},
       	afterShowForm: function(form) {
       		$("#TblGrid_grid").find("input").each(function(index, element) {
    			$(element).attr({"autocomplete": "off"});
    		});
       		
       		$("#editmodgrid").css({width:"380px"});
     		
       	},
    	closeAfterAdd: true,
    	reloadAfterSubmit: true,
    	beforeSubmit: function(postdata, formid){
	    	if ( rtrim(ltrim($("#nomeCliente").val())) == "" ) {
	    		genericMessage("Informe o nome do cliente.", "Erro");
	    		return false;
	    	}
	    	
	    },
    	afterSubmit : function(response, postdata) { 
        	var result = eval('(' + response.responseText + ')');
     		var errors = "";
     
            if (result.success == false) {
      			for (var i = 0; i < result.message.length; i++) {
       				errors +=  result.message[i] + "<br/>";
      			}
           } else {
           		jq("#dialog").text('O cliente foi incluído com sucesso.');
      
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
			url: "editCliente", 
			editData: {},
	        recreateForm: true,
	        resize: false,
	        beforeShowForm: function(form) {},
	        afterShowForm: function(form) {
	       		$("#TblGrid_grid").find("input").each(function(index, element) {
	    			$(element).attr({"autocomplete": "off"});
	    		});
	       		
	       		$("#editmodgrid").css({width:"380px"});
	       	},
	   		closeAfterEdit: true,
	   		reloadAfterSubmit: true,
	   		beforeSubmit: function(postdata, formid){
		    	if ( rtrim(ltrim($("#nomeCliente").val())) == "" ) {
		    		genericMessage("Informe o nome do cliente.", "Erro");
		    		return false;
		    	}
		    	
		    },
	   		afterSubmit : function(response, postdata) { 
	        	var result = eval('(' + response.responseText + ')');
	    		var errors = "";
	    
	            if (result.success == false) {
	     			for (var i = 0; i < result.message.length; i++) {
	      				errors +=  result.message[i] + "<br/>";
	     			}
	            } else {
	            	jq("#dialog").text('O cliente foi alterado com sucesso.');
	            	
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
  			url: 'deleteCliente', 
  			recreateForm: true,
            beforeShowForm: function(form) {
            	// Muda o título
                jq(".delmsg").replaceWith('<span style="white-space: pre;">' +
                   'Confirma a exclusão do cliente?' + '</span>');
                 
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
                	jq("#dialog").text('O cliente foi excluído com sucesso.');
        
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

<div id="dialog" title="Feature not supported" style="display:none">
    <p>That feature is not supported.</p>
</div>

<div id="dialogSelectRow" title="Warning" style="display:none">
    <p>Selecione um registro.</p>
</div>

</body>

</html>