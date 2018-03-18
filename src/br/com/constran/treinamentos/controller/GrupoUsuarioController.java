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

import br.com.constran.treinamentos.jdbc.dao.GrupoUsuarioDao;
import br.com.constran.treinamentos.jdbc.dao.MenuDao;
import br.com.constran.treinamentos.jdbc.modelo.GrupoUsuario;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
import br.com.constran.treinamentos.jdbc.modelo.Menu;
import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;

@Controller
public class GrupoUsuarioController {

	@RequestMapping(value="/grupoUsuario")
	public ModelAndView loadPageGrupoUsuario (HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpSession session) {
		
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
				
		return new ModelAndView ("grupoUsuario", "menus", menus);
	}
	
	
	// Método usado para carregar dropdown
	@RequestMapping(value="listaGrupoUsuario", method=RequestMethod.GET)
	public @ResponseBody String listaGrupoUsuario(HttpSession session) throws SQLException {
		
		String strSelectOption = null;
		String strSelectTag = null;
		
        GrupoUsuarioDao grupoUsuarioDao = new GrupoUsuarioDao();

        List<GrupoUsuario> gruposUsuarios = grupoUsuarioDao.getListaGrupoUsuario(session);
        
        for (GrupoUsuario grupoUsuario : gruposUsuarios) {
        	strSelectOption = strSelectOption + "<option value=" + grupoUsuario.getId() + ">" + grupoUsuario.getId() + " (" + grupoUsuario.getNomeGrupo() + ")" + "</option>";
        }

        strSelectTag = "<select>" + strSelectOption + "</select>";
        
        return strSelectTag;
		
	}
	
	
	// Método usado para carregar grid de Obras
	@RequestMapping(value="listaGruposUsuarios", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaGruposUsuarios(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            												      @RequestParam(value = "rows", required = false) Integer linhasPorPagina) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;

        GrupoUsuarioDao grupoUsuarioDao = new GrupoUsuarioDao();

        List<GrupoUsuario> gruposUsuarios = grupoUsuarioDao.getListaGruposUsuarios();

        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) gruposUsuarios.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, gruposUsuarios.size());

        response.setRows(gruposUsuarios.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(gruposUsuarios.size()));
        
        return response;
		
	}
	
	
	@RequestMapping(value="/addGrupoUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addGrupoUsuario(@RequestParam(value="id", required=true) String id,
														       @RequestParam("nomeGrupo") String nomeGrupo) throws SQLException {
		
		boolean success;
		
		GrupoUsuarioDao grupoUsuarioDao = new GrupoUsuarioDao();
		GrupoUsuario grupoUsuario = new GrupoUsuario();
		
		grupoUsuario.setId(id);
		grupoUsuario.setNomeGrupo(nomeGrupo);
		
		success = grupoUsuarioDao.adiciona(grupoUsuario);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editGrupoUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editGrupoUsuario(@RequestParam(value="id", required=true) String id,
															    @RequestParam("nomeGrupo") String nomeGrupo) throws SQLException {
		
		boolean success;
		
		GrupoUsuarioDao grupoUsuarioDao = new GrupoUsuarioDao();
		GrupoUsuario grupoUsuario = new GrupoUsuario();
		
		grupoUsuario.setId(id);
		grupoUsuario.setNomeGrupo(nomeGrupo);
		
		success = grupoUsuarioDao.altera(grupoUsuario);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteGrupoUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteGrupoUsuario(@RequestParam(value="id", required=true) String id) throws SQLException {
		
		boolean success;
		
		GrupoUsuarioDao grupoUsuarioDao = new GrupoUsuarioDao();
		GrupoUsuario grupoUsuario = new GrupoUsuario();
		
		grupoUsuario.setId(id);
				
		success = grupoUsuarioDao.exclui(grupoUsuario);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
}
