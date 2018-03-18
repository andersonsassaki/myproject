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

import br.com.constran.treinamentos.jdbc.dao.ContratoDao;
import br.com.constran.treinamentos.jdbc.dao.MenuDao;
import br.com.constran.treinamentos.jdbc.modelo.Contrato;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
import br.com.constran.treinamentos.jdbc.modelo.Menu;

import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;

@Controller
public class ContratoController {
	
	@RequestMapping(value="/contrato")
	public ModelAndView loadPageContrato (HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpSession session) {
		
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
				
		return new ModelAndView ("contrato", "menus", menus);
	}

	
	@RequestMapping(value="listaContrato", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaContrato(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            												@RequestParam(value = "rows", required = false) Integer linhasPorPagina,
            												HttpSession session) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;
              

        ContratoDao contratoDao = new ContratoDao();

        List<Contrato> contratos = contratoDao.getListaContrato(session);

        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) contratos.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, contratos.size());

        response.setRows(contratos.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(contratos.size()));
        
        return response;
		
	}
	
	
	@RequestMapping(value="/addContrato", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addContrato(@RequestParam(value="id", required=true) String id,
														   @RequestParam("descricao") String descricao,
														   @RequestParam("ccObra") String ccObra) throws SQLException {
		
		boolean success;
		
		ContratoDao contratoDao = new ContratoDao();
		Contrato contrato = new Contrato();
		
		contrato.setId(0);
		contrato.setDescricao(descricao);
		contrato.setCcObra(Integer.parseInt(ccObra));
		
		success = contratoDao.adiciona(contrato);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editContrato", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editContrato(@RequestParam(value="id", required=true) String id,
															@RequestParam("descricao") String descricao,
															@RequestParam("ccObra") String ccObra) throws SQLException {
		
		boolean success;
		
		ContratoDao contratoDao = new ContratoDao();
		Contrato contrato = new Contrato();
		
		contrato.setId(Integer.parseInt(id));
		contrato.setDescricao(descricao);
		contrato.setCcObra(Integer.parseInt(ccObra));
		
		success = contratoDao.altera(contrato);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteContrato", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteContrato(@RequestParam(value="id", required=true) String id) throws SQLException {
		
		boolean success;
		
		ContratoDao contratoDao = new ContratoDao();
		Contrato contrato = new Contrato();
		
		contrato.setId(Integer.parseInt(id));
		
		success = contratoDao.exclui(contrato);

		return  GenericResponseJqGrid.processResponse(success);

	}

}
