package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericDao {
	private static Connection c;

	public static Connection conexao() throws ClassNotFoundException, SQLException {
		String hostName = "localhost";
		String dbName = "livraria";
		String user = "UL";
		String senha = "123456";

		Class.forName("net.sourceforge.jtds.jdbc.Driver");

		Connection c = DriverManager.getConnection("jdbc:jtds:sqlserver://" + hostName + ":1433;databaseName=" + dbName + ";user=" + user + ";password=" + senha);
		
		return c;
	}
}
