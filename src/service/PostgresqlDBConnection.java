package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlDBConnection {

	private static PostgresqlDBConnection instance = null;
	private static Connection connection = null;

	public static Connection getConnection() {
		if (instance == null) {
			try {
				instance = new PostgresqlDBConnection();
			} catch (Exception e) {

			}
			assert (instance != null);
		}
		return connection;
	}

	private PostgresqlDBConnection() throws SQLException,
			ClassNotFoundException {

		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:6543/crm",
				"postgres",
				"qweasdzxc123");

	}
}
