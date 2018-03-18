package br.com.constran.treinamentos.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public Connection getConnection() {
		try {
			
			//Class.forName("com.mysql.jdbc.Driver");
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			
			return DriverManager.getConnection("jdbc:mysql://192.168.1.24:3306/treinamentos", "root", "@D31n");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	public Connection getConnectionLinkedServer() throws ClassNotFoundException {
		try {
			//Class.forName("net.sourceforge.jtds.jdbc.Driver");
			//DriverManager.registerDriver(new net.sourceforge.jtds.jdbc.Driver());
			
			//DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			//Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			return DriverManager.getConnection("jdbc:sqlserver://192.168.1.24:1433;user=sa;password=S@tg3r!41;databaseName=Constran");
			//return DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.16:1433/Constran;instance=MSSQLSERVER", "sa", "S@tg3r!41");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
