package grafica.ventanas;

import grafica.controladores.ContVentanaInscribirDragQueen;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaInscribirDragQueen
{

	private JFrame frmInscribirDragQueen;
	private ContVentanaInscribirDragQueen cont;
	private JTextField txtNombre;
	private JTextField txtNroTemp;

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
					VentanaInscribirDragQueen window = new VentanaInscribirDragQueen();
					window.frmInscribirDragQueen.setVisible(true);
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
	public VentanaInscribirDragQueen()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmInscribirDragQueen = new JFrame();
		frmInscribirDragQueen.setTitle("Inscribir DragQueen");
		frmInscribirDragQueen.setBounds(100, 100, 249, 288);
		
		cont = new ContVentanaInscribirDragQueen(this);
		
		frmInscribirDragQueen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmInscribirDragQueen.getContentPane().setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(86, 14, 53, 14);
		frmInscribirDragQueen.getContentPane().add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(69, 39, 86, 20);
		frmInscribirDragQueen.getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNroTemp = new JLabel("Nro de Temporada");
		lblNroTemp.setBounds(60, 86, 104, 14);
		frmInscribirDragQueen.getContentPane().add(lblNroTemp);
		
		txtNroTemp = new JTextField();
		txtNroTemp.setBounds(69, 114, 86, 20);
		frmInscribirDragQueen.getContentPane().add(txtNroTemp);
		txtNroTemp.setColumns(10);
		
		JButton btnInscribirDragQueen = new JButton("Inscribir");
		btnInscribirDragQueen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String nom = txtNombre.getText();
				int nroTemp = Integer.parseInt(txtNroTemp.getText());
				cont.InscribirDragQueen(nom, nroTemp);
			}
		});
		btnInscribirDragQueen.setBounds(68, 187, 89, 23);
		frmInscribirDragQueen.getContentPane().add(btnInscribirDragQueen);
	}
	
	public void setVisible(boolean valor)
	{
		frmInscribirDragQueen.setVisible(valor);
	}
	
	public void mostrarError(String res)
	{
		JOptionPane.showMessageDialog(frmInscribirDragQueen, res, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void mostrarResultado(String res)
	{
		
		JOptionPane.showMessageDialog(frmInscribirDragQueen, res, "Resultado", JOptionPane.INFORMATION_MESSAGE);
		frmInscribirDragQueen.dispose();
	}
}
