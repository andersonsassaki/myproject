package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;
import br.com.constran.treinamentos.jdbc.modelo.Menu;

public class MenuDao {
	private Connection conn;

	
	public MenuDao() {
		this.conn = new ConnectionFactory().getConnection();
		
	}
	

	public List<Menu> getListaMenu (HttpSession session) {
		
		String strGrupoUsuario;
		String strSql;
		
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario");
		
		strGrupoUsuario = loginUsuario.getGrupoUsuario().toString();
				
	
		if (strGrupoUsuario.equalsIgnoreCase("adm")) {
			strSql = "SELECT CASE WHEN idMenuPai IS NULL THEN id ELSE idMenuPai END menuGrupo, ";
			strSql = strSql + "id, tituloMenu, idMenuPai, pagina "; 	
			strSql = strSql + "FROM menu ";
			strSql = strSql + "ORDER BY CASE WHEN idMenuPai IS NULL THEN id ELSE idMenuPai END, ordem";
		} else {
			strSql = "SELECT CASE WHEN m.idMenuPai IS NULL THEN m.id ELSE m.idMenuPai END menuGrupo, ";
			strSql = strSql + "m.id, m.tituloMenu, m.idMenuPai, m.pagina "; 	
			strSql = strSql + "FROM menuGrupoUsuario mgu ";
			strSql = strSql + "INNER JOIN menu m ON (m.id = mgu.idMenu) ";
			strSql = strSql + "WHERE mgu.grupoUsuario = ? ";
			strSql = strSql + "ORDER BY CASE WHEN idMenuPai IS NULL THEN id ELSE idMenuPai END, ordem"; 
		}
		
		
		try {
			List<Menu> menus = new ArrayList<Menu>();
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			if (!strGrupoUsuario.equalsIgnoreCase("adm")) {
				stmt.setString(1, strGrupoUsuario);
			}
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Menu menu = new Menu();
				
				menu.setMenuGrupo(rs.getInt("menuGrupo"));
				menu.setId(rs.getInt("id"));
				menu.setTituloMenu(rs.getString("tituloMenu"));
				menu.setIdMenuPai(rs.getInt("idMenuPai"));
				menu.setPagina(rs.getString("pagina"));
		
				menus.add(menu);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
				
			return menus;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	// Método usado para carregar dropdown de usuários
	public List<Menu> getListaDropDownMenu (HttpSession session) throws SQLException {
		
		String strSql;
		
		strSql = "SELECT id, tituloMenu ";
		strSql = strSql + "FROM menu ";
		
		try {
			List<Menu> menus = new ArrayList<Menu>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Menu menu = new Menu();
				
				menu.setId(rs.getInt("id"));
				menu.setTituloMenu(rs.getString("tituloMenu"));
				
				menus.add(menu);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return menus;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	} 
	
	
	// Método usado para carregar grid de Usuarios
	public List<Menu> getListaGridMenu () {
		
		String strSql;
		
		strSql = "SELECT m.id, m.tituloMenu, m.descricao, m.pagina, m.idMenuPai, sm.tituloMenu AS tituloMenuPai, m.ordem ";
		strSql = strSql + "FROM menu m ";
		strSql = strSql + "LEFT JOIN menu sm ON (sm.id = m.idMenuPai) ";
		 		
		try {
			List<Menu> menus = new ArrayList<Menu>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Menu menu = new Menu();
				
				menu.setId(rs.getInt("id"));				
				menu.setTituloMenu(rs.getString("tituloMenu"));
				menu.setDescricao(rs.getString("descricao"));
				menu.setPagina(rs.getString("pagina"));
				menu.setIdMenuPai(rs.getInt("idMenuPai"));
				menu.setTituloMenuPai(rs.getString("tituloMenuPai"));
				menu.setOrdem(rs.getInt("ordem"));
				
				menus.add(menu);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return menus;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}


	public boolean adiciona(Menu menu) throws SQLException {
		String strSql = "INSERT INTO menu (tituloMenu, descricao, pagina, idMenuPai, ordem) VALUES (?, ?, ?, ?, ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setString(1, menu.getTituloMenu());
			stmt.setString(2, menu.getDescricao());
			stmt.setString(3, menu.getPagina());
			stmt.setInt(4, menu.getIdMenuPai());
			stmt.setInt(5, menu.getOrdem());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	

	public boolean altera(Menu menu) throws SQLException {
		String strSql = "UPDATE menu set tituloMenu = ?, descricao = ?, pagina = ?, idMenuPai = ?, ordem = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setString(1, menu.getTituloMenu());
			stmt.setString(2, menu.getDescricao());
			stmt.setString(3, menu.getPagina());
			stmt.setInt(4, menu.getIdMenuPai());
			stmt.setInt(5, menu.getOrdem());
			stmt.setInt(6, menu.getId());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}


	public boolean exclui(Menu menu) throws SQLException {
		String strSql = "DELETE FROM menu WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, menu.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}
	
}
