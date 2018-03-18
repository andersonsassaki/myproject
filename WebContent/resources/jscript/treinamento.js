	function changeInputStyle(oInput) {
		$(oInput).addClass("dotSearchActive");
	}
	
	
	function addSearchClass(oSearch) {
		if ( !$(oSearch).hasClass("dotSearchActive") ) {
			$(oSearch).addClass("dotSearchHover");
		}
	}
	
	
	function removeSearchClass(oSearch) {
		$(oSearch).removeClass("dotSearchHover");
	}
	

	function loadDropDownForm() {
		
		/***************************/
		/* Alimenta DropDown Obras */
		/***************************/
		oObras = $.ajax({
			url: "listaTreinamentoObras",
			dataType: "json",
			data: {
				featureClass: "P",
				style: "full"
			},
			async: false,
			error: function() {
				genericMessage("Erro ao carregar lista de obras.", "Erro");
			}
		 }).responseText;
		
		jObras = $.parseJSON(oObras);
		
		// Remove as obras do dropdown
		$("#obra").contents().remove();
		$("#obra").append("<option value='0'></option>")
		
		// Inclui no dropdown as obras que o usuário tem acesso.
		$.each(jObras, function(index, item) {
			$("#obra").append("<option value='" + item.id + "'>" + item.nomeObra +"</option>")
		});			
		
		
		/******************************/
		/* Alimenta DropDown Clientes */
		/******************************/
		oClientes = $.ajax({
			url: "listaTreinamentoClientes",
			dataType: "json",
			data: {
				featureClass: "P",
				style: "full"
			},
			async: false,
			error: function() {
				genericMessage("Erro ao carregar lista de clientes.", "Erro");
			}
		 }).responseText;
		
		jClientes = $.parseJSON(oClientes);
		
		// Remove as obras do dropdown
		$("#cliente").contents().remove();
		$("#cliente").append("<option value='0'></option>")
		
		// Inclui no dropdown as obras que o usuário tem acesso.
		$.each(jClientes, function(index, item) {
			$("#cliente").append("<option value='" + item.id + "'>" + item.nomeCliente +"</option>")
		});
				
		//Desabilita todos os campos do form
		disableFormFields();
	}


	function loadDropDownContratos(oObra) {
		/*******************************/
		/* Alimenta DropDown Contratos */
		/*******************************/
		if ( $(oObra).val() != 0 ) {
			oContratos = $.ajax({
				url: "listaTreinamentoContratos",
				dataType: "json",
				data: {
					ccObra: $(oObra).val(),
					featureClass: "P",
					style: "full"
				},
				async: false,
				error: function() {
					genericMessage("Erro ao carregar lista de contratos.", "Erro");
				}
			 }).responseText;
			
			jContratos = $.parseJSON(oContratos);
			
			// Remove as obras do dropdown
			$("#contrato").contents().remove();
			$("#contrato").append("<option value='0'></option>")
			
			// Inclui no dropdown as obras que o usuário tem acesso.
			$.each(jContratos, function(index, item) {
				$("#contrato").append("<option value='" + item.id + "'>" + item.descricao +"</option>")
			});
			
			$("#contrato").prop("disabled", false);
		} else {
			$("#contrato").prop("disabled", true);
		} 
	}


	function loadAutoCompleteInstrutor(obj) {
		$("#instrutorNome").autocomplete({
			source: function(request, response) {
				$.ajax({
					url: "listaTreinamentoInstrutores",
					dataType: "json",
					data: {
						dbType: "S",
						searchInstrutor: $(obj).val(),
						featureClass: "P",
						style: "full",
						maxRows: 12,
						name_startsWith: request.term
					},
					success: function( data ) {
						
						response($.map( data, function( item ) {
							return {
								label:item.nomeInstrutor,
								values:item.id,
								nomeFuncao: item.nomeFuncao
							}
						}));	
					}
				});
			},
			select: function( event, ui ) {
				$(obj).val(ui.item.label);
				
				$("#idInstrutor").val(ui.item.values);
				$("#instrutorFuncao").val(ui.item.nomeFuncao);
				
				return false;
			}
		}); 
	}

	
	function loadAutoCompleteParticipante(obj) {
		$("#cpf").autocomplete({
			source: function(request, response) {
				$.ajax({
					url: "listaTreinamentoParticipante",
					dataType: "json",
					data: {
						dbType: "S",
						searchFuncionario: $(obj).val(),
						featureClass: "P",
						style: "full",
						maxRows: 12,
						minLenght: 3,
						name_startsWith: request.term
					},
					success: function( data ) {
						
						$("#FrmGrid_gridParticipante").css({overflow:"hidden"});
						
						response($.map( data, function( item ) {
							return {
								label:item.nome,
								values:item.cpf,
								matricula: item.matricula,
								funcao: item.funcao
							}
						}));	
					}
				});
			},
			select: function( event, ui ) {
				$(obj).val(ui.item.label);
				
				$("#cpf").val(ui.item.values);
				$("#nome").val(ui.item.label);
				$("#matricula").val(ui.item.matricula);
				$("#descFuncao").val(ui.item.funcao);
				
				return false;
			}
		});
		/*.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			
	      return $( "<li>" )
		  	.append( "<a>CPF: " + item.values + " - " + item.label + "<br>Doc.: " + strTextoLookup + "</a>" )
	        .appendTo( ul );
	    };*/
	}
	
	
	function loadAutoCompleteTreinamento(obj) {
		$("#searchTreinamento").autocomplete({
			source: function(request, response) {
				$.ajax({
					url: "searchListaTreinamentos",
					dataType: "json",
					data: {
						dbType: "S",
						searchTreinamento: encodeURI($(obj).val()),
						featureClass: "P",
						style: "full",
						maxRows: 12,
						name_startsWith: request.term
					},
					success: function( data ) {
						
						response($.map( data, function( item ) {
							
							if (item.numeroRegistro == null) {
								strNumeroRegistro = "Sem Nº de Registro";
							} else {
								strNumeroRegistro = item.numeroRegistro
							}
							
							/*
							if (item.numeroDocumento == null) {
								strNumeroDocumento = "Sem Nº de Documento";
							} else {
								strNumeroDocumento = item.numeroDocumento;
							}
							
							if (item.descDocumento == null) {
								strDescDocumento = "";
							} else {
								strDescDocumento = item.descDocumento;
							}
							*/
							
							/*
							strDocs = "";
							
							$.each(item.treinamentoDocumento, function(index, itemDocs) {
								
								if (itemDocs.descDocumento != "") {
									strTextoDoc = itemDocs.numeroDocumento + " - " + itemDocs.descDocumento;
								} else {
									strTextoDoc = itemDocs.numeroDocumento;
								} 
							
								strDocs = strDocs + "&#8226; " + strTextoDoc + "<br>";
							}
							*/
							return {
								values:item.idTreinamento,
								//label:"Nº Reg.: " + item.numeroRegistro + " / Doc.: " + item.numeroDocumento + " - " + item.descDocumento
								label:strNumeroRegistro,
								//numDoc: strNumeroDocumento,
								//descDoc: strDescDocumento,
								docs: item.treinamentoDocumento,
								obra: item.obra
							}
						}));	
					} 
				});
			},
			select: function( event, ui ) {
				
				$("#searchTreinamento").val(ui.item.label);
				loadTreinamento(ui.item.values);
				$("#editTreinamento").removeClass("disabledButton");
				$("#editTreinamento").click(function() {editTreinamento(this)});
				$("#deleteTreinamento").removeClass("disabledButton");
				$("#deleteTreinamento").click(function() {deleteTreinamento(this)});
				
				$("#printTreinamento").removeClass("disabledButton");
				$("#printTreinamento").click(function() {geraRelatorio('treinamento')});
				
				$("#contrato").prop("disabled", true);
				
				return false;
			}
		})
		.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		
			strItemTagA = "<a><b>Nº Reg.:</b> " + item.label + "<br><b>Documento(s):</b><br>";	
			
			strDocs = "";
			
			/*
			$.each(item.treinamentoDocumento, function(index, itemDocs) {
				
				if (itemDocs.descDocumento != "") {
					strTextoDoc = itemDocs.numeroDocumento + " - " + itemDocs.descDocumento;
				} else {
					strTextoDoc = itemDocs.numeroDocumento;
				} 
			
				strDocs = strDocs + "&#8226; " + strTextoDoc + "<br>";
			}
			*/
			
			for (i=0; i<item.docs.length; i++) {
				if (item.docs[i].descDocumento != "") {
					strTextoDoc = item.docs[i].numeroDocumento + " - " + item.docs[i].descDocumento;
				} else {
					strTextoDoc = item.docs[i].numeroDocumento;
				} 
			
				strDocs = strDocs + "&nbsp;&#8227; " + strTextoDoc + "<br>";
			}

				
			strItemTagA = strItemTagA + strDocs + "</a>";  
			
			
		    return $( "<li>" ).append( strItemTagA ).appendTo( ul );
		    
		  	  
	    };

	}
	 //.append( "<a>Nº Reg.: " + item.label + "<br>Doc.: " + strTextoLookup + "</a>" )

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
	
	
	function pad(valor, tamanho, caractere) {
	    return new Array(tamanho - valor.toString().length + 1).join(caractere) + valor;
	}
	
	
	function loadTreinamento(idTreinamento) {
		
		oTreinamento = $.ajax({
			url: "listaTreinamento", 
			dataType: "json",
			data: {
				idTreinamento: idTreinamento,
				featureClass: "P",
				style: "full"
			},
			async: false,
			error: function() {
				genericMessage("Erro ao carregar o treinamento.", "Erro");
				return false;
			}
		}).responseText;
	
		disableFormFields();
				
		$("#addTreinamento").removeClass("disabledButton");
		$("#addTreinamento").click(function(){addTreinamento(this);});
		$("#editTreinamento").addClass("disabledButton");
		$("#editTreinamento").removeAttr("onclick");
		$("#saveTreinamento").addClass("disabledButton");
		$("#saveTreinamento").removeAttr("onclick");
		$("#deleteTreinamento").addClass("disabledButton");
		$("#deleteTreinamento").removeAttr("onclick");
		
		$("#printTreinamento").removeClass("disabledButton");
		$("#printTreinamento").click(function() {geraRelatorio('treinamento')});
				
		jTreinamento = $.parseJSON(oTreinamento);
		
		$.each(jTreinamento, function(index, item) {
			$("#idTreinamento").val(item.idTreinamento);
			$("#usuario").val(item.usuario);
			$("#obra").val(item.obra);
			$("#numeroRegistro").val(item.numeroRegistro);
			
			var dData = new Date(item.data);
			var sDataFormatada = pad(dData.getDate(), 2 , "0") + "/" + pad(dData.getMonth() + 1, 2, "0") + "/" + dData.getFullYear();
	
			$("#data").val(sDataFormatada);
			$("#horaInicio").val(item.horaInicio);
			$("#horaTermino").val(item.horaTermino);
			
			dData = new Date(item.dataAvaliacaoEficacia);
			sDataFormatada = pad(dData.getDate(), 2 , "0") + "/" + pad(dData.getMonth() + 1, 2, "0") + "/" + dData.getFullYear();
			
			$("#dataAvaliacaoEficacia").val(sDataFormatada);
			$("#localTrecho").val(item.localTrecho);
			
			loadDropDownContratos($("#obra"));
			$("#contrato").val(item.contrato);
			
			$("#cliente").val(item.cliente);
			//$("#hhTreinamento").val(item.hhTreinamento);
			$("#recursosUtilizados").val(item.recursosUtilizados);
			
			if (item.metodoAvaliacaoCampo == "S") {
				$("#metodoAvaliacaoCampo").prop("checked", true);
			}
			
			if (item.metodoAvaliacaoCertificado == "S") {
				$("#metodoAvaliacaoCertificado").prop("checked", true);
			}
			
			if (item.metodoAvaliacaoTeste == "S") {
				$("#metodoAvaliacaoTeste").prop("checked", true);
			}
			
			$("#idInstrutor").val("");
			$("#instrutorNome").val(item.instrutorNome);
			$("#instrutorFuncao").val(item.instrutorFuncao);
			$("#descSetorTreinamento").val(item.descSetorTreinamento);
		});
		
		
		idTreinamento = $("#idTreinamento").val();
    	
		jq("#gridDoc").setGridParam({
			postData: {treinamento:idTreinamento}
	    }).trigger("reloadGrid");
		
		
		jq("#gridParticipante").setGridParam({
			postData: {treinamento:idTreinamento}
	    }).trigger("reloadGrid");
		
		
		$("#divPartDoc").show();
	}


	function addTreinamento(oAddTreinamento) {
		
		$("#dialog").text('Deseja incluir um novo treinamento?');
	    
		jq("#dialog").dialog({ 
			title: 'Alerta',
	       modal: true,
			buttons: {
				"Sim": function() {
					clearFormFields();
					enableFormFields();
					
					$(oAddTreinamento).addClass("disabledButton");
					$(oAddTreinamento).prop("onclick", null).off("click");
					
					$("#editTreinamento").addClass("disabledButton");
					$("#editTreinamento").off("click");
					$("#saveTreinamento").removeClass("disabledButton");
					$("#saveTreinamento").click(function(){ saveTreinamento(this); });
					$("#deleteTreinamento").addClass("disabledButton");
					$("#deleteTreinamento").off("click");
					
					$("#printTreinamento").addClass("disabledButton");
					$("#printTreinamento").off("click");
					
					$("#divPartDoc").hide();
					
					jq(this).dialog("close");
				},
				"Não": function() {
					jq(this).dialog("close");
					return false;
				}
			}
		});
	}


	function editTreinamento(oEditTreinamento) {
		$("#dialog").text('Deseja alterar este treinamento?');
	    
		jq("#dialog").dialog({ 
			title: 'Alerta',
	       modal: true,
			buttons: {
				"Sim": function() {
					enableFormFields();
					$("#editTreinamento").addClass("disabledButton");
					$("#editTreinamento").off("click");
					$("#saveTreinamento").removeClass("disabledButton");
					$("#saveTreinamento").click(function(){ saveTreinamento(this); });
					
					$("#printTreinamento").removeClass("disabledButton");
					$("#printTreinamento").click(function() {geraRelatorio('treinamento')});
					
					jq(this).dialog("close");
					
					$("#contrato").prop("disabled", false);
				},
				"Não": function() {
					jq(this).dialog("close");
					return false;
				}
			}
		});
	}


	function saveTreinamento() {
		
		if (!validaCamposObrigatorios()) {
			return false;
		}
		
		if ( $("#metodoAvaliacaoCampo").prop("checked") ) {
			strCampo = "S" 
		} else {
			strCampo = "N";
		}
		
		if ( $("#metodoAvaliacaoCertificado").prop("checked") ) {
			strCertificado = "S" 
		} else { 
			strCertificado = "N"
		}
		
		if ( $("#metodoAvaliacaoTeste").prop("checked") ) {
			strTeste = "S" 	
		} else {
			strTeste = "N"
		}
		
		if ( $("#idTreinamento").val() == "" ) {
			strURL = "addTreinamento";
			iTreinamento = 0;
		} else {
			strURL = "editTreinamento";
			iTreinamento = $("#idTreinamento").val();
		}
	
		oTreinamento = $.ajax({
			url: strURL, 
			dataType: "text",
			data: {
				idTreinamento: iTreinamento,
				obra: $("#obra").val(),
				numeroRegistro: $("#numeroRegistro").val(),
				data: $("#data").val(),
				horaInicio: $("#horaInicio").val(),
				horaTermino: $("#horaTermino").val(),
				dataAvaliacaoEficacia: $("#dataAvaliacaoEficacia").val(),
				localTrecho: encodeURI($("#localTrecho").val()),
				contrato: $("#contrato").val(),
				cliente: $("#cliente").val(),
				//hhTreinamento: $("#hhTreinamento").val(),
				recursosUtilizados: encodeURI($("#recursosUtilizados").val()),
				metodoAvaliacaoCampo: strCampo,
				metodoAvaliacaoCertificado: strCertificado,
				metodoAvaliacaoTeste: strTeste,
				idInstrutor: $("#idInstrutor").val(),
				instrutorNome: encodeURI($("#instrutorNome").val()),
				instrutorFuncao: encodeURI($("#instrutorFuncao").val()),
				descSetorTreinamento: encodeURI($("#descSetorTreinamento").val()),
				featureClass: "P",
				style: "full"
			},
			async: false,
			error: function() {
				genericMessage("Erro ao salvar o treinamento.", "Erro");
				return false;
			}
		}).responseText;
				
		
		if (oTreinamento > 0) {
			$("#idTreinamento").val(oTreinamento);
			genericMessage("O treinamento foi salvo com sucesso.", "Alerta");
			
			$("#addTreinamento").removeClass("disabledButton");
			$("#addTreinamento").click(function(){ addTreinamento(this); });
			$("#editTreinamento").removeClass("disabledButton");
			$("#editTreinamento").click(function(){ editTreinamento(this); });
			$("#saveTreinamento").addClass("disabledButton");
			$("#saveTreinamento").off("click");
			$("#deleteTreinamento").removeClass("disabledButton");
			$("#deleteTreinamento").click(function(){ deleteTreinamento(this); });
			
			$("#printTreinamento").removeClass("disabledButton");
			$("#printTreinamento").click(function() {geraRelatorio('treinamento')});
			
			disableFormFields();
			
			$("#divPartDoc").show();
		} else {
			genericMessage("Erro ao salvar o treinamento.", "Erro");
			return false;
		}

	}


	function deleteTreinamento() {
		$("#dialog").text('Deseja excluir o treinamento e seus respectivos documentos e participantes?');
	    
		jq("#dialog").dialog({ 
			title: 'Alerta',
	       modal: true,
			buttons: {
				"Sim": function() {
					jq(this).dialog("close");
					confirmDeleteTreinamento();
					
				},
				"Não": function() {
					jq(this).dialog("close");
					return false;
				}
			}
		});
	}


	function confirmDeleteTreinamento() {
		oTreinamento = $.ajax({
			url: "deleteTreinamento", 
			dataType: "text",
			data: {
				idTreinamento: $("#idTreinamento").val(),
				featureClass: "P",
				style: "full"
			},
			async: false,
			error: function() {
				genericMessage("Erro ao excluir o treinamento.", "Erro");
				//return false;
			}
		}).responseText;
	
		
		if (oTreinamento == "OK") {
			$("#divPartDoc").hide();
			$("#addTreinamento").removeClass("disabledButton");
			$("#addTreinamento").click(function(){ addTreinamento(this); });
			$("#editTreinamento").addClass("disabledButton");
			$("#editTreinamento").off("click");
			$("#deleteTreinamento").addClass("disabledButton");
			$("#deleteTreinamento").off("click");
			
			$("#printTreinamento").addClass("disabledButton");
			$("#printTreinamento").off("click");
			
			clearFormFields();
			disableFormFields();
			
			$("#divPartDoc").hide();
			genericMessage("O treinamento foi excluído com sucesso.", "Alerta");
		} else {
			genericMessage("Erro ao excluir o treinamento.", "Erro");
			return false;
		}
		
	}


	function clearFormFields() {	
		$("#idTreinamento").val("");
		$("#obra").val(0);
		$("#numeroRegistro").val("");
		$("#data").val("");
		$("#horaInicio").val("");
		$("#horaTermino").val("");
		$("#dataAvaliacaoEficacia").val("");
		$("#localTrecho").val("");
		$("#contrato").val(0);
		$("#cliente").val(0);
		//$("#hhTreinamento").val("");
		$("#recursosUtilizados").val("");
		$("#metodoAvaliacaoCampo").prop("checked", false);
		$("#metodoAvaliacaoCertificado").prop("checked", false);
		$("#metodoAvaliacaoTeste").prop("checked", false);
		$("#idInstrutor").val("");
		$("#instrutorNome").val("");
		$("#instrutorFuncao").val("");
		$("#descSetorTreinamento").val("");
	}


	function enableFormFields() {
		$("#idTreinamento").prop("disabled", false);
		$("#obra").prop("disabled", false);
		$("#numeroRegistro").prop("disabled", false);
		$("#data").prop("disabled", false);
		$("#horaInicio").prop("disabled", false);
		$("#horaTermino").prop("disabled", false);
		$("#dataAvaliacaoEficacia").prop("disabled", false);
		$("#localTrecho").prop("disabled", false);
		//$("#contrato").prop("disabled", false);
		$("#cliente").prop("disabled", false);
		//$("#hhTreinamento").prop("disabled", false);
		$("#recursosUtilizados").prop("disabled", false);
		$("#metodoAvaliacaoCampo").prop("disabled", false);
		$("#metodoAvaliacaoCertificado").prop("disabled", false);
		$("#metodoAvaliacaoTeste").prop("disabled", false);
		$("#idInstrutor").prop("disabled", false);
		$("#instrutorNome").prop("disabled", false);
		$("#instrutorFuncao").prop("disabled", false);
		$("#descSetorTreinamento").prop("disabled", false);	
	}


	function disableFormFields() {
		$("#idTreinamento").prop("disabled", true);
		$("#obra").prop("disabled", true);
		$("#numeroRegistro").prop("disabled", true);
		$("#data").prop("disabled", true);
		$("#horaInicio").prop("disabled", true);
		$("#horaTermino").prop("disabled", true);
		$("#dataAvaliacaoEficacia").prop("disabled", true);
		$("#localTrecho").prop("disabled", true);
		$("#contrato").prop("disabled", true);
		$("#cliente").prop("disabled", true);
		//$("#hhTreinamento").prop("disabled", true);
		$("#recursosUtilizados").prop("disabled", true);
		$("#metodoAvaliacaoCampo").prop("disabled", true);
		$("#metodoAvaliacaoCertificado").prop("disabled", true);
		$("#metodoAvaliacaoTeste").prop("disabled", true);
		$("#idInstrutor").prop("disabled", true);
		$("#instrutorNome").prop("disabled", true);
		$("#instrutorFuncao").prop("disabled", true);
		$("#descSetorTreinamento").prop("disabled", true);	
	}	
	
	
	function validaCamposObrigatorios() {
		if ( $("#obra").val() == "0" ) {
			genericMessage("Selecione a obra.", "Alerta");
			
			//$("#obra").css({"background-color":"#d3d2ca"});
			$("#obra").css({"border-color":"#ffa779"});
		
			$("#obra").click(function() { $("#obra").css({"border-color":"#eee"}) });
			return false;
		}
		
		if ( $("#numeroRegistro").val() == "" ) {
			genericMessage("Informe o número de registro.", "Alerta");
			
			//$("#obra").css({"background-color":"#d3d2ca"});
			$("#numeroRegistro").css({"border-color":"#ffa779"});
		
			$("#numeroRegistro").click(function() { $("#numeroRegistro").css({"border-color":"#eee"}) });
			return false;
		}
		
		if ( $("#data").val() == "" ) {
			genericMessage("Informe a data do treinamento.", "Alerta");
			
			//$("#obra").css({"background-color":"#d3d2ca"});
			$("#data").css({"border-color":"#ffa779"});
		
			$("#data").click(function() { $("#data").css({"border-color":"#eee"}) });
			return false;
		}
		
		if ( $("#localTrecho").val() == "" ) {
			genericMessage("Informe o local do treinamento.", "Alerta");
			
			//$("#obra").css({"background-color":"#d3d2ca"});
			$("#localTrecho").css({"border-color":"#ffa779"});
		
			$("#localTrecho").click(function() { $("#localTrecho").css({"border-color":"#eee"}) });
			return false;
		}
		
		if ( $("#contrato").val() == "0" ) {
			genericMessage("Selecione o contrato.", "Alerta");
			
			//$("#contrato").css({"background-color":"#d3d2ca"});
			$("#contrato").css({"border-color":"#ffa779"});
			
			$("#contrato").click(function() { $("#contrato").css({"border-color":"#eee"}) });
			return false;
		}
		
		
		if ( $("#cliente").val() == "0" ) {
			genericMessage("Selecione o cliente.", "Alerta");
			
			//$("#cliente").css({"background-color":"#d3d2ca"});
			$("#cliente").css({"border-color":"#ffa779"});
			
			$("#cliente").click(function() { $("#cliente").css({"border-color":"#eee"}) });
			return false;
		}
		
		return true;
	}
 
	
	var slideIndex = 1;

	function initialSlide() {
		showSlides(slideIndex);
	}
	
	
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
	  
		if (n > slides.length) {
			slideIndex = 1
		} 
		
		if (n < 1) {
			slideIndex = slides.length
		}
	  
		for (i = 0; i < slides.length; i++) {
			slides[i].style.display = "none"; 
		}
		
		for (i = 0; i < dots.length; i++) {
			dots[i].className = dots[i].className.replace(" active", "");
		}
		
		slides[slideIndex-1].style.display = "block"; 
		dots[slideIndex-1].className += " active";
	} 

	
	function mascaraData(obj) {
		oData = obj;
		setTimeout("execMascaraData()", 1);
	}


	function execMascaraData() {
		var rePattern = /^\d{1,2}\/\d{1,2}\/\d{4}$/;
			
		oData.value = insereMascaraData(oData.value);
		
		if ( oData.value != "" && oData.value.match(rePattern) ) {
				
			aData = oData.value.split("/");
				
			if (Number(aData[1]) < 1 || Number(aData[1]) > 12) {
				genericMessage("O mês informado é inválido.", "Erro");
				oData.value = "";
		
				$(oData).css({"border-color":"#ffa779"});				
				$(oData).click(function() { $(oData).css({"border-color":"#eee"}) });
				
				return false;
			}
		
			if (!validaDia(oData.value)) {
				genericMessage("O dia informado é inválido.", "Erro");
				oData.value = "";
				
				$(oData).css({"border-color":"#ffa779"});
				$(oData).click(function() { $(oData).css({"border-color":"#eee"}) });
				
				return false;
			}
				
		} 	
	}
	
	
	function insereMascaraData(data) {
		data = data.replace(/\D/g,"");                    // Remove todos os dígitos
		data = data.replace(/(\d{2})(\d)/,"$1/$2");       // Coloca barra entre o segundo e terceiro dígitos
		data = data.replace(/(\d{2})(\d)/,"$1/$2");       // Coloca nova barra entre o segundo e terceiro dígitos

		return data;
	}
	
	
	function validaDia(strData) {
		
		var aData = strData.split("/");	
		
		var dtAno = aData[2];
		var dtMes = aData[1];
		
		var dtUltimoDia = (new Date((new Date(dtAno, dtMes,1))-1)).getDate();

		if (parseInt(aData[0]) > parseInt(dtUltimoDia)) {
			return false;
		} else {
			return true;
		}
	}


	function mascaraHora(obj) {
		oHora = obj;
		setTimeout("execMascaraHora()", 1);
	}

	
	function execMascaraHora() {
		var rePattern = /^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/;
		
		oHora.value = insereMascaraHora(oHora.value);

	}	
	
	
	function insereMascaraHora(hora) {
		hora = hora.replace(/\D/g,"");                    // Remove todos os dígitos
		hora = hora.replace(/(\d{2})(\d)/,"$1:$2");       // Coloca dois pontos entre o segundo e terceiro dígitos

		return hora;
	}	

		
	function validaDataForm(oData) {
		var rePattern = /^\d{1,2}\/\d{1,2}\/\d{4}$/;

		if ($(oData).prop("name") == "data") {
			strMsg = "data de treinamento ";
		} else {
			strMsg = "data da avaliação de eficácia "
		}		
		
		if ($(oData).val() != '' && !$(oData).val().match(rePattern)) {
			genericMessage("A " + strMsg + "é inválida.", "Erro");
			
			$(oData).css({"border-color":"#ffa779"});
			$(oData).click(function() { $(oData).css({"border-color":"#eee"}) });
			
			return false;
		}
			
		if ($(oData).val() == "") {			
			genericMessage("Informe a " + strMsg + ".", "Erro");
			
			$(oData).css({"border-color":"#ffa779"});
			$(oData).click(function() { $(oData).css({"border-color":"#eee"}) });
			
			return false;
		}	
	
	}
	
	
	function validaHoraForm(oHoraInicio, oHoraTermino) {
		strHoraInicio = $(oHoraInicio).val();
		strHoraTermino = $(oHoraTermino).val();

		horaInicio = new Date();
		aHora = strHoraInicio.split(":");
		horaInicio.setHours(aHora[0], aHora[1], 0)
		
		horaFinal = new Date();
		aHora = strHoraTermino.split(":");
		horaFinal.setHours(aHora[0], aHora[1], 0)
		
		var reNumberPattern;

		var rePattern = /^([01]?[0-9]|2[0-3]):([0-5][0-9])/;		
		
		if ( strHoraInicio != "" && strHoraInicio.match(rePattern) == null ) {
			genericMessage("Hora inicial inválida.", "Erro");
			
			$(oHoraInicio).val("");
			
			$(oHoraInicio).css({"border-color":"#ffa779"});
			$(oHoraInicio).click(function() { $(oHoraInicio).css({"border-color":"#eee"}) });
					
			return false;
		}
		
		if ( strHoraTermino != "" && strHoraTermino.match(rePattern) == null ) {
			genericMessage("Hora final inválida.", "Erro");
			
			$(oHoraTermino).val("");
			
			$(oHoraTermino).css({"border-color":"#ffa779"});
			$(oHoraTermino).click(function() { $(oHoraTermino).css({"border-color":"#eee"}) });
			
			return false; 
		}

		if ( horaInicio > horaFinal ) {
			genericMessage("A hora final deve ser maior que a hora inicial.", "Erro");
			
			$(oHoraInicio).css({"border-color":"#ffa779"});
			$(oHoraInicio).click(function() { $(oHoraInicio).css({"border-color":"#eee"}) });
			
			$(oHoraTermino).css({"border-color":"#ffa779"});
			$(oHoraTermino).click(function() { $(oHoraTermino).css({"border-color":"#eee"}) });
			
			return false; 
		}
		
		/*
		var diferencaHoras = horaTermino - horaInicio;
		
		difHora = dif.getHours().toString();
		difMinuto = dif.getMinutes().toString();
		
		$("#hhTreinamento").val(difHora + ":" + difMinuto)
		*/
	}
		
	
	function geraRelatorio(strRelatorio) {
		
		var url = "geraRelatorio?relatorio=" + strRelatorio + "&idTreinamento=" + $("#idTreinamento").val() + "&ccObra=" + $("#obra").val();
		
		//var myWindow = window.open(url, "_blank", "width=500,height=500"); // Abrir uma nova janela
		var myWindow = window.open(url, "_blank");

	}