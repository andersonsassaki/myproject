<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/css/jquery-ui-1.8.16.smoothness.css" />" />
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/resources/jqGrid/css/ui.jqgrid.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/menu.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/treinamento.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/teste.css" />" />

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


<title>TESTE SLIDE</title>


</head>

<body onload="adjustSubMenu();">

	<!-- Seção para Logo e Menu -->
 
<script type="text/javascript">
/***********************************/
  
var slideIndex = 1;
showSlides(slideIndex);

function plusSlides(n) {
  showSlides(slideIndex += n);
}

function currentSlide(n) {
  showSlides(slideIndex = n);
}

function showSlides(n) {
  var i;
  var slides = document.getElementsByClassName("mySlides");
  var dots = document.getElementsByClassName("dot");
  if (n > slides.length) {slideIndex = 1} 
  if (n < 1) {slideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
      slides[i].style.display = "none"; 
  }
  for (i = 0; i < dots.length; i++) {
      dots[i].className = dots[i].className.replace(" active", "");
  }
  slides[slideIndex-1].style.display = "block"; 
  dots[slideIndex-1].className += " active";
} 
 
/***********************************/


/***** GRID INÍCIO *****/
jq(function() {
	jq("#grid").jqGrid({
		url:'listaContrato',
		datatype: 'json',
		mtype: 'GET',
		colNames:['id', 'Obra', 'Número Contrato'],
		colModel:[
			{name:'id',index:'id', width:60, editable:false, editoptions:{readonly:true,size:10}, hidden:true},
			{name:'ccObra',index:'ccObra', width:60, sorttype:"string", editable:true, edittype:'select',
				
				editoptions:{
					dataUrl: "listaObra", 
				    style:"width:auto; height:28px; border: solid 1px #888" 
				}

			},
			{name:'descricao',index:'descricao', width:500, sorttype:"string", editable:true},
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
	    caption:"Contratos",
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
    	url: "addContrato", 
     	editData: {},
       	recreateForm: true,
       	beforeShowForm: function(form) {},
       	afterShowForm: function(form) {
       		$("#TblGrid_grid").find("input").each(function(index, element) {
    			$(element).attr({"autocomplete": "off"});
    		});
       		
       		$("#editmodgrid").css({width:"auto"});
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
           		jq("#dialog").text('O contrato foi incluído com sucesso.');
      
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

function editRow() {
	// Obtém a linha selecionada
	var row = jq("#grid").jqGrid('getGridParam','selrow');
 
	if( row != null ) 
		jq("#grid").jqGrid('editGridRow', row, { 
			url: "editContrato", 
			editData: {},
	        recreateForm: true,
	        beforeShowForm: function(form) {},
	        afterShowForm: function(form) {
	       		$("#TblGrid_grid").find("input").each(function(index, element) {
	    			$(element).attr({"autocomplete": "off"});
	    		});
	       		
	       		$("#editmodgrid").css({width:"auto"});
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
	            	jq("#dialog").text('O contrato foi alterado com sucesso.');
	            	
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

function deleteRow() {
 	// Obtém a linha selecionada
    var row = jq("#grid").jqGrid('getGridParam','selrow');

 	
    // Um pop-up será exibido para confirmar a seleção da operação
 	if( row != null ) 
  		jq("#grid").jqGrid( 'delGridRow', row, {
  			url: 'deleteContrato', 
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
                	jq("#dialog").text('O contrato foi excluído com sucesso.');
        
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
 
 
</script>

<div style="text-align:center">
	<!-- ÁREA MENU -->
	<div style="width:100%; height:15%">
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
	<div style="border:solid 1px red; width:90%; height:35%; text-align:center; margin:0% 5% 0% 5%;">
 		<table id="tblTreinamento" name="tblTreinamento" style="width:100%; table-layout:fixed;" role="grid" aria-labelledby="gbox_grid" cellspacing="0" cellpadding="0" border="0">
 		<tr>
 			<td rowspan="4" style="width:26%; border-right:solid 1px #888; padding-right:10px; padding-bottom: 5px;">
 				<img id="imgLogoObra" style="height: 70px" />
  				<label class="labelTreinamento">Obra:</label><br>
  				<select id="obra" name="obra" class="selectTreinamento" style="width:100%" onchange="loadDropDownContratos(this);"></select>
 			</td>
 			<td rowspan="4" colspan="2" style="width:58%; border-right:solid 1px #888; text-align:center; background-color: beige;">
 				<label class="labelTreinamento" style="font-size: 25px !important; line-height: 0px !important;"><b>REGISTRO DE TREINAMENTO</b></label>
 			</td>
 		</tr>		    			
 		<tr>
 			<td>
 				<label class="labelTreinamento">Reg. Nº:</label>
 			</td>
 		</tr>
 		<tr>
 			<td style="border-top: solid 1px #888">
 				<label class="labelTreinamento">Data:</label>
 				<input type="text" id="data" name="data" class="inputTreinamento" style="width:90px" maxlength="10" />
 			</td>
 		</tr>
 		<tr>	
 			<td style="border-top: solid 1px #888">
 				<label class="labelTreinamento">Folha:</label>
 			</td>
 		</tr>
 		<tr>
 			<td style="width:26%; border-right:solid 1px #888; border-top:solid 1px #888; padding-right:10px; padding-bottom: 5px;">
 				<label class="labelTreinamento">Contrato:</label><br>
  			<select id="contrato" name="contrato" class="selectTreinamento" style="width:100%" disabled="disabled"></select>
 			</td>
 			<td style="width:29%; border-right:solid 1px #888; border-top:solid 1px #888; padding-right:10px; padding-bottom: 5px;">
					<label class="labelTreinamento">Cliente:</label><br>
   		<select id="cliente" name="cliente" class="selectTreinamento" style="width:100%"></select>
	</td>
	<td style="width:29%; border-right:solid 1px #888; border-top:solid 1px #888; padding-right:13px; padding-bottom: 5px;">
					<label class="labelTreinamento">Local / Trecho:</label><br>
 				<input type="text" id="local" name="local" class="inputTreinamento" style="width:100%" />  				
 			</td>
 			<td style="width:16%; border-top:solid 1px #888; padding-right:13px; padding-bottom: 5px;">
 				<label class="labelTreinamento">Data Avaliação Eficácia:</label><br>
 				<input type="text" id="dataEficacia" name="dataEficacia" class="inputTreinamento" style="width:90px" maxlength="10" />  	
 			</td>
 		</tr>
 		<tr>
 			<td colspan="3" style="border-top:solid 1px #888; border-right:solid 1px #888; vertical-align:top;">
 				<table id="tblDocumentos" name="tblDocumentos" class="ui-jqgrid-htable" role="grid" aria-labelledby="gbox_grid" cellspacing="0" cellpadding="0" border="0" style="width:100%; ">
 				<tr>
 					<td style="width:25%;"></td>
 					<td style="width:10%;"></td>
 					<td style="width:65%;"></td>
 				</tr>
 				
 			    <tr>
 					<td colspan="3" style="text-align:center; background-color: beige; border-bottom:solid 1px #888; height:30px; padding-top:8px; width:100%;">
 						<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;"><b>TREINAMENTO / DOCUMENTO DE REFERÊNCIA</b></label>
 					</td>
 				</tr>
 				<tr>
 					<td style="width:25%; text-align:center; background-color: beige; border-bottom:solid 1px #888; border-right:solid 1px #888; height:24px; padding-top:8px;">
 						<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;"><b>Número</b></label>
 					</td>
 					<td style="width:10%; text-align:center; background-color: beige; border-bottom:solid 1px #888; border-right:solid 1px #888; height:24px; padding-top:8px;">
 						<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;"><b>Rev.</b></label>
 					</td>
 					<td style="width:65%; text-align:center; background-color: beige; border-bottom:solid 1px #888; height:24px; padding-top:8px;">
 						<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;"><b>Descrição</b></label>
 					</td>
 				</tr>
 				<tr>
 					<td style="width:25%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="numeroDoc1" name="numeroDoc1" class="inputTreinamento" style="width:100%" />
 					</td>
 					<td style="width:10%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="revDoc1" name="revDoc1" class="inputTreinamento" style="width:100%" />
 					</td>
 					<td style="width:65%; border-bottom:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="descDoc1" name="descDoc1" class="inputTreinamento" style="width:100%" />
 					</td>
 				</tr>	    				
 				<tr>
 					<td style="width:25%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="numeroDoc2" name="numeroDoc2" class="inputTreinamento" style="width:100%" />
 					</td>
 					<td style="width:10%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="revDoc2" name="revDoc2" class="inputTreinamento" style="width:100%" />
 					</td>
 					<td style="width:65%; border-bottom:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="descDoc2" name="descDoc2" class="inputTreinamento" style="width:100%" />
 					</td>
 				</tr>		    				
 				<tr>
 					<td style="width:25%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="numeroDoc3" name="numeroDoc3" class="inputTreinamento" style="width:100%" />
 					</td>
 					<td style="width:10%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="revDoc3" name="revDoc3" class="inputTreinamento" style="width:100%" />
 					</td>
 					<td style="width:65%; border-bottom:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="descDoc3" name="descDoc3" class="inputTreinamento" style="width:100%" />
 					</td>
 				</tr>
 				<tr>
 					<td style="width:25%; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="numeroDoc4" name="numeroDoc4" class="inputTreinamento" style="width:100%" />
 					</td>
 					<td style="width:10%; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="revDoc4" name="revDoc4" class="inputTreinamento" style="width:100%" />
 					</td>
 					<td style="width:65%; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="descDoc4" name="descDoc4" class="inputTreinamento" style="width:100%" />
 					</td>
 				</tr>
 				
 				</table>
 			</td>
 			<td style="border-top:solid 1px #888; padding-bottom: 5px; padding-right:13px; vertical-align:top;">
 				<label class="labelTreinamento">Horário</label><br><br>
 				
 				<label class="labelTreinamento">Início:</label><br>
 				<input type="text" id="horaInicio" name="horaInicio" class="inputTreinamento" style="width:50px" /><br><br> 
 				
 				<label class="labelTreinamento">Término:</label><br>
 				<input type="text" id="horaTermino" name="horaTermino" class="inputTreinamento" style="width:60px" /> 
 			</td>
 		</tr>
 		
 		<tr>
 			<td colspan="4" style="border-top:solid 1px #888; vertical-align:top;">
 				<table id="tblParticipantes" name="tblParticipantes" class="ui-jqgrid-htable" role="grid" aria-labelledby="gbox_grid" cellspacing="0" cellpadding="0" border="0" style="width:100%;">
 				<tr>
 					<td style="width:8%;"></td>
 					<td style="width:5%;"></td>
 					<td style="width:13%;"></td>
 					<td style="width:37%;"></td>
 					<td style="width:37%;"></td>
 				</tr>
 				
 			    <tr>
 					<td colspan="5" style="text-align:center; background-color: beige; border-bottom:solid 1px #888; height:30px; padding-top:8px; width:100%;">
 						<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;"><b>LISTA DE PRESENÇA</b></label>
 					</td>
 				</tr>
 				<tr>
 					<td style="width:8%; text-align:center; background-color: beige; border-bottom:solid 1px #888; border-right:solid 1px #888; height:24px; padding-top:8px;">
 						<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;">&nbsp;</label>
 					</td>
 					<td style="width:5%; text-align:center; background-color: beige; border-bottom:solid 1px #888; border-right:solid 1px #888; height:24px; padding-top:8px;">
 						<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;"><b>Nº</b></label>
 					</td>
 					<td style="width:13%; text-align:center; background-color: beige; border-bottom:solid 1px #888; border-right:solid 1px #888; height:24px; padding-top:8px;">
 						<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;"><b>CPF</b></label>
 					</td>
 					<td style="width:37%; text-align:center; background-color: beige; border-bottom:solid 1px #888; border-right:solid 1px #888; height:24px; padding-top:8px;">
 						<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;"><b>Participante</b></label>
 					</td>
 					<td style="width:37%; text-align:center; background-color: beige; border-bottom:solid 1px #888; height:24px; padding-top:8px;">
 						<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;"><b>Função</b></label>
 					</td>
 				</tr> 				
 				
 				<tr>
 					<td style="width:8%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:5px; text-align:center;">
 						<img id="delParticipante" name="delParticipante" class="imgButtonDisabled" title="Excluir" src="<c:url value="/resources/images/delete.png"/>" disabled /> &nbsp;
 						<img id="editParticipante" name="editParticipante" class="imgButtonDisabled" title="Editar" style="padding-left:18px" src="<c:url value="/resources/images/edit.png"/>" disabled /> 
 					</td>
 					<td style="width:5%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="seqPart_1" name="seqPart_1" class="inputTreinamento" style="width:100%; border: none; text-align:center;" readonly value="1" />
 					</td>
 					<td style="width:13%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="cpfPart_1" name="cpfPart_1" class="inputTreinamento" style="width:100%" onkeyup="loadAutoCompleteFuncionario(this)" />
 					</td>
 					<td style="width:37%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="nomePart_1" name="nomePart_1" class="inputTreinamento" style="width:100%" />
 					</td>
 					<td style="width:37%; border-bottom:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 						<input type="text" id="funcPart_1" name="funcPart_1" class="inputTreinamento" style="width:100%" />
 					</td>
 				</tr>	    				
 				
 				<tr>
 					<td style="width:8%; border-bottom:solid 1px #888; border-right:solid 1px #888; padding-top:5px; text-align:center;">
 						<img id="addParticipante" name="addParticipante" class="imgButtonDisabled" title="Adicionar" style="padding-left:6px; padding-bottom:4px; width:28px; height:28px;" src="<c:url value="/resources/images/addPerson.png"/>" disabled /> 
 					</td>
 					<td colspan="4" style="width:5%; border-bottom:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
 					</td>
 				</tr>
 				</table>
 			</td>

 		</tr>
 		
 		<tr>
				<td colspan="4" style="text-align:center; background-color: beige; border-bottom:solid 1px #888; height:30px; padding-top:8px; width:100%;">
					<label class="labelTreinamento" style="font-size: 15px !important; line-height: 0px !important;"><b>MÉTODO DE AVALIAÇÃO DE EFICÁCIA</b></label>
				</td>
			</tr>   				
			<tr>
				<td colspan="4" style="width:40%; border-bottom:solid 1px #888; padding-top:3px; padding-right:13px; padding-bottom:5px;">
				<table class="ui-jqgrid-htable" role="grid" aria-labelledby="gbox_grid" cellspacing="0" cellpadding="0" border="0" style="width:100%;">
				<tr>
					<td style="width:40%; text-align:right;">
						<label class="labelTreinamento">(  ) Campo</label>
					</td>
					<td style="width:20%; text-align:center;">
						<label class="labelTreinamento">(  ) Certificado</label>
					</td>
					<td style="width:40%; text-align:left;">
						<label class="labelTreinamento">(  ) Teste</label>
					</td>
				</tr>
				</table>    					
				</td>
			</tr>
 		
 		<tr>
 			<td colspan="4" style="width:100%; border-bottom:solid 1px #888; padding-right:13px;">
					<table class="ui-jqgrid-htable" role="grid" aria-labelledby="gbox_grid" cellspacing="0" cellpadding="0" border="0" style="width:100%;">
 				<tr>
   			<td style="width:13%; border-right:solid 1px #888; padding-right:10px; padding-bottom: 5px;">
   				<label class="labelTreinamento">HH. Treinamento:</label><br>
    			<input type="text" id="hhTreinamento" name="hhTreinamento" class="inputTreinamento" style="width:60px" />
   			</td>
   			<td style="width:87%; padding-bottom: 5px;">
  					<label class="labelTreinamento">Recursos Utilizados:</label><br>
	    		<input type="text" id="recursos" name="recursos" class="inputTreinamento" style="width:100%" />
			</td>
		</tr>
		</table>
 		</tr>
 		
 		<tr>
 			<td style="border-right:solid 1px #888; padding-right:5px; padding-bottom:5px;">
 				<label class="labelTreinamento">Instrutor:</label><br>
  			<label class="labelTreinamento">Nome:</label>
  			<input type="hidden" id="idInstrutor" name="idInstrutor" />
  			<input type="text" id="nomeInstrutor" name="nomeInstrutor" class="inputTreinamento" style="width:317px" onkeyup="loadAutoCompleteInstrutor(this)" />
  			<label class="labelTreinamento">Função:</label>
  			<input type="text" id="nomeInstrutor" name="nomeInstrutor" class="inputTreinamento" style="width:308px; border:none;" />
  			<label class="labelTreinamento">Assinatura:</label><br>
 			</td>
 			<td colspan="3" style="padding-right:13px; padding-bottom: 5px; vertical-align: top;">
 				<label class="labelTreinamento">Setor de Treinamento:</label><br>
 			    <textarea id="observacao" name="observacao" rows="10" cols="141" class="inputTreinamento" style="resize:none; width:100%;"></textarea>   	
 			</td>
 		</tr>
 		</table>  
	</div>
	
	<!-- ÁREA DOCS E PARTICIPANTES -->
	<div style="border:solid 1px blue; width:90%; height:50%; margin: 0% 5% 0% 5%;">
	
	</div>

</div>


<div id="dialog" title="Feature not supported" style="display:none">
    <p>That feature is not supported.</p>
</div>

<div id="dialogSelectRow" title="Warning" style="display:none">
    <p>Please select row</p>
</div>

</body>

</html>