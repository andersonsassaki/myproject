package br.com.constran.treinamentos.jdbc.modelo;

import java.util.List;

public class Report {
	private String nomeArquivo;
	private List<String> parametros;
	
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public List<String> getParametros() {
		return parametros;
	}
	
	public void setParametros(List<String> parametros) {
		this.parametros = parametros;
	}
}
