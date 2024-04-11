package ExcelPreciosToSQL;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import BD.ConexionDB;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import button.MyButton;

public class ExcelPreciosToSQL extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField filePathTextField;
	
	private JLabel dirr;
	private MyButton mbtnConvertirALa;
	private MyButton mbtnBuscar;

	public ExcelPreciosToSQL() {
		String imagePath = "/logoventana.png";
		

		
		ImageIcon iconoVentana = new ImageIcon(getClass().getResource(imagePath));

		// Establece la imagen como ícono de la ventana
		setIconImage(iconoVentana.getImage());
		
		// Obtén la imagen del ImageIcon
		Image imagenIcono = iconoVentana.getImage();

		// Escala la imagen para que tenga un tamaño adecuado
		ImageIcon iconoEscalado = new ImageIcon(imagenIcono.getScaledInstance(32, 32, Image.SCALE_SMOOTH));

		// Establece la imagen como ícono de la ventana
		setIconImage(iconoEscalado.getImage());
		setTitle("Importar precios");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setModal(true); // Make the dialog modal

		JPanel panel = new JPanel(new GridBagLayout());

		dirr = new JLabel();
		dirr.setText("Direccion del archivo:");
		dirr.setForeground(new Color(255, 255, 255));
		dirr.setFont(new Font("SansSerif", Font.PLAIN, 12));
		GridBagConstraints gbc_dirr = new GridBagConstraints();
		gbc_dirr.insets = new Insets(0, 0, 5, 5);
		gbc_dirr.gridx = 1;
		gbc_dirr.gridy = 0;
		panel.add(dirr, gbc_dirr);

		filePathTextField = new JTextField(30);

		GridBagConstraints gbc_filePathTextField = new GridBagConstraints();
		gbc_filePathTextField.gridwidth = 2;
		gbc_filePathTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_filePathTextField.insets = new Insets(0, 0, 5, 5);
		gbc_filePathTextField.gridx = 1;
		gbc_filePathTextField.gridy = 1;
		panel.add(filePathTextField, gbc_filePathTextField);

		getContentPane().add(panel, BorderLayout.CENTER);
		
			
			mbtnBuscar = new MyButton();
			mbtnBuscar.setText("Buscar");
			mbtnBuscar. addActionListener(this);
			mbtnBuscar.setForeground(Color.WHITE);
			mbtnBuscar.setColorOver(new Color(119, 193, 255));
			mbtnBuscar.setColorClick(new Color(15, 147, 255));
			mbtnBuscar.setColor(Color.DARK_GRAY);
			mbtnBuscar.setBorderColor(new Color(1, 179, 169));
			mbtnBuscar.setBackground(new Color(1, 179, 169));
			 mbtnBuscar.addMouseListener(new MouseAdapter() {
		  			@Override
		  			public void mouseExited(MouseEvent e) {
		  				mbtnBuscar.setForeground(Color.WHITE);
		  				mbtnBuscar.setBorderColor(new Color(1, 179, 169));
		  				mbtnBuscar.setBackground(new Color(1, 179, 169));
		  			}
		  		});
			GridBagConstraints gbc_mbtnBuscar = new GridBagConstraints();
			gbc_mbtnBuscar.insets = new Insets(0, 0, 5, 0);
			gbc_mbtnBuscar.gridx = 3;
			gbc_mbtnBuscar.gridy = 1;
			panel.add(mbtnBuscar, gbc_mbtnBuscar);
		
		mbtnConvertirALa = new MyButton();
		mbtnConvertirALa.setText("Convertir a la base de datos");
		mbtnConvertirALa.setForeground(Color.WHITE);
		mbtnConvertirALa.setColorOver(new Color(119, 193, 255));
		mbtnConvertirALa. addActionListener(this);
		mbtnConvertirALa.setColorClick(new Color(15, 147, 255));
		mbtnConvertirALa.setColor(Color.DARK_GRAY);
		mbtnConvertirALa.setBorderColor(new Color(1, 179, 169));
		mbtnConvertirALa.setBackground(new Color(1, 179, 169));
		mbtnConvertirALa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				mbtnConvertirALa.setForeground(Color.WHITE);
				mbtnConvertirALa.setBorderColor(new Color(1, 179, 169));
				mbtnConvertirALa.setBackground(new Color(1, 179, 169));
			}
		});
		GridBagConstraints gbc_mbtnConvertirALa = new GridBagConstraints();
		gbc_mbtnConvertirALa.gridwidth = 2;
		gbc_mbtnConvertirALa.insets = new Insets(0, 0, 0, 5);
		gbc_mbtnConvertirALa.gridx = 1;
		gbc_mbtnConvertirALa.gridy = 2;
		panel.add(mbtnConvertirALa, gbc_mbtnConvertirALa);
	}

	public void showConverterDialog() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, "Error UI PrToSql: linea 127: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mbtnBuscar) {
			JFileChooser fileChooser = new JFileChooser();
			
			fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files", "xlsx", "xls"));
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				String filePath = selectedFile.getAbsolutePath();
				filePathTextField.setText(filePath);
			}
		} else if (e.getSource() == mbtnConvertirALa) {
			String filePath = filePathTextField.getText();
			if (!filePath.isEmpty()) {
				mbtnConvertirALa.setEnabled(false);
				new Thread(() -> {

					convertExcelToSQL(filePath);
					
		

					JOptionPane.showMessageDialog(this, "La conversion se realizo correctamente!", "Exito",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}).start();

			} else {
				JOptionPane.showMessageDialog(this, "Seleccione un archivo Excel", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void convertExcelToSQL(String excelFilePath) {
		try {
			String user = ConexionDB.getUser();
			String pass = ConexionDB.getPassword();
			String currentDir = System.getProperty("user.dir");
			String relativePath = "resources" + File.separator + "excel_reader_precios.py";
			String absolutePath = currentDir + File.separator + relativePath;
			File file = new File(absolutePath);
			if (file.exists()) {
				String comando = "python " + absolutePath + " --host localhost --user " + user + " --password " + pass
						+ " --database flow_database_login " + excelFilePath;
				Process proceso = Runtime.getRuntime().exec(comando);
				// Espera a que el proceso termine
				int codigoSalida = proceso.waitFor();
				if (codigoSalida != 0) {
					JOptionPane.showMessageDialog(this, "Error occurred during conversion.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(this, "Python script not found.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException | InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Error Conversion PrToSql: linea 188: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
