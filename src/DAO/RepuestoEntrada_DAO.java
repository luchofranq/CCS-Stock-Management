package DAO;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Clases.Repuesto;
import Clases.RepuestoEntrada;
import BD.ConexionDB;

public class RepuestoEntrada_DAO {

	private String query;
	


	public static void limpiarRepuestos() {
		String sql = "{call DeleteWeeklyData()}";
		Connection connection = ConexionDB.getConnection();
		try (CallableStatement stmt = connection.prepareCall(sql);) {
			// Ejecutar el procedimiento almacenado
			limpiarEntrada();
			stmt.execute();
			System.out.println("Procedimiento almacenado DeleteWeeklyData ejecutado correctamente.");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepEn DAO: linea 33: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {

			try {
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error RepEn DAO: linea 39: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	public static void limpiarEntrada() {
		String sql = "{call DeleteEntrada()}";
		Connection connection = ConexionDB.getConnection();
		try (
				// Establecer la conexión a la base de datos

				// Preparar la llamada al procedimiento almacenado
				CallableStatement stmt = connection.prepareCall(sql);) {
			// Ejecutar el procedimiento almacenado
			stmt.execute();
			System.out.println("Procedimiento almacenado DeleteEntrada ejecutado correctamente.");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepEn DAO: linea 58: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {

			try {
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error RepEn DAO: linea 64: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	public static void limpiarStock() {
		String sql = "{call DeleteStock()}";
		Connection connection = ConexionDB.getConnection();
		try (
				// Establecer la conexión a la base de datos

				// Preparar la llamada al procedimiento almacenado
				CallableStatement stmt = connection.prepareCall(sql);) {
			// Ejecutar el procedimiento almacenado
			stmt.execute();
			System.out.println("Procedimiento almacenado DeleteStock ejecutado correctamente.");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepEn DAO: linea 83: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {

			try {
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error RepEn DAO: linea 89: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	public static void pasarAStock() {

		Connection connection = ConexionDB.getConnection();
		try {
			limpiarStock();
			Statement statement = connection.createStatement();
			String query = "SELECT numeroDeParte, descripcion, SUM(cantidad) AS total_cantidad FROM repuestoentrada GROUP BY numeroDeParte, descripcion";

			try (ResultSet resultSet = statement.executeQuery(query)) {

				while (resultSet.next()) {
					BigDecimal precioTotal = new BigDecimal(0);
					BigDecimal precioExt = new BigDecimal(0);
					Repuesto rep;
					rep = new Repuesto("", "", 0, precioTotal,precioExt);
					rep.setNumeroDeParte(resultSet.getString("numeroDeParte"));
					rep.setDescripcion(resultSet.getString("descripcion"));
					rep.setExistencia(resultSet.getInt("total_cantidad"));
					rep.setPrecioLocal(precioTotal);
					Repuesto_DAO repdao = new Repuesto_DAO();
					repdao.agregarRespuesto(rep);

				}
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepEn DAO: linea 120: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public ArrayList<RepuestoEntrada> consultarTodosLosRepuestosEntrada() {
		query = "SELECT * FROM `repuestoentrada` ORDER BY `numeroDeParte` ";
		Connection conexion = ConexionDB.getConnection();
		ArrayList<RepuestoEntrada> repuestos = new ArrayList<>();
		try {

			Statement	statement = conexion.createStatement();
			ResultSet	rs = statement.executeQuery(query);

			while (rs.next()) {
				 
				 RepuestoEntrada repuesto = new RepuestoEntrada(rs.getString("numeroDeParte"), rs.getString("descripcion"),
						rs.getInt("cantidad"), rs.getBigDecimal("costo"), rs.getBigDecimal("costoValorizado"),
						rs.getString("NumeroDespacho"), rs.getString("numeroPartida"));
				repuestos.add(repuesto);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepEn DAO: linea 142: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConexionDB.closeConnection(conexion);
		}

		return repuestos;
	}
}
