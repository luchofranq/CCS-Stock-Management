package Login;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import Clases.Usuario;
import DAO.Usuario_DAO;
import Principal.Principal;
import textfield.TextField;
import textfield.PasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Login extends JFrame {
	int xx, xy;

	public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                Login frame = new Login();
	                frame.setUndecorated(true);
	                frame.setBounds(75, 75, 600, 300);
	                frame.setVisible(true);
	                
	                // Obtener el tamaño de la pantalla
	                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	                
	                // Calcular las coordenadas para posicionar la ventana en el centro
	                int x = (int) ((screenSize.getWidth() - frame.getWidth()) / 2);
	                int y = (int) ((screenSize.getHeight() - frame.getHeight()) / 2);
	                
	                // Establecer las coordenadas para la ubicación de la ventana
	                frame.setLocation(x, y);
	                
	                // Verifica si SystemTray es compatible
	                if (!SystemTray.isSupported()) {
	                    System.out.println("SystemTray no está soportado.");
	                    return;
	                }

	              

	            

	            } catch (Exception e) {
	            	JOptionPane.showMessageDialog(null, "Error Login: linea 70: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	}
	public Login() {
		JPanel panel = new JPanel();
		panel.setBackground(new Color( 0, 143, 159 ));
		panel.setBounds(0, 0, 185, 300);
		getContentPane().add(panel);
		panel.setLayout(null);
		ImageIcon newIcon = null;
		
		 String currentDir = System.getProperty("user.dir");
         String relativePath = "resources" + File.separator + "logochiquito.png";
         String imagePath = currentDir + File.separator + relativePath;
		
		
		try {

			newIcon = new ImageIcon(imagePath);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error Login: linea 87: " + e.getMessage()+" "+imagePath , "Error", JOptionPane.ERROR_MESSAGE);
		}
		JButton logo = new JButton();
		logo.setBounds(0, 0, 185, 300);

		logo.setIcon(newIcon);
		logo.setOpaque(false);
		logo.setContentAreaFilled(false);
		logo.setBorderPainted(false);
		logo.setFocusable(false);
		panel.add(logo);

		getContentPane().setBackground(new Color(29,29,29));

		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 332);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				xx = e.getX();

				xy = e.getY();

			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();

				int y = e.getYOnScreen();

				setLocation(x - xx, y - xy);
			}
		});

		setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		PasswordField passwordField2 = new PasswordField();
		passwordField2.setLineColor(new Color(0, 143, 159));
		passwordField2.setForeground(Color.WHITE);
		passwordField2.setBackground(new Color(29,29,29));
		passwordField2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		passwordField2.setShowAndHide(true);
		passwordField2.setLabelText("Contraseña");
		passwordField2.setBounds(277, 117, 221, 44);
		getContentPane().add(passwordField2);

		TextField txtUsuario = new TextField();
		txtUsuario.setLineColor(new Color(0, 143, 159));
		txtUsuario.setForeground(Color.WHITE);
		txtUsuario.setBackground(new Color(29,29,29));
		txtUsuario.setLabelText("Usuario");
		txtUsuario.setBounds(277, 46, 221, 44);
		getContentPane().add(txtUsuario);
		if (txtUsuario.hasFocus()) {
			txtUsuario.setText("");
		}

		JButton btnRegistrarse = new JButton("Registrarse");

		btnRegistrarse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
		
				btnRegistrarse.setForeground(Color.white);
			}

			public void mouseExited(MouseEvent e) {
				Color white = new Color(255, 255, 255);
				btnRegistrarse.setForeground(white);

			}
		});

		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtUsuario.getText().isEmpty()||passwordField2.getPassword().length==0) {
					UIManager.put("OptionPane.background", new Color(29,29,29));
					UIManager.put("Panel.background", new Color(29,29,29));
					UIManager.put("Panel.foreground", Color.WHITE);
					UIManager.put("Button.background", new Color( 0, 143, 159 ));
					UIManager.put("Button.foreground", Color.WHITE);
					UIManager.put("Label.foreground", Color.WHITE);
					
					UIManager.put("OptionPane.messageForeground", Color.WHITE);
					UIManager.put("Button.cursor", Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					JOptionPane.showMessageDialog(null, "Usuario o contraseña vacios");
				
				}
				else {
					Usuario user = new Usuario();
					Usuario_DAO usuarioDAO = new Usuario_DAO();
					user.setUsuario(txtUsuario.getText());
					user.setPassword(String.valueOf(passwordField2.getPassword()));
					
					if (usuarioDAO.consultarUsuario(user)) {
						JOptionPane.showMessageDialog(null, "Usuario ya registrado anteriormente");
						
					} else {

						usuarioDAO.guardarUsuario(user);
						JOptionPane.showMessageDialog(null, "Se registro correctamente");
						
					}
			}}
		});
		btnRegistrarse.setForeground(Color.WHITE);
		btnRegistrarse.setBackground(new Color( 0, 143, 159 ));
		btnRegistrarse.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegistrarse.setBorder(null);
		btnRegistrarse.setBounds(277, 208, 90, 25);
		getContentPane().add(btnRegistrarse);

		JLabel btnSalir = new JLabel("X");
		btnSalir.setForeground(new Color(141, 159, 154));

		btnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}

			public void mouseEntered(MouseEvent e) {
				Color red = new Color(255, 9, 9);

				btnSalir.setForeground(red);

			}

			public void mouseExited(MouseEvent e) {
				Color gray = new Color(141, 159, 154);
				btnSalir.setForeground(gray);

			}
		});
		btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalir.setFont(new Font("Arial Black", Font.BOLD, 18));
		btnSalir.setBounds(554, 0, 20, 37);
		getContentPane().add(btnSalir);

		JButton btnIniciarSesin = new JButton("Iniciar Sesión");
		btnIniciarSesin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				
				btnIniciarSesin.setForeground(Color.white);
			}

			public void mouseExited(MouseEvent e) {
				Color white = new Color(255, 255, 255);
				btnIniciarSesin.setForeground(white);

			}
		});
		btnIniciarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario user = new Usuario();
				Usuario_DAO usuarioDAO = new Usuario_DAO();
				user.setUsuario(txtUsuario.getText());
				user.setPassword(String.valueOf(passwordField2.getPassword()));

				if (usuarioDAO.consultarUsuario(user)) {

					// Establece las propiedades específicas para tu clase
					UIManager.put("OptionPane.background", new Color(29,29,29));
					UIManager.put("Panel.background", new Color(29,29,29));
					UIManager.put("Panel.foreground", Color.WHITE);
					UIManager.put("Button.background", new Color( 0, 143, 159 ));
					UIManager.put("Button.foreground", Color.WHITE);
					UIManager.put("OptionPane.messageForeground", Color.WHITE);
					UIManager.put("Button.cursor", Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

					// Tu código que utiliza las configuraciones personalizadas
					JOptionPane.showMessageDialog(null, "Login correcto. Bienvenido " + user.getUsuario());

					// Restaura las propiedades originales del UIManager

					loginExitoso();
				} else {
					UIManager.put("OptionPane.background", new Color(29,29,29));
					UIManager.put("Panel.background", new Color(29,29,29));
					UIManager.put("Panel.foreground", Color.WHITE);
					UIManager.put("Button.background", new Color( 0, 143, 159 ));
					UIManager.put("Button.foreground", Color.WHITE);
					UIManager.put("OptionPane.messageForeground", Color.WHITE);
					UIManager.put("Button.cursor", Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
					
				}
			}
		});

		btnIniciarSesin.setForeground(Color.WHITE);
		btnIniciarSesin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnIniciarSesin.setBorder(null);
		btnIniciarSesin.setBackground(new Color( 0, 143, 159 ));
		btnIniciarSesin.setBounds(408, 208, 90, 25);
		getContentPane().add(btnIniciarSesin);

	}

	public void loginExitoso() {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	                // Obtener el tamaño de la barra de herramientas de Windows
	                Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
	                int taskBarHeight = screenInsets.bottom;

	                // Restar la altura de la barra de herramientas del alto total de la pantalla
	                int frameWidth = screenSize.width;
	                int frameHeight = screenSize.height - taskBarHeight;

	                setSize(frameWidth, frameHeight);

	                Principal principal = new Principal();
	                setBackground(Color.BLACK);
	                // Establecer la posición de la ventana en (0, 0)
	                setLocation(0, 0);

	                // Eliminar los listeners de mouse
	                removeMouseListeners();

	                setContentPane(principal);
	                setVisible(true);
	            } catch (Exception e) {
	            	JOptionPane.showMessageDialog(null, "Error Login: linea 321: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	}
	   
	
	private void removeMouseListeners() {
	    for (MouseListener listener : getMouseListeners()) {
	        removeMouseListener(listener);
	    }

	    for (MouseMotionListener listener : getMouseMotionListeners()) {
	        removeMouseMotionListener(listener);
	    }
	}
}