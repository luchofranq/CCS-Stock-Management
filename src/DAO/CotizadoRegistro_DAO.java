package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JOptionPane;

import BD.ConexionDB;
import Clases.CotizadoRegistro;
import java.util.HashMap;

public class CotizadoRegistro_DAO {

	private String query;
	
	
	
	
	
	public ArrayList<CotizadoRegistro> todosLosCotizados() {
		query = "SELECT cantidad, precioTotal, tipo, numeroDeParte, precioUnitario FROM `repuestocotizado_registros`";
		Connection conexion = ConexionDB.getConnection();
		ArrayList<CotizadoRegistro> RegistrosCotizados = new ArrayList<>();
		try {
			 
			 PreparedStatement prepareStatement = conexion.prepareStatement(query);
			 ResultSet rs = prepareStatement.executeQuery();

			while (rs.next()) {
				CotizadoRegistro RegistroCotizado = new CotizadoRegistro();
				RegistroCotizado.setCantidad(rs.getInt("cantidad"));
				RegistroCotizado.setPrecioTotal(rs.getBigDecimal("precioTotal"));
				RegistroCotizado.setNumeroDeParte(rs.getString("numeroDeParte"));
				RegistroCotizado.setTipo(rs.getString("tipo"));
				RegistroCotizado.setPrecioUnitario(rs.getBigDecimal("precioUnitario"));
				RegistrosCotizados.add(RegistroCotizado);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error Cotizado DAO: linea 45: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
		} finally {
			ConexionDB.closeConnection(conexion);
		}

		return RegistrosCotizados;
	}

	public Map<String, Integer> calcularFrecuenciaNumerosParte() {
		query = "SELECT numeroDeParte, COUNT(*) AS frecuencia FROM repuestocotizado_registros GROUP BY numeroDeParte";
		Connection conexion = ConexionDB.getConnection();
		 Map<String, Integer> frecuenciaNumerosParte = new HashMap<>();
		try {
			PreparedStatement prepareStatement = conexion.prepareStatement(query);
			ResultSet	 rs = prepareStatement.executeQuery();
			while (rs.next()) {
				String numeroDeParte = rs.getString("numeroDeParte");
				int frecuencia = rs.getInt("frecuencia");
				frecuenciaNumerosParte.put(numeroDeParte, frecuencia);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error Cotizado DAO: linea 67: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConexionDB.closeConnection(conexion);
		}
		return frecuenciaNumerosParte;
	}

	public static void limpiarHistorial() {
		String sql = "{call limpiarHistorial()}";
		Connection connection = ConexionDB.getConnection();
		try (CallableStatement stmt = connection.prepareCall(sql);) {
			// Ejecutar el procedimiento almacenado

			stmt.execute();
			System.out.println("Procedimiento almacenado limpiarHistorial ejecutado correctamente.");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al ejecutar el procedimiento almacenado limpiarHistorial, Cotizado DAO: linea 84: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
		} finally {

			try {
				connection.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error Cotizado DAO: linea 91: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				
				
			}

		}
	}

}
