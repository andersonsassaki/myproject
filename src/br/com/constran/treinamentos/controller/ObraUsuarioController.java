package br.com.constran.treinamentos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.constran.treinamentos.jdbc.dao.ObraDao;
import br.com.constran.treinamentos.jdbc.dao.ObraUsuarioDao;
import br.com.constran.treinamentos.jdbc.modelo.Obra;
import br.com.constran.treinamentos.jdbc.modelo.ObraUsuario;
import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;

@Controller
public class ObraUsuarioController {
	
	// Método usado para carregar dropdown de obra usuário
	@RequestMapping(value="listaObraUsuario", method=RequestMethod.GET)
	public @ResponseBody String listaObraUsuario(@RequestParam(value="usuario", required=true) String usuario) throws SQLException {
		
		String strSelectOption = null;
		String strSelectTag = null;
			
        ObraDao obraDao = new ObraDao();

        List<Obra> obras = obraDao.getListaObraUsuario(usuario);
        
        for (Obra obra : obras) {
        	strSelectOption = strSelectOption + "<option value=" + obra.getId() + ">" + obra.getId() + " - " + obra.getNomeObra() + "</option>";
        }

        strSelectTag = "<select>" + strSelectOption + "</select>";
        
        return strSelectTag;
		
	}
	
	
	// Método usado para carregar grid de Obras
	@RequestMapping(value="listaObrasUsuarios", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaObrasUsuarios(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            											         @RequestParam(value = "rows", required = false) Integer linhasPorPagina,
            											         @RequestParam(value = "id", required = false) String id) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;
        
        ObraUsuarioDao obraUsuarioDao = new ObraUsuarioDao();

        List<ObraUsuario> obrasUsuarios = obraUsuarioDao.getListaObrasUsuarios(id);

        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) obrasUsuarios.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, obrasUsuarios.size());

        response.setRows(obrasUsuarios.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(obrasUsuarios.size()));
        
        return response;
		
	}
	
	
	@RequestMapping(value="/addObraUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addObraUsuario(@RequestParam(value="id", required=true) String id,
													   	      @RequestParam("usuario") int usuario,
													   	      @RequestParam("ccObra") int ccObra) throws SQLException {
		
		boolean success;
		
		ObraUsuarioDao obraUsuarioDao = new ObraUsuarioDao();
		ObraUsuario obraUsuario = new ObraUsuario();
		
		obraUsuario.setId(0);
		obraUsuario.setUsuario(usuario);
		obraUsuario.setCcObra(ccObra);
		
		success = obraUsuarioDao.adiciona(obraUsuario);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editObraUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editObraUsuario(@RequestParam(value="id", required=true) int id,
														       @RequestParam("usuario") int usuario,
												   	           @RequestParam("ccObra") int ccObra) throws SQLException {
		
		boolean success;
	
		ObraUsuarioDao obraUsuarioDao = new ObraUsuarioDao();
		ObraUsuario obraUsuario = new ObraUsuario();
		
		obraUsuario.setId(id);
		obraUsuario.setUsuario(usuario);
		obraUsuario.setCcObra(ccObra);
		
		success = obraUsuarioDao.altera(obraUsuario);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteObraUsuario", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteObraUsuario(@RequestParam(value="id", required=true) int id) throws SQLException {
		
		boolean success;
		
		ObraUsuarioDao obraUsuarioDao = new ObraUsuarioDao();
		ObraUsuario obraUsuario = new ObraUsuario();
		
		obraUsuario.setId(id);
				
		success = obraUsuarioDao.exclui(obraUsuario);

		return  GenericResponseJqGrid.processResponse(success);

	}
}
