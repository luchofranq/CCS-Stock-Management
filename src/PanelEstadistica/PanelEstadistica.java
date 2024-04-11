package PanelEstadistica;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import Clases.CotizadoRegistro;
import DAO.CotizadoRegistro_DAO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import org.jfree.chart.plot.PiePlot;
@SuppressWarnings("serial")
public class PanelEstadistica extends JPanel {
    private CotizadoRegistro_DAO crd = new CotizadoRegistro_DAO();
    private DefaultPieDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private boolean running;

    public PanelEstadistica() {
    	setBackground(new Color(29, 29, 29));
        setName("Gráfico Dinámico");
        setLayout(new BorderLayout());
        setSize(600, 400);

        dataset = new DefaultPieDataset();

        // Crear el gráfico de pastel
        chart = ChartFactory.createPieChart("Porcentaje de cotizaciones locales y exteriores", // Título del gráfico
                dataset, // Datos
                true, // Incluir leyenda
                true, // Incluir tooltips
                false // No incluir URLs
        );

        chart.setBackgroundPaint(new Color(29,29,29)); // Cambiar color de fondo
        chart.getTitle().setPaint(Color.WHITE); // Cambiar color del título
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 18)); // Cambiar fuente y tamaño del título
        chart.setBorderVisible(true); // Mostrar borde
        chart.setBorderPaint(new Color(29,29,29)); // Cambiar color del borde
        chart.getPlot().setBackgroundPaint(new Color(29,29,29)); 
        // Crear el panel del gráfico
        chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(new Color(29, 29, 29));
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBackground(new Color(29,29,29)); 
        // Agregar el panel del gráfico al marco
        add(chartPanel, BorderLayout.CENTER);

        // Inicializar el hilo para la actualización periódica
        running = true;
        Thread thread = new Thread(this::actualizarPeriodicamente);
        thread.start();
    }

    // Método para actualizar los datos del gráfico periódicamente
    private void actualizarPeriodicamente() {
        while (running) {
            SwingUtilities.invokeLater(this::actualizarDatos);
            try {
                Thread.sleep(5000); // Actualizar cada 5 segundos (puedes ajustar el intervalo según tus necesidades)
            } catch (InterruptedException e) {
            	JOptionPane.showMessageDialog(null, "Error Thread EstadPane: linea 66: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para actualizar los datos del gráfico
    private void actualizarDatos() {
        // Obtener todos los registros cotizados
        ArrayList<CotizadoRegistro> registrosCotizados = crd.todosLosCotizados();

        // Contadores para repuestos exteriores y locales
        int exteriores = 0;
        int locales = 0;

        // Calcular el número de repuestos exteriores y locales
        for (CotizadoRegistro registro : registrosCotizados) {
            if (registro.getTipo().equals("EXTERIOR")) {
                exteriores += 1;
            } else if (registro.getTipo().equals("LOCAL")) {
                locales += 1;
            }
        }

        // Calcular el total de registros
        int totalRegistros = exteriores + locales;

        // Calcular porcentajes
        double porcentajeExteriores = ((double) exteriores / totalRegistros) * 100;
        double porcentajeLocales = ((double) locales / totalRegistros) * 100;

        // Limpiar el dataset
        dataset.clear();

        // Determinar el color para cada sección basado en el porcentaje
        Paint colorExteriores = porcentajeExteriores > 50 ? new Color(134, 55, 248) : new Color(255,102,39);
        Paint colorLocales = porcentajeLocales > 50 ? new Color(134, 55, 248): new Color(255,102,39);

        
         
        // Agregar los datos al dataset con porcentajes y colores determinados
        dataset.setValue("Cotizaciones Exteriores (" + String.format("%.2f", porcentajeExteriores) + "%)", exteriores);
        dataset.setValue("Cotizaciones Locales (" + String.format("%.2f", porcentajeLocales) + "%)", locales);

        // Asignar colores a las secciones del gráfico
        PiePlot plot = (PiePlot) chart.getPlot();
       
        plot.setLabelBackgroundPaint(new Color( 0, 143, 159 ));
        plot.setLabelPaint(Color.white);
        plot.setSectionPaint("Cotizaciones Exteriores (" + String.format("%.2f", porcentajeExteriores) + "%)", colorExteriores);
        plot.setSectionPaint("Cotizaciones Locales (" + String.format("%.2f", porcentajeLocales) + "%)", colorLocales);

        // Repintar el panel del gráfico
        chartPanel.repaint();
    }

    // Método para detener la actualización periódica
    public void detenerActualizacion() {
        running = false;
    }
}
