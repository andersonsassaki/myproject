
/* Para função ONLOAD */
function adjustSubMenu() {
	var nLeft, nTop, nHeight, nWidth, oSubMenu;
	
	$("a[id^='menu_']").each(function(index, element) {
		//alert($(element).attr("id"));
		
		nHeight = $(element).height();
		nWidth = ($(element).width() - 5) + "px";
		nLeft = $(element).position().left + "px";
		nTop = ($(element).position().top + nHeight) + "px";
		
		// Obtém o submenu
		oSubMenu = $("#sub" + $(element).attr("id"));
		
		
		// Posiciona o submenu
		$(oSubMenu).css({"top": nTop, "left": nLeft, "width": nWidth});
		
		
		// Altera largura dos itens do submenu
		$(oSubMenu).find("a").each(function(index, element) {
			$(element).css({"width": nWidth});
		});
		
		
		// Adiciona função para esconder submenu
		$(oSubMenu).mouseleave(function() {
			$(this).animate({"opacity":0, "filter":"alpha(opacity=0)"}, 150, function(){
				$(this).animate({"zIndex": -999}, 50);
			});
			
			$(this).find("a").each(function(index, element) {
				$(element).prop("disabled", true);
				$(element).css({"cursor":"default"});
			});
			
			$("a[id^='menu_']").each(function(aIndex, aElement){
				$(aElement).removeClass("activeMenu");
			});
	    });
		
	});
	

}


/* Função para onmouseover no item do menu */
function showSubMenu (oMenu) {
	var nLeft, nTop, nHeight, nWidth, oSubMenu;
	
	nHeight = $(oMenu).height();
	nWidth = ($(oMenu).width() - 5) + "px";
	nLeft = $(oMenu).position().left + "px";
	nTop = ($(oMenu).position().top + nHeight) + "px";
	
	// Obtém o submenu
	oSubMenu = $("#sub" + $(oMenu).attr("id"));
	$(oMenu).addClass("activeMenu");
	
	// Esconde outros submenus caso estejam abertos
	$("ul[id^='submenu_']").not("#sub" + $(oMenu).attr("id")).each(function(index, element){
		//$(element).fadeTo(300, 0);
		$(element).animate({"opacity":0, "filter":"alpha(opacity=0)"}, 150, function(){
			$(element).animate({"zIndex":-999}, 50);
		});
		
		$(this).find("a").each(function(index, element) {
			$(element).prop("disabled", true);
			$(element).css({"cursor":"default"});
		});
	});
	
	
	// Posiciona o menu caso seja o primeiro click
	if ($(oSubMenu).position().left != $(oMenu).position().left || $(oSubMenu).position().top != ($(oMenu).position().top + nHeight) || $(oSubMenu).width() != $(oMenu).width()) {

		// Posiciona o submenu
		$(oSubMenu).css({top: nTop, left: nLeft, width: nWidth});
		
		
		// Altera largura do submenu
		$(oSubMenu).find("a").each(function(index, element) {
			$(element).css({width: nWidth});
		});
			    
	}
	
	// Exibe o submenu
	$(oSubMenu).animate({"zIndex": 999}, 50, function() {
		$(this).animate({"opacity":1, "filter":"alpha(opacity=100)"}, 150);
		
		$(oSubMenu).find("a").each(function(index, element) {
			$(element).prop("disabled", false);
			$(element).css({"cursor":"pointer"});
		});
	});

}




