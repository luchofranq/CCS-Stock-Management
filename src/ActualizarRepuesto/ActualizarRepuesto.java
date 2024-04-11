package ActualizarRepuesto;

import javax.swing.JPanel;
import Clases.Repuesto;
import DAO.Repuesto_DAO;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import textfield_suggestion.TextFieldSuggestion;
import button.MyButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import combobox.Combobox;
import java.util.ArrayList;

public class ActualizarRepuesto extends JPanel {

	private static final long serialVersionUID = 1L;
	private Repuesto repuesto;
	private ArrayList<Repuesto> repuestos = new ArrayList<>();
	private Repuesto_DAO repdao = new Repuesto_DAO();
	private TextFieldSuggestion NumeroDeParte = new TextFieldSuggestion();
	private TextFieldSuggestion Existencias = new TextFieldSuggestion();
	private TextFieldSuggestion Descripcion = new TextFieldSuggestion();
	private TextFieldSuggestion Precio = new TextFieldSuggestion();
	private Combobox<String> combobox = new Combobox<String>();
	private Color colorNegro = new Color(29, 29, 29);
	private String numeroParte;
	private String numparte1;
	private JLabel lblAgregarRepuesto = new JLabel();
	private JLabel IngreseLosDatos = new JLabel();
	private MyButton Actualizar = new MyButton();
	private Repuesto repAct;
	private String numparte;
	private 	BigDecimal precio ;
	private String desc;
	private int existencias;
	private JLabel lblNumeroDeParte = new JLabel();
	private JLabel lblExistencias = new JLabel();
	private JLabel lblDescripcion = new JLabel();
	private JLabel lblPrecio = new JLabel();
	private JLabel lblBuscar = new JLabel();
	private int tiempoMilisegundos;
	private 	TextFieldSuggestion PrecioExterior = new TextFieldSuggestion();
	private BigDecimal precioExterior;
	
