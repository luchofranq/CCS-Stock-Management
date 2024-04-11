package DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import BD.ConexionDB;
import Clases.Repuesto;

public class Repuesto_DAO {



	
	
	public boolean agregarRespuesto(Repuesto repuesto) {
		if (!numParteDuplicado(repuesto.getNumeroDeParte())) {
			String numeroDeParte = repuesto.getNumeroDeParte();
			int existencia = repuesto.getExistencia();
			BigDecimal precio = repuesto.getPrecioLocal();
			BigDecimal precioExt= repuesto.getPrecioExterior();
			String descripcion = repuesto.getDescripcion();

			String sql = "INSERT INTO repuesto(numeroDeParte, existencias, precioLocal, descripcion, precioExterior) VALUES (?, ?, ?, ?,?)";

			try (Connection conexion = ConexionDB.getConnection();
					PreparedStatement statement = conexion.prepareStatement(sql)) {
				statement.setString(1, numeroDeParte);
				statement.setInt(2, existencia);
				statement.setBigDecimal(3, precio);
				statement.setString(4, descripcion);
				statement.setBigDecimal(5, precioExt);

				int filasAfectadas = statement.executeUpdate();
				if (filasAfectadas > 0) {
					System.out.print("repuestos agregados");
					return true;
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 46: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				
			}
		} else {
			JOptionPane.showMessageDialog(null, "El ID ya existe en la base de datos.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	public ArrayList<Repuesto> consultarTodosLosRepuestos() {
		String query = "SELECT * FROM `repuesto` ORDER BY `numeroDeParte` ";
		Connection conexion = ConexionDB.getConnection();
		ArrayList<Repuesto> repuestos= new ArrayList<Repuesto>();
		try {

			Statement statement = conexion.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Repuesto repuesto = new Repuesto(resultSet.getString("numeroDeParte"), resultSet.getString("descripcion"),
						resultSet.getInt("existencias"), resultSet.getBigDecimal("precioLocal"),resultSet.getBigDecimal("precioExterior"));
				repuestos.add(repuesto);
			}

		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 73: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
			e.printStackTrace();
		} finally {
			ConexionDB.closeConnection(conexion);
		}

		return repuestos;
	}

	public int consultarIdRepuesto(String numeroDeParte) {
		String query = "SELECT `IdRepuesto` FROM `repuesto` WHERE `numeroDeParte` = ?";
		 Connection conexion = ConexionDB.getConnection();
		int IdRepuesto = 0;
		try {

			PreparedStatement consultaId = conexion.prepareStatement(query);
			consultaId.setString(1, numeroDeParte);
		ResultSet	resultSet = consultaId.executeQuery();
			if (resultSet.next()) {
				IdRepuesto = resultSet.getInt("IdRepuesto");
				return IdRepuesto;
			}
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 98: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
		} finally {
			ConexionDB.closeConnection(conexion);
		}

		return IdRepuesto;
	}

	public Repuesto consultarRepuesto(int IdRepuesto) {
		String query = "SELECT * FROM `repuesto` WHERE `IdRepuesto` = ?";

		Connection conexion = ConexionDB.getConnection();

		try {

			PreparedStatement prepareStatement = conexion.prepareStatement(query);
			prepareStatement.setInt(1, IdRepuesto);
		ResultSet	resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {

			String	numeroDeParte = resultSet.getString("numeroDeParte");
				int existencia = resultSet.getInt("existencias");
				BigDecimal precio = resultSet.getBigDecimal("precioLocal");
				BigDecimal precioExt = resultSet.getBigDecimal("precioExterior");
				String descripcion = resultSet.getString("descripcion");

				return new Repuesto(numeroDeParte, descripcion, existencia, precio,precioExt);
			}
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 129: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
		} finally {
			ConexionDB.closeConnection(conexion);
		}
		return null;
	}

	public boolean updateRepuesto(Repuesto repuesto, String numeroDeParte) {
	String	query = "UPDATE `repuesto` SET `numeroDeParte` = ?, `existencias` = ?, `precioLocal` = ?, `descripcion` = ? WHERE `numeroDeParte` = ?";

		 Connection conexion = ConexionDB.getConnection();
		try {

			PreparedStatement prepareStatement = conexion.prepareStatement(query);

			prepareStatement.setString(1, repuesto.getNumeroDeParte());
			prepareStatement.setInt(2, repuesto.getExistencia());
			prepareStatement.setBigDecimal(3, repuesto.getPrecioLocal());
			prepareStatement.setString(4, repuesto.getDescripcion());
			prepareStatement.setString(5, numeroDeParte);
			prepareStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 153: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
			return false;
		} finally {
			ConexionDB.closeConnection(conexion);
		}
	}

	public void borrarRepuesto(Repuesto repuesto) {
		String query = "DELETE FROM `repuesto` WHERE `numeroDeParte` = ?";
		 Connection conexion = ConexionDB.getConnection();
		try {

			PreparedStatement prepareStatement = conexion.prepareStatement(query);
			prepareStatement.setString(1, repuesto.getNumeroDeParte());
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 171: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
		} finally {
			ConexionDB.closeConnection(conexion);
		}
	}

	private boolean numParteDuplicado(String numeroDeParte) {
		String query = "SELECT COUNT(*) FROM repuesto WHERE `numeroDeParte` = ?";
		 Connection conexion = ConexionDB.getConnection();
		try {

			PreparedStatement statement = conexion.prepareStatement(query);
			statement.setString(1, numeroDeParte);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);
			return count > 0;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 190: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
			return false;
		}
	}

	public ArrayList<Repuesto> menorCantidad() {
	String	query = "SELECT * FROM `repuesto` ORDER BY `existencias` LIMIT 7 ";
		 Connection conexion = ConexionDB.getConnection();
		 ArrayList<Repuesto> repuestos= new ArrayList<Repuesto>();
		try {

			Statement statement = conexion.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Repuesto repuesto = new Repuesto(resultSet.getString("numeroDeParte"), resultSet.getString("descripcion"),
						resultSet.getInt("existencias"), resultSet.getBigDecimal("precioLocal"), resultSet.getBigDecimal("precioExterior"));
				repuestos.add(repuesto);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 212: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
		} finally {
			ConexionDB.closeConnection(conexion);
		}

		return repuestos;

	}

	public ArrayList<Repuesto> consultaCuadro() {
		String query = "SELECT * FROM `repuesto` WHERE `existencias` <= 3 ORDER BY `existencias`;";
		 Connection conexion = ConexionDB.getConnection();
		 ArrayList<Repuesto> repuestos= new ArrayList<Repuesto>();
		try {

			Statement statement = conexion.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Repuesto repuesto = new Repuesto(resultSet.getString("numeroDeParte"), resultSet.getString("descripcion"),
						resultSet.getInt("existencias"), resultSet.getBigDecimal("precioLocal"), resultSet.getBigDecimal("precioExterior"));
				repuestos.add(repuesto);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 238: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
		} finally {
			ConexionDB.closeConnection(conexion);
		}

		return repuestos;

	}

	public boolean repuestosCantidadMinimo() {
		String query = "SELECT COUNT(*) FROM repuesto WHERE `existencias` < 3";
		 Connection conexion = ConexionDB.getConnection();
		try {
			PreparedStatement statement = conexion.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int count = resultSet.getInt(1);
			return count > 0;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 258: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
			return false;
		}
	}

	public ArrayList<Repuesto> repuestosMenoresAminimo(int minStock) {
	    String query = "SELECT * FROM `repuesto` WHERE `existencias` <= ? ORDER BY `existencias`";
	    ArrayList<Repuesto> repuestos = new ArrayList<>();

	    try (Connection conexion = ConexionDB.getConnection();
	         PreparedStatement statement = conexion.prepareStatement(query)) {
	        statement.setInt(1, minStock);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                Repuesto repuesto = new Repuesto(
	                        resultSet.getString("numeroDeParte"),
	                        resultSet.getString("descripcion"),
	                        resultSet.getInt("existencias"),
	                        resultSet.getBigDecimal("precioLocal"),
	                        resultSet.getBigDecimal("precioExterior"));
	                repuestos.add(repuesto);
	            }
	        }
	    } catch (SQLException e) {
	    	JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 283: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
	    }

