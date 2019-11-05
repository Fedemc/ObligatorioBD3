package grafica.ventanas;

import java.awt.EventQueue;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorListarTemporadas;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOTemporada;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;


public class VentanaListarTemporadas
{

	private JFrame frmListarTemporadas;
	private JTable tblDatos;
	private ControladorListarTemporadas cont;

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
					VentanaListarTemporadas window = new VentanaListarTemporadas();
					window.frmListarTemporadas.setVisible(true);
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
	public VentanaListarTemporadas()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		cont = new ControladorListarTemporadas(this);
		frmListarTemporadas = new JFrame();
		frmListarTemporadas.setTitle("Listado de Temporadas");
		frmListarTemporadas.setBounds(100, 100, 300, 400);
		frmListarTemporadas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListarTemporadas.getContentPane().setLayout(null);
		
		tblDatos = new JTable();
		tblDatos.setBorder(UIManager.getBorder("ComboBox.border"));
		tblDatos.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(tblDatos);
		scrollPane.setBounds(10, 36, 264, 314);
		frmListarTemporadas.getContentPane().add(scrollPane);				
		
		JLabel lblTemporadas = new JLabel("Listado de temporadas");
		lblTemporadas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemporadas.setForeground(SystemColor.textHighlight);
		lblTemporadas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTemporadas.setBounds(10, 11, 264, 14);
		frmListarTemporadas.getContentPane().add(lblTemporadas);
		frmListarTemporadas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		listarTemporadas();
		
	}
	
	public void setVisible(boolean valor)
	{
		frmListarTemporadas.setVisible(valor);
	}
	
	public void listarTemporadas()
	{
		List<VOTemporada> lista = new ArrayList<VOTemporada>();
		
		// Listamos las temporadas en pantalla
		try {
			lista = cont.ListarTemporadas();
		} catch (RemoteException | PersistenciaException e1) {
			JOptionPane.showMessageDialog(frmListarTemporadas, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		// Me traigo la lista de las temporadas, recorro y tiro cada dato a la tabla
		DefaultTableModel modelo=new DefaultTableModel();
		modelo.addColumn("Nro");
		modelo.addColumn("Año");
		modelo.addColumn("Capítulos");
		Object rowData[]= new Object[3];
		
		for(VOTemporada voT : lista)
		{
			rowData[0] = voT.getNroTemp();
			rowData[1] = voT.getAnio();
			rowData[2] = voT.getCantCapitulos();
			modelo.addRow(rowData);
		}		
		tblDatos.setModel(modelo);
	}
}
