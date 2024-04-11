package Principal;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import com.raven.swing.RoundPanel;
import ActualizarRepuesto.ActualizarRepuesto;
import AgregarRepuesto.AgregarRepuesto;
import Clases.Repuesto;
import CotizarRepuestoExterior.CotizarRepuestoExterior;
import CotizarRepuestoLocal.CotRepuestoLocal;
import DAO.Repuesto_DAO;
import ExcelPreciosToSQL.ExcelPreciosToSQL;
import ExcelToSQLConverter.ExcelToSQLConverter;
import PanelEstadistica.PanelEstadistica;
import PanelInicioCuadro.PanelInicioCuadro;
import StockBajoMinimo.StockBajoMinimo;
import TablaCotizado.TablaCotizado;
import TablaEntrada.TablaEntrada;
import TablaStock.TablaStock;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import button.MyButton;
import tabledark.TableDark;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;

@SuppressWarnings("serial")
public class Principal extends JPanel {
	private Repuesto_DAO repdao = new Repuesto_DAO();
	private ArrayList<Repuesto> repuestos = new ArrayList<>();
	private final MyButton mbtnAgregarRepuesto = new MyButton();
	private javax.swing.JScrollPane jScrollPane1 = new JScrollPane();
	private tabledark.TableDark tableDark1 = new TableDark();
	private TableRowSorter<DefaultTableModel> sorter;
	private DefaultTableModel model = new DefaultTableModel(new Object[][] {},
			new String[] { "P/N", "Descripcion", "Cant." });
private Color colorbtn = new Color( 0, 143, 159 );
	/**
	 * Create the panel.
	 * 
	 * 
	 */
	public static void setUIFont(Font font) {
		Enumeration<Object> keys = javax.swing.UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			if (javax.swing.UIManager.get(key) instanceof javax.swing.plaf.FontUIResource) {
				javax.swing.UIManager.put(key, font);
			}
		}
	}

	public Principal() {
		
		Timer timer1 = new Timer(5000, new ActionListener() { // Actualiza cada 5 segundos (5000 milisegundos)
			@Override
			public void actionPerformed(ActionEvent e) {
				// Llama al método para actualizar la tabla y otros componentes
				updateTable();
				
			}
		});

		// Inicia el Timer
		timer1.start();
		
		
		RoundPanel panelCabecera = new RoundPanel();
	
		 String currentDir = System.getProperty("user.dir");
         String relativePath = "resources" + File.separator + "logoventana.png";
         String imagePathven = currentDir + File.separator + relativePath;
		
		
		ImageIcon iconoVentana = new ImageIcon(imagePathven);
		Image imagenIcono = iconoVentana.getImage();
		ImageIcon iconoEscalado = new ImageIcon(imagenIcono.getScaledInstance(32, 32, Image.SCALE_SMOOTH));
		JLabel imageLabelven = new JLabel();
		imageLabelven.setBounds(0, 0, 32, 32); // Establecer el tamaño del JLabel para que coincida con el tamaño de la
												// imagen
		imageLabelven.setIcon(iconoEscalado); // Establecer la imagen en el JLabel
		panelCabecera.add(imageLabelven);
	
	
		
		ImageIcon icon = null;
		
		 String currentDir1 = System.getProperty("user.dir");
         String relativePath1 = "resources" + File.separator + "/logochiquito.png";
         String imagePath = currentDir1 + File.separator + relativePath1;
		
		
		try {
			icon = new ImageIcon(imagePath);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error Principal: linea 114: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		setBackground(Color.black);

		repuestos = repdao.menorCantidad();
		jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
		jScrollPane1.setViewportBorder(BorderFactory.createEmptyBorder());
		
		Object[] fila = new Object[model.getColumnCount()];

		for (Repuesto p : repuestos) {
			fila[0] = p.getNumeroDeParte();
			fila[1] = p.getDescripcion();
			fila[2] = p.getExistencia();
			model.addRow(fila);
			tableDark1.validate();
			model.fireTableDataChanged();
			updateTable();
		}
		sorter = new TableRowSorter<>(model);
		setLayout(null);
		setLayout(null);
		setLayout(new BorderLayout(0, 0));
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBackground(new Color(1, 179, 169));
		popupMenu.setForeground(Color.WHITE);
		popupMenu.setBorderPainted(false);

		RoundPanel panelMenu = new RoundPanel();
		panelMenu.setSize(166, 713);
		add(panelMenu, BorderLayout.WEST);
		panelMenu.setBackground(new Color(29, 29, 29));
		panelMenu.setLayout(null);
		panelMenu.setPreferredSize(new Dimension(153, 0)); // Ancho fijo para el menú
		GridBagLayout gbl_panelMenu = new GridBagLayout();
		gbl_panelMenu.columnWidths = new int[] { 152, 0 };
		gbl_panelMenu.rowHeights = new int[] { 150, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 0 };
		gbl_panelMenu.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panelMenu.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panelMenu.setLayout(gbl_panelMenu);

		MyButton mbtnVerCotizados = new MyButton();
		mbtnVerCotizados.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				mbtnVerCotizados.setBorderColor(colorbtn);
				mbtnVerCotizados.setBackground(colorbtn);
				mbtnVerCotizados.setForeground(Color.WHITE);
			}
		});
		mbtnVerCotizados.setBackground(new Color(1, 179, 169));
		mbtnVerCotizados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TablaCotizado tablaCotizado = new TablaCotizado();
				cambiarPanel(tablaCotizado);
				updateTable();

			}
		});
		mbtnAgregarRepuesto.setFont(new Font("Dialog", Font.BOLD, 12));
		mbtnAgregarRepuesto.setBackground(new Color(1, 179, 169));
		mbtnAgregarRepuesto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				AgregarRepuesto agregarRepuesto = new AgregarRepuesto();
				cambiarPanel(agregarRepuesto);
				updateTable();
			}
			public void mouseExited(MouseEvent e) {
				mbtnAgregarRepuesto.setBorderColor(colorbtn);
				mbtnAgregarRepuesto.setBackground(colorbtn);
				mbtnAgregarRepuesto.setForeground(Color.WHITE);
			}
		});
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 153, 145);
		panel.setLayout(null); // Usar AbsoluteLayout para controlar la posición de los componentes
		panel.setBackground(new Color(29, 29, 29));
		panel.setFocusable(false);

		JLabel imageLabel = new JLabel();
		imageLabel.setBounds(0, 0, 152, 145); // Establecer el tamaño del JLabel para que coincida con el tamaño de la
												// imagen
		imageLabel.setIcon(icon); // Establecer la imagen en el JLabel
		panel.add(imageLabel);

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		panelMenu.add(panel, gbc_panel);

		MyButton mbtnInicio = new MyButton();
		mbtnInicio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mbtnInicio.setSize(150, 45);
		mbtnInicio.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				PanelInicioCuadro pic = new PanelInicioCuadro();
				cambiarPanel(pic);
				updateTable();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mbtnInicio.setBorderColor(colorbtn);
				mbtnInicio.setBackground(colorbtn);
				mbtnInicio.setForeground(Color.WHITE);
			}
		});

		mbtnInicio.setText("Inicio");
		mbtnInicio.setRadius(20);
		mbtnInicio.setForeground(Color.WHITE);
		mbtnInicio.setColorOver(new Color(0, 128, 178));
		mbtnInicio.setColorClick(new Color(15, 147, 255));
		mbtnInicio.setColor(Color.WHITE);
		mbtnInicio.setBorderColor(colorbtn);
		mbtnInicio.setBackground(colorbtn);
		GridBagConstraints gbc_mbtnInicio = new GridBagConstraints();
		gbc_mbtnInicio.fill = GridBagConstraints.BOTH;
		gbc_mbtnInicio.insets = new Insets(0, 0, 5, 0);
		gbc_mbtnInicio.gridx = 0;
		gbc_mbtnInicio.gridy = 1;
		panelMenu.add(mbtnInicio, gbc_mbtnInicio);

		mbtnAgregarRepuesto.setText("Agregar stock");
		mbtnAgregarRepuesto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mbtnAgregarRepuesto.setRadius(20);
		mbtnAgregarRepuesto.setForeground(Color.white);
		mbtnAgregarRepuesto.setColorOver(new Color(0, 128, 178));
		mbtnAgregarRepuesto.setColorClick(new Color(15, 147, 255));
		mbtnAgregarRepuesto.setColor(Color.DARK_GRAY);
		mbtnAgregarRepuesto.setBorderColor(colorbtn);
		mbtnAgregarRepuesto.setBackground(colorbtn);
		GridBagConstraints gbc_mbtnAgregarRepuesto = new GridBagConstraints();
		gbc_mbtnAgregarRepuesto.fill = GridBagConstraints.BOTH;
		gbc_mbtnAgregarRepuesto.insets = new Insets(0, 0, 5, 0);
		gbc_mbtnAgregarRepuesto.gridx = 0;
		gbc_mbtnAgregarRepuesto.gridy = 2;
		panelMenu.add(mbtnAgregarRepuesto, gbc_mbtnAgregarRepuesto);

		MyButton verEntrada = new MyButton();
		verEntrada.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				verEntrada.setBorderColor(colorbtn);
				verEntrada.setBackground(colorbtn);
				verEntrada.setForeground(Color.WHITE);
			}
		});
		verEntrada.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		verEntrada.setSize(150, 45);
		verEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TablaEntrada tablaentrada = new TablaEntrada();
				cambiarPanel(tablaentrada);
				updateTable();

			}
		});

		MyButton mbtnModificarRepuesto = new MyButton();
		mbtnModificarRepuesto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				mbtnModificarRepuesto.setBorderColor(colorbtn);
				mbtnModificarRepuesto.setBackground(colorbtn);
				mbtnModificarRepuesto.setForeground(Color.WHITE);
			}
		});
		mbtnModificarRepuesto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mbtnModificarRepuesto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ActualizarRepuesto actualizarRepuesto = new ActualizarRepuesto();
				cambiarPanel(actualizarRepuesto);
				updateTable();
			}
		});
		mbtnModificarRepuesto.setText("Modificar stock");
		mbtnModificarRepuesto.setRadius(20);
		mbtnModificarRepuesto.setForeground(Color.WHITE);
		mbtnModificarRepuesto.setColorOver(new Color(0, 128, 178));
		mbtnModificarRepuesto.setColorClick(new Color(15, 147, 255));
		mbtnModificarRepuesto.setColor(Color.DARK_GRAY);
		mbtnModificarRepuesto.setBorderColor(colorbtn);
		mbtnModificarRepuesto.setBackground(colorbtn);
		GridBagConstraints gbc_mbtnModificarRepuesto = new GridBagConstraints();
		gbc_mbtnModificarRepuesto.fill = GridBagConstraints.BOTH;
		gbc_mbtnModificarRepuesto.insets = new Insets(0, 0, 5, 0);
		gbc_mbtnModificarRepuesto.gridx = 0;
		gbc_mbtnModificarRepuesto.gridy = 3;
		panelMenu.add(mbtnModificarRepuesto, gbc_mbtnModificarRepuesto);

		MyButton mbtnCotizarRepuesto = new MyButton();
		mbtnCotizarRepuesto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mbtnCotizarRepuesto.setText("Cotizar repuesto");
		mbtnCotizarRepuesto.setRadius(20);
		mbtnCotizarRepuesto.setForeground(new Color(255, 255, 255));
		mbtnCotizarRepuesto.setColorOver(new Color(0, 128, 178));
		mbtnCotizarRepuesto.setColorClick(new Color(15, 147, 255));
		mbtnCotizarRepuesto.setColor(Color.DARK_GRAY);
		mbtnCotizarRepuesto.setBorderColor(colorbtn);
		mbtnCotizarRepuesto.setBackground(colorbtn);

		// Agregar MouseListener al botón para mostrar el popup

		GridBagConstraints gbc_mbtnCotizarRepuesto = new GridBagConstraints();
		gbc_mbtnCotizarRepuesto.fill = GridBagConstraints.BOTH;
		gbc_mbtnCotizarRepuesto.insets = new Insets(0, 0, 5, 0);
		gbc_mbtnCotizarRepuesto.gridx = 0;
		gbc_mbtnCotizarRepuesto.gridy = 4;
		panelMenu.add(mbtnCotizarRepuesto, gbc_mbtnCotizarRepuesto);

		MyButton mbtnImportarExcel = new MyButton();
		mbtnImportarExcel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				mbtnImportarExcel.setBorderColor(colorbtn);
				mbtnImportarExcel.setBackground(colorbtn);
				mbtnImportarExcel.setForeground(Color.WHITE);
			
			}
		});
		mbtnImportarExcel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mbtnImportarExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == mbtnImportarExcel) {

					ExcelToSQLConverter converter = new ExcelToSQLConverter();
					try {
						UIManager.put("OptionPane.background", new Color(29,29,29));
						UIManager.put("Panel.background", new Color(29,29,29));
						UIManager.put("Panel.foreground", Color.WHITE);
						UIManager.put("Button.background", new Color( 0, 143, 159 ));
						UIManager.put("Button.foreground", Color.WHITE);
						UIManager.put("OptionPane.messageForeground", Color.WHITE);
						UIManager.put("Button.cursor", Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Error Principal: linea 360: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					converter.showConverterDialog();
				}

			}
		});
		mbtnImportarExcel.setText("Importar excel");
		mbtnImportarExcel.setRadius(20);
		mbtnImportarExcel.setForeground(Color.WHITE);
		mbtnImportarExcel.setColorOver(new Color(0, 128, 178));
		mbtnImportarExcel.setColorClick(new Color(15, 147, 255));
		mbtnImportarExcel.setColor(Color.DARK_GRAY);
		mbtnImportarExcel.setBorderColor(colorbtn);
		mbtnImportarExcel.setBackground(colorbtn);
		GridBagConstraints gbc_mbtnImportarExcel = new GridBagConstraints();
		gbc_mbtnImportarExcel.fill = GridBagConstraints.BOTH;
		gbc_mbtnImportarExcel.insets = new Insets(0, 0, 5, 0);
		gbc_mbtnImportarExcel.gridx = 0;
		gbc_mbtnImportarExcel.gridy = 5;
		panelMenu.add(mbtnImportarExcel, gbc_mbtnImportarExcel);

		MyButton importarPrecios = new MyButton();
		importarPrecios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		importarPrecios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				importarPrecios.setBorderColor(colorbtn);
				importarPrecios.setBackground(colorbtn);
				importarPrecios.setForeground(Color.WHITE);
			}
		});
		importarPrecios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (e.getSource() == importarPrecios) {

					ExcelPreciosToSQL converter = new ExcelPreciosToSQL();
					try {
						UIManager.put("OptionPane.background", new Color(29,29,29));
						UIManager.put("Panel.background", new Color(29,29,29));
						UIManager.put("Panel.foreground", Color.WHITE);
						UIManager.put("Button.background", new Color( 0, 143, 159 ));
						UIManager.put("Button.foreground", Color.WHITE);
						UIManager.put("OptionPane.messageForeground", Color.WHITE);
						UIManager.put("Button.cursor", Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Error Principal: linea 407: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					converter.showConverterDialog();
				}

			}
		});
		importarPrecios.setText("Importar precios");
		importarPrecios.setRadius(20);
		importarPrecios.setForeground(Color.WHITE);
		importarPrecios.setColorOver(new Color(0, 128, 178));
		importarPrecios.setColorClick(new Color(15, 147, 255));
		importarPrecios.setColor(Color.DARK_GRAY);
		importarPrecios.setBorderColor(colorbtn);
		importarPrecios.setBackground(colorbtn);
		GridBagConstraints gbc_importarPrecios = new GridBagConstraints();
		gbc_importarPrecios.fill = GridBagConstraints.BOTH;
		gbc_importarPrecios.insets = new Insets(0, 0, 5, 0);
		gbc_importarPrecios.gridx = 0;
		gbc_importarPrecios.gridy = 6;
		panelMenu.add(importarPrecios, gbc_importarPrecios);
		verEntrada.setText("Ver entrada");
		verEntrada.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		verEntrada.setRadius(20);
		verEntrada.setForeground(Color.WHITE);
		verEntrada.setColorOver(new Color(0, 128, 178));
		verEntrada.setColorClick(new Color(15, 147, 255));
		verEntrada.setColor(Color.DARK_GRAY);
		verEntrada.setBorderColor(colorbtn);
		verEntrada.setBackground(colorbtn);
		GridBagConstraints gbc_verEntrada = new GridBagConstraints();
		gbc_verEntrada.fill = GridBagConstraints.BOTH;
		gbc_verEntrada.insets = new Insets(0, 0, 5, 0);
		gbc_verEntrada.gridx = 0;
		gbc_verEntrada.gridy = 7;
		panelMenu.add(verEntrada, gbc_verEntrada);

		MyButton mbtnVerStock = new MyButton();
		mbtnVerStock.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				mbtnVerStock.setBorderColor(colorbtn);
				mbtnVerStock.setBackground(colorbtn);
				mbtnVerStock.setForeground(Color.WHITE);
			}
		});
		mbtnVerStock.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mbtnVerStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TablaStock tablarepuesto = new TablaStock();
				cambiarPanel(tablarepuesto);
				updateTable();
			}
		});
		mbtnVerStock.setText("Ver stock");
		mbtnVerStock.setRadius(20);
		mbtnVerStock.setForeground(Color.WHITE);
		mbtnVerStock.setColorOver(new Color(0, 128, 178));
		mbtnVerStock.setColorClick(new Color(15, 147, 255));
		mbtnVerStock.setColor(Color.DARK_GRAY);
		mbtnVerStock.setBorderColor(colorbtn);
		mbtnVerStock.setBackground(colorbtn);
		GridBagConstraints gbc_mbtnVerStock = new GridBagConstraints();
		gbc_mbtnVerStock.fill = GridBagConstraints.BOTH;
		gbc_mbtnVerStock.insets = new Insets(0, 0, 5, 0);
		gbc_mbtnVerStock.gridx = 0;
		gbc_mbtnVerStock.gridy = 8;
		panelMenu.add(mbtnVerStock, gbc_mbtnVerStock);
		mbtnVerCotizados.setText("Historial cotizados");
		mbtnVerCotizados.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mbtnVerCotizados.setRadius(20);
		mbtnVerCotizados.setForeground(Color.WHITE);
		mbtnVerCotizados.setColorOver(new Color(0, 128, 178));
		mbtnVerCotizados.setColorClick(new Color(15, 147, 255));
		mbtnVerCotizados.setColor(Color.DARK_GRAY);
		mbtnVerCotizados.setBorderColor(colorbtn);
		mbtnVerCotizados.setBackground(colorbtn);
		GridBagConstraints gbc_mbtnVerCotizados = new GridBagConstraints();
		gbc_mbtnVerCotizados.insets = new Insets(0, 0, 5, 0);
		gbc_mbtnVerCotizados.fill = GridBagConstraints.BOTH;
		gbc_mbtnVerCotizados.gridx = 0;
		gbc_mbtnVerCotizados.gridy = 9;
		panelMenu.add(mbtnVerCotizados, gbc_mbtnVerCotizados);

		MyButton Minimo = new MyButton();
		Minimo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Minimo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				Minimo.setBorderColor(colorbtn);
				Minimo.setBackground(colorbtn);
				Minimo.setForeground(Color.WHITE);
			}
		});
		Minimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StockBajoMinimo sbm = new StockBajoMinimo();
				cambiarPanel(sbm);
				updateTable();

			}
		});
		Minimo.setText("Stock bajo minimo");
		Minimo.setRadius(20);
		Minimo.setForeground(new Color(255, 255, 255));
		Minimo.setColorOver(new Color(0, 128, 178));
		Minimo.setColorClick(new Color(15, 147, 255));
		Minimo.setColor(Color.DARK_GRAY);
		Minimo.setBorderColor(colorbtn);
		Minimo.setBackground(colorbtn);
		GridBagConstraints gbc_Minimo = new GridBagConstraints();
		gbc_Minimo.fill = GridBagConstraints.BOTH;
		gbc_Minimo.gridx = 0;
		gbc_Minimo.gridy = 10;
		panelMenu.add(Minimo, gbc_Minimo);

		// Agregar los botones al popupMenu

		add(panelCentral);
		panelCentral.setBackground(Color.BLACK);
		panelCentral.setLayout(null);
		tableDark1.setShowGrid(false);
		tableDark1.setEnabled(false);
		tableDark1.setShowVerticalLines(false);
		tableDark1.setShowHorizontalLines(false);
		tableDark1.setBackground(Color.DARK_GRAY);
		tableDark1.setRowSelectionAllowed(false);

		tableDark1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableDark1.setBorder(null);
		tableDark1.setForeground(Color.WHITE);
		tableDark1.setAutoCreateRowSorter(true);

		tableDark1.setModel(model);
		tableDark1.setRowSorter(sorter);
		jScrollPane1.setViewportBorder(null);

		jScrollPane1.setViewportView(tableDark1);
		jScrollPane1.getViewport().setBackground(new Color(29, 29, 29));

		jScrollPane1.setPreferredSize(new Dimension(597, 264));

		JMenuItem mntmCotizarNacional = new JMenuItem("Cotizar Local");
		
		mntmCotizarNacional.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmCotizarNacional.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CotRepuestoLocal cotrep = new CotRepuestoLocal();
				cambiarPanel(cotrep);
				updateTable();
			}
		});
		mntmCotizarNacional.setHorizontalAlignment(SwingConstants.LEFT);
		mntmCotizarNacional.setForeground(Color.white);
		mntmCotizarNacional.setBackground(colorbtn);
		popupMenu.add(mntmCotizarNacional);
		JMenuItem mntmCotizarInternacional = new JMenuItem("Cotizar Exterior");
	
		mntmCotizarInternacional.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		mntmCotizarInternacional.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CotizarRepuestoExterior cotrepin = new CotizarRepuestoExterior();
				cambiarPanel(cotrepin);
				updateTable();
			}
		});
		mntmCotizarInternacional.setHorizontalAlignment(SwingConstants.LEFT);
		mntmCotizarInternacional.setForeground(Color.white);
		mntmCotizarInternacional.setBackground(colorbtn);
		popupMenu.add(mntmCotizarInternacional);
		popupMenu.setBackground(new Color(29,29,29));

		mbtnCotizarRepuesto.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				popupMenu.show(mbtnCotizarRepuesto, mbtnCotizarRepuesto.getWidth() - 5, 0);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mbtnCotizarRepuesto.setBorderColor(colorbtn);
				mbtnCotizarRepuesto.setBackground(colorbtn);
				mbtnCotizarRepuesto.setForeground(Color.WHITE);
			}
		});

		// Agrega un MouseListener al menú emergente para mantenerlo abierto mientras el
		// cursor esté dentro de él
		popupMenu.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				popupMenu.setVisible(true);
			}

			public void mouseExited(MouseEvent e) {
				popupMenu.setVisible(false);
			}
		});

		PanelInicioCuadro panelInicioCuadro = new PanelInicioCuadro();
		panelInicioCuadro.setBounds(0, 0, 1153, 385);
		// Añadir PanelInicioCuadro al panel_cuadroPrincipal
		panel_cuadroPrincipal = new JPanel();
		panel_cuadroPrincipal.setBounds(16, 0, 1185, 385);
		panel_cuadroPrincipal.setLayout(null);
		panel_cuadroPrincipal.add(panelInicioCuadro);

		JPanel panel_porcentajes = new JPanel();
		panel_porcentajes.setBackground(new Color(255, 250, 205));
		panel_porcentajes.setBounds(626, 397, 575, 293);
		panel_porcentajes.setLayout(new BoxLayout(panel_porcentajes, BoxLayout.X_AXIS));

		PanelEstadistica panelEstadistica = new PanelEstadistica();
		panel_porcentajes.add(panelEstadistica);
		panelCentral.add(panel_porcentajes);

		panel_cuadroPrincipal.setBackground(new Color(29, 29, 29));

		panelCentral.add(panel_cuadroPrincipal);

		JPanel panel_menorStock = new JPanel();
		panel_menorStock.setBackground(new Color(29,29,29));
		panel_menorStock.setBounds(16, 397, 597, 293);
		panel_menorStock.setBackground(new Color(29,29,29));
		panelCentral.add(panel_menorStock);
		panel_menorStock.setLayout(new BoxLayout(panel_menorStock, BoxLayout.PAGE_AXIS));

		JLabel lblRepuestosConMenor = new JLabel();
		lblRepuestosConMenor.setBackground(Color.WHITE);
		lblRepuestosConMenor.setText("Repuestos con menor stock");
		lblRepuestosConMenor.setForeground(Color.WHITE);
		lblRepuestosConMenor.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_menorStock.add(lblRepuestosConMenor);

		panel_cuadroPrincipal.setPreferredSize(new Dimension(1195, 385)); // Tamaño preferido para el panel

		panel_menorStock.setPreferredSize(new Dimension(597, 304)); // Tamaño preferido para el panel
		panel_menorStock.add(jScrollPane1);

		
		panelCabecera.setLayout(null);
		panelCabecera.setPreferredSize(new Dimension(0, 36));
		panelCabecera.setBackground(Color.BLACK);
		add(panelCabecera, BorderLayout.NORTH);

		JLabel lblNewLabel_1 = new JLabel("X  ");
		lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1.setForeground(Color.red);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1.setForeground(new Color(141, 159, 154));
			}
		});
		lblNewLabel_1.setForeground(new Color(141, 159, 154));
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblNewLabel_1.setBounds(1339, 7, 21, 21);
		panelCabecera.add(lblNewLabel_1);

		JLabel lblControlYCotizacion = new JLabel();
		lblControlYCotizacion.setText("CCS - Control y Cotizacion de Stock");
		lblControlYCotizacion.setForeground(Color.WHITE);
		lblControlYCotizacion.setFont(new Font("Dialog", Font.BOLD, 14));
		lblControlYCotizacion.setBounds(44, 8, 252, 19);
		panelCabecera.add(lblControlYCotizacion);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {

					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

					// Obtener los bordes de la ventana
					Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());

					// Obtener la altura de la barra de tareas
					int taskBarHeight = screenInsets.bottom;

					// Restar la altura de la barra de tareas del alto total de la pantalla
					int frameWidth = screenSize.width;
					int frameHeight = screenSize.height - taskBarHeight;

					// Establecer el tamaño de la ventana
					setPreferredSize(new Dimension(frameWidth, frameHeight));

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error Principal: linea 700: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}

	public void cambiarPanel(JPanel nuevoPanel) {

		if (panel_cuadroPrincipal != null) {
			panel_cuadroPrincipal.setVisible(false);
		}
		panelAnterior = panel_cuadroPrincipal;
		panel_cuadroPrincipal = nuevoPanel;
		panel_cuadroPrincipal.setBounds(16, 0, 1207, 385);
		panelCentral.add(panel_cuadroPrincipal);
		panel_cuadroPrincipal.setVisible(true);

	}

	public void volver() {
		if (panelAnterior != null) {
			panelAnterior.setVisible(true);
			panel_cuadroPrincipal.setVisible(false);
			panel_cuadroPrincipal = panelAnterior;

			cambiarPanel(panelAnterior);
		}
	}

	private JPanel panel_cuadroPrincipal;
	private JPanel panelAnterior = new JPanel();
	private JPanel panelCentral = new JPanel();

	private void updateTable() {
		model.setRowCount(0);
		repuestos = repdao.menorCantidad();
		Object[] fila = new Object[model.getColumnCount()];
		for (Repuesto p : repuestos) {
			fila[0] = p.getNumeroDeParte();
			fila[1] = p.getDescripcion();
			fila[2] = p.getExistencia();
			model.addRow(fila);
			tableDark1.validate();
			model.fireTableDataChanged();
		}
		tableDark1.validate();
		model.fireTableDataChanged();
	}

}
