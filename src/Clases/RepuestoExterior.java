package Clases;
import java.math.BigDecimal;
public class RepuestoExterior extends Cotizado{

	private BigDecimal precioExterior;

	public BigDecimal getPrecioExterior() {
		return precioExterior;
	}

	public void setPrecioExterior(BigDecimal precioExterior) {
		this.precioExterior = precioExterior;
	}
	
	public void setTipo() {
		this.tipo="EXTERIOR";
	}
	public String getTipo() {
		return "EXTERIOR";
	}
	
}