	    return repuestos;
	}

	
	
	public void updateCantRep(int idRep, int cant) {
	    String querySelect = "SELECT existencias FROM repuesto WHERE IdRepuesto = ?";
	    String queryUpdate = "UPDATE repuesto SET existencias = ? WHERE IdRepuesto = ?";
	    Connection conexion = ConexionDB.getConnection();
	    try {
	        // Obtener existencias actuales
	        PreparedStatement selectStatement = conexion.prepareStatement(querySelect);
	        selectStatement.setInt(1, idRep);
	        ResultSet resultSet = selectStatement.executeQuery();
	        int existenciasActuales = 0;
	        if (resultSet.next()) {
	            existenciasActuales = resultSet.getInt("existencias");
	        }

	        // Calcular nuevas existencias
	        int nuevasExistencias = existenciasActuales - cant;

	        // Actualizar existencias
	        PreparedStatement updateStatement = conexion.prepareStatement(queryUpdate);
	        updateStatement.setInt(1, nuevasExistencias);
	        updateStatement.setInt(2, idRep);
	        updateStatement.executeUpdate();

	      
	    } catch (SQLException e) {
	    	JOptionPane.showMessageDialog(null, "Error Repuesto DAO: linea 317: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			
	        
	    } finally {
	        ConexionDB.closeConnection(conexion);
	    }
	}
	

}