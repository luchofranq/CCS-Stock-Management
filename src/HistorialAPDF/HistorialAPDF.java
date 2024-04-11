package HistorialAPDF;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import Clases.PDFDataHistorial;

public class HistorialAPDF {

   

    public static void addTableToPDF(JTable table, PDFDataHistorial data) {
       
    	  String currentDir = System.getProperty("user.dir");
          String relativePath = "resources" + File.separator + "historialpdf.pdf";
          String existingPdfFilePath = currentDir + File.separator + relativePath;
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            PDDocument document = PDDocument.load(new File(existingPdfFilePath));
            PDPage page = document.getPage(0);

           

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page, AppendMode.APPEND, true,
                    true)) {
                data.setCurrentRow(0);

                drawTable(contentStream, document, page, table, data);

                String fecha = data.getFecha().toUpperCase();

                contentStream.beginText();
                contentStream.newLineAtOffset(520, 670);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText(fecha);
                contentStream.endText(); // End text block for the date
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int selection = fileChooser.showSaveDialog(null);
            if (selection == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = fileChooser.getSelectedFile();
                File outputFile = new File(selectedDirectory,
                        "HISTORIAL COTIZACIONES " + data.getFecha() + ".pdf");
                document.save(outputFile);
                Desktop.getDesktop().open(outputFile);
            }

            document.close();
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException e) {
        	JOptionPane.showMessageDialog(null, "Error HistToPdf: linea 62: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void drawTable(PDPageContentStream contentStream, PDDocument document, PDPage page, JTable table,
    		PDFDataHistorial data) throws IOException {
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
				float minTextWidth = Float.MAX_VALUE;
				float textWidth = PDType1Font.HELVETICA.getStringWidth("") / 1000f * 12; // Tamaño de fuente 12
				minTextWidth = Math.min(minTextWidth, textWidth);
				   
				
				

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
			float[] columnWidths, PDFDataHistorial data) throws IOException {
		contentStream.setFont(PDType1Font.HELVETICA, 12);
		// contentStream.setNonStrokingColor(0, 0, 0); // Color negro para el texto
		
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
			contentStream.showText("");
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
}

