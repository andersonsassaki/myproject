package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.Cliente;

public class ClienteDao {
	// Conexão com o banco de dados
	private Connection conn;

	public ClienteDao() {
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
	public List<Cliente> getListaClientes () {
		
		String strSql;
		
		strSql = "SELECT id, nomeCliente, cnpj ";
		strSql = strSql + "FROM clientes ";
		 		
		try {
			List<Cliente> clientes = new ArrayList<Cliente>();
			
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			ResultSet rs = stmt.executeQuery();	
			
			while (rs.next()) {
				Cliente cliente = new Cliente();
				
				cliente.setId(rs.getInt("id"));				
				cliente.setNomeCliente(rs.getString("nomeCliente"));
				cliente.setCnpj(rs.getString("cnpj"));
				
				clientes.add(cliente);
			} 
			
			rs.close();
			stmt.close();
			conn.close();
			
			return clientes;
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}


	public boolean adiciona(Cliente cliente) throws SQLException {
		String strSql = "INSERT INTO clientes (nomeCliente, cnpj) VALUES (?, ?)";
			
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setString(1, cliente.getNomeCliente());
			stmt.setString(2, cliente.getCnpj());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}
	

	public boolean altera(Cliente cliente) throws SQLException {
		String strSql = "UPDATE clientes set nomeCliente = ?, cnpj = ? WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
					
			// Seta valores
			stmt.setString(1, cliente.getNomeCliente());
			stmt.setString(2, cliente.getCnpj());
			stmt.setInt(3, cliente.getId());
			
			//stmt.executeQuery();
			stmt.executeUpdate();
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return true;
	}


	public boolean exclui(Cliente cliente) throws SQLException {
		String strSql = "DELETE FROM clientes WHERE id = ?";
		
		try {
			// Prepara Insert no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			// Seta valores
			stmt.setInt(1, cliente.getId());
			
			stmt.executeUpdate();	

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		
		return true;
	}
}
