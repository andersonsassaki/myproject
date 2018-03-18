package br.com.constran.treinamentos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.constran.treinamentos.jdbc.dao.GrupoUsuarioDao;
import br.com.constran.treinamentos.jdbc.dao.MenuGrupoUsuarioDao;
import br.com.constran.treinamentos.jdbc.modelo.GrupoUsuario;
import br.com.constran.treinamentos.jdbc.modelo.MenuGrupoUsuario;
import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;

@Controller
public class MenuGrupoUsuarioController {

	// Método usado para carregar dropdown de menu / grupo usuário
	@RequestMapping(value="listaMenuGrupoUsuario", method=RequestMethod.GET)
	public @ResponseBody String listaObraUsuario(@RequestParam(value="idMenu", required=true) int idMenu) throws SQLException {
		
		String strSelectOption = null;
		String strSelectTag = null;
			
        GrupoUsuarioDao grupoUsuarioDao = new GrupoUsuarioDao();

        List<GrupoUsuario> gruposUsuarios = grupoUsuarioDao.getListaMenuGrupoUsuario(idMenu);
        
        for (GrupoUsuario grupoUsuario : gruposUsuarios) {
        	strSelectOption = strSelectOption + "<option value=" + grupoUsuario.getId() + ">" + grupoUsuario.getId() + " - " + grupoUsuario.getNomeGrupo() + "</option>";
        }

        strSelectTag = "<select>" + strSelectOption + "</select>";
        
        return strSelectTag;
		
	}
	
	
	// Método usado para carregar grid de menu / grupo usuário
	@RequestMapping(value="listaMenusGruposUsuarios", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaMenusGruposUsuarios(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            											         	   @RequestParam(value = "rows", required = false) Integer linhasPorPagina,
            											               @RequestParam(value = "id", required = false) String id) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;
               
        MenuGrupoUsuarioDao menuGrupoUsuarioDao = new MenuGrupoUsuarioDao();

        List<MenuGrupoUsuario> menusGruposUsuarios = menuGrupoUsuarioDao.getListaMenusGruposUsuarios(id);
        
        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) menusGruposUsuarios.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, menusGruposUsuarios.size());

        response.setRows(menusGruposUsuarios.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(menusGruposUsuarios.size()));
        
        return response;
		
	}
	
	
	@RequestMapping(value="/addMenuGrupoUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addMenuGrupoUsuario(@RequestParam(value="id", required=true) String id,
													   	           @RequestParam("idMenu") int idMenu,
													   	           @RequestParam("grupoUsuario") String grupoUsuario) throws SQLException {
		
		boolean success;
		
		MenuGrupoUsuarioDao menuGrupoUsuarioDao = new MenuGrupoUsuarioDao();
		MenuGrupoUsuario menuGrupoUsuario = new MenuGrupoUsuario();
		
		menuGrupoUsuario.setId(0);
		menuGrupoUsuario.setIdMenu(idMenu);
		menuGrupoUsuario.setGrupoUsuario(grupoUsuario);
		
		success = menuGrupoUsuarioDao.adiciona(menuGrupoUsuario);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editMenuGrupoUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editMenuGrupoUsuario(@RequestParam(value="id", required=true) int id,
																	@RequestParam("idMenu") int idMenu,
															        @RequestParam("grupoUsuario") String grupoUsuario) throws SQLException {
		
		boolean success;
	
		MenuGrupoUsuarioDao menuGrupoUsuarioDao = new MenuGrupoUsuarioDao();
		MenuGrupoUsuario menuGrupoUsuario = new MenuGrupoUsuario();
		
		menuGrupoUsuario.setId(id);
		menuGrupoUsuario.setIdMenu(idMenu);
		menuGrupoUsuario.setGrupoUsuario(grupoUsuario);
		
		success = menuGrupoUsuarioDao.altera(menuGrupoUsuario);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteMenuGrupoUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteMenuGrupoUsuario(@RequestParam(value="id", required=true) int id) throws SQLException {
		
		boolean success;
		
		MenuGrupoUsuarioDao menuGrupoUsuarioDao = new MenuGrupoUsuarioDao();
		MenuGrupoUsuario menuGrupoUsuario = new MenuGrupoUsuario();
		
		menuGrupoUsuario.setId(id);
				
		success = menuGrupoUsuarioDao.exclui(menuGrupoUsuario);

		return  GenericResponseJqGrid.processResponse(success);

	}	
	
}
