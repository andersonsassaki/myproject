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


function ltrim(strDescricao) {
	return strDescricao.replace(/^\s+/,"");
}


//right trim
function rtrim(strDescricao) {
	return strDescricao.replace(/\s+$/,"");
}