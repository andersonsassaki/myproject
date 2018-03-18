package br.com.constran.treinamentos.jdbc.modelo;

import java.util.Calendar;
import java.util.List;

public class Pesquisa {
	
	private String participanteDoc;
	private int idTreinamento;
	private int obra;
	private String nomeObra;
	private String numeroRegistro;
	private Calendar data;
	private String instrutorNome;
	private String instrutorFuncao;
	private String cpfRevisao;
	private String nomeNumeroDoc;
	private String descFuncaoDoc;
	private String presencaTreinamento;

	private List<TreinamentoDocumento> treinamentoDocumento;
	private List<TreinamentoParticipante> treinamentoParticipante;
	
	
	public String getParticipanteDoc() {
		return participanteDoc;
	}
	
	public void setParticipanteDoc(String participanteDoc) {
		this.participanteDoc = participanteDoc;
	}
	
	public int getIdTreinamento() {
		return idTreinamento;
	}
	
	public void setIdTreinamento(int idTreinamento) {
		this.idTreinamento = idTreinamento;
	}
	
	public int getObra() {
		return obra;
	}
	
	public void setObra(int obra) {
		this.obra = obra;
	}
	
	public String getNomeObra() {
		return nomeObra;
	}
	
	public void setNomeObra(String nomeObra) {
		this.nomeObra = nomeObra;
	}
	
	public String getNumeroRegistro() {
		return numeroRegistro;
	}
	
	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	
	public Calendar getData() {
		return data;
	}
	
	public void setData(Calendar data) {
		this.data = data;
	}
	
	public String getInstrutorNome() {
		return instrutorNome;
	}
	
	public void setInstrutorNome(String instrutorNome) {
		this.instrutorNome = instrutorNome;
	}
	
	public String getInstrutorFuncao() {
		return instrutorFuncao;
	}
	
	public void setInstrutorFuncao(String instrutorFuncao) {
		this.instrutorFuncao = instrutorFuncao;
	}
	
	public String getCpfRevisao() {
		return cpfRevisao;
	}
	
	public void setCpfRevisao(String cpfRevisao) {
		this.cpfRevisao = cpfRevisao;
	}
	
	public String getNomeNumeroDoc() {
		return nomeNumeroDoc;
	}
	
	public void setNomeNumeroDoc(String nomeNumeroDoc) {
		this.nomeNumeroDoc = nomeNumeroDoc;
	}
	
	public String getDescFuncaoDoc() {
		return descFuncaoDoc;
	}
	
	public void setDescFuncaoDoc(String descFuncaoDoc) {
		this.descFuncaoDoc = descFuncaoDoc;
	}
	
	public String getPresencaTreinamento() {
		return presencaTreinamento;
	}
	
	public void setPresencaTreinamento(String presencaTreinamento) {
		this.presencaTreinamento = presencaTreinamento;
	}
	
	public List<TreinamentoDocumento> getTreinamentoDocumento() {
		return treinamentoDocumento;
	}
	
	public void setTreinamentoDocumento(List<TreinamentoDocumento> treinamentoDocumento) {
		this.treinamentoDocumento = treinamentoDocumento;
	}
	
	public List<TreinamentoParticipante> getTreinamentoParticipante() {
		return treinamentoParticipante;
	}
	
	public void setTreinamentoParticipante(List<TreinamentoParticipante> treinamentoParticipante) {
		this.treinamentoParticipante = treinamentoParticipante;
	}

}
