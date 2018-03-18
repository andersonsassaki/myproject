package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.MenuGrupoUsuario;

public class MenuGrupoUsuarioDao {

	private Connection conn;
	
	public MenuGrupoUsuarioDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	
	// Método usado para carregar grid de menus / grupos usuários
	public List<MenuGrupoUsuario> getListaMenusGruposUsuarios (String id) {
		
		String strSql;
		
		strSql = "SELECT mgu.id, mgu.idMenu, m.tituloMenu, mgu.grupoUsuario, gu.nomeGrupo ";
		strSql = strSql + "FROM menuGrupoUsuario mgu ";
		strSql = strSql + "INNER JOIN menu m ON (m.id = mgu.idMenu) ";
		strSql = strSql + "INNER JOIN grupoUsuario gu ON (gu.id = mgu.grupoUsuario) ";
		strSql = strSql + "WHERE mgu.idMenu = " + id;
		 		
		try {
			List<MenuGrupoUsuario> menusGruposUsuarios = new ArrayList<MenuGrupoUsuario>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				MenuGrupoUsuario menuGrupoUsuario = new MenuGrupoUsuario();
				
				menuGrupoUsuario.setId(rs.getInt("id"));
				menuGrupoUsuario.setIdMenu(rs.getInt("idMenu"));
				menuGrupoUsuario.setTituloMenu(rs.getString("tituloMenu"));
				menuGrupoUsuario.setGrupoUsuario(rs.getString("grupoUsuario"));
				menuGrupoUsuario.setNomeGrupo(rs.getString("nomeGrupo"));
				
				menusGruposUsuarios.add(menuGrupoUsuario);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return menusGruposUsuarios;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	

	public boolean adiciona(MenuGrupoUsuario menuGrupoUsuario) throws SQLException {
		String strSql = "INSERT INTO menuGrupoUsuario (idMenu, grupoUsuario) VALUES (?, ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, menuGrupoUsuario.getIdMenu());
			stmt.setString(2, menuGrupoUsuario.getGrupoUsuario());
			
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	
	
	public boolean altera(MenuGrupoUsuario menuGrupoUsuario) throws SQLException {
		String strSql = "UPDATE menuGrupoUsuario SET idMenu = ?, grupoUsuario = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setInt(1, menuGrupoUsuario.getIdMenu());
			stmt.setString(2, menuGrupoUsuario.getGrupoUsuario());
			stmt.setInt(3, menuGrupoUsuario.getId());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}

	
	public boolean exclui(MenuGrupoUsuario menuGrupoUsuario) throws SQLException {
		String strSql = "DELETE FROM menuGrupoUsuario WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, menuGrupoUsuario.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}
		
	
}
