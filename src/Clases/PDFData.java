package Clases;

import java.math.BigDecimal;


public class PDFData {
	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getCondicionDePago() {
		return condicionDePago;
	}

	public void setCondicionDePago(String condicionDePago) {
		this.condicionDePago = condicionDePago;
	}

	public String getValidezCotizacion() {
		return validezCotizacion;
	}

	public void setValidezCotizacion(String validezCotizacion) {
		this.validezCotizacion = validezCotizacion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getAtte() {
		return atte;
	}

	public void setAtte(String atte) {
		this.atte = atte;
	}



	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}



	private String nombreCliente;
	private String condicionDePago;
	private String validezCotizacion;
	private String fecha;
	private String atte;
	private BigDecimal precioTotal;
	private int currentRow;
	private String tipo;
	public void setCurrentRow(int i) {
		// TODO Auto-generated method stub
		this.currentRow=i;
	}
	public int getCurrentRow() {
		// TODO Auto-generated method stub
		return currentRow;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


}
