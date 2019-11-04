package grafica.ventanas;

import grafica.controladores.ControladorRegistrarVictoria;
import logicaPersistencia.excepciones.PersistenciaException;

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

public class VentanaRegistrarVictoria
{

	private JFrame frmRegistrarVictoria;
	private JTextField txtNroParticipante;
	private JTextField txtNroTemporada;
	private JButton btnRegistrarVictoria;
	private ControladorRegistrarVictoria cont;

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
					VentanaRegistrarVictoria window = new VentanaRegistrarVictoria();
					window.frmRegistrarVictoria.setVisible(true);
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
	public VentanaRegistrarVictoria()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmRegistrarVictoria = new JFrame();
		frmRegistrarVictoria.setResizable(false);
		frmRegistrarVictoria.setTitle("Registrar Victoria");
		frmRegistrarVictoria.setBounds(100, 100, 219, 224);
		frmRegistrarVictoria.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegistrarVictoria.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 35, 187, 111);
		frmRegistrarVictoria.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNomTemporada = new JLabel("Nro Participante");
		lblNomTemporada.setBounds(10, 11, 167, 14);
		panel.add(lblNomTemporada);
		
		txtNroParticipante = new JTextField();
		txtNroParticipante.setBounds(10, 26, 167, 20);
		panel.add(txtNroParticipante);
		txtNroParticipante.setColumns(10);
		
		JLabel lblAo = new JLabel("Nro Temporada");
		lblAo.setBounds(10, 57, 167, 14);
		panel.add(lblAo);
		
		txtNroTemporada = new JTextField();
		txtNroTemporada.setBounds(10, 72, 167, 20);
		panel.add(txtNroTemporada);
		txtNroTemporada.setColumns(10);
		
		JLabel lblRegistrarVictoria = new JLabel("Registrar Victoria");
		lblRegistrarVictoria.setForeground(SystemColor.textHighlight);
		lblRegistrarVictoria.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRegistrarVictoria.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistrarVictoria.setBounds(10, 10, 187, 14);
		frmRegistrarVictoria.getContentPane().add(lblRegistrarVictoria);
		
		btnRegistrarVictoria = new JButton("Agregar victoria");
		btnRegistrarVictoria.setBounds(84, 157, 113, 23);
		frmRegistrarVictoria.getContentPane().add(btnRegistrarVictoria);
		
		btnRegistrarVictoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				String nroParticipante = txtNroParticipante.getText();
				String nroTemporada = txtNroTemporada.getText();
				try {
					cont.RegistrarVictoria(Integer.parseInt(nroParticipante), Integer.parseInt(nroTemporada));
					JOptionPane.showMessageDialog(frmRegistrarVictoria, "Victoria registrada correctamente", "Resultado", JOptionPane.INFORMATION_MESSAGE);
					frmRegistrarVictoria.dispose();
				} catch (NumberFormatException | RemoteException | PersistenciaException e1) {
					JOptionPane.showMessageDialog(frmRegistrarVictoria, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}				
			}
			
		});
		
		cont = new ControladorRegistrarVictoria(this);		
		
		frmRegistrarVictoria.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setVisible(boolean valor)
	{
		frmRegistrarVictoria.setVisible(valor);
	}
}
