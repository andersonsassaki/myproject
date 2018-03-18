package br.com.constran.treinamentos.controller;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.constran.treinamentos.jdbc.dao.LoginDao;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
  

@Controller
//@RequestMapping("/")
public class LoginController {
	
	@RequestMapping("/")
    public ModelAndView index(){
        System.out.println("Entrando na home da CDC");
        return new ModelAndView ("login");
    }

	@RequestMapping(value="/home") 
	public ModelAndView /*String*/ validaLogin(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpSession session) throws SQLException { 
		String strLogin = request.getParameter("login"); 
		String strPassword = request.getParameter("password"); 
		String strNome;
		
		LoginDao loginDao = new LoginDao();
					
		strNome = loginDao.validaLogin(strLogin, strPassword, session);
		
		if(!strNome.equalsIgnoreCase("")) {
						
			//redirectAttrs.addAttribute("nomeUsuario", strNome);
			//return "mainPage";
			return new ModelAndView ("mainPage", "nomeUsuario", strNome);
			
		} else { 
					
			//redirectAttrs.addAttribute("login","erro");
			//return "redirect:/";
			return new ModelAndView ("login", "login", "erro");
		}
	}
	
	
	@RequestMapping(value="/login")
	public ModelAndView /*String*/ validaSessao (HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttrs, HttpSession session) {
		
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario"); 
		String strNome;
	
		if (loginUsuario == null) {
			//return "redirect:login";

			return new ModelAndView ("login");
		} else {
		
			strNome = loginUsuario.getNome();
			
			if(!strNome.equalsIgnoreCase("")) {
				//redirectAttrs.addAttribute("nomeUsuario", strNome);
				//return "redirect:mainPage";
				return new ModelAndView ("mainPage", "nomeUsuario", strNome);
			} else {
				//return "redirect:login";
				return new ModelAndView ("login");
			}
		}
	}
	
	
	@RequestMapping(value="/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		session.invalidate();
		return new ModelAndView ("index");
	}
	
}

