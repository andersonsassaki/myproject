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

import br.com.constran.treinamentos.jdbc.dao.InstrutorDao;
import br.com.constran.treinamentos.jdbc.dao.MenuDao;
import br.com.constran.treinamentos.jdbc.modelo.Funcao;
import br.com.constran.treinamentos.jdbc.modelo.Instrutor;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
import br.com.constran.treinamentos.jdbc.modelo.Menu;
import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;

@Controller
public class InstrutorController {
	@RequestMapping(value="/instrutor")
	public ModelAndView loadPageCliente (HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpSession session) {
		
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
				
		return new ModelAndView ("instrutor", "menus", menus);
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
	
	
	// Método usado para carregar grid de Obras
	@RequestMapping(value="listaInstrutores", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaInstrutores(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            												   @RequestParam(value = "rows", required = false) Integer linhasPorPagina) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;

        InstrutorDao instrutorDao = new InstrutorDao();

        List<Instrutor> instrutores = instrutorDao.getListaInstrutores();

        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) instrutores.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, instrutores.size());

        response.setRows(instrutores.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(instrutores.size()));
        
        return response;
		
	}
	
	
	
	@RequestMapping(value="/addInstrutor", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addInstrutor(@RequestParam(value="id", required=true) String id,
														    @RequestParam("nomeInstrutor") String nomeInstrutor,
														    @RequestParam("ativo") String ativo,
														    @RequestParam("funcao") int funcao) throws SQLException {
		
		boolean success;
		
		InstrutorDao instrutorDao = new InstrutorDao();
		Instrutor instrutor = new Instrutor();
		
		instrutor.setId(0);
		instrutor.setNomeInstrutor(nomeInstrutor);
		instrutor.setAtivo(ativo);
		instrutor.setFuncao(funcao);
		
		success = instrutorDao.adiciona(instrutor);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editInstrutor", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editInstrutor(@RequestParam(value="id", required=true) int id,
														     @RequestParam("nomeInstrutor") String nomeInstrutor,
														     @RequestParam("ativo") String ativo,
														     @RequestParam("funcao") int funcao) throws SQLException {
		
		boolean success;

		InstrutorDao instrutorDao = new InstrutorDao();
		Instrutor instrutor = new Instrutor();
		
		instrutor.setId(id);
		instrutor.setNomeInstrutor(nomeInstrutor);
		instrutor.setAtivo(ativo);
		instrutor.setFuncao(funcao);
		
		success = instrutorDao.altera(instrutor);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteInstrutor", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteInstrutor(@RequestParam(value="id", required=true) int id) throws SQLException {
		
		boolean success;
		
		InstrutorDao instrutorDao = new InstrutorDao();
		Instrutor instrutor = new Instrutor();
		
		instrutor.setId(id);
				
		success = instrutorDao.exclui(instrutor);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	// Método usado para carregar autocomplete de funções na página de instrutores
	@RequestMapping(value="listaFuncao", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Funcao> listaFuncao(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
		
		String searchFuncao;
		
		searchFuncao = request.getParameter("searchFuncao");
		
		InstrutorDao instrutorDao = new InstrutorDao();
		
		List<Funcao> funcoes = instrutorDao.getListaFuncao(searchFuncao);
		
		return funcoes;
		
	}	
}
