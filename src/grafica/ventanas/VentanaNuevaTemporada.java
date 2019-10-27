package grafica.ventanas;

import grafica.controladores.ContVentanaNuevaTemporada;

import java.awt.event.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class VentanaNuevaTemporada
{

	private JFrame frmNuevaTemporada;
	private JTextField txtNroTemporada;
	private JTextField txtAnio;
	private JTextField txtCantCapitulos;
	private JButton btnRegistrarNuevaTemporada;
	private ContVentanaNuevaTemporada cont;

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
		frmNuevaTemporada.setTitle("Nueva Temporada");
		frmNuevaTemporada.setBounds(100, 100, 240, 271);
		frmNuevaTemporada.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNuevaTemporada.getContentPane().setLayout(null);
		
		txtNroTemporada = new JTextField();
		txtNroTemporada.setBounds(64, 24, 86, 20);
		frmNuevaTemporada.getContentPane().add(txtNroTemporada);
		txtNroTemporada.setColumns(10);
		
		JLabel lblNomTemporada = new JLabel("Nro de Temporada");
		lblNomTemporada.setBounds(64, 11, 113, 14);
		frmNuevaTemporada.getContentPane().add(lblNomTemporada);
		
		JLabel lblAo = new JLabel("A\u00F1o");
		lblAo.setBounds(64, 65, 46, 14);
		frmNuevaTemporada.getContentPane().add(lblAo);
		
		txtAnio = new JTextField();
		txtAnio.setColumns(10);
		txtAnio.setBounds(64, 78, 86, 20);
		frmNuevaTemporada.getContentPane().add(txtAnio);
		
		JLabel lblCantidadDeCaptulos = new JLabel("Cantidad de cap\u00EDtulos");
		lblCantidadDeCaptulos.setBounds(64, 121, 113, 14);
		frmNuevaTemporada.getContentPane().add(lblCantidadDeCaptulos);
		
		txtCantCapitulos = new JTextField();
		txtCantCapitulos.setColumns(10);
		txtCantCapitulos.setBounds(64, 134, 86, 20);
		frmNuevaTemporada.getContentPane().add(txtCantCapitulos);
		
		btnRegistrarNuevaTemporada = new JButton("Registrar");
		btnRegistrarNuevaTemporada.setBounds(64, 176, 89, 23);
		frmNuevaTemporada.getContentPane().add(btnRegistrarNuevaTemporada);
		
		cont = new ContVentanaNuevaTemporada(this);		
		
		btnRegistrarNuevaTemporada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String nroTemp = txtNroTemporada.getText();
				String anio = txtAnio.getText();
				String cantCaps = txtCantCapitulos.getText();
				cont.InscribirNuevaTemporada(Integer.parseInt(nroTemp), Integer.parseInt(anio), Integer.parseInt(cantCaps));
			}
			
		});
		
		frmNuevaTemporada.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setVisible(boolean valor)
	{
		frmNuevaTemporada.setVisible(valor);
	}
	
	public void mostrarError(String res)
	{
		JOptionPane.showMessageDialog(frmNuevaTemporada, res, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void mostrarResultado(String res)
	{
		
		JOptionPane.showMessageDialog(frmNuevaTemporada, res, "Resultado", JOptionPane.INFORMATION_MESSAGE);
		frmNuevaTemporada.dispose();
	}
}