	public ActualizarRepuesto() {
		repuestos = repdao.consultarTodosLosRepuestos();
		setBackground(colorNegro);
		setLayout(null);
		
		combobox.setForeground(Color.WHITE);
		combobox.setBackground(colorNegro);
		combobox.setSelectedIndex(-1);
		combobox.setLabeText("Numero de parte");
		combobox.setBounds(376, 11, 166, 34);
		for (Repuesto e : repuestos) {
			numeroParte = e.getNumeroDeParte().trim();
			combobox.addItem(numeroParte);
		}
		combobox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				numparte1 = combobox.getSelectedItem().toString();
				repuesto = repdao.consultarRepuesto(repdao.consultarIdRepuesto(numparte1));
				NumeroDeParte.setText(repuesto.getNumeroDeParte());
				NumeroDeParte.setEnabled(false);
				Existencias.setText(Integer.toString(repuesto.getExistencia()));
				Existencias.setEnabled(true);
				Existencias.setEditable(true);
				Descripcion.setText(repuesto.getDescripcion());
				Descripcion.setEnabled(true);
				Descripcion.setEditable(true);
				Precio.setText(repuesto.getPrecioLocal().toString());
				Precio.setEnabled(true);
				Precio.setEditable(true);
				Descripcion.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						while (Descripcion.getText().isEmpty())
							Descripcion.setText(repuesto.getDescripcion());
					}
				});
				Precio.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						while (Precio.getText().isEmpty())
							Precio.setText(repuesto.getPrecioLocal().toString());
					}

				});
				Existencias.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent e) {
						while (Existencias.getText().isEmpty())
							Existencias.setText(Integer.toString(repuesto.getExistencia()));
					}

				});

			}
		});

		add(combobox);

			lblAgregarRepuesto.setBounds(28, 11, 207, 19);
		lblAgregarRepuesto.setText("Modificar stock");
		lblAgregarRepuesto.setForeground(new Color(255, 255, 255));
		lblAgregarRepuesto.setFont(new Font("SansSerif", Font.BOLD, 14));
		add(lblAgregarRepuesto);
		NumeroDeParte.setEditable(false);

		NumeroDeParte.setBounds(109, 108, 178, 34);
		NumeroDeParte.setForeground(Color.WHITE);
		NumeroDeParte.setText("NUMERO DE PARTE");
		NumeroDeParte.setRound(0);
		add(NumeroDeParte);
		Existencias.setEditable(false);

		Existencias.setBounds(968, 108, 178, 34);
		Existencias.setText("EXISTENCIAS");
		Existencias.setRound(0);
		Existencias.setForeground(Color.WHITE);
		add(Existencias);
		Descripcion.setEditable(false);

		Descripcion.setBounds(376, 108, 178, 34);
		Descripcion.setText("DESCRIPCION");
		Descripcion.setRound(0);
		Descripcion.setForeground(Color.WHITE);
		add(Descripcion);
		Precio.setEditable(false);

		Precio.setBounds(687, 108, 178, 34);
		Precio.setText("PRECIO LOCAL");
		Precio.setRound(0);
		Precio.setForeground(Color.WHITE);
		add(Precio);

			IngreseLosDatos.setBounds(334, 277, 372, 19);
		IngreseLosDatos.setText("INGRESE LOS DATOS");
		IngreseLosDatos.setForeground(Color.WHITE);
		IngreseLosDatos.setFont(new Font("SansSerif", Font.BOLD, 14));
		add(IngreseLosDatos);

		Actualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					 numparte = combobox.getSelectedItem().toString();
					repuesto = repdao.consultarRepuesto(repdao.consultarIdRepuesto(numparte));

					 precio = new BigDecimal(Precio.getText());
					 desc = Descripcion.getText();
					 existencias = Integer.parseInt(Existencias.getText());
					 precioExterior = new BigDecimal(PrecioExterior.getText());

					 repAct = new Repuesto(repuesto.getNumeroDeParte(), desc, existencias, precio, precioExterior);

					if (repdao.updateRepuesto(repAct, numparte)) {
						cambiarColorLabel(IngreseLosDatos, "DATOS MODIFICADOS CORRECTAMENTE", new Color(20, 240, 20));
					} else {
						cambiarColorLabel(IngreseLosDatos, "ERROR EN LA CARGA DE DATOS", new Color(240, 40, 40));
					}

				} catch (NumberFormatException ex) {
					// Si ocurre una excepción, mostrar el icono de alerta y resaltar el campo
					JOptionPane.showMessageDialog(null, "Error en los campos de texto.", "Error",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		Actualizar.setBounds(716, 268, 117, 40);
		Actualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
			
				Actualizar.setForeground(Color.WHITE);
				Actualizar.setBorderColor(new Color(1, 179, 169));
				Actualizar.setBackground(new Color(1, 179, 169));
			}
		});
		Actualizar.setText("MODIFICAR");
		Actualizar.setForeground(Color.WHITE);
		Actualizar.setColorOver(new Color(119, 193, 255));
		Actualizar.setColorClick(new Color(15, 147, 255));
		Actualizar.setColor(Color.DARK_GRAY);
		Actualizar.setBorderColor(new Color(1, 179, 169));
		Actualizar.setBackground(new Color(1, 179, 169));
		add(Actualizar);

			lblNumeroDeParte.setText("Numero de parte");
		lblNumeroDeParte.setForeground(Color.WHITE);
		lblNumeroDeParte.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNumeroDeParte.setBounds(10, 89, 102, 19);
		add(lblNumeroDeParte);

			lblExistencias.setText("Existencias");
		lblExistencias.setForeground(Color.WHITE);
		lblExistencias.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblExistencias.setBounds(902, 89, 77, 19);
		add(lblExistencias);

		lblDescripcion.setText("Descripcion");
		lblDescripcion.setForeground(Color.WHITE);
		lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblDescripcion.setBounds(313, 89, 106, 19);
		add(lblDescripcion);

			lblPrecio.setText("Precio Local");
		lblPrecio.setForeground(Color.WHITE);
		lblPrecio.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPrecio.setBounds(622, 89, 77, 19);
		add(lblPrecio);
		
			lblBuscar.setText("Buscar:");
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblBuscar.setBounds(294, 23, 77, 19);
		add(lblBuscar);
		
	
		PrecioExterior.setText("PRECIO EXTERIOR");
		PrecioExterior.setRound(0);
		PrecioExterior.setForeground(Color.WHITE);
		PrecioExterior.setBounds(687, 173, 178, 34);
		add(PrecioExterior);
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
					PrecioExterior.setText("PRECIO EXTERIOR");
			}
		});
		
		JLabel lblPrecioExterior = new JLabel();
		lblPrecioExterior.setText("Precio Exterior");
		lblPrecioExterior.setForeground(Color.WHITE);
		lblPrecioExterior.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPrecioExterior.setBounds(622, 153, 81, 19);
		add(lblPrecioExterior);

	}

	private void cambiarColorLabel(JLabel label, String mensaje, Color color) {
		tiempoMilisegundos = 3000;
		label.setForeground(color);
		label.setText(mensaje);

		Thread hilo = new Thread(() -> {
			try {
				Thread.sleep(tiempoMilisegundos);
			} catch (InterruptedException e) {
				JOptionPane.showMessageDialog(null, "Error color label.", "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} finally {
				label.setText("INGRESE LOS DATOS");
				label.setForeground(new Color(224, 224, 224));
			}
		});

		hilo.start();
	}
}
