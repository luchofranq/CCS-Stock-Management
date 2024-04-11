package Clases;

public class PDFDataHistorial {

	private String fecha;
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	private int currentRow;

	public void setCurrentRow(int i) {
		// TODO Auto-generated method stub
		this.currentRow=i;
	}
	public int getCurrentRow() {
		// TODO Auto-generated method stub
		return currentRow;
	}
}
