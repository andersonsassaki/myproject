package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.TreinamentoDocumento;

public class TreinamentoDocumentoDao {

	private Connection conn;
	
	public TreinamentoDocumentoDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	
	// Método usado para carregar grid de documentos do treinamento
	public List<TreinamentoDocumento> getListaTreinamentosDocs (String treinamento) {
		
		String strSql;
		
		strSql = "SELECT id, treinamento, numeroDocumento, revisaoDocumento, descDocumento ";
		strSql = strSql + "FROM treinamentoDocumentos ";
		strSql = strSql + "WHERE treinamento = " + treinamento;
		
		try {
			List<TreinamentoDocumento> treinamentoDocumentos = new ArrayList<TreinamentoDocumento>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				TreinamentoDocumento treinamentoDocumento = new TreinamentoDocumento();
				
				treinamentoDocumento.setId(rs.getInt("id"));
				treinamentoDocumento.setTreinamento(rs.getInt("treinamento"));
				treinamentoDocumento.setNumeroDocumento(rs.getString("numeroDocumento"));
				treinamentoDocumento.setRevisaoDocumento(rs.getString("revisaoDocumento"));
				treinamentoDocumento.setDescDocumento(rs.getString("descDocumento"));
				
				treinamentoDocumentos.add(treinamentoDocumento);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return treinamentoDocumentos;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	

	public boolean adiciona(TreinamentoDocumento treinamentoDocumento) throws SQLException {
		String strSql = "INSERT INTO treinamentoDocumentos (treinamento, numeroDocumento, revisaoDocumento, descDocumento) VALUES (?, ?, ?, ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, treinamentoDocumento.getTreinamento());
			stmt.setString(2, treinamentoDocumento.getNumeroDocumento());
			stmt.setString(3, treinamentoDocumento.getRevisaoDocumento());
			stmt.setString(4, treinamentoDocumento.getDescDocumento());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	
	
	public boolean altera(TreinamentoDocumento treinamentoDocumento) throws SQLException {
		String strSql = "UPDATE treinamentoDocumentos SET numeroDocumento = ?, revisaoDocumento = ?, descDocumento = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setString(1, treinamentoDocumento.getNumeroDocumento());
			stmt.setString(2, treinamentoDocumento.getRevisaoDocumento());
			stmt.setString(3, treinamentoDocumento.getDescDocumento());
			stmt.setInt(4, treinamentoDocumento.getId());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}

	
	public boolean exclui(TreinamentoDocumento treinamentoDocumento) throws SQLException {
		String strSql = "DELETE FROM treinamentoDocumentos WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, treinamentoDocumento.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}	
}
