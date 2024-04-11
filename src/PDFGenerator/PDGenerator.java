package PDFGenerator;

import java.awt.Desktop;
import org.apache.pdfbox.text.PDFTextStripper;
import Clases.PDFData;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PDGenerator {

	
	public static void addTableToPDF(JTable table, PDFData data) {
		  String currentDir = System.getProperty("user.dir");
          String relativePath = "resources" + File.separator + "pruebapdf.pdf";
          String existingPdfFilePath = currentDir + File.separator + relativePath;
		try {

			try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Error UI PDGenerator: linea 36: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

			PDDocument document = PDDocument.load(new File(existingPdfFilePath));
			PDPage page = document.getPage(0);

			PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true, true);

			data.setCurrentRow(0);

			// Dibujar la tabla en una o más páginas

			// Draw the table on the current page or the new page
			drawTable(contentStream, document, page, table, data);
			String noteText = "";
			if (data.getTipo() == "EXTERIOR") {

				noteText = "Nota\n" + "Esta cotización no incluye flete ni derechos de importación\n"
						+ "Por favor informar cuenta de courier o el forwarder designado para recoger la carga.\n"
						+ "IMPORTANTE: No se proveeran repuestos y/o servicios a clientes que posean deuda vencida, sin excepción\n"
						+ "Revisar que los elementos requeridos, sean los correspondientes a su maquina, Flow no acepta devoluciones\n"
						+ "Incluir en la orden de compra los datos completos de Facturacion/Envio\n"
						+ "Datos Bancarios\n" + "Bank of America\n" + "800 Fifth Avenue , 8th Floor\n"
						+ "Seattle, WA 98104\n" + "ABA #02600959";
			} else if (data.getTipo() == "LOCAL") {
				noteText = "Nota\n"
						+ "IMPORTANTE: No se proveeran repuestos y/o servicios a clientes que posean deuda vencida, sin excepción\n"
						+ "Revisar que los elementos requeridos, sean los correspondientes a su maquina, Flow no acepta devoluciones\n"
						+ "Incluir en la orden de compra los datos completos de Facturacion/Envio\n"
						+ "Datos Bancarios\n" + "CUENTA CORRIENTE $(pesos): 042-3106/8\n"
						+ "CBU:  07200427 20000000310686 \n" + "BANCO: Santander Río Suc Retiro.\n"
						+ "NOMBRE:   FLOW INTERNATIONAL CORPORATION \n" + "CUIT:    30-69732816-1";
			}

			insertTextAtEndOfLastPage(document, noteText, table);

			String NombreCliente = data.getNombreCliente().toUpperCase();
			String CondicionDePago = data.getCondicionDePago().toUpperCase();
			String ValidezCotizacion = data.getValidezCotizacion().toUpperCase();
			String Fecha = data.getFecha().toUpperCase();
			String Atte = data.getAtte().toUpperCase();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			contentStream.newLineAtOffset(200, 670);
			contentStream.showText(NombreCliente);
			contentStream.endText();

			contentStream.beginText();
			contentStream.newLineAtOffset(200, 655);
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			contentStream.showText(CondicionDePago);
			contentStream.endText();

			contentStream.beginText();
			contentStream.newLineAtOffset(200, 640);
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			contentStream.showText(ValidezCotizacion);
			contentStream.endText();

			contentStream.beginText();
			contentStream.newLineAtOffset(520, 670);
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			contentStream.showText(Fecha);
			contentStream.endText();

			contentStream.beginText();
			contentStream.newLineAtOffset(520, 655);
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			contentStream.showText(Atte);
			contentStream.endText();

			contentStream.close();

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int seleccion = fileChooser.showSaveDialog(null);
			if (seleccion != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File selectedDirectory = fileChooser.getSelectedFile();

			File outputFile = new File(selectedDirectory,
					"COTIZACION " + data.getTipo() + " " + data.getNombreCliente() + " " + data.getFecha() + ".pdf");
			document.save(outputFile);

			document.close();

			Desktop.getDesktop().open(outputFile);

		} catch (

		IOException e) {
			JOptionPane.showMessageDialog(null, "Error PDGenerator: linea 129: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void drawTable(PDPageContentStream contentStream, PDDocument document, PDPage page, JTable table,
			PDFData data) throws IOException {
		float margin = 20;
		float startY = page.getMediaBox().getHeight() - margin - 200;
		contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
		try {
			float yPosition = startY;

			DefaultTableModel model = (DefaultTableModel) table.getModel();
			TableRowSorter<DefaultTableModel> newSorter = new TableRowSorter<>(model);

			newSorter.setComparator(4, new Comparator<Double>() {
				public int compare(Double price1, Double price2) {
					return Double.compare(price1, price2);
				}
			});

			table.setRowSorter(newSorter);

			int rowCount = model.getRowCount();
			int columnCount = model.getColumnCount();
			float[] columnWidths = new float[columnCount];

			// Calcular anchos y alturas de columnas y filas
			for (int col = 0; col < columnCount; col++) {
				float maxWidth = 0;

				// Calcular el ancho máximo de texto en el encabezado de la columna
				String headerText = table.getColumnName(col);
				float headerWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(headerText) / 1000f * 12; // Tamaño de
																										// fuente
				// 12
				maxWidth = Math.max(maxWidth, headerWidth);

				// Calcular el ancho máximo de los elementos de la columna
				for (int row = 0; row < rowCount; row++) {
					String text = model.getValueAt(row, col).toString();
					float width = PDType1Font.HELVETICA.getStringWidth(text) / 1000f * 12; // Tamaño de fuente 12
					maxWidth = Math.max(maxWidth, width);
				}

				// Obtener el ancho mínimo de la columna considerando los datos de la última
				// fila (textobajo)
				String[] textobajo;
				float minTextWidth = Float.MAX_VALUE;
				
				    textobajo = new String[]{"Dolar", "Estadounidense", "Total:", "", "USD " + data.getPrecioTotal()};
			
				
				for (String text : textobajo) {
					float textWidth = PDType1Font.HELVETICA.getStringWidth(text) / 1000f * 12; // Tamaño de fuente 12
					minTextWidth = Math.min(minTextWidth, textWidth);
				}

				// Tomar el máximo entre el ancho mínimo del texto y el ancho del encabezado
				float minWidth = Math.max(minTextWidth + 10, headerWidth + 10);
				maxWidth = Math.max(maxWidth, minWidth);

				columnWidths[col] = maxWidth + 10;
			}
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			// Dibujar el encabezado
			yPosition = drawRow(contentStream, margin, yPosition, columnWidths, model, 0, true);

			// Dibujar las filas de datos
			for (int row = 0; row < rowCount; row++) {
				// Dibujar fila de datos
				contentStream.setFont(PDType1Font.HELVETICA, 12);
				yPosition = drawRow(contentStream, margin, yPosition, columnWidths, model, row, false);
				if (yPosition < margin) {
					// Nueva página si no hay suficiente espacio
					contentStream.close(); // Cerrar el flujo de contenido antes de crear una nueva página
					PDPage newPage = new PDPage(page.getMediaBox());
					document.addPage(newPage);

					contentStream = new PDPageContentStream(document, newPage, PDPageContentStream.AppendMode.APPEND,
							true);
					yPosition = newPage.getMediaBox().getHeight() - margin - 20;
					contentStream.setFont(PDType1Font.HELVETICA, 12); // Establecer la fuente para la nueva página

					// Dibujar el encabezado en la nueva página
					yPosition = drawRow(contentStream, margin, yPosition, columnWidths, model, 0, true);
				}
			}

			contentStream.setFont(PDType1Font.HELVETICA, 12); // Dibujar la fila del precio total al final de la parte
																// de la tabla en la nueva página
			// Espacio entre la última fila de datos y la fila del precio total
			drawTotalRow(contentStream, margin, yPosition, columnWidths, data);
		} finally {
			if (contentStream != null) {
				contentStream.close();
			}
		}
	}

	private static void drawTotalRow(PDPageContentStream contentStream, float margin, float yPosition,
			float[] columnWidths, PDFData data) throws IOException {
		contentStream.setFont(PDType1Font.HELVETICA, 12);
		// contentStream.setNonStrokingColor(0, 0, 0); // Color negro para el texto
		String[] textobajo;
		
		    textobajo = new String[]{"Dolar", "Estadounidense", "Total:", "", "USD " + data.getPrecioTotal()};
		
		float rowHeight = 12f + 10; // Tamaño de fuente 12 con un pequeño margen
		float xPosition = margin;

		// Dibujar cada celda de la fila
		for (int col = 0; col < columnWidths.length; col++) {
			// Dibujar celda
			contentStream.setNonStrokingColor(0f, 0f, 0f); // Color negro para las celdas de datos
			contentStream.addRect(xPosition, yPosition, columnWidths[col], rowHeight);
			contentStream.stroke();

			// Dibujar texto en la celda

			xPosition += columnWidths[col];
		}

		// Dibujar línea horizontal entre filas
		xPosition = margin;
		for (int col = 0; col < columnWidths.length; col++) {
			contentStream.moveTo(xPosition, yPosition);
			contentStream.lineTo(xPosition + columnWidths[col], yPosition);
			contentStream.stroke();
			xPosition += columnWidths[col];
		}
		xPosition = margin;
		yPosition -= rowHeight;
		for (int col = 0; col < columnWidths.length; col++) {
			// Dibujar celda
			contentStream.setNonStrokingColor(0f, 0f, 0f); // Color negro para las celdas de datos
			contentStream.addRect(xPosition, yPosition, columnWidths[col], rowHeight);
			contentStream.stroke();

			// Dibujar texto en la celda
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 12);

			contentStream.newLineAtOffset(xPosition + 3f, yPosition + 4f); // Ajustar el texto verticalmente
			contentStream.showText(textobajo[col]);
			contentStream.endText();

			xPosition += columnWidths[col];
		}

		// Dibujar línea horizontal entre filas
		xPosition = margin;
		for (int col = 0; col < columnWidths.length; col++) {
			contentStream.moveTo(xPosition, yPosition);
			contentStream.lineTo(xPosition + columnWidths[col], yPosition);
			contentStream.stroke();
			xPosition += columnWidths[col];
		}
	}

	private static float drawRow(PDPageContentStream contentStream, float margin, float yPosition, float[] columnWidths,
			DefaultTableModel model, int rowIndex, boolean isHeader) throws IOException {
		float xPosition = margin;
		float rowHeight = 12f + 10; // Tamaño de fuente 12 con un pequeño margen

// Color de fondo para el encabezado
		float totalWidth = 0;
		for (float width : columnWidths) {
			totalWidth += width;
		}

		// Color de fondo para el encabezado
		if (isHeader) {
			contentStream.setNonStrokingColor(0.00000f, 0.56078f, 0.62353f); // Color celeste claro para el encabezado
			contentStream.addRect(xPosition, yPosition, totalWidth, rowHeight);
			contentStream.fill();
		}

// Dibujar cada celda de la fila
		for (int col = 0; col < columnWidths.length; col++) {
// Obtener texto de la celda
			String text = "";
			if (isHeader) {
				text = model.getColumnName(col);
			} else {
				Object cellValue = model.getValueAt(rowIndex, col);
				if (cellValue != null) {
					text = cellValue.toString();
				}
			}

// Dibujar celda
			contentStream.setNonStrokingColor(0f, 0f, 0f); // Color negro para las celdas de datos
			contentStream.addRect(xPosition, yPosition, columnWidths[col], rowHeight);
			contentStream.stroke();

// Dibujar texto en la celda
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			contentStream.newLineAtOffset(xPosition + 3f, yPosition + 4f); // Ajustar el texto verticalmente
			contentStream.showText(text);
			contentStream.endText();

			xPosition += columnWidths[col];
		}

// Dibujar línea horizontal entre filas
		xPosition = margin;
		for (int col = 0; col < columnWidths.length; col++) {
			contentStream.moveTo(xPosition, yPosition);
			contentStream.lineTo(xPosition + columnWidths[col], yPosition);
			contentStream.stroke();
			xPosition += columnWidths[col];
		}

		return yPosition - rowHeight; // Devolver la nueva posición Y
	}

	public static void insertTextAtEndOfLastPage(PDDocument document, String compressedText, JTable table)
			throws IOException {
		String uncompressedText = decompress(compressedText);
		String[] lines = uncompressedText.split("\n");
		PDPage lastPage = document.getPage(document.getNumberOfPages() - 1); // Obtener la última página
		float fontSize = 12;

		// Obtener la última posición Y de la tabla
		float lastYPosition = findTableEndYPosition(document, lastPage, table);

		// Obtener la altura total del texto a insertar
		float totalTextHeight = lines.length * (1.5f * fontSize); // Espacio entre líneas

		// Calcular la nueva posición Y donde comenzará el texto
		float newStartYPosition = lastYPosition - totalTextHeight;

		// Verificar si el texto cabe en la página actual
		if (newStartYPosition >= 20) { // 20 es el margen inferior
			try (PDPageContentStream contentStream = new PDPageContentStream(document, lastPage,
					PDPageContentStream.AppendMode.APPEND, true)) {
				contentStream.setFont(PDType1Font.HELVETICA, fontSize);
				contentStream.beginText();
				contentStream.newLineAtOffset(15, newStartYPosition); // Mover a la nueva posición Y
				for (String line : lines) {
					if (line.equals("Nota") || line.equals("Datos Bancarios")) {
						contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize); // Establecer negrita
					} else {
						contentStream.setFont(PDType1Font.HELVETICA, fontSize); // Restaurar la fuente regular
					}
					contentStream.showText(line);
					contentStream.newLineAtOffset(0, -1.5f * fontSize); // Espacio entre líneas
				}
				contentStream.endText();
			}
		} else { // No cabe en la página actual, se necesita una nueva página
			PDPage newPage = new PDPage(lastPage.getMediaBox());
			document.addPage(newPage);

			try (PDPageContentStream contentStream = new PDPageContentStream(document, newPage,
					PDPageContentStream.AppendMode.APPEND, true)) {
				contentStream.setFont(PDType1Font.HELVETICA, fontSize);
				contentStream.beginText();
				contentStream.newLineAtOffset(15, lastPage.getMediaBox().getHeight() - 20); // Mover al margen inferior
																							// de la nueva página
				for (String line : lines) {
					if (line.equals("Nota") || line.equals("Datos Bancarios")) {
						contentStream.setFont(PDType1Font.HELVETICA_BOLD, fontSize); // Establecer negrita
					} else {
						contentStream.setFont(PDType1Font.HELVETICA, fontSize); // Restaurar la fuente regular
					}
					contentStream.showText(line);
					contentStream.newLineAtOffset(0, -1.5f * fontSize); // Espacio entre líneas
				}
				contentStream.endText();
			}
		}
	}

	public static float findTableEndYPosition(PDDocument document, PDPage page, JTable table) {
		float margin = 20; // Margen inferior
		PDRectangle mediaBox = page.getMediaBox();
		float startY = mediaBox.getHeight() - margin - 200; // Posición inicial Y de la tabla
		float rowHeight = 12f + 10; // Altura de fila
		float yPosition = startY;

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();

		// Calcular alturas de fila
		for (int row = 0; row < rowCount; row++) {
			yPosition -= rowHeight; // Restar la altura de la fila
			if (yPosition < margin) {
				// Nueva página si no hay suficiente espacio
				PDPage newPage = new PDPage(page.getMediaBox());
				document.addPage(newPage);

				yPosition = newPage.getMediaBox().getHeight() - margin - rowHeight; // Nueva posición Y en la nueva
																					// página
			}
		}

		return yPosition;
	}

	public static float findYPosition(PDDocument document, PDPage page, JTable table, float lastYPosition)
			throws IOException {
		int pageNumber = document.getPages().indexOf(page) + 1; // Obtener el número de página
		List<String> textLines = getTextLines(document, pageNumber);

		// Obtener la altura de la tabla
		int rowCount = table.getRowCount();
		float rowHeight = 12f + 10; // Altura de fila, tamaño de fuente 12 con un pequeño margen
		float tableHeight = rowCount * rowHeight;

		float fontSize = 12;
		float leading = 1.5f * fontSize;
		PDRectangle mediaBox = page.getMediaBox();

		// Calcular la posición Y final considerando el texto y la tabla
		float finalYPosition = mediaBox.getUpperRightY() - ((textLines.size() * leading) + tableHeight);

		// Devolver la posición Y final
		return finalYPosition;
	}

	private static List<String> getTextLines(PDDocument document, int pageNumber) throws IOException {
		PDFTextStripper stripper = new PDFTextStripper();
		stripper.setStartPage(pageNumber);
		stripper.setEndPage(pageNumber);

		String text = stripper.getText(document);

		List<String> lines = new ArrayList<>();
		String[] textLines = text.split("\n");
		for (String line : textLines) {
			if (!line.trim().isEmpty()) {
				lines.add(line.trim());
			}
		}

		return lines;
	}

	public static String decompress(String compressed) throws IOException {
		// Implementación de descompresión
		return compressed;
	}

}