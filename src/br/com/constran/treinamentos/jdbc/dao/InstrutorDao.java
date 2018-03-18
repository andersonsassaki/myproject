package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.Funcao;
import br.com.constran.treinamentos.jdbc.modelo.Instrutor;

public class InstrutorDao {
	
	// Conexão com o banco de dados
	private Connection conn;

	public InstrutorDao() {
		this.conn = new ConnectionFactory().getConnection();
	}

	
	
	
	// ***** TODO - Método para carregar dropdown de usuário *****
	/*
	// Método usado para carregar dropdown de usuários
	public List<Obra> getUsuario (HttpSession session) throws SQLException {
		
		int strIdUsuario;
		String strGrupoUsuario;
		String strSql;
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario");
		
		strIdUsuario = loginUsuario.getIdUsuario();
		strGrupoUsuario = loginUsuario.getGrupoUsuario();
				
		if (strGrupoUsuario.indexOf("adm") != -1 ) {
			strSql = "SELECT id, nomeObra ";
			strSql = strSql + "FROM obras ";
		} else {
			strSql = "SELECT o.id, o.nomeObra ";
			strSql = strSql + "FROM obras o ";
			strSql = strSql + "INNER JOIN obrasUsuarios ou ON (ou.ccObra = o.id) ";
			strSql = strSql + "WHERE ou.usuario = ? ";
		}

		
		try {
			List<Obra> obras = new ArrayList<Obra>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			if (strGrupoUsuario.indexOf("adm") == -1 ) {
				stmt.setInt(1, strIdUsuario);
			}
			
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
	*/
	
	 
	// Método usado para carregar grid de Usuarios
	public List<Instrutor> getListaInstrutores () {
		
		String strSql;
		
		strSql = "SELECT i.id, i.nomeInstrutor, i.ativo, ";
		strSql = strSql + "CASE WHEN i.ativo = 'S' THEN 'Sim' ELSE 'Não' END descAtivo, ";
		strSql = strSql + "i.funcao, f.nomeFuncao ";
		strSql = strSql + "FROM instrutores i ";
		strSql = strSql + "LEFT JOIN funcoes f ON (f.id = i.funcao)";

		
		try {
			List<Instrutor> instrutores = new ArrayList<Instrutor>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Instrutor instrutor = new Instrutor();
				
				instrutor.setId(rs.getInt("id"));				
				instrutor.setNomeInstrutor(rs.getString("nomeInstrutor"));
				instrutor.setAtivo(rs.getString("ativo"));
				instrutor.setDescAtivo(rs.getString("descAtivo"));
				instrutor.setFuncao(rs.getInt("funcao"));
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


	public boolean adiciona(Instrutor instrutor) throws SQLException {
		String strSql = "INSERT INTO instrutores (nomeInstrutor, ativo, funcao) VALUES (?, ?, ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setString(1, instrutor.getNomeInstrutor());
			stmt.setString(2, instrutor.getAtivo());
			stmt.setInt(3,  instrutor.getFuncao());
			
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	

	public boolean altera(Instrutor instrutor) throws SQLException {
		String strSql = "UPDATE instrutores set nomeInstrutor = ?, ativo = ?, funcao = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setString(1, instrutor.getNomeInstrutor());
			stmt.setString(2, instrutor.getAtivo());
			stmt.setInt(3, instrutor.getFuncao());
			stmt.setInt(4, instrutor.getId());
			
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}


	public boolean exclui(Instrutor instrutor) throws SQLException {
		String strSql = "DELETE FROM instrutores WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, instrutor.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}
	
	
	
	// Método usado para carregar autocomplete de funcoes na página de instrutores
	public List<Funcao> getListaFuncao (String searchFuncao) throws SQLException {
	
		String strSql;
			
		strSql = "SELECT id, nomeFuncao ";
		strSql = strSql + "FROM funcoes ";
		strSql = strSql + "WHERE nomeFuncao LIKE '%" + searchFuncao + "%'";

		try {
			List<Funcao> funcoes = new ArrayList<Funcao>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Funcao funcao = new Funcao();
				
				funcao.setId(rs.getInt("id"));
				funcao.setNomeFuncao(rs.getString("nomeFuncao"));
				
				funcoes.add(funcao);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return funcoes;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}	
	
}
