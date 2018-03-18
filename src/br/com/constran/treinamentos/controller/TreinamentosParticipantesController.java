package br.com.constran.treinamentos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.constran.treinamentos.jdbc.dao.TreinamentoParticipanteDao;
import br.com.constran.treinamentos.jdbc.modelo.TreinamentoParticipante;
import br.com.constran.treinamentos.jqgrid.EntityResponseJqGrid;
import br.com.constran.treinamentos.jqgrid.GenericResponseJqGrid;

@Controller
public class TreinamentosParticipantesController {

	// Método usado para carregar grid de Obras
	@RequestMapping(value="listaTreinamentosParticipantes", method=RequestMethod.GET)
	public @ResponseBody EntityResponseJqGrid listaTreinamentosParticipantes(@RequestParam(value = "page", required = false, defaultValue = "1") Integer pagina,
            											            		 @RequestParam(value = "rows", required = false) Integer linhasPorPagina,
            											            		 @RequestParam(value = "treinamento", required = true) String treinamento) {
		
		final int startIndex;
        final int endIndex;
        Integer totalPaginas;
    
        TreinamentoParticipanteDao treinamentoParticipanteDao = new TreinamentoParticipanteDao();

        List<TreinamentoParticipante> treinamentosParticipantes = treinamentoParticipanteDao.getListaTreinamentosParticipantes(treinamento);

        EntityResponseJqGrid response = new EntityResponseJqGrid();

        totalPaginas = (int) Math.ceil((double) treinamentosParticipantes.size() / (double) linhasPorPagina);
        startIndex = (pagina - 1) * linhasPorPagina;
        endIndex = Math.min(startIndex + linhasPorPagina, treinamentosParticipantes.size());

        response.setRows(treinamentosParticipantes.subList(startIndex, endIndex));
        response.setPage(pagina.toString());
        response.setTotal(totalPaginas.toString());
        response.setRecords(String.valueOf(treinamentosParticipantes.size()));
        
        return response;
		
	}
	
	
	@RequestMapping(value="/addTreinamentoParticipante", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid addTreinamentoParticipante(@RequestParam(value="id", required=true) String id,
													   	      		      @RequestParam("treinamento") int treinamento,
													   	      		      @RequestParam("cpf") String cpf,
													   	      		      @RequestParam("matricula") String matricula,
													   	      		      @RequestParam("nome") String nome,
													   	      		      @RequestParam("descFuncao") String descFuncao) throws SQLException {
		
		boolean success;
		
		TreinamentoParticipanteDao treinamentoParticipanteDao = new TreinamentoParticipanteDao();
		TreinamentoParticipante treinamentoParticipante = new TreinamentoParticipante();
		
		treinamentoParticipante.setId(0);
		treinamentoParticipante.setTreinamento(treinamento);
		treinamentoParticipante.setCpf(cpf);
		treinamentoParticipante.setMatricula(matricula);
		treinamentoParticipante.setNome(nome);
		treinamentoParticipante.setDescFuncao(descFuncao);
		
		success = treinamentoParticipanteDao.adiciona(treinamentoParticipante);
		
		return  GenericResponseJqGrid.processResponse(success);
	}
	
	
	@RequestMapping(value="/editTreinamentoParticipante", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid editTreinamentoParticipante(@RequestParam(value="id", required=true) int id,
																		   @RequestParam("cpf") String cpf,
																   		   @RequestParam("matricula") String matricula,
																   		   @RequestParam("nome") String nome,
																   		   @RequestParam("descFuncao") String descFuncao,
																   		   @RequestParam("presencaTreinamento") String presencaTreinamento) throws SQLException {
		
		boolean success;
	
		TreinamentoParticipanteDao treinamentoParticipanteDao = new TreinamentoParticipanteDao();
		TreinamentoParticipante treinamentoParticipante = new TreinamentoParticipante();
		
		treinamentoParticipante.setId(id);
		treinamentoParticipante.setCpf(cpf);
		treinamentoParticipante.setMatricula(matricula);
		treinamentoParticipante.setNome(nome);
		treinamentoParticipante.setDescFuncao(descFuncao);
		treinamentoParticipante.setPresencaTreinamento(presencaTreinamento);
		
		success = treinamentoParticipanteDao.altera(treinamentoParticipante);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
	
	@RequestMapping(value="/deleteTreinamentoParticipante", method = RequestMethod.POST)
	public @ResponseBody GenericResponseJqGrid deleteTreinamentoParticipante(@RequestParam(value="id", required=true) int id) throws SQLException {
		
		boolean success;
		
		TreinamentoParticipanteDao treinamentoParticipanteDao = new TreinamentoParticipanteDao();
		TreinamentoParticipante treinamentoParticipante = new TreinamentoParticipante();
		
		treinamentoParticipante.setId(id);
				
		success = treinamentoParticipanteDao.exclui(treinamentoParticipante);

		return  GenericResponseJqGrid.processResponse(success);

	}
	
}
