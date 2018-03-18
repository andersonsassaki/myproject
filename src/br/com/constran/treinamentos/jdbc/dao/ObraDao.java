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
import br.com.constran.treinamentos.jdbc.modelo.Obra;

public class ObraDao {
	private Connection conn;
	
	public ObraDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	
	// Método usado para carregar dropdown de obras
	public List<Obra> getListaObraUsuario (String usuario) throws SQLException {
		
		String strSql;
			
		strSql = "SELECT id, nomeObra ";
		strSql = strSql + "FROM obras ";
		strSql = strSql + "WHERE id NOT IN ( ";
		strSql = strSql + "SELECT ccObra FROM obrasUsuarios WHERE usuario = ? ) ";

		
		try {
			List<Obra> obras = new ArrayList<Obra>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			stmt.setString(1, usuario);
			
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
	
	
	// Método usado para carregar dropdown de obras
	public List<Obra> getListaObra (HttpSession session) throws SQLException {
		
		int strIdUsuario;
		String strGrupoUsuario;
		String strSql;
		
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario");
		
		strIdUsuario = loginUsuario.getId();
		strGrupoUsuario = loginUsuario.getGrupoUsuario();
		
		if (strGrupoUsuario.indexOf("adm") != -1 ) {
			strSql = "SELECT id, nomeObra ";
			strSql = strSql + "FROM obras ";
		} else {
			strSql = "SELECT o.id, o.nomeObra ";
			strSql = strSql + "FROM obras o ";
			strSql = strSql + "INNER JOIN obrasUsuarios ou ON (ou.ccObra = o.id) ";
			strSql = strSql + "WHERE ou.usuario = " + strIdUsuario;
		}

		
		try {
			List<Obra> obras = new ArrayList<Obra>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			//if (strGrupoUsuario.indexOf("adm") == -1 ) {
			//	stmt.setInt(1, strIdUsuario);
			//}
			
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
	
	
	// Método usado para carregar grid de obras
	public List<Obra> getListaObras () {
		
		String strSql;
		
		strSql = "SELECT id, nomeObra ";
		strSql = strSql + "FROM Obras ";
		 		
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
	
	
	public boolean adiciona(Obra obra) throws SQLException {
		String strSql = "INSERT INTO obras (id, nomeObra) VALUES (?, ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, obra.getId());
			stmt.setString(2, obra.getNomeObra());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	
	
	public boolean altera(Obra obra) throws SQLException {
		String strSql = "UPDATE obras set nomeObra = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setString(1, obra.getNomeObra());
			stmt.setInt(2, obra.getId());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}

	
	public boolean exclui(Obra obra) throws SQLException {
		String strSql = "DELETE FROM obras WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, obra.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}
	
}
