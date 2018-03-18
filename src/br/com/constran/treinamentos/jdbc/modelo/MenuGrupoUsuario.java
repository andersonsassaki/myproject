package br.com.constran.treinamentos.jdbc.modelo;

public class MenuGrupoUsuario {
	private int id;
	private int idMenu;
	private String tituloMenu;
	private String grupoUsuario;
	private String nomeGrupo;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdMenu() {
		return idMenu;
	}
	
	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}
	
	public String getTituloMenu() {
		return tituloMenu;
	}

	public void setTituloMenu(String tituloMenu) {
		this.tituloMenu = tituloMenu;
	}
	
	public String getGrupoUsuario() {
		return grupoUsuario;
	}
	
	public void setGrupoUsuario(String grupoUsuario) {
		this.grupoUsuario = grupoUsuario;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
	
	
	
}
