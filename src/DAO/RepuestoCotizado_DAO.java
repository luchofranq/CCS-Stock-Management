package DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import BD.ConexionDB;
import Clases.RepuestoExterior;
import Clases.RepuestoLocal;

public class RepuestoCotizado_DAO {
	private 	Repuesto_DAO repdao= new Repuesto_DAO();
	private  String sql;
	private String query;
	private String tipoExt = "EXTERIOR";
	private String tipo = "LOCAL";
	
	
	public boolean agregarRespuestoCotizado(RepuestoLocal repuestoCotizado) {
	         sql = "INSERT INTO repuestocotizado (cantidad, precioTotal, repuesto_IdRepuesto, tipo, precioUnitario) VALUES (?, ?, ?, ?,?)";
	        try (
	            Connection conexion = ConexionDB.getConnection();
	            PreparedStatement ps = conexion.prepareStatement(sql)
	        ) {
	        	
	        	repdao.updateCantRep(repuestoCotizado.getIdRepuesto(), repuestoCotizado.getCantidad());
	  	      
	        	
	            ps.setInt(1, repuestoCotizado.getCantidad());
	            ps.setBigDecimal(2, repuestoCotizado.getPrecioTotal());
	            ps.setInt(3, repuestoCotizado.getIdRepuesto());
	            ps.setString(4, repuestoCotizado.getTipo());
	           ps.setBigDecimal(5, repuestoCotizado.getRepuesto().getPrecioLocal());
	            ps.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	        	JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 42: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				
	            return false;
	        }
	    }
	 
	 public boolean agregarRespuestoExterior(RepuestoExterior repuestoCotizado) {
	         sql = "INSERT INTO repuestocotizado (cantidad, precioTotal, repuesto_IdRepuesto, tipo, precioUnitario) VALUES (?, ?, ?, ?,?)";
	        try (
	            Connection conexion = ConexionDB.getConnection();
	            PreparedStatement ps = conexion.prepareStatement(sql)
	        ) {
	           
	        	repdao.updateCantRep(repuestoCotizado.getIdRepuesto(), repuestoCotizado.getCantidad());
	        	
	        	ps.setInt(1, repuestoCotizado.getCantidad());
	            ps.setBigDecimal(2, repuestoCotizado.getPrecioTotal());
	            ps.setInt(3, repuestoCotizado.getIdRepuesto());
	            ps.setString(4, repuestoCotizado.getTipo());
	            ps.setBigDecimal(5, repuestoCotizado.getPrecioExterior());
	            ps.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	        	JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 65: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            return false;
	        }
	    }

	 	
	 

