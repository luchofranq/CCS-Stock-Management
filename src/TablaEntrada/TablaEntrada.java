package TablaEntrada;

import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;

import Clases.RepuestoEntrada;
import DAO.RepuestoEntrada_DAO;
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

public class TablaEntrada extends JPanel {

	private javax.swing.JScrollPane jScrollPane1;

	private static final long serialVersionUID = 1L;
	private TableRowSorter<DefaultTableModel> sorter;
	private TextFieldSuggestion NumeroDeParte;
	private DefaultTableModel model = new DefaultTableModel(new Object[][] {

	}, new String[] { "P/N", "DESCRIPCION", "N_PARTIDA", "N_DESPACHO", "COSTO", "COSTO_V", "CANTIDAD" });
	private RepuestoEntrada_DAO repdao = new RepuestoEntrada_DAO();
	private tabledark.TableDark tableDark1 = new tabledark.TableDark();
	private 	JLabel lbltabladeEntradahtml = new JLabel();
	private JLabel lblFiltrarPor = new JLabel();
	private TextFieldSuggestion Existencia = new TextFieldSuggestion();
	private 	TextFieldSuggestion Descripcion = new TextFieldSuggestion();
	private TextFieldSuggestion Partida = new TextFieldSuggestion();
	private 	TextFieldSuggestion Despacho = new TextFieldSuggestion();
	private TextFieldSuggestion CostoValorizado = new TextFieldSuggestion();
	private TextFieldSuggestion Costo = new TextFieldSuggestion();
	
	public TablaEntrada() {
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
		jScrollPane1.setSize(1045, 318);
		jScrollPane1.setLocation(134, 56);
		jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
		jScrollPane1.setViewportBorder(BorderFactory.createEmptyBorder());

		tableDark1.setRowSelectionAllowed(false);
		tableDark1.setEnabled(false);
		tableDark1.setBorder(null);
		tableDark1.setForeground(new Color(192, 192, 192));
		tableDark1.setAutoCreateRowSorter(true);

		model.fireTableDataChanged();
		ArrayList<RepuestoEntrada> repuestos = new ArrayList<>();
		repuestos = repdao.consultarTodosLosRepuestosEntrada();
		Object[] fila = new Object[model.getColumnCount()];
		for (RepuestoEntrada p : repuestos) {
			fila[0] = p.getNumeroDeParte();
			fila[1] = p.getDescripcion();
			fila[2] = p.getNumeroPartida();
			fila[3] = p.getNumeroDespacho();

			fila[4] = p.getCosto();
			fila[5] = p.getCostoValorizado();
			fila[6] = p.getExistencia();
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
		NumeroDeParte.setBounds(134, 5, 125, 34);

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

			Existencia.setBounds(944, 5, 125, 34);
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
					Existencia.setText("CANTIDAD");
			}
		});

		tableDark1.setBackground(new Color(29, 29, 29));
		jScrollPane1.setBackground(new Color(29, 29, 29));
		setLayout(null);
		add(lblFiltrarPor);
		add(Existencia);
		add(NumeroDeParte);
		add(jScrollPane1);

		Descripcion.setText("DESCRIPCION");
		Descripcion.setRound(0);
		Descripcion.setForeground(Color.WHITE);
		Descripcion.setBounds(269, 5, 125, 34);

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
		add(Descripcion);
			Partida.setText("N_PARTIDA");
		Partida.setRound(0);
		Partida.setForeground(Color.WHITE);
		Partida.setBounds(404, 5, 125, 34);
		Partida.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Partida.setText(null);
			}
		});

		Partida.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (Partida.getText().isEmpty())
					Partida.setText("N_PARTIDA");
			}
		});
		add(Partida);

		Despacho.setText("N_DESPACHO");
		Despacho.setRound(0);
		Despacho.setForeground(Color.WHITE);
		Despacho.setBounds(539, 5, 125, 34);
		Despacho.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Despacho.setText(null);
			}
		});

		Despacho.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (Despacho.getText().isEmpty())
					Despacho.setText("N_DESPACHO");
			}
		});
		add(Despacho);

			Costo.setText("COSTO");
		Costo.setRound(0);
		Costo.setForeground(Color.WHITE);
		Costo.setBounds(674, 5, 125, 34);
		Costo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Costo.setText(null);
			}
		});

		Costo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (Costo.getText().isEmpty())
					Costo.setText("COSTO");
			}
		});
		add(Costo);

			CostoValorizado.setText("COSTO_V");
		CostoValorizado.setRound(0);
		CostoValorizado.setForeground(Color.WHITE);
		CostoValorizado.setBounds(809, 5, 125, 34);

		CostoValorizado.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				CostoValorizado.setText(null);
			}
		});

		CostoValorizado.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (CostoValorizado.getText().isEmpty())
					CostoValorizado.setText("COSTO_V");
			}
		});
		add(CostoValorizado);

		lbltabladeEntradahtml.setText("<html>Tabla<br>de entrada</html>");
		lbltabladeEntradahtml.setForeground(Color.WHITE);
		lbltabladeEntradahtml.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lbltabladeEntradahtml.setBounds(41, 59, 79, 38);
		add(lbltabladeEntradahtml);
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
		Partida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(Partida, 2);
			}
		});

		Despacho.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(Despacho, 3);
			}
		});

		Existencia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar( Existencia, 6);
			}
		});

		Costo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(Costo , 4);
			}
		});

		CostoValorizado.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(CostoValorizado , 5);
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
		ArrayList<RepuestoEntrada> repuestos = new ArrayList<>();
		// Consulta los repuestos cotizados
		repuestos = repdao.consultarTodosLosRepuestosEntrada();
		Object[] fila = new Object[model.getColumnCount()];
		for (RepuestoEntrada p : repuestos) {
			fila[0] = p.getNumeroDeParte();
			fila[1] = p.getDescripcion();
			fila[2] = p.getNumeroPartida();
			fila[3] = p.getNumeroDespacho();
			fila[4] = p.getCosto();
			fila[5] = p.getCostoValorizado();
			fila[6] = p.getExistencia();

			model.addRow(fila);
			tableDark1.validate();
		}
		// Actualiza la tabla

		tableDark1.validate();

		model.fireTableDataChanged();
	}
}
