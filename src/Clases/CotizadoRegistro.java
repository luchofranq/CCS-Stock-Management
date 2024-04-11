package Clases;

import java.math.BigDecimal;


public class CotizadoRegistro {

	private String numeroDeParte;
	private int cantidad;
	private BigDecimal precioUnitario;
	private BigDecimal precioTotal;
	private String tipo;
	
	
	
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNumeroDeParte() {
		return numeroDeParte;
	}
	public void setNumeroDeParte(String numeroDeParte) {
		this.numeroDeParte = numeroDeParte;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	
	
	
	
}
