package br.com.constran.treinamentos.jdbc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.Cliente;
import br.com.constran.treinamentos.jdbc.modelo.Contrato;
import br.com.constran.treinamentos.jdbc.modelo.Instrutor;
import br.com.constran.treinamentos.jdbc.modelo.Obra;
import br.com.constran.treinamentos.jdbc.modelo.Treinamento;

public class TreinamentoDao {
	private Connection conn;
	
	public TreinamentoDao() throws ClassNotFoundException {
		this.conn = new ConnectionFactory().getConnection();
	}

	
	// Método usado para carregar dropdown de obras na página de treinamentos
	public List<Obra> getListaTreinamentoObras (int idUsuario, String grupoUsuario) throws SQLException {
	
		String strSql;
			
		if ( grupoUsuario.contains("adm") ) {
			strSql = "SELECT id, CONCAT(CONVERT(id, CHAR(6)), ' - ',  nomeObra) AS nomeObra ";
			strSql = strSql + "FROM obras ";
		} else {
			strSql = "SELECT o.id, CONCAT(CONVERT(ou.ccObra, CHAR(6)), ' - ',  o.nomeObra) AS nomeObra ";
			strSql = strSql + "FROM obras o ";
			strSql = strSql + "INNER JOIN obrasUsuarios ou ON (ou.ccObra = o.id) ";
			strSql = strSql + "WHERE ou.usuario = " + idUsuario;
		}
		
		try {
			List<Obra> obras = new ArrayList<Obra>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Obra obra = new Obra();
				
				obra.setId(rs.getInt("id"));
				obra.setNomeObra(rs.getString("nomeObra"));
				
				obras.add(obra);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return obras;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	
	
	// Método usado para carregar dropdown de contratos na página de treinamentos
	public List<Contrato> getListaTreinamentoContratos (String ccObra) throws SQLException {
	
		String strSql;
			
		strSql = "SELECT id, descricao ";
		strSql = strSql + "FROM contratos ";
		strSql = strSql + "WHERE ccObra = " + ccObra;

		
		try {
			List<Contrato> contratos = new ArrayList<Contrato>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Contrato contrato = new Contrato();
				
				contrato.setId(rs.getInt("id"));
				contrato.setDescricao(rs.getString("descricao"));
				
				contratos.add(contrato);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return contratos;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	
	
	// Método usado para carregar dropdown de clientes na página de treinamentos
	public List<Cliente> getListaTreinamentoClientes () throws SQLException {
	
		String strSql;
			
		strSql = "SELECT id, nomeCliente ";
		strSql = strSql + "FROM clientes ";

		
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Cliente cliente = new Cliente();
				
				cliente.setId(rs.getInt("id"));
				cliente.setNomeCliente(rs.getString("nomeCliente"));
				
				clientes.add(cliente);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return clientes;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	
	
	// Método usado para carregar autocomplete de instrutores na página de treinamentos
	public List<Instrutor> getListaTreinamentoInstrutores (String searchInstrutor) throws SQLException {
	
		String strSql;
			
		strSql = "SELECT i.id, i.nomeInstrutor, f.nomeFuncao ";
		strSql = strSql + "FROM instrutores i ";
		strSql = strSql + "INNER JOIN funcoes f ON (f.id = i.funcao) ";
		strSql = strSql + "WHERE i.ativo = 'S' ";
		strSql = strSql + "AND i.nomeInstrutor LIKE '%" + searchInstrutor + "%' ";


		try {
			List<Instrutor> instrutores = new ArrayList<Instrutor>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Instrutor instrutor = new Instrutor();
				
				instrutor.setId(rs.getInt("id"));
				instrutor.setNomeInstrutor(rs.getString("nomeInstrutor"));
				instrutor.setNomeFuncao(rs.getString("nomeFuncao"));
				
				instrutores.add(instrutor);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return instrutores;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	

	// Método usado para carregar autocomplete de treinamentos na página de treinamentos
	public List<Treinamento> getSearchListaTreinamentos (String searchTreinamento, int idUsuario, String grupoUsuario) throws SQLException {
	
		String strSql;
		
		if (grupoUsuario.contains("adm")) {
		
			strSql = "SELECT DISTINCT t.idTreinamento, t.numeroRegistro, t.obra "; //d.numeroDocumento, d.descDocumento, t.obra ";
			strSql = strSql + "FROM treinamentos t ";
			strSql = strSql + "LEFT JOIN treinamentoDocumentos d ON (d.treinamento = t.idTreinamento) ";
			strSql = strSql + "WHERE (d.numeroDocumento LIKE '%" + searchTreinamento + "%' ";
			strSql = strSql + "   OR d.descDocumento LIKE '%" + searchTreinamento + "%' ";
			strSql = strSql + "   OR t.numeroRegistro LIKE '%" + searchTreinamento + "%') ";
		} else {
			strSql = "SELECT DISTINCT t.idTreinamento, t.numeroRegistro, t.obra "; //d.numeroDocumento, d.descDocumento, t.obra ";
			strSql = strSql + "FROM treinamentos t ";
			strSql = strSql + "LEFT JOIN treinamentoDocumentos d ON (d.treinamento = t.idTreinamento) ";
			strSql = strSql + "INNER JOIN obrasUsuarios ou ON (ou.ccObra = t.obra) ";
			strSql = strSql + "WHERE (d.numeroDocumento LIKE '%" + searchTreinamento + "%' ";
			strSql = strSql + "   OR d.descDocumento LIKE '%" + searchTreinamento + "%' ";
			strSql = strSql + "   OR t.numeroRegistro LIKE '%" + searchTreinamento + "%') ";
			strSql = strSql + "  AND ou.usuario = " + idUsuario;
		}
			
		

		try {
			List<Treinamento> treinamentos = new ArrayList<Treinamento>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Treinamento treinamento = new Treinamento();
				
				treinamento.setIdTreinamento(rs.getInt("idTreinamento"));
				treinamento.setNumeroRegistro(rs.getString("numeroRegistro"));
				treinamento.setObra(rs.getInt("obra"));
				
				/*
				treinamento.setNumeroDocumento(rs.getString("numeroDocumento"));
				treinamento.setDescDocumento(rs.getString("descDocumento"));
				*/
				
				TreinamentoDocumentoDao treinamentoDocumentoDao = new TreinamentoDocumentoDao();
				
				treinamento.setTreinamentoDocumento(treinamentoDocumentoDao.getListaTreinamentosDocs(Integer.toString(rs.getInt("idTreinamento"))));
					
				treinamentos.add(treinamento);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return treinamentos;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}	
	

	// Método usado para carregar página de treinamentos
	public List<Treinamento> getListaTreinamentos(String idTreinamento) throws SQLException, ParseException {
	
		String strSql;
			
		strSql = "SELECT idTreinamento, usuario, obra, numeroRegistro, data, horaInicio, horaTermino, ";
		strSql = strSql + "dataAvaliacaoEficacia, localTrecho, contrato, cliente, recursosUtilizados, ";
		strSql = strSql + "metodoAvaliacaoCampo, metodoAvaliacaoCertificado, metodoAvaliacaoTeste, ";
		strSql = strSql + "instrutorNome, instrutorFuncao, descSetorTreinamento ";
		strSql = strSql + "FROM treinamentos ";
		strSql = strSql + "WHERE idTreinamento = " + idTreinamento;

		
		try {
			List<Treinamento> treinamentos = new ArrayList<Treinamento>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();
					
			while (rs.next()) {
				Treinamento treinamento = new Treinamento();
								
				treinamento.setIdTreinamento(rs.getInt("idTreinamento"));
				treinamento.setUsuario(rs.getInt("usuario"));
				treinamento.setObra(rs.getInt("obra"));
				treinamento.setNumeroRegistro(rs.getString("numeroRegistro"));

				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("data"));
				treinamento.setData(data);				
		    
			    treinamento.setHoraInicio(rs.getString("horaInicio").substring(1, 5));
			    treinamento.setHoraTermino(rs.getString("horaTermino").substring(1, 5));
			    
			    data.setTime(rs.getDate("dataAvaliacaoEficacia"));
			    treinamento.setDataAvaliacaoEficacia(data);
			    
			    treinamento.setLocalTrecho(rs.getString("localTrecho"));
			    treinamento.setContrato(rs.getInt("contrato"));
			    treinamento.setCliente(rs.getInt("cliente"));
			    //treinamento.setHhTreinamento(rs.getString("hhTreinamento").substring(1, 5));
			    treinamento.setRecursosUtilizados(rs.getString("recursosUtilizados"));
			    treinamento.setMetodoAvaliacaoCampo(rs.getString("metodoAvaliacaoCampo"));
			    treinamento.setMetodoAvaliacaoCertificado(rs.getString("metodoAvaliacaoCertificado"));
			    treinamento.setMetodoAvaliacaoTeste(rs.getString("metodoAvaliacaoTeste"));
			    treinamento.setInstrutorNome(rs.getString("instrutorNome"));
			    treinamento.setInstrutorFuncao(rs.getString("instrutorFuncao"));
				treinamento.setDescSetorTreinamento(rs.getString("descSetorTreinamento"));
				
				treinamentos.add(treinamento);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return treinamentos;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}	
	
/*
	// Método usado para carregar autocomplete de treinamentos na página de treinamentos
	public List<Treinamento> getListaTreinamentos (String searchTreinamento) throws SQLException {
	
		String strSql;
			
		strSql = "SELECT t.idTreinamento AS id, t.usuario, t.obra, t.numeroRegistro, t.data, ";
		strSql = strSql + "t.horaInicio, t.horaTermino, t.dataAvaliacaoEficacia, t.localTrecho, ";
		strSql = strSql + "t.contrato, t.cliente, t.hhTreinamento, t.recursosUtilizados, ";
		strSql = strSql + "t.metodoAvaliacaoCampo, t.metodoAvaliacaoCertificado, t.metodoAvaliacaoTeste, ";
		strSql = strSql + "t.instrutorNome, t.instrutorFuncao, t.descSetorTreinamento ";
		strSql = strSql + "FROM treinamentos t ";
		strSql = strSql + "INNER JOIN treinamentoDocumentos d ON (d.treinamento = t.idTreinamento) ";
		strSql = strSql + "WHERE (d.numeroDocumento LIKE '%" + searchTreinamento + "%' ";
		strSql = strSql + "   OR d.descDocumento LIKE '%" + searchTreinamento + "%' ";
		strSql = strSql + "   OR t.numeroRegistro LIKE '%" + searchTreinamento + "%') ";
		


		try {
			List<Instrutor> instrutores = new ArrayList<Instrutor>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Instrutor instrutor = new Instrutor();
				
				instrutor.setId(rs.getInt("id"));
				instrutor.setNomeInstrutor(rs.getString("nomeInstrutor"));
				
				instrutores.add(instrutor);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return instrutores;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}		
 */
	
	
	public int adiciona(Treinamento treinamento) throws SQLException {
		String strSql;
		int insertedIdTreinamento = 0;
		
		strSql = "CALL insertTreinamento (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			
			// Prepara instrução no banco
			CallableStatement cstmt = conn.prepareCall(strSql); //("{call dbo.loadTreinamentoFuncionario(?)}");
	        
			cstmt.setInt(1, treinamento.getUsuario());
			cstmt.setInt(2, treinamento.getObra());
			cstmt.setString(3, treinamento.getNumeroRegistro());
			cstmt.setDate(4, new Date(treinamento.getData().getTimeInMillis()));
			cstmt.setString(5, treinamento.getHoraInicio());
			cstmt.setString(6, treinamento.getHoraTermino());		
			cstmt.setDate(7, new Date(treinamento.getDataAvaliacaoEficacia().getTimeInMillis())); 				
			cstmt.setString(8, treinamento.getLocalTrecho());
			cstmt.setInt(9, treinamento.getContrato());
			cstmt.setInt(10, treinamento.getCliente());
			//cstmt.setString(11, treinamento.getHhTreinamento());
			cstmt.setString(11, treinamento.getRecursosUtilizados());
			cstmt.setString(12, treinamento.getMetodoAvaliacaoCampo());
			cstmt.setString(13, treinamento.getMetodoAvaliacaoCertificado());
			cstmt.setString(14, treinamento.getMetodoAvaliacaoTeste());
			cstmt.setString(15, treinamento.getInstrutorNome());
			cstmt.setString(16, treinamento.getInstrutorFuncao());
			cstmt.setString(17, treinamento.getDescSetorTreinamento());
			cstmt.setInt(18, 0);
			
			ResultSet rs = cstmt.executeQuery();
	        	        
			while (rs.next()) {				
				insertedIdTreinamento = rs.getInt("insertedIdTreinamento");
			} 
	        
	        rs.close();	        	        					
			cstmt.close();
			conn.close();
			
			return insertedIdTreinamento;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}	
 
	
	public int altera(Treinamento treinamento) throws SQLException {
		String strSql;
		
		strSql = "UPDATE treinamentos ";	
		strSql = strSql + "SET usuario = ?, obra = ?, numeroRegistro = ?, data = ?, horaInicio = ?, horaTermino = ?, ";
		strSql = strSql + "dataAvaliacaoEficacia = ?, localTrecho = ?, contrato = ?, cliente = ?, ";
		strSql = strSql + "recursosUtilizados = ?, metodoAvaliacaoCampo = ?, ";
		strSql = strSql + "metodoAvaliacaoCertificado = ?, metodoAvaliacaoTeste = ?, instrutorNome = ?, ";
		strSql = strSql + "instrutorFuncao = ?, descSetorTreinamento = ? ";
		strSql = strSql + "WHERE idTreinamento = ? ";
		
		try {
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);	
			
			stmt.setInt(1, treinamento.getUsuario());
			stmt.setInt(2, treinamento.getObra());
			stmt.setString(3, treinamento.getNumeroRegistro());			
			stmt.setDate(4, new Date(treinamento.getData().getTimeInMillis()));			
			stmt.setString(5, treinamento.getHoraInicio());
			stmt.setString(6, treinamento.getHoraTermino());		
			stmt.setDate(7, new Date(treinamento.getDataAvaliacaoEficacia().getTimeInMillis())); 			
			stmt.setString(8, treinamento.getLocalTrecho());
			stmt.setInt(9, treinamento.getContrato());
			stmt.setInt(10, treinamento.getCliente());
			//stmt.setString(11, treinamento.getHhTreinamento());
			stmt.setString(11, treinamento.getRecursosUtilizados());
			stmt.setString(12, treinamento.getMetodoAvaliacaoCampo());
			stmt.setString(13, treinamento.getMetodoAvaliacaoCertificado());
			stmt.setString(14, treinamento.getMetodoAvaliacaoTeste());
			stmt.setString(15, treinamento.getInstrutorNome());
			stmt.setString(16, treinamento.getInstrutorFuncao());
			stmt.setString(17, treinamento.getDescSetorTreinamento());
			
			stmt.setInt(18, treinamento.getIdTreinamento());

			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
			return treinamento.getIdTreinamento();
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public boolean exclui(Treinamento treinamento) throws SQLException {
		String strSql;
		
		// Exclui os documentos do treinmanento
		strSql = "DELETE FROM treinamentoDocumentos WHERE treinamento = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, treinamento.getIdTreinamento());
			
			stmt.executeUpdate();	

			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		
		// Exclui os participantes do treinmanento
		strSql = "DELETE FROM treinamentoParticipantes WHERE treinamento = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, treinamento.getIdTreinamento());
			
			stmt.executeUpdate();	

			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
				
		
		// Exclui os treinamentos
		strSql = "DELETE FROM treinamentos WHERE idTreinamento = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, treinamento.getIdTreinamento());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}
	
}
