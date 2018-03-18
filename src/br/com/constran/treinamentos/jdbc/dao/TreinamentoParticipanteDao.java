package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.TreinamentoParticipante;

public class TreinamentoParticipanteDao {
	private Connection conn;
	
	public TreinamentoParticipanteDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	// Método usado para carregar grid de documentos do treinamento
	public List<TreinamentoParticipante> getListaTreinamentosParticipantes (String treinamento) {
		
		String strSql;
		
		strSql = "SELECT id, treinamento, cpf, matricula, nome, descFuncao, presencaTreinamento, ";
		strSql = strSql + "CASE WHEN presencaTreinamento = 'S' THEN 'Sim' ELSE 'Não' END descPresencaTreinamento ";
		strSql = strSql + "FROM treinamentoParticipantes ";
		strSql = strSql + "WHERE treinamento = " + treinamento;
		 		
		try {
			List<TreinamentoParticipante> treinamentoParticipantes = new ArrayList<TreinamentoParticipante>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				TreinamentoParticipante treinamentoParticipante = new TreinamentoParticipante();
				
				treinamentoParticipante.setId(rs.getInt("id"));
				treinamentoParticipante.setTreinamento(rs.getInt("treinamento"));
				treinamentoParticipante.setCpf(rs.getString("cpf"));
				treinamentoParticipante.setMatricula(rs.getString("matricula"));
				treinamentoParticipante.setNome(rs.getString("nome"));
				treinamentoParticipante.setDescFuncao(rs.getString("descFuncao"));
				treinamentoParticipante.setPresencaTreinamento(rs.getString("presencaTreinamento"));
				treinamentoParticipante.setDescPresencaTreinamento(rs.getString("descPresencaTreinamento"));
				
				treinamentoParticipantes.add(treinamentoParticipante);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return treinamentoParticipantes;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	

	public boolean adiciona(TreinamentoParticipante treinamentoParticipante) throws SQLException {
		String strSql = "INSERT INTO treinamentoParticipantes (treinamento, cpf, matricula, nome, descFuncao) VALUES (?, ?, ?, ?, ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, treinamentoParticipante.getTreinamento());
			stmt.setString(2, treinamentoParticipante.getCpf());
			stmt.setString(3, treinamentoParticipante.getMatricula());
			stmt.setString(4, treinamentoParticipante.getNome());
			stmt.setString(5, treinamentoParticipante.getDescFuncao());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	
	
	public boolean altera(TreinamentoParticipante treinamentoParticipante) throws SQLException {
		String strSql = "UPDATE treinamentoParticipantes SET cpf = ?, matricula = ?, nome = ?, descFuncao = ?, presencaTreinamento = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setString(1, treinamentoParticipante.getCpf());
			stmt.setString(2, treinamentoParticipante.getMatricula());
			stmt.setString(3, treinamentoParticipante.getNome());
			stmt.setString(4, treinamentoParticipante.getDescFuncao());
			stmt.setString(5, treinamentoParticipante.getPresencaTreinamento());
			stmt.setInt(6, treinamentoParticipante.getId());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}

	
	public boolean exclui(TreinamentoParticipante treinamentoParticipante) throws SQLException {
		String strSql = "DELETE FROM treinamentoParticipantes WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, treinamentoParticipante.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}	
	
}
