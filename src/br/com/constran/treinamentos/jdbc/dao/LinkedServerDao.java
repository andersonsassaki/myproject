package br.com.constran.treinamentos.jdbc.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.Funcionario;


public class LinkedServerDao {
	private Connection connLinkedServer;
	
	public LinkedServerDao() throws ClassNotFoundException, SQLException {	
		this.connLinkedServer = new ConnectionFactory().getConnectionLinkedServer();
	}
	
	
	// Método usado para carregar autocomplete de instrutores na página de treinamentos
	public List<Funcionario> getListaFuncionarios (String searchFuncionario) throws SQLException {
	
		String strSql;
			
		strSql = "EXEC loadTreinamentoFuncionario '" + searchFuncionario + "'";

		try {
			List<Funcionario> funcionarios = new ArrayList<Funcionario>();
			
			// Prepara instrução no banco
			CallableStatement cstmt = connLinkedServer.prepareCall(strSql); 
       
	        ResultSet rs = cstmt.executeQuery();		
			
			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				
				funcionario.setCpf(rs.getString("cpf"));
				funcionario.setMatricula(rs.getString("matricula"));
				funcionario.setNome(rs.getString("nome"));
				funcionario.setFuncao(rs.getString("funcao"));
				
				funcionarios.add(funcionario);
			} 
			
			rs.close();
			cstmt.close();
			connLinkedServer.close();
			
			return funcionarios;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}	

}
