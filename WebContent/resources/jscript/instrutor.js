function loadAutoCompleteFuncao(obj) {
	$("#nomeFuncao").autocomplete({
		source: function(request, response) {
			$.ajax({
				url: "listaFuncao",
				dataType: "json",
				data: {
					dbType: "S",
					searchFuncao: $(obj).val(),
					featureClass: "P",
					style: "full",
					maxRows: 12,
					minLenght: 2,
					name_startsWith: request.term
				},
				success: function( data ) {
					
					$("#FrmGrid_grid").css({overflow:"hidden"});
					
					response($.map( data, function( item ) {
						return {
							label:item.nomeFuncao,
							values:item.id
						}
					}));	
				}
			});
		},
		select: function( event, ui ) {
			$(obj).val(ui.item.label);
			
			$("#funcao").val(ui.item.values);
			
			return false;
		}
	});
	/*.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
		
      return $( "<li>" )
	  	.append( "<a>CPF: " + item.values + " - " + item.label + "<br>Doc.: " + strTextoLookup + "</a>" )
        .appendTo( ul );
    };*/
}
