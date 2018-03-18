package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.ObraUsuario;

public class ObraUsuarioDao {

	private Connection conn;
	
	public ObraUsuarioDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	
	// Método usado para carregar grid de obras usuários
	public List<ObraUsuario> getListaObrasUsuarios (String id) {
		
		String strSql;
		
		strSql = "SELECT ou.id, ou.usuario, ou.ccObra, CONCAT(CONVERT(ou.ccObra, CHAR(6)), ' - ',  o.nomeObra) AS nomeObra ";
		strSql = strSql + "FROM obrasUsuarios ou ";
		strSql = strSql + "INNER JOIN Obras o ON (o.id = ou.ccObra) ";
		strSql = strSql + "WHERE ou.usuario = " + id;
		 		
		try {
			List<ObraUsuario> obrasUsuarios = new ArrayList<ObraUsuario>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				ObraUsuario obraUsuario = new ObraUsuario();
				
				obraUsuario.setId(rs.getInt("id"));
				obraUsuario.setUsuario(rs.getInt("usuario"));
				obraUsuario.setCcObra(rs.getInt("ccObra"));
				obraUsuario.setNomeObra(rs.getString("nomeObra"));
				
				obrasUsuarios.add(obraUsuario);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return obrasUsuarios;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	

	public boolean adiciona(ObraUsuario obraUsuario) throws SQLException {
		String strSql = "INSERT INTO obrasUsuarios (usuario, ccObra) VALUES (?, ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, obraUsuario.getUsuario());
			stmt.setInt(2, obraUsuario.getCcObra());
			
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	
	
	public boolean altera(ObraUsuario obraUsuario) throws SQLException {
		String strSql = "UPDATE obrasUsuarios SET usuario = ?, ccObra = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setInt(1, obraUsuario.getUsuario());
			stmt.setInt(2, obraUsuario.getCcObra());
			stmt.setInt(3, obraUsuario.getId());
			
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}

	
	public boolean exclui(ObraUsuario obraUsuario) throws SQLException {
		String strSql = "DELETE FROM obrasUsuarios WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, obraUsuario.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}
	
}
