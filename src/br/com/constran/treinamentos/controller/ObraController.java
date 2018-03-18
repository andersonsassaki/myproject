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
import br.com.constran.treinamentos.jdbc.dao.ObraDao;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
import br.com.constran.treinamentos.jdbc.modelo.Menu;
import br.com.constran.treinamentos.jdbc.modelo.Obra;
import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;

@Controller
public class ObraController {
	
	@RequestMapping(value="/obra")
	public ModelAndView loadPageObra (HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpSession session) {
		
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
				
		return new ModelAndView ("obra", "menus", menus);
	}
	
	
	// Método usado para carregar dropdown
	@RequestMapping(value="listaObra", method=RequestMethod.GET)
	public @ResponseBody String listaObra(HttpSession session) throws SQLException {
		
		String strSelectOption = null;
		String strSelectTag = null;
		
        ObraDao obraDao = new ObraDao();

        List<Obra> obras = obraDao.getListaObra(session);
        
        for (Obra obra : obras) {
        	strSelectOption = strSelectOption + "<option value=" + obra.getId() + ">" + obra.getId() + " - " + obra.getNomeObra() + "</option>";
        }

        strSelectTag = "<select>" + strSelectOption + "</select>";
        
        return strSelectTag;
		
	}
	
	
	// Método usado para carregar grid de Obras
	@RequestMapping(value="listaObras", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaObras(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            											 @RequestParam(value = "rows", required = false) Integer linhasPorPagina) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;

        ObraDao obraDao = new ObraDao();

        List<Obra> obras = obraDao.getListaObras();

        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) obras.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, obras.size());

        response.setRows(obras.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(obras.size()));
        
        return response;
		
	}
	
	
	@RequestMapping(value="/addObra", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addObra(@RequestParam(value="id", required=true) int id,
													   @RequestParam("nomeObra") String nomeObra) throws SQLException {
		
		boolean success;
		
		ObraDao obraDao = new ObraDao();
		Obra obra = new Obra();
		
		obra.setId(id);
		obra.setNomeObra(nomeObra);
		
		success = obraDao.adiciona(obra);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editObra", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editObra(@RequestParam(value="id", required=true) int id,
														@RequestParam("nomeObra") String nomeObra) throws SQLException {
		
		boolean success;
		
		ObraDao obraDao = new ObraDao();
		Obra obra = new Obra();
		
		obra.setId(id);
		obra.setNomeObra(nomeObra);
		
		success = obraDao.altera(obra);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteObra", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteObra(@RequestParam(value="id", required=true) int id) throws SQLException {
		
		boolean success;
		
		ObraDao obraDao = new ObraDao();
		Obra obra = new Obra();
		
		obra.setId(id);
				
		success = obraDao.exclui(obra);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
}
