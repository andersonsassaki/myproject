package br.com.constran.treinamentos.jdbc.modelo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class LoginUsuario {
	private int id;
	private String login;
	private String nome;
	private String grupoUsuario;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getGrupoUsuario() {
		return grupoUsuario;
	}
	
	public void setGrupoUsuario(String grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}
	
}
