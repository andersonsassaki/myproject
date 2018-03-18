
package br.com.constran.treinamentos.controller;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

import br.com.constran.treinamentos.jdbc.dao.LinkedServerDao;
import br.com.constran.treinamentos.jdbc.dao.MenuDao;
import br.com.constran.treinamentos.jdbc.dao.TreinamentoDao;
import br.com.constran.treinamentos.jdbc.modelo.Cliente;
import br.com.constran.treinamentos.jdbc.modelo.Contrato;
import br.com.constran.treinamentos.jdbc.modelo.Funcionario;
import br.com.constran.treinamentos.jdbc.modelo.Instrutor;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
import br.com.constran.treinamentos.jdbc.modelo.Menu;
import br.com.constran.treinamentos.jdbc.modelo.Obra;
import br.com.constran.treinamentos.jdbc.modelo.Treinamento;


@Controller
public class TreinamentosController {
	 
	@RequestMapping(value="/treinamento")
	public ModelAndView loadPageTreinamento (HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpSession session) {
	
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
				
		return new ModelAndView ("treinamento", "menus", menus);
	}
	
	
	// Método usado para carregar dropdown de obras na página de treinamentos
	@RequestMapping(value="listaTreinamentoObras", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Obra> listaTreinamentoObras(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, ClassNotFoundException {

		int idUsuario;
		String grupoUsuario;
		
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario"); 
		
		idUsuario = loginUsuario.getId();
		grupoUsuario = loginUsuario.getGrupoUsuario();
		
		TreinamentoDao treinamentoDao = new TreinamentoDao();
		
		List<Obra> obras = treinamentoDao.getListaTreinamentoObras(idUsuario, grupoUsuario);
		
		return obras;
		
	}
	
	
	// Método usado para carregar dropdown de contratos na página de treinamentos
	@RequestMapping(value="listaTreinamentoContratos", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Contrato> listaTreinamentoContratos(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
		
		String ccObra;
		
		ccObra = request.getParameter("ccObra");
		
		TreinamentoDao treinamentoDao = new TreinamentoDao();
		
		List<Contrato> contratos = treinamentoDao.getListaTreinamentoContratos(ccObra);
		
		return contratos;
		
	}
	 
	
	// Método usado para carregar dropdown de clientes na página de treinamentos
	@RequestMapping(value="listaTreinamentoClientes", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Cliente> listaTreinamentoClientes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
	
		TreinamentoDao treinamentoDao = new TreinamentoDao();
		
		List<Cliente> clientes = treinamentoDao.getListaTreinamentoClientes();
		
		return clientes;
		
	}
	
	
	// Método usado para carregar autocomplete de instrutores na página de treinamentos
	@RequestMapping(value="listaTreinamentoInstrutores", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Instrutor> listaTreinamentoInstrutores(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
		
		String searchInstrutor;
		
		searchInstrutor = request.getParameter("searchInstrutor");
		
		TreinamentoDao treinamentoDao = new TreinamentoDao();
		
		List<Instrutor> instrutores = treinamentoDao.getListaTreinamentoInstrutores(searchInstrutor);
		
		return instrutores;
		
	}
			
	
	// Método usado para carregar autocomplete de treinamentos na página de treinamentos
	@RequestMapping(value="searchListaTreinamentos", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Treinamento> searchListaTreinamentos(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
		
		String searchParam;
		int idUsuario;
		String grupoUsuario;
		
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario"); 
		
		idUsuario = loginUsuario.getId();
		grupoUsuario = loginUsuario.getGrupoUsuario();
		
		searchParam = request.getParameter("searchTreinamento");
		
		String searchTreinamento = java.net.URLDecoder.decode(searchParam, "UTF-8");
		
		TreinamentoDao treinamentoDao = new TreinamentoDao();
		
		List<Treinamento> treinamentos = treinamentoDao.getSearchListaTreinamentos(searchTreinamento, idUsuario, grupoUsuario);
		
		return treinamentos;
		
	}
	
	
	// Método usado para carregar dados de treinamentos ao selecioar uma opção do search
	@RequestMapping(value="listaTreinamento", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Treinamento> listaTreinamento(HttpServletRequest request, HttpServletResponse response,
															@RequestParam("idTreinamento") String idTreinamento) throws SQLException, ClassNotFoundException, ParseException {
			
        TreinamentoDao treinamentoDao = new TreinamentoDao();
       
        List<Treinamento> treinamentos = treinamentoDao.getListaTreinamentos(idTreinamento);
        
        return treinamentos;
	}
	
	
	// Método usado para carregar autocomplete de funcionarios na página de treinamentos
	@RequestMapping(value="listaTreinamentoParticipante", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Funcionario> listaTreinamentoFuncionarios(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {
		
		String searchFuncionario;
		
		searchFuncionario = request.getParameter("searchFuncionario");
		
		LinkedServerDao linkedServerDao = new LinkedServerDao();
		
		List<Funcionario> funcionarios = linkedServerDao.getListaFuncionarios(searchFuncionario);
		
		return funcionarios;
		
	}		
	

	// Método usado para incluir treinamento
	@RequestMapping(value="addTreinamento", method=RequestMethod.GET) //, produces = "application/json")
	public @ResponseBody int addTreinamento(HttpServletRequest request, HttpServletResponse response,
											@RequestParam("idTreinamento") int idTreinamento,
											@RequestParam("obra") int obra,
											@RequestParam("numeroRegistro") String numeroRegistro,
											@RequestParam("data") String data,
											@RequestParam("horaInicio") String horaInicio,
											@RequestParam("horaTermino") String horaTermino,
											@RequestParam("dataAvaliacaoEficacia") String dataAvaliacaoEficacia,
											@RequestParam("localTrecho") String localTrecho,
											@RequestParam("contrato") int contrato,
											@RequestParam("cliente") int cliente,
											//@RequestParam("hhTreinamento") String hhTreinamento,
											@RequestParam("recursosUtilizados") String recursosUtilizados,
											@RequestParam("metodoAvaliacaoCampo") String metodoAvaliacaoCampo,
											@RequestParam("metodoAvaliacaoCertificado") String metodoAvaliacaoCertificado,
											@RequestParam("metodoAvaliacaoTeste") String metodoAvaliacaoTeste,
											@RequestParam("instrutorNome") String instrutorNome,
											@RequestParam("instrutorFuncao") String instrutorFuncao,
											@RequestParam("descSetorTreinamento") String descSetorTreinamento,
											HttpSession session
											) throws SQLException, ClassNotFoundException, ParseException {
		
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario");
		
		TreinamentoDao treinamentoDao = new TreinamentoDao();
		Treinamento treinamento = new Treinamento();
		
		treinamento.setIdTreinamento(idTreinamento);
		treinamento.setUsuario(loginUsuario.getId());
		treinamento.setObra(obra);
		treinamento.setNumeroRegistro(numeroRegistro);
		
		Calendar dataCalendar = null;
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		dataCalendar = Calendar.getInstance();
		dataCalendar.setTime(date);	
				
		treinamento.setData(dataCalendar);
		treinamento.setHoraInicio(horaInicio);
		treinamento.setHoraTermino(horaTermino);
		
		date = new SimpleDateFormat("dd/MM/yyyy").parse(dataAvaliacaoEficacia);
		dataCalendar = Calendar.getInstance();
		dataCalendar.setTime(date);	
		
		treinamento.setDataAvaliacaoEficacia(dataCalendar);
		treinamento.setLocalTrecho(localTrecho);
		treinamento.setContrato(contrato);
		treinamento.setCliente(cliente);
		//treinamento.setHhTreinamento(hhTreinamento);
		treinamento.setRecursosUtilizados(recursosUtilizados);
		treinamento.setMetodoAvaliacaoCampo(metodoAvaliacaoCampo);
		treinamento.setMetodoAvaliacaoCertificado(metodoAvaliacaoCertificado);
		treinamento.setMetodoAvaliacaoTeste(metodoAvaliacaoTeste);
		treinamento.setInstrutorNome(instrutorNome);
		treinamento.setInstrutorFuncao(instrutorFuncao);
		treinamento.setDescSetorTreinamento(descSetorTreinamento);
		
		int iRetorno = treinamentoDao.adiciona(treinamento);
	
		return iRetorno;
		
	}

	
	// Método usado para alterar treinamento
	@RequestMapping(value="editTreinamento", method=RequestMethod.GET) //, produces = "application/json")
	public @ResponseBody int editTreinamento(@RequestParam("idTreinamento") int idTreinamento,
											 @RequestParam("obra") int obra,
											 @RequestParam("numeroRegistro") String numeroRegistro,
											 @RequestParam("data") String data,
											 @RequestParam("horaInicio") String horaInicio,
											 @RequestParam("horaTermino") String horaTermino,
											 @RequestParam("dataAvaliacaoEficacia") String dataAvaliacaoEficacia,
											 @RequestParam("localTrecho") String localTrecho,
											 @RequestParam("contrato") int contrato,
											 @RequestParam("cliente") int cliente,
											 //@RequestParam("hhTreinamento") String hhTreinamento,
											 @RequestParam("recursosUtilizados") String recursosUtilizados,
											 @RequestParam("metodoAvaliacaoCampo") String metodoAvaliacaoCampo,
											 @RequestParam("metodoAvaliacaoCertificado") String metodoAvaliacaoCertificado,
											 @RequestParam("metodoAvaliacaoTeste") String metodoAvaliacaoTeste,
											 @RequestParam("instrutorNome") String instrutorNome,
											 @RequestParam("instrutorFuncao") String instrutorFuncao,
											 @RequestParam("descSetorTreinamento") String descSetorTreinamento,
											 HttpSession session
											 ) throws SQLException, ClassNotFoundException, ParseException, UnsupportedEncodingException {
		
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario");
		
		TreinamentoDao treinamentoDao = new TreinamentoDao();
		Treinamento treinamento = new Treinamento();
	
		treinamento.setIdTreinamento(idTreinamento);
		treinamento.setUsuario(loginUsuario.getId());
		treinamento.setObra(obra);
		treinamento.setNumeroRegistro(numeroRegistro);
		
		Calendar dataCalendar = null;
		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		dataCalendar = Calendar.getInstance();
		dataCalendar.setTime(date);	
		
		treinamento.setData(dataCalendar);
		treinamento.setHoraInicio(horaInicio);
		treinamento.setHoraTermino(horaTermino);
		
		date = new SimpleDateFormat("dd/MM/yyyy").parse(dataAvaliacaoEficacia);
		dataCalendar = Calendar.getInstance();
		dataCalendar.setTime(date);	
		
		treinamento.setDataAvaliacaoEficacia(dataCalendar);
		treinamento.setLocalTrecho( java.net.URLDecoder.decode(localTrecho, "UTF-8") );
		treinamento.setContrato(contrato);
		treinamento.setCliente(cliente);
		//treinamento.setHhTreinamento(hhTreinamento);
		treinamento.setRecursosUtilizados( java.net.URLDecoder.decode(recursosUtilizados, "UTF-8") );
		treinamento.setMetodoAvaliacaoCampo(metodoAvaliacaoCampo);
		treinamento.setMetodoAvaliacaoCertificado(metodoAvaliacaoCertificado);
		treinamento.setMetodoAvaliacaoTeste(metodoAvaliacaoTeste);
		treinamento.setInstrutorNome( java.net.URLDecoder.decode(instrutorNome, "UTF-8") );
		treinamento.setInstrutorFuncao( java.net.URLDecoder.decode(instrutorFuncao, "UTF-8") );
		treinamento.setDescSetorTreinamento( java.net.URLDecoder.decode(descSetorTreinamento, "UTF-8") );
		
		int iRetorno = treinamentoDao.altera(treinamento);
		
		return iRetorno;
		
	}
	
	
	// Método usado para excluir treinamento
	@RequestMapping(value="deleteTreinamento", method=RequestMethod.GET) //, produces = "application/json")
	public @ResponseBody String deleteTreinamento(@RequestParam("idTreinamento") int idTreinamento
											 ) throws SQLException, ClassNotFoundException, ParseException {
			
		TreinamentoDao treinamentoDao = new TreinamentoDao();
		Treinamento treinamento = new Treinamento();
	
		treinamento.setIdTreinamento(idTreinamento);
		
		boolean bRetorno = treinamentoDao.exclui(treinamento);
		
		if (bRetorno) {
			return "OK";
		} else {
			return "ERRO";
		}
	}	
}

