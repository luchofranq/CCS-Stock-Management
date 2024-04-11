package Clases;

import java.math.BigDecimal;

public class Repuesto extends RepuestoAbstract {
	protected BigDecimal precioLocal;
	protected BigDecimal precioExterior;
	public BigDecimal getPrecioLocal() {
		return precioLocal;
	}
	public void setPrecioLocal(BigDecimal precioLocal) {
		this.precioLocal = precioLocal;
	}
	public BigDecimal getPrecioExterior() {
		return precioExterior;
	}
	public void setPrecioExterior(BigDecimal precioExterior) {
		this.precioExterior = precioExterior;
	}
	public Repuesto(String numeroDeParte, String descripcion, int existencia, BigDecimal precioLocal, BigDecimal precioExterior) {
		this.numeroDeParte = numeroDeParte;
		this.descripcion = descripcion;
		this.existencia = existencia;
		this.precioLocal = precioLocal;
		this.precioExterior=precioExterior;
	}

}
