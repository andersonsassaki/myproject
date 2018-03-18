package br.com.constran.treinamentos.jdbc.modelo;

public class ObraUsuario {
	private int id;
	private int usuario;
	private int ccObra;
	private String nomeObra;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getUsuario() {
		return usuario;
	}
	
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	
	public int getCcObra() {
		return ccObra;
	}
	
	public void setCcObra(int ccObra) {
		this.ccObra = ccObra;
	}
	
	public String getNomeObra() {
		return nomeObra;
	}
	
	public void setNomeObra(String nomeObra) {
		this.nomeObra = nomeObra;
	}
	
}
