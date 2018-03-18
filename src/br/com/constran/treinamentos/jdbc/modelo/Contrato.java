package br.com.constran.treinamentos.jdbc.modelo;


public class Contrato {
	private int id;
	private String descricao;
	private int ccObra;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	public int getCcObra() {
		return ccObra;
	}
	
	public void setCcObra(int ccObra) {
		this.ccObra = ccObra;
	}

}
