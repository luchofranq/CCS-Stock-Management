package PanelInicioCuadro;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.IntervalCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;

import DAO.CotizadoRegistro_DAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class PanelInicioCuadro extends JPanel {
	private DefaultCategoryDataset dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private CotizadoRegistro_DAO cotizadoRegistroDAO;
	private JComboBox<String> comboBoxFiltro;
	private boolean running;

	public PanelInicioCuadro() {
		setName("Gráfico Dinámico");
		setLayout(new BorderLayout());

		cotizadoRegistroDAO = new CotizadoRegistro_DAO();

		// Crear el conjunto de datos
		dataset = new DefaultCategoryDataset();

		// Crear el gráfico de barras inicial tridimensional
		chart = ChartFactory.createBarChart3D("Porcentaje por numero de parte cotizado", // Título del gráfico
				"Repuesto", // Etiqueta del eje X
				"Porcentaje", // Etiqueta del eje Y
				dataset, // Datos
				PlotOrientation.VERTICAL, // Orientación del gráfico
				true, // Incluir leyenda
				true, // Incluir tooltips
				false // No incluir URLs
		);

		// Configurar propiedades del gráfico
		chart.setBackgroundPaint(new Color(29, 29, 29));
		chart.getTitle().setPaint(Color.white);
		chart.getTitle().setFont(new Font("Arial", Font.BOLD, 18));
		chart.setBorderVisible(false);
		chart.setBorderPaint(new Color(29, 29, 29));

		// Configurar renderizador de barras tridimensionales
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new IntervalCategoryItemLabelGenerator("{2}%", NumberFormat.getInstance()));
		renderer.setBaseItemLabelsVisible(true);
		plot.setRenderer(renderer);

		// Crear el panel del gráfico
		chartPanel = new ChartPanel(chart);
		chartPanel.setBackground(new Color(29, 29, 29));

		// Añadir el panel del gráfico al centro del panel principal
		add(chartPanel, BorderLayout.CENTER);

		// Crear el JComboBox para seleccionar el filtro
		comboBoxFiltro = new JComboBox<>();
		comboBoxFiltro.addItem("Mayores");
		comboBoxFiltro.addItem("Menores");

		// Agregar ActionListener al JComboBox para cambiar el filtro
		comboBoxFiltro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actualizarGrafico();
			}
		});

		// Crear un panel para el JComboBox y agregarlo al sur del panel principal
		JPanel comboBoxPanel = new JPanel();
		comboBoxPanel.setBackground(new Color(29, 29, 29));
		JLabel a = new JLabel("Filtro:");
		a.setForeground(Color.white);
		comboBoxPanel.add(a);
		comboBoxPanel.add(comboBoxFiltro);
		add(comboBoxPanel, BorderLayout.SOUTH);

		// Iniciar la actualización periódica del gráfico
		running = true;
		Thread thread = new Thread(this::actualizarPeriodicamente);
		thread.start();

	}

	// Método para actualizar el gráfico según el filtro seleccionado
	private void actualizarGrafico() {
		String filtro = (String) comboBoxFiltro.getSelectedItem();
		if (filtro.equals("Mayores")) {
			mostrarMayoresPorcentajes();
		} else if (filtro.equals("Menores")) {
			mostrarMenoresPorcentajes();
		}
	}

	// Método para mostrar los 10 registros con mayor porcentaje de venta
	private void mostrarMayoresPorcentajes() {
		Map<String, Integer> frecuenciaNumerosParte = cotizadoRegistroDAO.calcularFrecuenciaNumerosParte();
		dataset.clear();

		// Ordenar los registros por porcentaje de venta de mayor a menor
		List<Map.Entry<String, Integer>> sortedEntries = frecuenciaNumerosParte.entrySet().stream()
				.sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).limit(10).collect(Collectors.toList());

		// Calcular el porcentaje de los números de parte más cotizados
		int totalRegistros = sortedEntries.stream().mapToInt(Map.Entry::getValue).sum();
		for (Map.Entry<String, Integer> entry : sortedEntries) {
			String numeroDeParte = entry.getKey();
			int frecuencia = entry.getValue();
			double porcentaje = (double) frecuencia / totalRegistros * 100;
			dataset.addValue(porcentaje, "Porcentaje", numeroDeParte);
		}

		// Ajustar automáticamente el rango del eje Y
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.configureRangeAxes();
		plot.getDomainAxis().setLabelPaint(Color.white);
		plot.getDomainAxis().setTickLabelPaint(Color.white);
		plot.getRangeAxis().setLabelPaint(Color.white);
		plot.getRangeAxis().setTickLabelPaint(Color.white);

		BarRenderer3D renderer = new BarRenderer3D();

		// Establecer el color de la pared del cuadro (lateral)
		renderer.setWallPaint(new Color(50, 50, 50)); // Por ejemplo, un color gris medio para la pared lateral

		// Establecer el color del piso del cuadro
		renderer.setBaseFillPaint(new Color(50, 50, 50)); // Por ejemplo, un color gris oscuro para el piso
		plot.setBackgroundPaint(new Color(65, 65, 65));
		// Configurar el renderizador en el gráfico
		Paint colorFijo = new Color(1, 179, 169); // Por ejemplo, azul
		for (int i = 0; i < dataset.getRowCount(); i++) {
		    renderer.setSeriesPaint(i, colorFijo);
		}
		plot.setRenderer(renderer);

	}

	// Método para mostrar los 10 registros con menor porcentaje de venta
	private void mostrarMenoresPorcentajes() {
		Map<String, Integer> frecuenciaNumerosParte = cotizadoRegistroDAO.calcularFrecuenciaNumerosParte();
		dataset.clear();

		// Ordenar los registros por porcentaje de venta de menor a mayor
		List<Map.Entry<String, Integer>> sortedEntries = frecuenciaNumerosParte.entrySet().stream()
				.sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue())).limit(10).collect(Collectors.toList());

		// Calcular el porcentaje de los números de parte menos cotizados
		int totalRegistros = sortedEntries.stream().mapToInt(Map.Entry::getValue).sum();
		for (Map.Entry<String, Integer> entry : sortedEntries) {
			String numeroDeParte = entry.getKey();
			int frecuencia = entry.getValue();
			double porcentaje = (double) frecuencia / totalRegistros * 100;
			dataset.addValue(porcentaje, "Porcentaje", numeroDeParte);
		}

		// Ajustar automáticamente el rango del eje Y
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.configureRangeAxes();
		plot.getDomainAxis().setLabelPaint(Color.white);
		plot.getDomainAxis().setTickLabelPaint(Color.white);
		plot.getRangeAxis().setLabelPaint(Color.white);
		plot.getRangeAxis().setTickLabelPaint(Color.white);
		BarRenderer3D renderer = new BarRenderer3D();

		// Establecer el color de la pared del cuadro (lateral)
		renderer.setWallPaint(new Color(50, 50, 50)); // Por ejemplo, un color gris medio para la pared lateral
		plot.setBackgroundPaint(new Color(65, 65, 65));
		// Establecer el color del piso del cuadro
		renderer.setBaseFillPaint(new Color(50, 50, 50)); // Por ejemplo, un color gris oscuro para el piso
		// Por ejemplo, un color gris oscuro para el piso

		// Configurar el renderizador en el gráfico
		Paint colorFijo = new Color(255,102,39); // Por ejemplo, azul
		for (int i = 0; i < dataset.getRowCount(); i++) {
		    renderer.setSeriesPaint(i, colorFijo);
		}
		plot.setRenderer(renderer);

	}

	// Método para detener la actualización periódica
	public void detenerActualizacion() {
		running = false;
	}

	// Método para actualizar el gráfico periódicamente
	private void actualizarPeriodicamente() {
		while (running) {
			SwingUtilities.invokeLater(this::actualizarGrafico);
			try {
				Thread.sleep(5000); // Actualizar cada 5 segundos (puedes ajustar el intervalo según tus
									// necesidades)
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Error Thread IniCuadPane: linea 211: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}