	public ArrayList<RepuestoLocal> consultarTodosLosRepuestosLocales() {
		 query = "SELECT * FROM `repuestocotizado` WHERE `tipo` = ? ORDER BY `cantidad`";
		 ArrayList<RepuestoLocal> repuestosCotizados = new ArrayList<>();
		 Connection conexion = ConexionDB.getConnection();
				try {

			PreparedStatement prepareStatement = conexion.prepareStatement(query);
			prepareStatement.setString(1,tipo );
			ResultSet	 rs = prepareStatement.executeQuery();

			while (rs.next()) {
			
				RepuestoLocal repuestoCotizado = new RepuestoLocal();
				repuestoCotizado.setCantidad(rs.getInt("cantidad"));
				repuestoCotizado.setPrecioTotal(rs.getBigDecimal("precioTotal"));
				repuestoCotizado.setRepuesto(repdao.consultarRepuesto(rs.getInt("repuesto_IdRepuesto")));
				repuestoCotizado.setIdRepuesto();
				repuestoCotizado.setIdrepuestoCotizado(rs.getInt("IdRepuestoCotizado"));
				repuestoCotizado.setTipo(rs.getString("tipo"));
				
				repuestosCotizados.add(repuestoCotizado);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 97: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConexionDB.closeConnection(conexion);
		}

		return repuestosCotizados;
	}

	public ArrayList<RepuestoExterior> consultarTodosLosRepuestosExteriores() {
		ArrayList<RepuestoExterior> repuestosCotizadosExt = new ArrayList<>();
		query = "SELECT * FROM `repuestocotizado` WHERE `tipo` = ? ORDER BY `cantidad`  ";
		 Connection conexion = ConexionDB.getConnection();
		
			try {

			PreparedStatement prepareStatement = conexion.prepareStatement(query);
			prepareStatement.setString(1,tipoExt );
			ResultSet	 rs = prepareStatement.executeQuery();

			while (rs.next()) {
				
				RepuestoExterior repuestoCotizadoExt = new RepuestoExterior();
				repuestoCotizadoExt.setCantidad(rs.getInt("cantidad"));
				repuestoCotizadoExt.setPrecioTotal(rs.getBigDecimal("precioTotal"));
				repuestoCotizadoExt.setRepuesto(repdao.consultarRepuesto(rs.getInt("repuesto_IdRepuesto")));
				repuestoCotizadoExt.setIdRepuesto();
				repuestoCotizadoExt.setIdrepuestoCotizado(rs.getInt("IdRepuestoCotizado"));
				repuestoCotizadoExt.setTipo(rs.getString("tipo"));
				repuestoCotizadoExt.setPrecioExterior(rs.getBigDecimal("precioUnitario"));
				repuestosCotizadosExt.add(repuestoCotizadoExt);
			}

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 130: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConexionDB.closeConnection(conexion);
		}

		return repuestosCotizadosExt;
	}
	

	public RepuestoLocal consultarRepuestoCotizado(int IdRepuestoCotizado) {
		 query = "SELECT * FROM `repuestocotizado` WHERE `IdRepuestoCotizado` = ?";

		 Connection conexion = ConexionDB.getConnection();

		try {

			PreparedStatement prepareStatement = conexion.prepareStatement(query);
			prepareStatement.setInt(1, IdRepuestoCotizado);
			ResultSet		 rs = prepareStatement.executeQuery();
			if (rs.next()) {

				RepuestoLocal repuestoCotizado = new RepuestoLocal();
				repuestoCotizado.setCantidad(rs.getInt("cantidad"));
				repuestoCotizado.setPrecioTotal(rs.getBigDecimal("precioTotal"));
				repuestoCotizado.setRepuesto(repdao.consultarRepuesto(repuestoCotizado.getIdRepuesto()));
				repuestoCotizado.setIdrepuestoCotizado(IdRepuestoCotizado);
				repuestoCotizado.setTipo(rs.getString("tipo"));
				return repuestoCotizado;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 160: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConexionDB.closeConnection(conexion);
		}
		return null;
	}

	public boolean updateRepuestoCotizado(RepuestoLocal repuestoCotizado, int idrepuestocotizado) {
         query = "UPDATE repuestocotizado SET cantidad = ?, precioTotal = ?, repuesto_IdRepuesto = ?, tipo = ? WHERE IdRepuestoCotizado = ?";
        try (
            Connection conexion = ConexionDB.getConnection();
            PreparedStatement prepareStatement = conexion.prepareStatement(query)
        ) {
            prepareStatement.setInt(1, repuestoCotizado.getCantidad());
            prepareStatement.setBigDecimal(2, repuestoCotizado.getPrecioTotal());
            prepareStatement.setInt(3, repuestoCotizado.getIdRepuesto());
            prepareStatement.setString(4, repuestoCotizado.getTipo());
            prepareStatement.setInt(5, idrepuestocotizado);
            prepareStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 181: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

	public void borrarRepuestoCotizado(int idRepuestoCotizado) throws SQLException {
		insertRegistro(idRepuestoCotizado);
		 query = "DELETE FROM `repuestocotizado` WHERE `IdRepuestoCotizado` = ?";
		 Connection conexion = ConexionDB.getConnection();
	
		try {

			PreparedStatement prepareStatement = conexion.prepareStatement(query);
			prepareStatement.setInt(1, idRepuestoCotizado);
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 198: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConexionDB.closeConnection(conexion);
		}
		
	}

	
	
	public void borrarRepuestosExteriores() throws SQLException {
		
		 query = "DELETE FROM `repuestocotizado` WHERE `tipo` = ?";
		 Connection conexion = ConexionDB.getConnection();
	
		try {

			PreparedStatement prepareStatement = conexion.prepareStatement(query);
			prepareStatement.setString(1, tipoExt);
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 219: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConexionDB.closeConnection(conexion);
		}
		
	}
	public void borrarRepuestosLocales() throws SQLException {
		
		 query = "DELETE FROM `repuestocotizado` WHERE `tipo` = ?";
		 Connection conexion = ConexionDB.getConnection();
	
		try {

			PreparedStatement prepareStatement = conexion.prepareStatement(query);
			prepareStatement.setString(1, tipo);
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 237: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			ConexionDB.closeConnection(conexion);
		}
		
	}
	
	public static void insertRegistro(int idRepuestoCotizado) {
	    // Llamada al procedimiento almacenado
	    String callProcedure = "{CALL insertRegistro(?)}";
	    
	    // Establecer la conexión a la base de datos
	    Connection connection = ConexionDB.getConnection();
	    
	    try (CallableStatement stmt = connection.prepareCall(callProcedure)) {
	        // Establecer el valor del parámetro idRepuestoCotizado
	        stmt.setInt(1, idRepuestoCotizado);
	        
	        // Ejecutar el procedimiento almacenado
	        stmt.execute();
	        System.out.println("Procedimiento almacenado insertRegistro ejecutado correctamente.");
	    } catch (SQLException e) {
	    	JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 259: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    } finally {
	        try {
	            if (connection != null) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	        	JOptionPane.showMessageDialog(null, "Error RepCot DAO: linea 266: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}

}
