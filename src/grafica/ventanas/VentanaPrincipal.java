package grafica.ventanas;

import java.awt.event.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import grafica.ventanas.*;
import grafica.controladores.ContPrincipal;

public class VentanaPrincipal
{

	private JFrame frmRu;
	private ContPrincipal cont;
	
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
					VentanaPrincipal window = new VentanaPrincipal();
					window.frmRu.setVisible(true);
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
	public VentanaPrincipal()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmRu = new JFrame();
		frmRu.setTitle("RuPaul\u2019s Drag Race Admin");
		frmRu.setBounds(100, 100, 755, 291);
		frmRu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRu.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(58, 37, 228, 115);
		frmRu.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNuevaTemporada = new JButton("Nueva Temporada");
		btnNuevaTemporada.setBounds(48, 13, 121, 23);
		panel.add(btnNuevaTemporada);
		btnNuevaTemporada.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				VentanaNuevaTemporada ventNuevaTemp = new VentanaNuevaTemporada();
				ventNuevaTemp.setVisible(true);
			}		
		});
		
		JButton btnListarTemporadas = new JButton("Listar Temporadas");
		btnListarTemporadas.setBounds(48, 47, 121, 23);
		panel.add(btnListarTemporadas);
		btnListarTemporadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				VentanaListarTemporadas ventListarTemp = new VentanaListarTemporadas();
				ventListarTemp.setVisible(true);
			}
		});
		
		
		JButton btnTempMasParticipantes = new JButton("Temporada con mas participantes");
		btnTempMasParticipantes.setBounds(10, 81, 193, 23);
		panel.add(btnTempMasParticipantes);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(435, 37, 141, 170);
		frmRu.getContentPane().add(panel_1);
		
		JButton btnInscribirDragQueen = new JButton("Inscribir DragQueen");
		btnInscribirDragQueen.setBounds(10, 23, 121, 23);
		panel_1.add(btnInscribirDragQueen);
		btnInscribirDragQueen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				VentanaInscribirDragQueen ventInscribirDQ = new VentanaInscribirDragQueen();
				ventInscribirDQ.setVisible(true);
			}
		});
		
		JButton btnRegistrarVictoria = new JButton("Registrar Victoria");
		btnRegistrarVictoria.setBounds(10, 91, 121, 23);
		panel_1.add(btnRegistrarVictoria);
		
		JButton btnListarDragQueens = new JButton("Listar DragQueens");
		btnListarDragQueens.setBounds(10, 57, 121, 23);
		panel_1.add(btnListarDragQueens);
		
		JButton btnObtenerGanadora = new JButton("Obtener Ganadora");
		btnObtenerGanadora.setBounds(10, 125, 121, 23);
		panel_1.add(btnObtenerGanadora);
		
		JLabel lblOperacionesConTemporadas = new JLabel("Operaciones con Temporadas");
		lblOperacionesConTemporadas.setBounds(98, 12, 155, 14);
		frmRu.getContentPane().add(lblOperacionesConTemporadas);
		
		JLabel lblOperacionesConDragqueens = new JLabel("Operaciones con DragQueens");
		lblOperacionesConDragqueens.setBounds(421, 12, 155, 14);
		frmRu.getContentPane().add(lblOperacionesConDragqueens);
		
		cont = ContPrincipal.GetInstancia();
		
	}
}
