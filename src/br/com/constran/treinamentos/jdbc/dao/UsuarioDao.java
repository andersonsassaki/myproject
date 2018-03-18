package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.Usuario;

public class UsuarioDao {

	// Conexão com o banco de dados
	private Connection conn;

	public UsuarioDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	
	// TODO - Método para carregar dropdown de usuário
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
	public List<Usuario> getListaUsuarios () {
		
		String strSql;
		
		strSql = "SELECT id, login, nome, '**********' AS senha, grupoUsuario ";
		strSql = strSql + "FROM usuarios ";
		 		
		try {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Usuario usuario = new Usuario();
				
				usuario.setId(rs.getInt("id"));				
				usuario.setLogin(rs.getString("login"));
				usuario.setNome(rs.getString("nome"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setGrupoUsuario(rs.getString("grupoUsuario"));
				
				usuarios.add(usuario);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return usuarios;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}



	public boolean adiciona(Usuario usuario) throws SQLException {
		String strSql = "INSERT INTO usuarios (login, nome, senha, grupoUsuario) VALUES (?, ?, PASSWORD(?), ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getNome());
			stmt.setString(3, usuario.getSenha());
			stmt.setString(4, usuario.getGrupoUsuario());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	

	public boolean altera(Usuario usuario) throws SQLException {
		String strSql = "UPDATE usuarios set login = ?, nome = ?, grupoUsuario = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getNome());
			stmt.setString(3, usuario.getGrupoUsuario());
			stmt.setInt(4, usuario.getId());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}


	public boolean exclui(Usuario usuario) throws SQLException {
		String strSql = "DELETE FROM usuarios WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, usuario.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}
	 

	
}



