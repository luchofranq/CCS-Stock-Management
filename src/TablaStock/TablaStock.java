package TablaStock;

import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import Clases.Repuesto;

import DAO.Repuesto_DAO;
import textfield_suggestion.TextFieldSuggestion;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TablaStock extends JPanel {

	private ArrayList<Repuesto> repuestos = new ArrayList<>();
	private javax.swing.JScrollPane jScrollPane1;
	private tabledark.TableDark tableDark1 = new tabledark.TableDark();
	private static final long serialVersionUID = 1L;
	private TableRowSorter<DefaultTableModel> sorter;
	private TextFieldSuggestion NumeroDeParte;
	private DefaultTableModel model = new DefaultTableModel(new Object[][] {

	}, new String[] { "P/N", "DESCRIPCION", "P. LOCAL","P. EXTERIOR", "EXISTENCIA" });
	private Repuesto_DAO repdao = new Repuesto_DAO();
private TextFieldSuggestion PrecioExterior = new TextFieldSuggestion();
private JLabel lblFiltrarPor = new JLabel();
private 	TextFieldSuggestion Existencia = new TextFieldSuggestion();
private TextFieldSuggestion Precio = new TextFieldSuggestion();
private TextFieldSuggestion Descripcion = new TextFieldSuggestion();
private final JLabel lbltabladestock = new JLabel();



	public TablaStock() {
		Timer timer1 = new Timer(5000, new ActionListener() { // Actualiza cada 5 segundos (5000 milisegundos)
			@Override
			public void actionPerformed(ActionEvent e) {
				// Llama al método para actualizar la tabla y otros componentes
				updateTable();

			}
		});
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

		repuestos = repdao.consultarTodosLosRepuestos();
		Object[] fila = new Object[model.getColumnCount()];
		for (Repuesto p : repuestos) {
			fila[0] = p.getNumeroDeParte();
			fila[1] = p.getDescripcion();
			fila[2] = p.getPrecioLocal();
			fila[3] = p.getPrecioExterior();
			fila[4] = p.getExistencia();
			model.addRow(fila);
			tableDark1.validate();
		}

		tableDark1.setModel(model);
		sorter = new TableRowSorter<>(model);
		tableDark1.setRowSorter(sorter);

		jScrollPane1.setViewportView(tableDark1);
		jScrollPane1.getViewport().setBackground(new Color(29, 29, 29));

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
					NumeroDeParte.setText("NUMERO DE PARTE");
			}
		});

		Existencia.setBounds(714, 5, 135, 34);
		Existencia.setText("EXISTENCIA");
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

			Precio.setBounds(424, 5, 135, 34);
		Precio.setText("PRECIO LOCAL");
		Precio.setRound(0);
		Precio.setForeground(Color.WHITE);
		Precio.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Precio.setText(null);
			}
		});

		Precio.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (Precio.getText().isEmpty())
					Precio.setText("PRECIO LOCAL");
			}
		});

		tableDark1.setBackground(new Color(29, 29, 29));
		jScrollPane1.setBackground(new Color(29, 29, 29));
		setLayout(null);
		add(lblFiltrarPor);
		add(Existencia);
		add(NumeroDeParte);
		add(Precio);
		add(jScrollPane1);

			Descripcion.setText("DESCRIPCION");
		Descripcion.setRound(0);
		Descripcion.setForeground(Color.WHITE);
		Descripcion.setBounds(279, 5, 135, 34);
		add(Descripcion);
		
		
		PrecioExterior.setText("PRECIO EXTERIOR");
		PrecioExterior.setRound(0);
		PrecioExterior.setForeground(Color.WHITE);
		PrecioExterior.setBounds(569, 5, 135, 34);
		add(PrecioExterior);
		lbltabladestock.setText("<html>Tabla <br>de stock</html>");
		lbltabladestock.setForeground(Color.WHITE);
		lbltabladestock.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lbltabladestock.setBounds(41, 59, 79, 38);
		
		add(lbltabladestock);
		PrecioExterior.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				PrecioExterior.setText(null);
			}
		});

		PrecioExterior.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (PrecioExterior.getText().isEmpty())
					PrecioExterior.setText("P. EXTERIOR");
			}
		});
		Descripcion.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Descripcion.setText(null);
			}
		});

		Descripcion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (Descripcion.getText().isEmpty())
					Descripcion.setText("DESCRIPCION");
			}
		});
		NumeroDeParte.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(NumeroDeParte, 0);
			}
		});

		Descripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(Descripcion, 1);
			}
		});

		Precio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(Precio, 2);
			}
		});
		Existencia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(Existencia, 4);
			}
		});
		PrecioExterior.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(PrecioExterior, 3);
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
		repuestos = repdao.consultarTodosLosRepuestos();
		Object[] fila = new Object[model.getColumnCount()];
		for (Repuesto p : repuestos) {
			fila[0] = p.getNumeroDeParte();
			fila[1] = p.getDescripcion();
			fila[2] = p.getPrecioLocal();
			fila[3] = p.getPrecioExterior();
			fila[4] = p.getExistencia();
			model.addRow(fila);
			tableDark1.validate();
		}
		// Actualiza la tabla

		tableDark1.validate();

		model.fireTableDataChanged();
	}
}
