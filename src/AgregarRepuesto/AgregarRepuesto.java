package AgregarRepuesto;

import javax.swing.JPanel;
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
import Clases.Repuesto;
import DAO.Repuesto_DAO;

public class AgregarRepuesto extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lblAgregarRepuesto = new JLabel();
	private TextFieldSuggestion NumeroDeParte = new TextFieldSuggestion();
	private TextFieldSuggestion Existencias = new TextFieldSuggestion();
	private TextFieldSuggestion Descripcion = new TextFieldSuggestion();
	private TextFieldSuggestion Precio = new TextFieldSuggestion();
	private JLabel IngreseLosDatos = new JLabel();
	private JLabel lblNumeroDeParte = new JLabel();
	private JLabel lblDescripcion = new JLabel();
	private JLabel lblPrecioLocal = new JLabel();
	private JLabel lblExistencias = new JLabel();
	private MyButton Agregar = new MyButton();
	private Repuesto repuesto;
	private String numeroDeParte;
	private int existencia;
	private BigDecimal precio;
	private BigDecimal precioExterior;
	private String descripcion;
	private Repuesto_DAO repdao = new Repuesto_DAO();
	private Color colorNegro = new Color(29, 29, 29);
	private int tiempoMilisegundos;
	private Color colorVerde = new Color(1, 179, 169);
	private Color rojo = new Color(240, 40, 40);
	private Color verde = new Color(20, 240, 20);
	private TextFieldSuggestion PrecioExterior = new TextFieldSuggestion();

	public AgregarRepuesto() {

		setBackground(colorNegro);
		setLayout(null);

		lblAgregarRepuesto.setBounds(28, 11, 207, 19);
		lblAgregarRepuesto.setText("Agregar Stock");
		lblAgregarRepuesto.setForeground(Color.WHITE);
		lblAgregarRepuesto.setFont(new Font("SansSerif", Font.BOLD, 14));
		add(lblAgregarRepuesto);

		NumeroDeParte.setBounds(109, 108, 178, 34);
		NumeroDeParte.setForeground(Color.WHITE);
		NumeroDeParte.setText("NUMERO DE PARTE");
		NumeroDeParte.setRound(0);
		add(NumeroDeParte);
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

		Existencias.setBounds(968, 108, 178, 34);
		Existencias.setText("EXISTENCIAS");
		Existencias.setRound(0);
		Existencias.setForeground(Color.WHITE);
		add(Existencias);
		Existencias.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Existencias.setText(null);
			}
		});

		Existencias.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				while (Existencias.getText().isEmpty())
					Existencias.setText("EXISTENCIAS");
			}
		});

		Descripcion.setBounds(376, 108, 178, 34);
		Descripcion.setText("DESCRIPCION");
		Descripcion.setRound(0);
		Descripcion.setForeground(Color.WHITE);
		add(Descripcion);
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

		Precio.setBounds(687, 108, 178, 34);
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

		Precio.setText("PRECIO LOCAL");
		Precio.setRound(0);
		Precio.setForeground(Color.WHITE);
		add(Precio);

		IngreseLosDatos.setBounds(376, 277, 289, 19);
		IngreseLosDatos.setText("INGRESE LOS DATOS");
		IngreseLosDatos.setForeground(Color.WHITE);
		IngreseLosDatos.setFont(new Font("SansSerif", Font.BOLD, 14));
		add(IngreseLosDatos);

		lblNumeroDeParte.setText("Numero de parte");
		lblNumeroDeParte.setForeground(Color.WHITE);
		lblNumeroDeParte.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblNumeroDeParte.setBounds(10, 89, 102, 19);
		add(lblNumeroDeParte);

		lblDescripcion.setText("Descripcion");
		lblDescripcion.setForeground(Color.WHITE);
		lblDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblDescripcion.setBounds(313, 89, 81, 19);
		add(lblDescripcion);

		lblPrecioLocal.setText("Precio Local");
		lblPrecioLocal.setForeground(Color.WHITE);
		lblPrecioLocal.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblPrecioLocal.setBounds(622, 89, 81, 19);
		add(lblPrecioLocal);

		lblExistencias.setText("Existencias");
		lblExistencias.setForeground(Color.WHITE);
		lblExistencias.setFont(new Font("SansSerif", Font.PLAIN, 12));
		lblExistencias.setBounds(902, 89, 102, 19);
		add(lblExistencias);

		Agregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					numeroDeParte = NumeroDeParte.getText();
					existencia = Integer.parseInt(Existencias.getText());
					precio = new BigDecimal(Precio.getText());
					descripcion = Descripcion.getText();
					precioExterior = new BigDecimal(PrecioExterior.getText());

					repuesto = new Repuesto(numeroDeParte, descripcion, existencia, precio, precioExterior);

					try {

						if (repdao.agregarRespuesto(repuesto)) {
							cambiarColorLabel(IngreseLosDatos, "DATOS CARGADOS CORRECTAMENTE", verde);
						} else {
							cambiarColorLabel(IngreseLosDatos, "ERROR EN LA CARGA DE DATOS", rojo);
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Error color label.", "Error",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				} catch (NumberFormatException ex) {
					// Si ocurre una excepción, mostrar el icono de alerta y resaltar el campo
					JOptionPane.showMessageDialog(null, "Error en los campos de texto.", "Error",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});

		Agregar.setBounds(716, 268, 117, 40);
		Agregar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				Agregar.setForeground(Color.WHITE);
				Agregar.setBorderColor(colorVerde);
				Agregar.setBackground(colorVerde);
			}
		});
		Agregar.setText("AGREGAR");
		Agregar.setForeground(Color.WHITE);
		Agregar.setColorOver(new Color(119, 193, 255));
		Agregar.setColorClick(new Color(15, 147, 255));
		Agregar.setColor(Color.DARK_GRAY);
		Agregar.setBorderColor(colorVerde);
		Agregar.setBackground(colorVerde);
		add(Agregar);

		PrecioExterior.setText("PRECIO EXTERIOR");
		PrecioExterior.setRound(0);
		PrecioExterior.setForeground(Color.WHITE);
		PrecioExterior.setBounds(687, 183, 178, 34);
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
		lblPrecioExterior.setBounds(622, 163, 81, 19);
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
				label.setForeground(Color.white);
			}
		});

		hilo.start();
	}
}
