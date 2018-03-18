package br.com.constran.treinamentos.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.constran.treinamentos.jdbc.ConnectionFactory;
import br.com.constran.treinamentos.jdbc.modelo.LoginUsuario;


public class LoginDao {

	private Connection conn;
	
	@Autowired
	private LoginUsuario loginUsuario;
	
	
	public LoginDao() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	
	public String validaLogin(String strUsuario, String strSenha, HttpSession session) throws SQLException {

		String strSql = "SELECT id, login, nome, grupoUsuario FROM Usuarios WHERE login=? AND senha=PASSWORD(?)";
		String strNome;
			
		try {
			// Prepara instrução no banco
			PreparedStatement stmt = conn.prepareStatement(strSql);
			
			stmt.setString(1, strUsuario);
			stmt.setString(2, strSenha);
			
			ResultSet rs = stmt.executeQuery();	
			
			if (rs.next()) {
				strNome = rs.getString("nome");
				
				loginUsuario = new LoginUsuario();
				
				loginUsuario.setId(rs.getInt("id"));
				loginUsuario.setLogin(rs.getString("login"));
				loginUsuario.setNome(rs.getString("nome"));
				loginUsuario.setGrupoUsuario(rs.getString("grupoUsuario"));
				
				session.setAttribute("sLoginUsuario", loginUsuario);
				session.setAttribute("menuAtivo", "Pesquisa");
				
			} else {
				strNome = "";
			}
			
			rs.close();
			stmt.close();
			conn.close();
				
			return strNome;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}				
	}
}
