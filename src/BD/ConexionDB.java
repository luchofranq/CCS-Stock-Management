package BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConexionDB {

	private static final String URL = "jdbc:mysql://localhost:3306/flow_database_login";
	private static final String USER = "root";
	private static final String PASSWORD = "admin";

	public static String getUser() {
		return USER;
	}

	public static String getPassword() {
		return PASSWORD;
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			// Registrar el driver JDBC
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Establecer la conexión
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error al cargar el controlador JDBC: "+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
		}
		return connection;
	}

	// Método para cerrar la conexión
	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				
			}
		}
	}
}