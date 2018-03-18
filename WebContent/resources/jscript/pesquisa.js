function pad(valor, tamanho, caractere) {
    return new Array(tamanho - valor.toString().length + 1).join(caractere) + valor;
}


function insereMascaraCpf(strCpf) {
	strCpf = strCpf.replace(/\D/g,"");                    // Remove todos os dígitos
	strCpf = strCpf.replace(/(\d{3})(\d)/,"$1.$2");       // Coloca barra entre o segundo e terceiro dígitos
	strCpf = strCpf.replace(/(\d{3})(\d)/,"$1.$2");       // Coloca nova barra entre o segundo e terceiro dígitos
	strCpf = strCpf.replace(/(\d{3})(\d)/,"$1-$2");       // Coloca nova barra entre o segundo e terceiro dígitos

	return strCpf;
}


function genericMessage(strMessage, strHeaderMessage) {
	$("#dialog").text(strMessage);
    
	jq("#dialog").dialog({ 
		title: strHeaderMessage,
       modal: true,
		buttons: {
			"OK": function() {
				jq(this).dialog("close");
			}
		}
	});
}


function searchTreinamento() {
	var dData, sDataFormatada;
	
	$("#resultPesquisa").contents().remove();

    treinamentos = $.ajax({
					        type : 'POST',
					        url : 'search',
					        dataType : 'json',
					        data: {
					        	inputSearch: $("#inputSearch").val()
					        },
					        /*contentType : 'application/json',*/
					        async: false,
					        error : function() {
					            alert('error');
					        }
					    }).responseText;
    
    
    jTreinamentos = $.parseJSON(treinamentos);   
    
    if (jTreinamentos.length == 0) {
    	genericMessage("Nenhum registro encontrado.", "Alert");
    	return false;
    }
    
	$.each(jTreinamentos, function(index, itemTreinamento) {
		
		
		if (index % 2 == 0) {
			strDivClass = "pesquisaItemColor";	
		} else {
			strDivClass = "pesquisaItemWhite";
		}
		
		
		$("#resultPesquisa").append("<div id='t_" + index + "' class='" + strDivClass + "' ondblclick='geraRelatorio(" + itemTreinamento.idTreinamento + ", " + itemTreinamento.obra + ")'>");

		if (itemTreinamento.participanteDoc == "P") {
			strIconLeft = "colaborador";
			strIconRight = "treinamentoDocs";
		} else {
			strIconLeft = "treinamentoDocs";
			strIconRight = "colaborador";
		}
		
		$("#t_" + index).append("<div class='divPesquisaItemImg'><img class='pesquisaItemImg' src='resources/images/" + strIconLeft + ".png'></div>");
		
		$("#t_" + index).append("<div id='divLeftItem_" + index + "' class='divPesquisaItemDetalhe' style='width:400px; vertical-align:top;'></div>");
		
		$("#divLeftItem_" + index).append("<ul id='ulLeftItem_" + index + "' class='resultItemColorUL'></ul>");
		
		if (itemTreinamento.participanteDoc == "P") {
			$("#ulLeftItem_" + index).append("<li><b>Nome:</b> " + itemTreinamento.nomeNumeroDoc + "</li>");
			$("#ulLeftItem_" + index).append("<li><b>CPF:</b> " + itemTreinamento.cpfRevisao + "</li>");
			$("#ulLeftItem_" + index).append("<li><b>Função:</b> " + itemTreinamento.descFuncaoDoc + "</li>");
			$("#ulLeftItem_" + index).append("<li><b>Local:</b> " + itemTreinamento.nomeObra + "</li>");
			
			switch(itemTreinamento.presencaTreinamento) {
		    case "S":
		        strPresenca = "Sim";
		        break;
		    case "N":
		    	strPresenca = "Não";
		        break;
		    default:
		    	strPresenca = "Não Informado";
		}
			
			$("#ulLeftItem_" + index).append("<li><b>Presença:</b> " + strPresenca + "</li>");
		} else {
			$("#ulLeftItem_" + index).append("<li><b>Nº Documento:</b> " + itemTreinamento.nomeNumeroDoc + "</li>");
			$("#ulLeftItem_" + index).append("<li><b>Revisão:</b> " + itemTreinamento.cpfRevisao + "</li>");
			$("#ulLeftItem_" + index).append("<li><b>Descrição Documento:</b> " + itemTreinamento.descFuncaoDoc + "</li>");
			$("#ulLeftItem_" + index).append("<li><b>Local:</b> " + itemTreinamento.nomeObra + "</li>");
		}
		
	
		$("#t_" + index).append("<div id='divRightItem_" + index + "' class='divDetalhe'></div>");
		

		$("#divRightItem_" + index).append("<div class='divPesquisaItemImg'><img class='pesquisaItemImg' src='resources/images/registro.png'></div>");
		
		$("#divRightItem_" + index).append("<div id='divRightItemRegistro_" + index + "' class='divPesquisaItemDetalhe' style='width:150px;'></div>");
		
		$("#divRightItemRegistro_" + index).append("<ul id='ulRightItemRegistro_" + index + "' class='resultItemColorUL'></ul>");	
		$("#ulRightItemRegistro_" + index).append("<li><b>Nº Registro:</b> " + itemTreinamento.numeroRegistro + "</li>");
		
		dData = new Date(itemTreinamento.data);
		sDataFormatada = pad(dData.getDate(), 2 , "0") + "/" + pad(dData.getMonth() + 1, 2, "0") + "/" + dData.getFullYear();
		
		$("#ulRightItemRegistro_" + index).append("<li><b>Data:</b> " + sDataFormatada + "</li>");
		
		$("#divRightItem_" + index).append("<div id='divRightItemInstrutor_" + index + "' class='divPesquisaItemDetalhe' style='width:570px;'></div>");
		
		$("#divRightItemInstrutor_" + index).append("<ul id='ulRightItemInstrutor_" + index + "' class='resultItemColorUL'></ul>");
		$("#ulRightItemInstrutor_" + index).append("<li><b>Instrutor:</b> " + itemTreinamento.instrutorNome + "</li>");
		$("#ulRightItemInstrutor_" + index).append("<li><b>Função:</b> " + itemTreinamento.instrutorFuncao + "</li>");
		

		$("#divRightItem_" + index).append("<div class='divPesquisaItemImg' style='margin-top:15px;'><img class='pesquisaItemImg' src='resources/images/" + strIconRight + ".png'></div>");
			
		$("#divRightItem_" + index).append("<div id='divRightItemLista_" + index + "' class='divPesquisaItemDetalhe' style='width:726px; margin-top:10px;'></div>");
		
		$("#divRightItemLista_" + index).append("<ul id='ulRightItemLista_" + index + "' class='resultItemColorUL'></ul>");
		
		if (itemTreinamento.participanteDoc == "P") {
			
			$.each(itemTreinamento.treinamentoDocumento, function(indexDoc, itemDoc) {
				$("#ulRightItemLista_" + index).append("<li>" + itemDoc.numeroDocumento + " - " + itemDoc.descDocumento + " (Rev. " + itemDoc.revisaoDocumento + ")" + "</li>");
			});
			
		} else {
			$.each(itemTreinamento.treinamentoParticipante, function(indexPart, itemPart) {
				
				if (itemPart.presencaTreinamento == "S") {
					strStyle = "";
				} else {
					strStyle = "style='color:#e42d2d'"
				}
				
				$("#ulRightItemLista_" + index).append("<li " + strStyle + ">" + itemPart.cpf + " - " + itemPart.nome + " (" + itemPart.descFuncao + ")" + "</li>");
			});
		}
			
		
		/*
		$("#resultPesquisa").append("<ul id='pesquisaItem" + (index + 1) + "' class='" + strUlClass + "'></ul>");
		
		$("#pesquisaItem" + (index + 1)).append("<li><a href='#' style='width:35px; border:solid 1px black'><img class='pesquisaItemImg' src='resources/images/colaborador.png' /></a></li>");	
		$("#pesquisaItem" + (index + 1)).append("<li><a href='#' style='width:30%; border:solid 1px black'>" + itemTreinamento.nome + "</a></li>");
		$("#pesquisaItem" + (index + 1)).append("<li><a href='#' style='width:30%; border:solid 1px black'>" + insereMascaraCpf(itemTreinamento.cpf) + "</a></li>");
		
		
		dData = new Date(itemTreinamento.data);
		sDataFormatada = pad(dData.getDate(), 2 , "0") + "/" + pad(dData.getMonth() + 1, 2, "0") + "/" + dData.getFullYear();
		
		$("#pesquisaItem" + (index + 1)).append("<li><a href='#' style='width:30%'>" + sDataFormatada + "</a></li>")
		*/
	});
	

	/*
	for (var i=1; i<=15; i++) {
		if (i % 2 == 0) {
			strUlClass = "pesquisaItemColor";	
		} else {
			strUlClass = "pesquisaItemWhite";
		}
		
		$("#resultPesquisa").append("<ul id='pesquisaItem" + (i + 1) + "' class='" + strUlClass + "'></ul>");
		$("#pesquisaItem" + (i + 1)).append("<li><a href='#' style='width:4%'><img class='pesquisaItemImg' src='resources/images/colaborador.png' /></a></li>");
	}
	*/
	    
}


function submitEnter(obj,e) {
	var keyCode;
	
	e = (e) ? e : event;

    var charCode = (e.charCode) ? e.charCode : ((e.which) ? e.which : e.keyCode);
		
    if (charCode == 13) {

    	searchTreinamento($("#search").val());
		
        return false;
    }
	
    return true;

}


function geraRelatorio(idTreinamento, obra) {
	
	var url = "geraRelatorio?relatorio=treinamento&idTreinamento=" + idTreinamento + "&ccObra=" + obra;
	
	//var myWindow = window.open(url, "_blank", "width=500,height=500"); // Abrir uma nova janela
	var myWindow = window.open(url, "_blank");

}