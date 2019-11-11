package grafica.ventanas;

import grafica.controladores.ControladorNuevaTemporada;
import logica.excepciones.PersistenciaException;
import logica.excepciones.TemporadaException;

import java.awt.event.*;
import java.rmi.RemoteException;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import java.awt.SystemColor;

public class VentanaNuevaTemporada
{

	private JFrame frmNuevaTemporada;
	private JTextField txtNroTemporada;
	private JTextField txtAnio;
	private JTextField txtCantCapitulos;
	private JButton btnRegistrarNuevaTemporada;
	private ControladorNuevaTemporada cont;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					VentanaNuevaTemporada window = new VentanaNuevaTemporada();
					window.frmNuevaTemporada.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaNuevaTemporada()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmNuevaTemporada = new JFrame();
		frmNuevaTemporada.setResizable(false);
		frmNuevaTemporada.setTitle("Nueva Temporada");
		frmNuevaTemporada.setBounds(100, 100, 219, 262);
		frmNuevaTemporada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNuevaTemporada.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 35, 187, 147);
		frmNuevaTemporada.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNomTemporada = new JLabel("N\u00FAmero");
		lblNomTemporada.setBounds(10, 11, 167, 14);
		panel.add(lblNomTemporada);
		
		txtNroTemporada = new JTextField();
		txtNroTemporada.setBounds(10, 26, 167, 20);
		panel.add(txtNroTemporada);
		txtNroTemporada.setColumns(10);
		
		JLabel lblAo = new JLabel("A\u00F1o");
		lblAo.setBounds(10, 57, 167, 14);
		panel.add(lblAo);
		
		txtAnio = new JTextField();
		txtAnio.setBounds(10, 72, 167, 20);
		panel.add(txtAnio);
		txtAnio.setColumns(10);
		
		JLabel lblCantidadDeCaptulos = new JLabel("Cantidad de cap\u00EDtulos");
		lblCantidadDeCaptulos.setBounds(10, 103, 167, 14);
		panel.add(lblCantidadDeCaptulos);
		
		txtCantCapitulos = new JTextField();
		txtCantCapitulos.setBounds(10, 119, 167, 20);
		panel.add(txtCantCapitulos);
		txtCantCapitulos.setColumns(10);
		
		JLabel lblNuevaTemporada = new JLabel("Nueva temporada");
		lblNuevaTemporada.setForeground(SystemColor.textHighlight);
		lblNuevaTemporada.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNuevaTemporada.setHorizontalAlignment(SwingConstants.CENTER);
		lblNuevaTemporada.setBounds(10, 10, 187, 14);
		frmNuevaTemporada.getContentPane().add(lblNuevaTemporada);
		
		btnRegistrarNuevaTemporada = new JButton("Registrar");
		btnRegistrarNuevaTemporada.setBounds(84, 193, 113, 23);
		frmNuevaTemporada.getContentPane().add(btnRegistrarNuevaTemporada);
		
		btnRegistrarNuevaTemporada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String nroTemp = txtNroTemporada.getText();
				String anio = txtAnio.getText();
				String cantCaps = txtCantCapitulos.getText();
				
				try {
					cont.nuevaTemporada(Integer.parseInt(nroTemp), Integer.parseInt(anio), Integer.parseInt(cantCaps));
					JOptionPane.showMessageDialog(frmNuevaTemporada, "Temporada creada correctamente", "Resultado", JOptionPane.INFORMATION_MESSAGE);
					frmNuevaTemporada.dispose();
				} catch (NumberFormatException | RemoteException | PersistenciaException | TemporadaException e1) {
					JOptionPane.showMessageDialog(frmNuevaTemporada, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}				
			}
			
		});
		
		cont = new ControladorNuevaTemporada(this);		
		
		frmNuevaTemporada.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setVisible(boolean valor)
	{
		frmNuevaTemporada.setVisible(valor);
	}
}
