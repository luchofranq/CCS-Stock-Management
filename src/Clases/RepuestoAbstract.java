package Clases;


public abstract class RepuestoAbstract {

	protected String numeroDeParte;
	protected String descripcion;
	protected int existencia;
	
	
	
	
	public String getNumeroDeParte() {
		return numeroDeParte;
	}
	public void setNumeroDeParte(String numeroDeParte) {
		this.numeroDeParte = numeroDeParte;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getExistencia() {
		return existencia;
	}
	public void setExistencia(int existencia) {
		this.existencia = existencia;
	}


}
