package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.Contrato;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;

public class ContratoDao {

	private Connection conn;
	
	
	public ContratoDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	
	public List<Contrato> getListaContrato (HttpSession session) {
		
		int idUsuario;
		String strGrupoUsuario;
		
		LoginUsuario loginUsuario = (LoginUsuario) session.getAttribute("sLoginUsuario"); 
		idUsuario = loginUsuario.getId();        
		strGrupoUsuario = loginUsuario.getGrupoUsuario().toString();   
		
		String strSql;

		if (strGrupoUsuario.indexOf("adm") != -1 ) {
			strSql = "SELECT id, descricao, ccObra ";
			strSql = strSql + "FROM contratos ";
			
		} else {
			strSql = "SELECT c.id, c.descricao, c.ccObra ";
			strSql = strSql + "FROM contratos c ";
			strSql = strSql + "INNER JOIN obrasUsuarios ou ON (ou.ccObra = c.ccObra) ";
			strSql = strSql + "WHERE ou.usuario = " + idUsuario;
		}

			
		try {
			List<Contrato> contratos = new ArrayList<Contrato>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
						
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Contrato contrato = new Contrato();
				
				contrato.setId(rs.getInt("id"));
				contrato.setDescricao(rs.getString("descricao"));
				contrato.setCcObra(rs.getInt("ccObra"));
				
				contratos.add(contrato);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return contratos;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	
	public boolean adiciona(Contrato contrato) throws SQLException {
		String strSql = "INSERT INTO contratos (descricao, ccObra) VALUES (?, ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setString(1, contrato.getDescricao());
			stmt.setInt(2, contrato.getCcObra());
			
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	
	
	public boolean altera(Contrato contrato) throws SQLException {
		String strSql = "UPDATE contratos set descricao = ?, ccObra = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setString(1, contrato.getDescricao());
			stmt.setInt(2, contrato.getCcObra());
			stmt.setInt(3, contrato.getId());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}

	
	public boolean exclui(Contrato contrato) throws SQLException {
		String strSql = "DELETE FROM contratos WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, contrato.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}
	
}
