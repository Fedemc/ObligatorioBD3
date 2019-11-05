package grafica.ventanas;

import grafica.controladores.ControladorInscribirDragQueen;
import logica.excepciones.PersistenciaException;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class VentanaInscribirDragQueen
{

	private JFrame frmInscribirDragQueen;
	private ControladorInscribirDragQueen cont;
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
		frmInscribirDragQueen.setBounds(100, 100, 300, 217);
		
		cont = new ControladorInscribirDragQueen(this);
		
		frmInscribirDragQueen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmInscribirDragQueen.getContentPane().setLayout(null);
		
		JButton btnInscribirDragQueen = new JButton("Inscribir");
		btnInscribirDragQueen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String nom = txtNombre.getText();
				int nroTemp = Integer.parseInt(txtNroTemp.getText());
				
				try {
					cont.InscribirDragQueen(nom, nroTemp);
					JOptionPane.showMessageDialog(frmInscribirDragQueen, "Drag Queen inscripta correctamente", "Resultado", JOptionPane.INFORMATION_MESSAGE);
					frmInscribirDragQueen.dispose();
				} catch (RemoteException | PersistenciaException e1) {
					JOptionPane.showMessageDialog(frmInscribirDragQueen, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnInscribirDragQueen.setBounds(185, 144, 89, 23);
		frmInscribirDragQueen.getContentPane().add(btnInscribirDragQueen);
		
		JLabel lblInscribirDragQueen = new JLabel("Inscribir Drag Queen");
		lblInscribirDragQueen.setHorizontalAlignment(SwingConstants.CENTER);
		lblInscribirDragQueen.setForeground(SystemColor.textHighlight);
		lblInscribirDragQueen.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInscribirDragQueen.setBounds(10, 11, 264, 14);
		frmInscribirDragQueen.getContentPane().add(lblInscribirDragQueen);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 29, 264, 104);
		frmInscribirDragQueen.getContentPane().add(panel);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 11, 53, 14);
		panel.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(10, 25, 244, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNroTemp = new JLabel("Nro. temporada");
		lblNroTemp.setBounds(10, 56, 100, 14);
		panel.add(lblNroTemp);
		
		txtNroTemp = new JTextField();
		txtNroTemp.setBounds(10, 71, 100, 20);
		panel.add(txtNroTemp);
		txtNroTemp.setColumns(10);
	}
	
	public void setVisible(boolean valor)
	{
		frmInscribirDragQueen.setVisible(valor);
	}
}
