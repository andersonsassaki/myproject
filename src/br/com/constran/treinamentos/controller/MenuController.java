package br.com.constran.treinamentos.controller;


import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.constran.treinamentos.jdbc.dao.MenuDao;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
import br.com.constran.treinamentos.jdbc.modelo.Menu;
import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;


@Controller
public class MenuController {
	
	// Método para montar o menu	
	@RequestMapping(value="/pesquisa")
	public ModelAndView listaMenu (HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpSession session) {
		
		/* Valida se sessão está ativa */
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario"); 
		String strNome;
		
		if (loginUsuario == null) {

			return new ModelAndView ("index");
		} else {
		
			strNome = loginUsuario.getNome();
			
			if(strNome.equalsIgnoreCase("")) {
				return new ModelAndView ("index");
			}
		}
		
		
		MenuDao menuDao = new MenuDao();	
		List<Menu> menus = menuDao.getListaMenu(session);
			
		Map<String, Object> params = new LinkedHashMap<String, Object>();  
		params.put("menus", "menus");  
		params.put("opcaoMenu", session.getAttribute("menuAtivo"));  
		
		return new ModelAndView ("pesquisa", "menus", menus);
	}
	
	
	@RequestMapping(value="/menu")
	public ModelAndView loadPageMenu (HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpSession session) {
		
		/* Valida se sessão está ativa */
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario"); 
		String strNome;
		
		if (loginUsuario == null) {

			return new ModelAndView ("index");
		} else {
		
			strNome = loginUsuario.getNome();
			
			if(strNome.equalsIgnoreCase("")) {
				return new ModelAndView ("index");
			}
		}
				
		MenuDao menuDao = new MenuDao();	
		List<Menu> menus = menuDao.getListaMenu(session);
			
		Map<String, Object> params = new LinkedHashMap<String, Object>();  
		params.put("menus", "menus");  
		params.put("opcaoMenu", session.getAttribute("menuAtivo"));  
				
		return new ModelAndView ("menu", "menus", menus);
	}
	
	 
	// Método usado para carregar dropdown
	@RequestMapping(value="listaDropDownMenu", method=RequestMethod.GET)
	public @ResponseBody String listaDropDownMenu(HttpSession session) throws SQLException {
		
		String strSelectOption = null;
		String strSelectTag = null;
		
        MenuDao menuDao = new MenuDao();

        List<Menu> menus = menuDao.getListaDropDownMenu(session);
        
        for (Menu menu : menus) {
        	strSelectOption = strSelectOption + "<option value=" + menu.getId() + ">" + menu.getId() + "-" + menu.getTituloMenu() + "</option>";
        }

        strSelectTag = "<select>" + strSelectOption + "</select>";
        
        return strSelectTag;
		
	}
		
	
	// Método usado para carregar grid de menus
	@RequestMapping(value="listaGridMenu", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaGridMenu(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            												@RequestParam(value = "rows", required = false) Integer linhasPorPagina) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;

        MenuDao menuDao = new MenuDao();

        List<Menu> menus = menuDao.getListaGridMenu();

        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) menus.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, menus.size());

        response.setRows(menus.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(menus.size()));
        
        return response;
		
	}
	
	
	@RequestMapping(value="/addMenu", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addMenu(@RequestParam(value="id", required=true) String id,
													   @RequestParam("tituloMenu") String tituloMenu,
													   @RequestParam("descricao") String descricao,
													   @RequestParam("pagina") String pagina,
													   @RequestParam("idMenuPai") int idMenuPai,
													   @RequestParam("ordem") int ordem) throws SQLException {
		
		boolean success;
		
		MenuDao menuDao = new MenuDao();
		Menu menu = new Menu();
		
		menu.setId(0);
		menu.setTituloMenu(tituloMenu);
		menu.setDescricao(descricao);
		menu.setPagina(pagina);
		menu.setIdMenuPai(idMenuPai);
		menu.setOrdem(ordem);
		
		success = menuDao.adiciona(menu);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editMenu", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editMenu(@RequestParam(value="id", required=true) int id,
														@RequestParam("tituloMenu") String tituloMenu,
														@RequestParam("descricao") String descricao,
														@RequestParam("pagina") String pagina,
														@RequestParam("idMenuPai") int idMenuPai,
														@RequestParam("ordem") int ordem) throws SQLException {
		
		boolean success;
		
		MenuDao menuDao = new MenuDao();
		Menu menu = new Menu();
		
		menu.setId(id);
		menu.setTituloMenu(tituloMenu);
		menu.setDescricao(descricao);
		menu.setPagina(pagina);
		menu.setIdMenuPai(idMenuPai);
		menu.setOrdem(ordem);
		
		success = menuDao.altera(menu);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteMenu", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteMenu(@RequestParam(value="id", required=true) int id) throws SQLException {
		
		boolean success;
		
		MenuDao menuDao = new MenuDao();
		Menu menu = new Menu();
		
		menu.setId(id);
				
		success = menuDao.exclui(menu);

		return  GenericResponseJqGrid.processResponse(success);

	} 

}
