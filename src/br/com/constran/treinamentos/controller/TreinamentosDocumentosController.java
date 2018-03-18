package br.com.constran.treinamentos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.constran.treinamentos.jdbc.dao.TreinamentoDocumentoDao;
import br.com.constran.treinamentos.jdbc.modelo.TreinamentoDocumento;
import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;

@Controller
public class TreinamentosDocumentosController {
	// Método usado para carregar grid de Obras
	@RequestMapping(value="listaTreinamentosDocs", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaTreinamentosDocs(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            											            @RequestParam(value = "rows", required = false) Integer linhasPorPagina,
            											            @RequestParam(value = "treinamento", required = true) String treinamento) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;
    
        TreinamentoDocumentoDao treinamentoDocumentoDao = new TreinamentoDocumentoDao();

        List<TreinamentoDocumento> treinamentosDocumentos = treinamentoDocumentoDao.getListaTreinamentosDocs(treinamento);

        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) treinamentosDocumentos.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, treinamentosDocumentos.size());

        response.setRows(treinamentosDocumentos.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(treinamentosDocumentos.size()));
        
        return response;
		
	}
	
	
	@RequestMapping(value="/addTreinamentoDocumento", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addTreinamentoDocumento(@RequestParam(value="id", required=true) String id,
													   	      		   @RequestParam("treinamento") int treinamento,
													   	      		   @RequestParam("numeroDocumento") String numeroDocumento,
													   	      		   @RequestParam("revisaoDocumento") String revisaoDocumento,
													   	      		   @RequestParam("descDocumento") String descDocumento) throws SQLException {
		
		boolean success;
		
		TreinamentoDocumentoDao treinamentoDocumentoDao = new TreinamentoDocumentoDao();
		TreinamentoDocumento treinamentoDocumento = new TreinamentoDocumento();
		
		treinamentoDocumento.setId(0);
		treinamentoDocumento.setTreinamento(treinamento);
		treinamentoDocumento.setNumeroDocumento(numeroDocumento);
		treinamentoDocumento.setRevisaoDocumento(revisaoDocumento);
		treinamentoDocumento.setDescDocumento(descDocumento);
		
		success = treinamentoDocumentoDao.adiciona(treinamentoDocumento);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editTreinamentoDocumento", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editTreinamentoDocumento(@RequestParam(value="id", required=true) int id,
														      		    @RequestParam("numeroDocumento") String numeroDocumento,
														      		    @RequestParam("revisaoDocumento") String revisaoDocumento,
														      		    @RequestParam("descDocumento") String descDocumento) throws SQLException {
		
		boolean success;
	
		TreinamentoDocumentoDao treinamentoDocumentoDao = new TreinamentoDocumentoDao();
		TreinamentoDocumento treinamentoDocumento = new TreinamentoDocumento();
		
		treinamentoDocumento.setId(id);
		treinamentoDocumento.setNumeroDocumento(numeroDocumento);
		treinamentoDocumento.setRevisaoDocumento(revisaoDocumento);
		treinamentoDocumento.setDescDocumento(descDocumento);
		
		success = treinamentoDocumentoDao.altera(treinamentoDocumento);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteTreinamentoDocumento", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteTreinamentoDocumento(@RequestParam(value="id", required=true) int id) throws SQLException {
		
		boolean success;
		
		TreinamentoDocumentoDao treinamentoDocumentoDao = new TreinamentoDocumentoDao();
		TreinamentoDocumento treinamentoDocumento = new TreinamentoDocumento();
		
		treinamentoDocumento.setId(id);
				
		success = treinamentoDocumentoDao.exclui(treinamentoDocumento);

		return  GenericResponseJqGrid.processResponse(success);

	}
}
