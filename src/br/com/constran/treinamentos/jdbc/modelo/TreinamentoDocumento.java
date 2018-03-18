package br.com.constran.treinamentos.jdbc.modelo;

public class TreinamentoDocumento {
	private int id;
	private int treinamento;
	private String numeroDocumento;
	private String revisaoDocumento;
	private String descDocumento;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getTreinamento() {
		return treinamento;
	}
	
	public void setTreinamento(int treinamento) {
		this.treinamento = treinamento;
	}
	
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	
	public String getRevisaoDocumento() {
		return revisaoDocumento;
	}
	
	public void setRevisaoDocumento(String revisaoDocumento) {
		this.revisaoDocumento = revisaoDocumento;
	}
	
	public String getDescDocumento() {
		return descDocumento;
	}
	
	public void setDescDocumento(String descDocumento) {
		this.descDocumento = descDocumento;
	}	
}
