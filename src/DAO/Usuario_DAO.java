package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import BD.ConexionDB;
import Clases.Usuario;

public class Usuario_DAO {

	private String usuario;
	private String password;
	private String sql;
	private String passCorrecto = null;
	private String usuarioCorrecto = null;



	public void guardarUsuario(Usuario user) {

		ConexionDB.getConnection();
		usuario = user.getUsuario();
		password = user.getPassword();

		sql = "insert into Usuario(usuario, password) values ('" + usuario + "', '" + password + "');";

		Connection conexion = ConexionDB.getConnection();
		try {
			Statement	st = conexion.createStatement();
			st.executeUpdate(sql);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error user DAO: linea 38: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConexionDB.closeConnection(conexion);
		}
	}

	public boolean consultarUsuario(Usuario user) {

		usuario = user.getUsuario();
		password = user.getPassword();
		Connection	conexion = ConexionDB.getConnection();
		sql = "SELECT usuario, password FROM Usuario";
		try {

			
			PreparedStatement pst = conexion.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				usuarioCorrecto = rs.getString(1);
				passCorrecto = rs.getString(2);
			}

			if (usuario.equals(usuarioCorrecto) && password.equals(passCorrecto)) {

				return true;

			} else if (!usuario.equals(usuarioCorrecto) || password.equals(passCorrecto)) {

				return false;
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error user DAO: linea 71: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		} finally {
			ConexionDB.closeConnection(conexion);

		}
		return false;
	}

}
