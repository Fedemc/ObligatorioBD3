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
import grafica.controladores.ControladorPrincipal;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class VentanaPrincipal
{

	private JFrame frmRu;
	private ControladorPrincipal cont;
	
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
		frmRu.setResizable(false);
		frmRu.setTitle("RuPaul\u2019s Drag Race");
		frmRu.setBounds(100, 100, 300, 373);
		frmRu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRu.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 36, 263, 115);
		frmRu.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNuevaTemporada = new JButton("Nueva Temporada");
		btnNuevaTemporada.setBounds(10, 13, 243, 23);
		panel.add(btnNuevaTemporada);
		btnNuevaTemporada.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				VentanaNuevaTemporada ventNuevaTemp = new VentanaNuevaTemporada();
				ventNuevaTemp.setVisible(true);
			}		
		});
		
		JButton btnListarTemporadas = new JButton("Listar Temporadas");
		btnListarTemporadas.setBounds(10, 47, 243, 23);
		panel.add(btnListarTemporadas);
		btnListarTemporadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				VentanaListarTemporadas ventListarTemp = new VentanaListarTemporadas();
				ventListarTemp.setVisible(true);
			}
		});
		
		
		JButton btnTempMasParticipantes = new JButton("Temporada con mas participantes");
		btnTempMasParticipantes.setBounds(10, 81, 243, 23);
		panel.add(btnTempMasParticipantes);
		btnTempMasParticipantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				VentanaTemporadaMasParticipantes ventTempMasPart = new VentanaTemporadaMasParticipantes();
				ventTempMasPart.setVisible(true);
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setLayout(null);
		panel_1.setBounds(10, 184, 263, 145);
		frmRu.getContentPane().add(panel_1);
		
		JButton btnInscribirDragQueen = new JButton("Inscribir DragQueen");
		btnInscribirDragQueen.setBounds(10, 11, 243, 23);
		panel_1.add(btnInscribirDragQueen);
		btnInscribirDragQueen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				VentanaInscribirDragQueen ventInscribirDQ = new VentanaInscribirDragQueen();
				ventInscribirDQ.setVisible(true);
			}
		});
		
		JButton btnListarDragQueens = new JButton("Listar DragQueens");
		btnListarDragQueens.setBounds(10, 45, 243, 23);
		panel_1.add(btnListarDragQueens);
		btnListarDragQueens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				VentanaListarDragQueens ventListarDQs = new VentanaListarDragQueens();
				ventListarDQs.setVisible(true);
			}
		});
		
		JButton btnRegistrarVictoria = new JButton("Registrar Victoria");
		btnRegistrarVictoria.setBounds(10, 79, 243, 23);
		panel_1.add(btnRegistrarVictoria);
		btnRegistrarVictoria.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e)
		{
			VentanaRegistrarVictoria ventRegPar = new VentanaRegistrarVictoria();
			ventRegPar.setVisible(true);
		}		
	});
	
		
		JButton btnObtenerGanadora = new JButton("Obtener Ganadora");
		btnObtenerGanadora.setBounds(10, 113, 243, 23);
		panel_1.add(btnObtenerGanadora);
		btnObtenerGanadora.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				VentanaListarDragQueenGanadora ventListarDQGanadora = new VentanaListarDragQueenGanadora();
				ventListarDQGanadora.setVisible(true);
			}
		});


		
		JLabel lblOperacionesConTemporadas = new JLabel("Temporadas");
		lblOperacionesConTemporadas.setForeground(SystemColor.textHighlight);
		lblOperacionesConTemporadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblOperacionesConTemporadas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOperacionesConTemporadas.setBounds(101, 11, 80, 14);
		frmRu.getContentPane().add(lblOperacionesConTemporadas);
		
		JLabel lblOperacionesConDragqueens = new JLabel("DragQueens");
		lblOperacionesConDragqueens.setForeground(SystemColor.textHighlight);
		lblOperacionesConDragqueens.setHorizontalAlignment(SwingConstants.CENTER);
		lblOperacionesConDragqueens.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOperacionesConDragqueens.setBounds(101, 162, 80, 14);
		frmRu.getContentPane().add(lblOperacionesConDragqueens);
		
		cont = ControladorPrincipal.getInstancia();
	}
}
