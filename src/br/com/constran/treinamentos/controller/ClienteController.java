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

import br.com.constran.treinamentos.jdbc.dao.ClienteDao;
import br.com.constran.treinamentos.jdbc.dao.MenuDao;
import br.com.constran.treinamentos.jdbc.modelo.Cliente;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
import br.com.constran.treinamentos.jdbc.modelo.Menu;
import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;

@Controller
public class ClienteController {
	@RequestMapping(value="/cliente")
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
				
		return new ModelAndView ("cliente", "menus", menus);
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
	@RequestMapping(value="listaClientes", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaClientes(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            												@RequestParam(value = "rows", required = false) Integer linhasPorPagina) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;

        ClienteDao clienteDao = new ClienteDao();

        List<Cliente> clientes = clienteDao.getListaClientes();

        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) clientes.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, clientes.size());

        response.setRows(clientes.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(clientes.size()));
        
        return response;
		
	}
	
	
	
	@RequestMapping(value="/addCliente", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addCliente(@RequestParam(value="id", required=true) String id,
														  @RequestParam("nomeCliente") String nomeCliente,
														  @RequestParam("cnpj") String cnpj) throws SQLException {
		
		boolean success;
		
		ClienteDao clienteDao = new ClienteDao();
		Cliente cliente = new Cliente();
		
		cliente.setId(0);
		cliente.setNomeCliente(nomeCliente);
		cliente.setCnpj(cnpj);
		
		success = clienteDao.adiciona(cliente);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editCliente", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editCliente(@RequestParam(value="id", required=true) int id,
														   @RequestParam("nomeCliente") String nomeCliente,
														   @RequestParam("cnpj") String cnpj) throws SQLException {
		
		boolean success;
		
		ClienteDao clienteDao = new ClienteDao();
		Cliente cliente = new Cliente();
		
		cliente.setId(id);
		cliente.setNomeCliente(nomeCliente);
		cliente.setCnpj(cnpj);
		
		success = clienteDao.altera(cliente);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteCliente", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteCliente(@RequestParam(value="id", required=true) int id) throws SQLException {
		
		boolean success;
		
		ClienteDao clienteDao = new ClienteDao();
		Cliente cliente = new Cliente();
		
		cliente.setId(id);
				
		success = clienteDao.exclui(cliente);

		return  GenericResponseJqGrid.processResponse(success);

	} 
}
