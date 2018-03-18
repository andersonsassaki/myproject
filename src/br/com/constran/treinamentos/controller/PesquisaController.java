package br.com.constran.treinamentos.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.constran.treinamentos.jdbc.dao.PesquisaDao;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
import br.com.constran.treinamentos.jdbc.modelo.Pesquisa;

@Controller
public class PesquisaController {

	@RequestMapping(value="search", method=RequestMethod.POST, produces = "application/json")
	public @ResponseBody List<Pesquisa> listaPesquisa(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String strSearch = request.getParameter("inputSearch"); 
		
		int idUsuario;
		String grupoUsuario;
		
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario"); 
		
		idUsuario = loginUsuario.getId();
		grupoUsuario = loginUsuario.getGrupoUsuario();
		
		PesquisaDao pesquisaDao = new PesquisaDao();
		
		List<Pesquisa> pesquisaList = pesquisaDao.getListaPesquisa(strSearch, idUsuario, grupoUsuario);
		
		return pesquisaList;
		
	}
	
	/*
	@RequestMapping(value="/pesquisaTeste")
	public ModelAndView pesquisateste(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		return new ModelAndView ("pesquisaTeste");
	}
	*/
}
