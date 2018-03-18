package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import br.com.constran.treinamentos.jdbc.ConnectionFactory;

public class ReportDao {
	private Connection conn;
	
	public ReportDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	
	public Connection getConexao() throws SQLException {
		
        try {
					
        	return conn;
        	
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório", e);
        }
	}
}
