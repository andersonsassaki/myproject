package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.GrupoUsuario;

public class GrupoUsuarioDao {
	
	private Connection conn;
	
	
	public GrupoUsuarioDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	
	// Método usado para carregar dropdown de menus grupos usuários
	public List<GrupoUsuario> getListaMenuGrupoUsuario (int idMenu) throws SQLException {
		
		String strSql;
			
		strSql = "SELECT id, nomeGrupo ";
		strSql = strSql + "FROM grupoUsuario ";
		strSql = strSql + "WHERE id NOT IN ( ";
		strSql = strSql + "SELECT grupoUsuario FROM menuGrupoUsuario WHERE idMenu = ? ) ";

		
		try {
			List<GrupoUsuario> gruposUsuarios = new ArrayList<GrupoUsuario>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			stmt.setInt(1, idMenu);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				GrupoUsuario grupoUsuario = new GrupoUsuario();
				
				grupoUsuario.setId(rs.getString("id"));
				grupoUsuario.setNomeGrupo(rs.getString("nomeGrupo"));
				
				gruposUsuarios.add(grupoUsuario);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return gruposUsuarios;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	
	// Método para carregar dropdown de grupo de usuário
	public List<GrupoUsuario> getListaGrupoUsuario (HttpSession session) throws SQLException {
		
		String strSql;
		
		strSql = "SELECT id, nomeGrupo ";
		strSql = strSql + "FROM grupoUsuario ";

		try {
			List<GrupoUsuario> gruposUsuarios = new ArrayList<GrupoUsuario>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				GrupoUsuario grupoUsuario = new GrupoUsuario();
				
				grupoUsuario.setId(rs.getString("id"));
				grupoUsuario.setNomeGrupo(rs.getString("nomeGrupo"));
				
				gruposUsuarios.add(grupoUsuario);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return gruposUsuarios;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	} 
	 
	
	// Método usado para carregar grid de grupo de usuários
	public List<GrupoUsuario> getListaGruposUsuarios () {
		
		String strSql;
		
		strSql = "SELECT id, nomeGrupo ";
		strSql = strSql + "FROM grupoUsuario ";
		 		
		try {
			List<GrupoUsuario> gruposUsuarios = new ArrayList<GrupoUsuario>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				GrupoUsuario grupoUsuario = new GrupoUsuario();
				
				grupoUsuario.setId(rs.getString("id"));
				grupoUsuario.setNomeGrupo(rs.getString("nomeGrupo"));
				
				gruposUsuarios.add(grupoUsuario);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return gruposUsuarios;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	
	public boolean adiciona(GrupoUsuario grupoUsuario) throws SQLException {
		String strSql = "INSERT INTO grupoUsuario (id, nomeGrupo) VALUES (?, ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setString(1, grupoUsuario.getId());
			stmt.setString(2, grupoUsuario.getNomeGrupo());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
		
		
	public boolean altera(GrupoUsuario grupoUsuario) throws SQLException {
		String strSql = "UPDATE grupoUsuario set nomeGrupo = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setString(1, grupoUsuario.getNomeGrupo());
			stmt.setString(2, grupoUsuario.getId());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}

		
	public boolean exclui(GrupoUsuario grupoUsuario) throws SQLException {
		String strSql = "DELETE FROM grupoUsuario WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setString(1, grupoUsuario.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}
}
