<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> -->

<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/jquery-ui-1.8.16.smoothness.css" />" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/jqGrid/css/ui.jqgrid.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/menu.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/treinamento.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/treinamentoGrid.css" />" />
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
<script language="javascript" src="<c:url value="/resources/jscript/treinamento.js" />"></script>

<title>Treinamentos Constran</title>

</head>

<body onload="adjustSubMenu(); initialSlide(); loadDropDownForm();">

<!-- Seção para Logo e Menu -->
<script type="text/javascript">


	
	
	
/***** GRID INÍCIO - DOCUMENTOS *****/
jq(function() {
	jq("#gridDoc").jqGrid({
		url:'listaTreinamentosDocs',
		datatype: 'json',
		mtype: 'GET',
		colNames:['id', 'treinamento', 'Número', 'Rev.', 'Descrição'],
		colModel:[
			{name:'id',index:'id', width:60, editable:true, editoptions:{readonly:true,size:10}, hidden:true},
			{name:'treinamento',index:'treinamento', width:60, editable:true, editoptions:{readonly:true,size:10}, hidden:true},
			{name:'numeroDocumento',index:'numeroDocumento', width:120, sorttype:"string", editable:true},
			{name:'revisaoDocumento',index:'revisaoDocumento', width:70, sorttype:"string", editable:true},
			{name:'descDocumento',index:'descDocumento', width:600, sorttype:"string", editable:true}
		],
	    postData: {treinamento:0},
	    rowNum:7,
	    //rowList:[8,16,24],
	    height: "auto",
	    autowidth: false,
	    rownumbers: true,
	    pager: '#pagerDoc',
	    sortname: 'id',
	    viewrecords: true,
	    sortorder: "asc",
	    caption:"Treinamento / Documento de Referência",
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
  
	
	jq("#gridDoc").jqGrid('navGrid', '#pagerDoc',
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

	
	jq("#gridDoc").jqGrid('inlineNav', '#pagerDoc', {
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

  
	jq("#gridDoc").navButtonAdd('#pagerDoc', {
		caption:"", 
	   	buttonicon:"ui-icon-plus", 
		onClickButton: addRowDoc,
		position: "last", 
		title:"", 
		cursor: "pointer"
	});
	
  
	jq("#gridDoc").navButtonAdd('#pagerDoc', {  
		caption:"", 
		buttonicon:"ui-icon-pencil", 
		onClickButton: editRowDoc,
		position: "last", 
		title:"", 
		cursor: "pointer"
    });
	
  
	jq("#gridDoc").navButtonAdd('#pagerDoc', {
		caption:"", 
		buttonicon:"ui-icon-trash", 
		onClickButton: deleteRowDoc,
		position: "last", 
		title:"", 
		cursor: "pointer"
	});

});

</script>
  

<script type="text/javascript">


function addRowDoc() {
		
	// Obtém a linha selecionada
    jq("#gridDoc").jqGrid('editGridRow','new', {
    	url: "addTreinamentoDocumento", 
     	editData: {},
       	recreateForm: true,
       	beforeShowForm: function(form) {},
       	afterShowForm: function(form) {
       		$("#TblGrid_gridDoc").find("input").each(function(index, element) {
    			$(element).attr({"autocomplete": "off"});
    			$(element).css({width:"300px"});
    		});
       		
       		$("#treinamento").val($("#idTreinamento").val());
       		
       		$("#editmodgridDoc").css({width:"400px"});
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
           		jq("#dialog").text('O documento foi incluído com sucesso.');
      
           		jq("#dialog").dialog({ 
           			title: 'Alerta',
			        modal: true,
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


function editRowDoc() {
		
	// Obtém a linha selecionada
	var row = jq("#gridDoc").jqGrid('getGridParam','selrow');
 
	if( row != null ) 
		jq("#gridDoc").jqGrid('editGridRow', row, { 
			url: "editTreinamentoDocumento", 
			editData: {},
	        recreateForm: true,
	        beforeShowForm: function(form) {},
	        afterShowForm: function(form) {
	       		$("#TblGrid_gridDoc").find("input").each(function(index, element) {
	    			$(element).attr({"autocomplete": "off"});
	    			$(element).css({width:"300px"});
	    		});
	       		
	       		$("#editmodgridDoc").css({width:"400px"});
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
	            	jq("#dialog").text('O documento foi alterado com sucesso.');
	            	
	     			jq("#dialog").dialog({
	     				title: 'Alerta',
	     				modal: true,
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

function deleteRowDoc() {
 	// Obtém a linha selecionada
    var row = jq("#gridDoc").jqGrid('getGridParam','selrow');

    // Um pop-up será exibido para confirmar a seleção da operação
 	if( row != null ) 
  		jq("#gridDoc").jqGrid( 'delGridRow', row, {
  			url: 'deleteTreinamentoDocumento', 
  			recreateForm: true,
            beforeShowForm: function(form) {
            	// Muda o título
                jq(".delmsg").replaceWith('<span style="white-space: pre;">' +
                   'Confirma a exclusão do contrato?' + '</span>');
                 
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
                	jq("#dialog").text('O documento foi excluído com sucesso.');
        
                	jq("#dialog").dialog({
                		title: 'Alerta',
                		modal: true,
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
/***** GRID FIM - DOCUMENTOS *****/ 
</script>



<script type="text/javascript">
/***** GRID INÍCIO - PARTICIPANTES *****/ 
jq(function() {
	jq("#gridParticipante").jqGrid({
		url:'listaTreinamentosParticipantes',
		datatype: 'json',
		mtype: 'GET',
		colNames:['id', 'treinamento', 'CPF', 'Matrícula', 'Nome', 'Função', 'Presença', 'Presença'],
		colModel:[
			{name:'id',index:'id', width:60, editable:true, editoptions:{readonly:true,size:10}, hidden:true},
			{name:'treinamento',index:'treinamento', width:60, editable:true, editoptions:{readonly:true,size:10}, hidden:true},
			{name:'cpf',index:'cpf', width:120, sorttype:"string", editable:true},
			{name:'matricula',index:'matricula', width:80, sorttype:"string", editable:true},
			{name:'nome',index:'nome', width:300, sorttype:"string", editable:true},
			{name:'descFuncao',index:'descFuncao', width:280, sorttype:"string", editable:true},
			{name:'descPresencaTreinamento',index:'descPresencaTreinamento', width:200, sorttype:"string", editable:false, hidden:false},
			{name:'presencaTreinamento',index:'presencaTreinamento', width:100, sorttype:"string", editable:true, hidden:true, edittype:"select",
				editoptions:{
					value:{'S':'Sim', 'N':'Não'},  
						
				    style:"width:auto; height:28px; border: solid 1px #888" 
				}
			}
		],
	    postData: {treinamento:0},
	    rowNum:7,
	    //rowList:[7,14,21],
	    height: "auto",
	    autowidth: false,
	    rownumbers: true,
	    pager: '#pagerParticipante',
	    sortname: 'id',
	    viewrecords: true,
	    sortorder: "asc",
	    caption:"Participantes",
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
  
	
	jq("#gridParticipante").jqGrid('navGrid', '#pagerParticipante',
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

	
	jq("#gridParticipante").jqGrid('inlineNav', '#pagerParticipante', {
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

  
	jq("#gridParticipante").navButtonAdd('#pagerParticipante', {
		caption:"", 
	   	buttonicon:"ui-icon-plus", 
		onClickButton: addRowParticipante,
		position: "last", 
		title:"", 
		cursor: "pointer"
	});
	
  
	jq("#gridParticipante").navButtonAdd('#pagerParticipante', {  
		caption:"", 
		buttonicon:"ui-icon-pencil", 
		onClickButton: editRowParticipante,
		position: "last", 
		title:"", 
		cursor: "pointer"
    });
	
  
	jq("#gridParticipante").navButtonAdd('#pagerParticipante', {
		caption:"", 
		buttonicon:"ui-icon-trash", 
		onClickButton: deleteRowParticipante,
		position: "last", 
		title:"", 
		cursor: "pointer"
	});

});


</script>
  

<script type="text/javascript">

	function addRowParticipante() {
	
		// Obtém a linha selecionada
	    jq("#gridParticipante").jqGrid('editGridRow','new', {
	    	url: "addTreinamentoParticipante", 
	     	editData: {},
	       	recreateForm: true,
	       	resize: false,
	       	beforeShowForm: function(form) {},
	       	afterShowForm: function(form) {
	       		$("#TblGrid_gridParticipante").find("input").each(function(index, element) {
	    			$(element).attr({"autocomplete": "off"});
	    			$(element).css({width:"300px"});
	    		});
	       		
	       		$("#treinamento").val($("#idTreinamento").val());
	       		
	       		$("#editmodgridParticipante").css({width:"400px"});
	       			       		
	       		loadAutoCompleteParticipante($("#cpf"));
  
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
	           		jq("#dialog").text('O participante foi incluído com sucesso.');
	      
	           		jq("#dialog").dialog({ 
	           			title: 'Alerta',
				        modal: true,
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


	function editRowParticipante() {
		// Obtém a linha selecionada
		var row = jq("#gridParticipante").jqGrid('getGridParam','selrow');
	 
		if( row != null ) 
			jq("#gridParticipante").jqGrid('editGridRow', row, { 
				url: "editTreinamentoParticipante", 
				editData: {},
		        recreateForm: true,
		        resize: false,
		        beforeShowForm: function(form) {},
		        afterShowForm: function(form) {
		       		$("#TblGrid_gridParticipante").find("input").each(function(index, element) {
		    			$(element).attr({"autocomplete": "off"});
		    			$(element).css({width:"300px"});
		    		});
		       		
		       		$("#editmodgridParticipante").css({width:"400px"});
		       		
		       			       		
		       		$("#tr_presencaTreinamento").css({"display":"table-row"});
		       		
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
		            	jq("#dialog").text('O participante foi alterado com sucesso.');
		            	
		     			jq("#dialog").dialog({
		     				title: 'Alerta',
		     				modal: true,
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

	function deleteRowParticipante() {
	 	// Obtém a linha selecionada
	    var row = jq("#gridParticipante").jqGrid('getGridParam','selrow');
	
	 	
	    // Um pop-up será exibido para confirmar a seleção da operação
	 	if( row != null ) 
	  		jq("#gridParticipante").jqGrid( 'delGridRow', row, {
	  			url: 'deleteTreinamentoParticipante', 
	  			recreateForm: true,
	            beforeShowForm: function(form) {
	            	// Muda o título
	                jq(".delmsg").replaceWith('<span style="white-space: pre;">' +
	                   'Confirma a exclusão do Participante?' + '</span>');
	                 
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
	                	jq("#dialog").text('O participante foi excluído com sucesso.');
	        
	                	jq("#dialog").dialog({
	                		title: 'Alerta',
	                		modal: true,
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
/***** GRID FIM - DOCUMENTOS *****/ 
</script>


<div>
	<!-- ÁREA MENU -->
	<div style="width:100%; height:14%">
		<div class="topContainer"> 
			<div class="logoContainer">
				<div class="logoDiv">
					<img class="logo" src="<c:url value="resources/images/logo.png"/>" />  
				</div>
				
				<div class="textLogoDiv">
					<label class="textLogoLabel">Treinamentos</label>
				</div>
				
				<nav style="height:50px">
				<ul class="menuBar">
				<c:forEach var="menuItem" items="${menus}" >
					 
					<c:if test="${menuItem.idMenuPai == '0'}">
						<li><a id="menu_${menuItem.id}" href="${menuItem.pagina}" onclick="showSubMenu(this)">${menuItem.tituloMenu}</a>
					</c:if>
					
					<ul id="submenu_${menuItem.id}" class="subMenu">
					<c:forEach var="subMenuItem" items="${menus}" begin="0">
						<c:if test="${subMenuItem.idMenuPai != '0' && subMenuItem.idMenuPai eq menuItem.id}">
							<!-- <div class="subMenuArrow"></div> -->
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
		</div>
	</div>

	<!-- ÁREA TREINAMENTO -->
	<div id="treinamentoMain" name="treinamentoMain" style="width:90%; height:auto/*39%*/; margin-left:5%; margin-right:5%; margin-top: 42px; border:1px solid #888; box-shadow:3px 4px 10px 0px rgba(0, 0, 0, 0.5);">
 		
 		<div class="headerTreinamento">
 			<label>Registro de Treinamento</label>
 			
 			<div style="float:right; width: auto">
 				<input id="searchTreinamento" name="searchTreinamento" type="text" class="dotSearch" title="Pesquisar por Nº do Registro ou Documentos" onclick="changeInputStyle(this);" onkeyup="loadAutoCompleteTreinamento(this);" onmouseover="addSearchClass(this);" onmouseout="removeSearchClass(this);" />
				<span id="printTreinamento" name="printTreinamento" class="dotTools dotPrint disabledButton" title="Imprimir"></span>
				<span id="addTreinamento" name="addTreinamento" class="dotTools dotAdd" title="Adicionar" onclick="addTreinamento(this)"></span>
				<span id="editTreinamento" name="editTreinamento" class="dotTools dotEdit disabledButton" title="Editar"></span>
				<span id="saveTreinamento" name="saveTreinamento" class="dotTools dotSave disabledButton" title="Salvar"></span>
				<span id="deleteTreinamento" name="deleteTreinamento" class="dotTools dotDelete disabledButton" title="Excluir"></span>
			</div>
 		</div>
 		
 		<table id="tblTreinamento" name="tblTreinamento" style="width:100%; table-layout:fixed; background-color:#fff;" role="grid" aria-labelledby="gbox_grid" cellspacing="0" cellpadding="0" border="0">
 		<tr>
 			<td style="width:26%; border-right:solid 1px #888; padding-right:10px; padding-bottom:5px;">
 				<input type="hidden" id="idTreinamento">
  				<label class="labelTreinamento">Obra:</label><br>
  				<select id="obra" name="obra" class="selectTreinamento" style="width:100%" onchange="loadDropDownContratos(this);"></select>
 			</td>
 			<td style="width:8%; border-right:solid 1px #888; padding-right:10px; padding-bottom:5px;">
 				<label class="labelTreinamento">Reg. Nº:</label>
 				<input type="text" id="numeroRegistro" name="numeroRegistro" class="inputTreinamento" style="width:97%" />
 			</td>
 			<td style="width:9%; border-right:solid 1px #888; padding-right:10px; padding-bottom:5px;">
 				<label class="labelTreinamento">Data:</label><br>
 				<input type="text" id="data" name="data" class="inputTreinamento" style="width:99%" maxlength="10" onkeyup="mascaraData(this);" onblur="validaDataForm(this);" />
 			</td>
 			<td style="width:20%; border-right:solid 1px #888; padding-bottom:5px;">
 				<label class="labelTreinamento">Horário:</label><br>
 				<label class="labelTreinamento">Início:</label>
 				<input type="text" id="horaInicio" name="horaInicio" class="inputTreinamento" style="width:20%; margin-left:2px !important;" maxlength="5" onkeyup="mascaraHora(this)" onblur="validaHoraForm(this, $('#horaTermino'))" />
 				<label class="labelTreinamento" style="margin-left:2px !important;">Término:</label>
 				<input type="text" id="horaTermino" name="horaTermino" class="inputTreinamento" style="width:20%; margin-left:2px !important;" maxlength="5" onkeyup="mascaraHora(this)" onblur="validaHoraForm($('#horaInicio'), this)" />
 			</td>
 			<td style="width:16%; border-right:solid 1px #888; padding-right:6px; padding-bottom:5px;">
 				<label class="labelTreinamento">Data Avaliação Eficácia:</label><br>
 				<input type="text" id="dataAvaliacaoEficacia" name="dataAvaliacaoEficacia" class="inputTreinamento" style="width:51%" maxlength="10" onkeyup="mascaraData(this);" onblur="validaDataForm(this);" />  	
 			</td>
 			<td style="width:24%; padding-right:11px; padding-bottom:5px;">
				<label class="labelTreinamento">Local / Trecho:</label><br>
 				<input type="text" id="localTrecho" name="localTrecho" class="inputTreinamento" style="width:100%" />  				
 			</td>
 			<!-- 		
 			<td style="width:6%; padding-bottom:5px;">
 				<label class="labelTreinamento">Folha:</label>
 			</td>
 			-->
 		</tr>		
 		
 		<tr>
 			<td style="width:26%; border-right:solid 1px #888; border-top:solid 1px #888; padding-right:10px; padding-bottom:5px;">
 				<label class="labelTreinamento">Contrato:</label><br>
  				<select id="contrato" name="contrato" class="selectTreinamento" style="width:100%" disabled="disabled"></select>
 			</td>
 			<td colspan="2" style="width:29%; border-right:solid 1px #888; border-top:solid 1px #888; padding-right:10px; padding-bottom: 5px;">
				<label class="labelTreinamento">Cliente:</label><br>
   				<select id="cliente" name="cliente" class="selectTreinamento" style="width:100%"></select>
			</td>
			<!-- 
			<td style="border-right:solid 1px #888; border-top:solid 1px #888; padding-right:10px; padding-bottom:5px;">
   				<label class="labelTreinamento">HH. Treinamento:</label><br>
    			<input type="text" id="hhTreinamento" name="hhTreinamento" class="inputTreinamento" style="width:23%" />
   			</td>
   			-->
   			<td colspan="4" style="border-top:solid 1px #888; padding-right:11px; padding-bottom:5px;">
  				<label class="labelTreinamento">Recursos Utilizados:</label><br>
	    		<input type="text" id="recursosUtilizados" name="recursosUtilizados" class="inputTreinamento" style="width:100%" />
			</td>
		</tr>
			
		<tr>
 			<td style="border-right:solid 1px #888; border-top:solid 1px #888; padding-right:10px; padding-bottom:5px;">
 				<label class="labelTreinamento">Método de Avaliação de Eficácia:</label><br>
  				<input type="checkbox" class="checkboxTreinamento" id="metodoAvaliacaoCampo" name="metodoAvaliacaoCampo" value="campo"><label class="checkBoxLabelTreinamento">Campo</label><br>
				<input type="checkbox" class="checkboxTreinamento" id="metodoAvaliacaoCertificado" name="metodoAvaliacaoCertificado" value="certificado"><label class="checkBoxLabelTreinamento">Certificado</label><br>
				<input type="checkbox" class="checkboxTreinamento" id="metodoAvaliacaoTeste" name="metodoAvaliacaoTeste" value="teste"><label class="checkBoxLabelTreinamento">Teste</label>  
 			</td>
 			<td colspan="3" style="border-right:solid 1px #888; border-top:solid 1px #888; padding-bottom:5px;">
 				<label class="labelTreinamento">Instrutor:</label><br>
 				<div style="width:15%; display:inline-block">
	  				<label class="labelTreinamento">Nome:</label>
	  			</div>
	  			<input type="hidden" id="idInstrutor" name="idInstrutor" />
	  			<input type="text" id="instrutorNome" name="instrutorNome" class="inputTreinamento" style="width:81%" onkeyup="loadAutoCompleteInstrutor(this)" />
	  			<div style="width:15%; display:inline-block">
	  				<label class="labelTreinamento">Função:</label>
	  			</div>
	  			<input type="text" id="instrutorFuncao" name="instrutorFuncao" class="inputTreinamento" style="width:81%;" />
			</td>
   			<td colspan="3" style="border-top:solid 1px #888; padding-right:11px; padding-bottom:5px; vertical-align: top;">
 				<label class="labelTreinamento">Setor de Treinamento:</label><br>
 			    <textarea id="descSetorTreinamento" name="descSetorTreinamento" rows="3" cols="141" class="inputTreinamento" style="resize:none; width:99%; height:100% !important;"></textarea>   	
 			</td>
		</tr>
		</table>
	</div>

	<!-- ÁREA DOCS E PARTICIPANTES -->
	<div id="divPartDoc" name="divPartDoc" style="width:90%; height:47%; margin:5px 5% 0% 5%; text-align:center; display:none;">

		<div style="float: left; width:5%; margin-top:15px;">
			<span class="dot dotDoc" title="Documentos de Referência" onclick="currentSlide(1)"></span><br>
			<span class="dot dotParticipante" title="Participantes" onclick="currentSlide(2)"></span> 
		</div>
 
		<div class="slideshow-container" style="display:inline-block; float:left; width: 95%;">
			<div class="mySlides fade">
				<div id="jqgrid" class="divGrid" style="padding-top: 12px !important">
				    <table id="gridDoc"></table>
				    <div id="pagerDoc"></div>
				</div>
		  	</div>
		
		  	<div class="mySlides fade">
		  		<div id="jqgrid" class="divGrid" style="padding-top: 12px !important;">
				    <table id="gridParticipante"></table>
				    <div id="pagerParticipante"></div>
				</div>
		  	</div>		
		</div>
		
	</div>
</div>


<div id="dialog" title="Feature not supported" style="display:none">
    <p>That feature is not supported.</p>
</div>

<div id="dialogSelectRow" title="Alerta" style="display:none">
    <p>Selecione um registro.</p>
</div>

</body>

</html>