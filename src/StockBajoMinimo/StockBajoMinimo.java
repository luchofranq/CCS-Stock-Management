package StockBajoMinimo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import DAO.Repuesto_DAO;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Clases.Repuesto;
import textfield_suggestion.TextFieldSuggestion;

public class StockBajoMinimo extends JPanel {


	private javax.swing.JScrollPane jScrollPane1;
	private tabledark.TableDark tableDark1 = new tabledark.TableDark();;
	private static final long serialVersionUID = 1L;
	private TableRowSorter<DefaultTableModel> sorter;
	private TextFieldSuggestion NumeroDeParte;
	private DefaultTableModel model = new DefaultTableModel(new Object[][] {

	}, new String[] { "P/N", "DESCRIPCION",  "P. LOCAL", "P. EXTERIOR","EXISTENCIA" });
	private 	TextFieldSuggestion PrecioExterior = new TextFieldSuggestion();
	private Repuesto_DAO repdao = new Repuesto_DAO();
	private JLabel lblFiltrarPor = new JLabel();
	private TextFieldSuggestion Existencia = new TextFieldSuggestion();
	private TextFieldSuggestion PrecioUnitario = new TextFieldSuggestion();
	private TextFieldSuggestion txtfldsgstnDescripcion = new TextFieldSuggestion();
	private JLabel lblminimoPreestablecido = new JLabel();
	private final JLabel lblstockPordebajoDelminimo = new JLabel();
	
	public StockBajoMinimo() {
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
		ArrayList<Repuesto> repuestos = new ArrayList<>();
		repuestos = repdao.repuestosMenoresAminimo(3);

		Object[] fila = new Object[model.getColumnCount()];

		for (Repuesto p : repuestos) {
			fila[0] = p.getNumeroDeParte();
			fila[1] = p.getDescripcion();
			fila[2] = p.getPrecioLocal();
			fila[3] = p.getPrecioExterior();
			fila[4] = p.getExistencia();
			model.addRow(fila);
			tableDark1.validate();
			model.fireTableDataChanged();
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

		tableDark1.setBackground(new Color(29, 29, 29));
		jScrollPane1.setBackground(new Color(29, 29, 29));
		setLayout(null);
		add(lblFiltrarPor);
		add(Existencia);
		add(NumeroDeParte);

		add(jScrollPane1);

			PrecioUnitario.setText("PRECIO LOCAL");
		PrecioUnitario.setRound(0);
		PrecioUnitario.setForeground(Color.WHITE);
		PrecioUnitario.setBounds(424, 5, 135, 34);
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
					PrecioUnitario.setText("PRECIO LOCAL");
			}
		});
		add(PrecioUnitario);

			txtfldsgstnDescripcion.setText("DESCRIPCION");
		txtfldsgstnDescripcion.setRound(0);
		txtfldsgstnDescripcion.setForeground(Color.WHITE);
		txtfldsgstnDescripcion.setBounds(279, 5, 135, 34);
		txtfldsgstnDescripcion.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				txtfldsgstnDescripcion.setText(null);
			}
		});

		txtfldsgstnDescripcion.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (txtfldsgstnDescripcion.getText().isEmpty())
					txtfldsgstnDescripcion.setText("DESCRIPCION");
			}
		});
		add(txtfldsgstnDescripcion);
		
	
		PrecioExterior.setText("PRECIO EXTERIOR");
		PrecioExterior.setRound(0);
		PrecioExterior.setForeground(Color.WHITE);
		PrecioExterior.setBounds(569, 5, 135, 34);
		add(PrecioExterior);
		
			lblminimoPreestablecido.setText("*minimo pre-establecido 3 existencias");
		lblminimoPreestablecido.setForeground(Color.WHITE);
		lblminimoPreestablecido.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblminimoPreestablecido.setBounds(853, 11, 241, 19);
		add(lblminimoPreestablecido);
		lblstockPordebajoDelminimo.setText("<html>Stock por<br>debajo del<br>minimo</html>");
		lblstockPordebajoDelminimo.setForeground(Color.WHITE);
		lblstockPordebajoDelminimo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		lblstockPordebajoDelminimo.setBounds(41, 55, 79, 65);
		
		add(lblstockPordebajoDelminimo);
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
		
		NumeroDeParte.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(NumeroDeParte, 0);
			}
		});
		txtfldsgstnDescripcion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(txtfldsgstnDescripcion, 1);
			}
		});
		Existencia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(Existencia, 4);
			}
		});

		PrecioUnitario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrar(PrecioUnitario, 2);
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
		ArrayList<Repuesto> repuestos = new ArrayList<>();
		repuestos = repdao.repuestosMenoresAminimo(3);
		// Consulta los repuestos cotizados
		
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