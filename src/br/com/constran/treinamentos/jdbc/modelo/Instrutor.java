package br.com.constran.treinamentos.jdbc.modelo;

public class Instrutor {
	private int id;
	private String nomeInstrutor;
	private String ativo;
	private String descAtivo;
	private int funcao;
	private String nomeFuncao;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNomeInstrutor() {
		return nomeInstrutor;
	}
	
	public void setNomeInstrutor(String nomeInstrutor) {
		this.nomeInstrutor = nomeInstrutor;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getDescAtivo() {
		return descAtivo;
	}

	public void setDescAtivo(String descAtivo) {
		this.descAtivo = descAtivo;
	}

	public int getFuncao() {
		return funcao;
	}

	public void setFuncao(int funcao) {
		this.funcao = funcao;
	}

	public String getNomeFuncao() {
		return nomeFuncao;
	}

	public void setNomeFuncao(String nomeFuncao) {
		this.nomeFuncao = nomeFuncao;
	}
	
}
