package br.com.constran.treinamentos.jdbc.modelo;

import java.util.Calendar;
import java.util.List;

public class Treinamento {
	private int idTreinamento;
	private int usuario;
	private int obra;
	private String numeroRegistro;
	
	//@DateTimeFormat(pattern="dd/MM/yyyy")
	private Calendar data;
	private String horaInicio;
	private String horaTermino;
	
	//@DateTimeFormat(pattern="dd/MM/yyyy")
	private Calendar dataAvaliacaoEficacia;
	private String localTrecho;
	private int contrato;
	private int cliente;
	private String hhTreinamento;
	private String recursosUtilizados;
	private String metodoAvaliacaoCampo;
	private String metodoAvaliacaoCertificado;
	private String metodoAvaliacaoTeste;
	private String instrutorNome;
	private String instrutorFuncao;
	private String descSetorTreinamento;
	
	private List<TreinamentoDocumento> treinamentoDocumento;
	/*
	private String numeroDocumento;
	private String descDocumento;
	*/
	
	public int getIdTreinamento() {
		return idTreinamento;
	}
	
	public void setIdTreinamento(int idTreinamento) {
		this.idTreinamento = idTreinamento;
	}
	
	public int getUsuario() {
		return usuario;
	}
	
	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}
	
	public int getObra() {
		return obra;
	}
	
	public void setObra(int obra) {
		this.obra = obra;
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
	public String getHoraInicio() {
		return horaInicio;
	}
	
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	
	public String getHoraTermino() {
		return horaTermino;
	}
	
	public void setHoraTermino(String horaTermino) {
		this.horaTermino = horaTermino;
	}
	
	public Calendar getDataAvaliacaoEficacia() {
		return dataAvaliacaoEficacia;
	}
	
	public void setDataAvaliacaoEficacia(Calendar dataAvaliacaoEficacia) {
		this.dataAvaliacaoEficacia = dataAvaliacaoEficacia;
	}
	
	public String getLocalTrecho() {
		return localTrecho;
	}
	
	public void setLocalTrecho(String localTrecho) {
		this.localTrecho = localTrecho;
	}
	
	public int getContrato() {
		return contrato;
	}
	
	public void setContrato(int contrato) {
		this.contrato = contrato;
	}
	
	public int getCliente() {
		return cliente;
	}
	
	public void setCliente(int cliente) {
		this.cliente = cliente;
	}
	
	public String getHhTreinamento() {
		return hhTreinamento;
	}
	
	public void setHhTreinamento(String hhTreinamento) {
		this.hhTreinamento = hhTreinamento;
	}
	
	public String getRecursosUtilizados() {
		return recursosUtilizados;
	}
	public void setRecursosUtilizados(String recursosUtilizados) {
		this.recursosUtilizados = recursosUtilizados;
	}
	
	public String getMetodoAvaliacaoCampo() {
		return metodoAvaliacaoCampo;
	}
	
	public void setMetodoAvaliacaoCampo(String metodoAvaliacaoCampo) {
		this.metodoAvaliacaoCampo = metodoAvaliacaoCampo;
	}
	
	public String getMetodoAvaliacaoCertificado() {
		return metodoAvaliacaoCertificado;
	}
	
	public void setMetodoAvaliacaoCertificado(String metodoAvaliacaoCertificado) {
		this.metodoAvaliacaoCertificado = metodoAvaliacaoCertificado;
	}
	
	public String getMetodoAvaliacaoTeste() {
		return metodoAvaliacaoTeste;
	}
	
	public void setMetodoAvaliacaoTeste(String metodoAvaliacaoTeste) {
		this.metodoAvaliacaoTeste = metodoAvaliacaoTeste;
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
	
	public String getDescSetorTreinamento() {
		return descSetorTreinamento;
	}
	
	public void setDescSetorTreinamento(String descSetorTreinamento) {
		this.descSetorTreinamento = descSetorTreinamento;
	}

	public List<TreinamentoDocumento> getTreinamentoDocumento() {
		return treinamentoDocumento;
	}

	public void setTreinamentoDocumento(List<TreinamentoDocumento> treinamentoDocumento) {
		this.treinamentoDocumento = treinamentoDocumento;
	}
	/*
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getDescDocumento() {
		return descDocumento;
	}

	public void setDescDocumento(String descDocumento) {
		this.descDocumento = descDocumento;
	}
	*/	
}
