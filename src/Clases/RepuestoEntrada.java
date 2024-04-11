package Clases;

import java.math.BigDecimal;

public class RepuestoEntrada extends RepuestoAbstract {
	private String numeroDespacho;
	private String numeroPartida;
	private BigDecimal costo;
	private BigDecimal costoValorizado;

	public RepuestoEntrada(String numeroDeParte, String descripcion, int existencia, 
			BigDecimal costo, BigDecimal costoValorizado, String numeroDespacho, String numeroPartida) {

		// TODO Auto-generated constructor stub
		this.numeroDespacho = numeroDespacho;
		this.numeroPartida = numeroPartida;
		this.numeroDeParte = numeroDeParte;
		this.descripcion = descripcion;
		this.existencia = existencia;
		this.costo = costo;
		this.costoValorizado = costoValorizado;
	}

	public String getNumeroDespacho() {
		return numeroDespacho;
	}

	public void setNumeroDespacho(String numeroDespacho) {
		this.numeroDespacho = numeroDespacho;
	}

	public String getNumeroPartida() {
		return numeroPartida;
	}

	public void setNumeroPartida(String numeroPartida) {
		this.numeroPartida = numeroPartida;
	}
	public BigDecimal getCosto() {
		return costo;
	}
	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}
	public BigDecimal getCostoValorizado() {
		return costoValorizado;
	}
	public void setCostoValorizado(BigDecimal costoValorizado) {
		this.costoValorizado = costoValorizado;
	}
}
