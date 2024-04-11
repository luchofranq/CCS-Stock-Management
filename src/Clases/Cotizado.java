package Clases;

import java.math.BigDecimal;

import DAO.Repuesto_DAO;

public abstract class Cotizado {

	protected Repuesto_DAO repdao = new Repuesto_DAO();
	protected Repuesto repuesto;
	protected int idrepuestoCotizado;
	protected int cantidad;
	protected BigDecimal precioTotal;
	protected int idRepuesto;
	protected String tipo;

	public void setRepuesto(Repuesto repuesto) {
		if (repuesto != null) {
			this.repuesto = repuesto;
		} else {
			// Puedes lanzar una excepción, mostrar un mensaje de error, o tomar otra acción
			// según tus necesidades.
			throw new IllegalArgumentException("El objeto repuesto no puede ser nulo.");
		}
	}

	public Repuesto getRepuesto() {
		return repuesto;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public int getIdRepuesto() {
		return idRepuesto;
	}

	public void setIdRepuesto() {
		if (repuesto != null) {
			this.idRepuesto = repdao.consultarIdRepuesto(repuesto.getNumeroDeParte());
		} else {
			throw new NullPointerException("El objeto repuesto no ha sido inicializado.");
		}
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getIdrepuestoCotizado() {
		return idrepuestoCotizado;
	}

	public void setIdrepuestoCotizado(int idrepuestoCotizado) {
		this.idrepuestoCotizado = idrepuestoCotizado;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {

		this.tipo = tipo;

	}

}
