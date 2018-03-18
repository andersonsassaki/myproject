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
import br.com.constran.treinamentos.jdbc.dao.UsuarioDao;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
import br.com.constran.treinamentos.jdbc.modelo.Menu;
import br.com.constran.treinamentos.jdbc.modelo.Usuario;
import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;

@Controller
public class UsuarioController {

	@RequestMapping(value="/usuario")
	public ModelAndView loadPageUsuario (HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpSession session) {
		
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
				
		return new ModelAndView ("usuario", "menus", menus);
	}
	
	
	// ***** TODO *****
	/* 
	// Método usado para carregar dropdown
	@RequestMapping(value="listaUsuario", method=RequestMethod.GET)
	public @ResponseBody String listaUsuario(HttpSession session) throws SQLException {
		
		String strSelectOption = null;
		String strSelectTag = null;
		
        ObraDao obraDao = new ObraDao();

        List<Obra> obras = obraDao.getListaObra(session);
        
        for (Obra obra : obras) {
        	strSelectOption = strSelectOption + "<option value=" + obra.getId() + ">" + obra.getId() + "-" + obra.getNomeObra() + "</option>";
        }

        strSelectTag = "<select>" + strSelectOption + "</select>";
        
        return strSelectTag;
		
	}
	*/
	
	
	// Método usado para carregar grid de usuários
	@RequestMapping(value="listaUsuarios", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaUsuarios(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            												@RequestParam(value = "rows", required = false) Integer linhasPorPagina) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;

        UsuarioDao usuarioDao = new UsuarioDao();

        List<Usuario> usuarios = usuarioDao.getListaUsuarios();

        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) usuarios.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, usuarios.size());

        response.setRows(usuarios.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(usuarios.size()));
        
        return response;
		
	}
	
		
	@RequestMapping(value="/addUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addUsuario(@RequestParam(value="id", required=true) String id,
														  @RequestParam("login") String login,
														  @RequestParam("nome") String nome,
														  @RequestParam("senha") String senha,
														  @RequestParam("grupoUsuario") String grupoUsuario) throws SQLException {
		
		boolean success;
		
		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = new Usuario();
		
		usuario.setId(0);
		usuario.setLogin(login);
		usuario.setNome(nome);
		usuario.setSenha(senha);
		usuario.setGrupoUsuario(grupoUsuario);
		
		success = usuarioDao.adiciona(usuario);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editUsuario(@RequestParam(value="id", required=true) int id,
														   @RequestParam("login") String login,
														   @RequestParam("nome") String nome,
														   @RequestParam("grupoUsuario") String grupoUsuario) throws SQLException {
		
		boolean success;
		
		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = new Usuario();
		
		usuario.setId(id);
		usuario.setLogin(login);
		usuario.setNome(nome);
		usuario.setGrupoUsuario(grupoUsuario);
		
		success = usuarioDao.altera(usuario);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteUsuario(@RequestParam(value="id", required=true) int id) throws SQLException {
		
		boolean success;
		
		UsuarioDao usuarioDao = new UsuarioDao();
		Usuario usuario = new Usuario();
		
		usuario.setId(id);
				
		success = usuarioDao.exclui(usuario);

		return  GenericResponseJqGrid.processResponse(success);

	} 
		
}
