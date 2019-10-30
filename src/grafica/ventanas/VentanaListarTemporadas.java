package grafica.ventanas;

import java.awt.EventQueue;
import java.awt.event.*;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ContVentanaListarTemporadas;
import logicaPersistencia.valueObjects.VOTemporada;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;


public class VentanaListarTemporadas
{

	private JFrame frmListarTemporadas;
	private JTable tblDatos;
	private ContVentanaListarTemporadas cont;

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
		cont = new ContVentanaListarTemporadas(this);
		frmListarTemporadas = new JFrame();
		frmListarTemporadas.setTitle("Listado de Temporadas");
		frmListarTemporadas.setBounds(100, 100, 801, 541);
		frmListarTemporadas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListarTemporadas.getContentPane().setLayout(null);
		
		JButton btnListarTemporadas = new JButton("Listar");
		btnListarTemporadas.setBounds(10, 219, 79, 23);
		frmListarTemporadas.getContentPane().add(btnListarTemporadas);
		
		tblDatos = new JTable();
		tblDatos.setBorder(UIManager.getBorder("ComboBox.border"));
		tblDatos.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(tblDatos);
		scrollPane.setBounds(99, 0, 686, 502);
		frmListarTemporadas.getContentPane().add(scrollPane);		
		
		btnListarTemporadas.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						cont.ListarTemporadas();
					}
				}
		);
		
		frmListarTemporadas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setVisible(boolean valor)
	{
		frmListarTemporadas.setVisible(valor);
	}
	
	public void ListarTemporadas(List<VOTemporada> lista)
	{

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
	
	public void mostrarError(String res)
	{
		JOptionPane.showMessageDialog(frmListarTemporadas, res, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void mostrarResultado(String res)
	{
		
		JOptionPane.showMessageDialog(frmListarTemporadas, res, "Resultado", JOptionPane.INFORMATION_MESSAGE);
		frmListarTemporadas.dispose();
	}
}
