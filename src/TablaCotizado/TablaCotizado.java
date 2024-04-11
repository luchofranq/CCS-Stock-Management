package TablaCotizado;

import javax.swing.JPanel;
import Clases.PDFDataHistorial;
import javax.swing.RowFilter;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.Date;
import textfield_suggestion.TextFieldSuggestion;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import DAO.CotizadoRegistro_DAO;
import HistorialAPDF.HistorialAPDF;
import Clases.CotizadoRegistro;
import button.MyButton;
import javax.swing.Timer;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TablaCotizado extends JPanel {

	private ArrayList<CotizadoRegistro> cotizados = new ArrayList<>();

	private javax.swing.JScrollPane jScrollPane1;

	private static final long serialVersionUID = 1L;
	private TableRowSorter<DefaultTableModel> sorter;
	private TextFieldSuggestion NumeroDeParte;
	private TextFieldSuggestion Fecha = new TextFieldSuggestion();
	private DefaultTableModel model = new DefaultTableModel(new Object[][] {},
			new String[] { "P/N", "P. UNITARIO", "CANTIDAD", "P. TOTAL", "TIPO", });
	private CotizadoRegistro_DAO repdao = new CotizadoRegistro_DAO();
	private tabledark.TableDark tableDark1 = new tabledark.TableDark();

	public TablaCotizado() {
		Timer timer1 = new Timer(5000, new ActionListener() { // Actualiza cada 5 segundos (5000 milisegundos)
			@Override
			public void actionPerformed(ActionEvent e) {
				// Llama al método para actualizar la tabla y otros componentes
				updateTable();

			}
		});

		// Inicia el Timer
		timer1.start();
		setBackground(new Color(29, 29, 29));
		setLayout(null);

		jScrollPane1 = new javax.swing.JScrollPane();
		jScrollPane1.setSize(960, 318);
		jScrollPane1.setLocation(134, 56);
		jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
		jScrollPane1.setViewportBorder(BorderFactory.createEmptyBorder());

		tableDark1.setRowSelectionAllowed(false);
		tableDark1.setEnabled(false);
		tableDark1.setBorder(null);
		tableDark1.setForeground(new Color(192, 192, 192));
		tableDark1.setAutoCreateRowSorter(true);

		model.fireTableDataChanged();

		cotizados = repdao.todosLosCotizados();

		Object[] fila = new Object[model.getColumnCount()];

		for (CotizadoRegistro p : cotizados) {
			fila[0] = p.getNumeroDeParte();
			fila[1] = p.getPrecioUnitario();
			fila[2] = p.getCantidad();
			fila[3] = p.getPrecioTotal();
			fila[4] = p.getTipo();

			model.addRow(fila);
			tableDark1.validate();
			model.fireTableDataChanged();
		}

		tableDark1.setModel(model);
		sorter = new TableRowSorter<>(model);
		tableDark1.setRowSorter(sorter);

		jScrollPane1.setViewportView(tableDark1);
		jScrollPane1.getViewport().setBackground(new Color(29, 29, 29));

		JLabel lblFiltrarPor = new JLabel();
		lblFiltrarPor.setBounds(41, 11, 79, 19);
		lblFiltrarPor.setText("Filtrar por:");
		lblFiltrarPor.setForeground(Color.WHITE);
		lblFiltrarPor.setFont(new Font("SansSerif", Font.PLAIN, 14));

		NumeroDeParte = new TextFieldSuggestion();
		NumeroDeParte.setBounds(134, 5, 135, 34);

		NumeroDeParte.setText("NUMERO DE PARTE");
		NumeroDeParte.setRound(0);
		NumeroDeParte.setForeground(Color.WHITE);
		NumeroDeParte.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				NumeroDeParte.setText(null);
			}
		});

		NumeroDeParte.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (NumeroDeParte.getText().isEmpty())
					NumeroDeParte.setText("P/N");
			}
		});

		TextFieldSuggestion Existencia = new TextFieldSuggestion();
		Existencia.setBounds(424, 5, 135, 34);
		Existencia.setText("CANTIDAD");
		Existencia.setRound(0);
		Existencia.setForeground(Color.WHITE);
		Existencia.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Existencia.setText(null);
			}
		});

		Existencia.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (Existencia.getText().isEmpty())
					Existencia.setText("EXISTENCIA");
			}
		});

		TextFieldSuggestion PrecioTotal = new TextFieldSuggestion();
		PrecioTotal.setBounds(569, 5, 135, 34);
		PrecioTotal.setText("PRECIO TOTAL");
		PrecioTotal.setRound(0);
		PrecioTotal.setForeground(Color.WHITE);
		PrecioTotal.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				PrecioTotal.setText(null);
			}
		});

		PrecioTotal.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (PrecioTotal.getText().isEmpty())
					PrecioTotal.setText("PRECIO LOCAL");
			}
		});

		tableDark1.setBackground(new Color(29, 29, 29));
		jScrollPane1.setBackground(new Color(29, 29, 29));
		setLayout(null);
		add(lblFiltrarPor);
		add(Existencia);
		add(NumeroDeParte);
		add(PrecioTotal);
		add(jScrollPane1);

		TextFieldSuggestion Tipo = new TextFieldSuggestion();
		Tipo.setText("TIPO");
		Tipo.setRound(0);
		Tipo.setForeground(Color.WHITE);
		Tipo.setBounds(714, 5, 135, 34);
		Tipo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Tipo.setText(null);
			}
		});

		Tipo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (Tipo.getText().isEmpty())
					Tipo.setText("TIPO");
			}
		});
		add(Tipo);

		TextFieldSuggestion PrecioUnitario = new TextFieldSuggestion();
		PrecioUnitario.setText("PRECIO UNITARIO");
		PrecioUnitario.setRound(0);
		PrecioUnitario.setForeground(Color.WHITE);
		PrecioUnitario.setBounds(279, 5, 135, 34);
		PrecioUnitario.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				PrecioUnitario.setText(null);
			}
		});

		PrecioUnitario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (PrecioUnitario.getText().isEmpty())
					PrecioUnitario.setText("PRECIO UNITARIO");
			}
		});
		add(PrecioUnitario);

		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Obtener la fecha actual
				Date currentDate = new Date();
				// Formatear la fecha sin la hora
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				String formattedDate = dateFormat.format(currentDate);
				// Actualizar el JTextField con la fecha formateada
				Fecha.setText(formattedDate);
			}
		});

		// Iniciar el Timer
		timer.start();

		MyButton Reset = new MyButton();
		Reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {

				Reset.setForeground(Color.WHITE);
				Reset.setBorderColor(new Color(1, 179, 169));
				Reset.setBackground(new Color(1, 179, 169));

			}
		});

		Reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Actualiza la tabla

				// Crea los campos de texto con sugerencia

				// Cambia el color del texto de los TextField a blanco
				Fecha.setForeground(Color.white);
				UIManager.put("Button.background", new Color(0, 188, 198));
				UIManager.put("Button.foreground", Color.WHITE);
				// Crea el mensaje con los componentes
				Object[] message = { "Fecha:", Fecha };

				try {
					// Establece el Look and Feel de Windows
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error UI TabCot: linea 262: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}

				// Muestra el diálogo de entrada de datos
				int result = JOptionPane.showConfirmDialog(null, message, "Ingrese los datos",
						JOptionPane.OK_CANCEL_OPTION);

				// Si se selecciona "OK" en el diálogo
				if (result == JOptionPane.OK_OPTION) {
					// Verificar si algún campo está vacío

					// Obtiene los datos ingresados
					PDFDataHistorial data = new PDFDataHistorial();

					data.setFecha(Fecha.getText());

					// Agrega la tabla al PDF
					HistorialAPDF.addTableToPDF(tableDark1, data);
					CotizadoRegistro_DAO.limpiarHistorial();

				}
			}

		});

		Reset.setText("<html>Resetear y<br>Exportar a<br>PDF</html>");
		Reset.setForeground(Color.WHITE);
		Reset.setColorOver(new Color(119, 193, 255));
		Reset.setColorClick(new Color(15, 147, 255));
		Reset.setColor(Color.DARK_GRAY);
		Reset.setBorderColor(new Color(1, 179, 169));
		Reset.setBackground(new Color(1, 179, 169));
		Reset.setBounds(10, 323, 110, 51);
		add(Reset);
		
		JLabel lblhistorialdeCotizaciones = new JLabel();
		lblhistorialdeCotizaciones.setText("<html>Historial de<br>cotizaciones</html>");
		lblhistorialdeCotizaciones.setForeground(Color.WHITE);
		lblhistorialdeCotizaciones.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblhistorialdeCotizaciones.setBounds(41, 59, 79, 38);
		add(lblhistorialdeCotizaciones);
		NumeroDeParte.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(NumeroDeParte, 0);
			}
		});

		Existencia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(Existencia, 2);
			}
		});

		Tipo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(Tipo, 4);
			}
		});

		PrecioTotal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(PrecioTotal, 3);
			}
		});
		PrecioUnitario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(PrecioUnitario, 1);
			}
		});

	}

	private void filtrar(TextFieldSuggestion text, int column) {
		sorter.setRowFilter(RowFilter.regexFilter(text.getText().toUpperCase(), column));
	}

	private void updateTable() {
		// Reinicia el modelo de la tabla
		model.setRowCount(0);
		// Reinicia el precio total de los repuestos

		// Consulta los repuestos cotizados
		cotizados = repdao.todosLosCotizados();

		Object[] fila = new Object[model.getColumnCount()];

		for (CotizadoRegistro p : cotizados) {
			fila[0] = p.getNumeroDeParte();
			fila[1] = p.getPrecioUnitario();
			fila[2] = p.getCantidad();
			fila[3] = p.getPrecioTotal();
			fila[4] = p.getTipo();

			model.addRow(fila);
			tableDark1.validate();
			model.fireTableDataChanged();
		}
		// Actualiza la tabla

		tableDark1.validate();

		model.fireTableDataChanged();
	}

}
