package br.com.constran.treinamentos.jdbc.modelo;

public class TreinamentoParticipante {
	private int id;
	private int treinamento;
	private String cpf;
	private String matricula;
	private String nome;
	private String descFuncao;
	private String presencaTreinamento;
	private String descPresencaTreinamento;
	
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
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescFuncao() {
		return descFuncao;
	}

	public void setDescFuncao(String descFuncao) {
		this.descFuncao = descFuncao;
	}

	public String getPresencaTreinamento() {
		return presencaTreinamento;
	}

	public void setPresencaTreinamento(String presencaTreinamento) {
		this.presencaTreinamento = presencaTreinamento;
	}

	public String getDescPresencaTreinamento() {
		return descPresencaTreinamento;
	}

	public void setDescPresencaTreinamento(String descPresencaTreinamento) {
		this.descPresencaTreinamento = descPresencaTreinamento;
	}
}
