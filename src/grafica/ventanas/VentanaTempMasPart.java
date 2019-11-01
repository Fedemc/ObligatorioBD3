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
import grafica.controladores.ContVentanaTempMaxPart;
import logicaPersistencia.valueObjects.VOTempMaxPart;
import logicaPersistencia.valueObjects.VOTemporada;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;


public class VentanaTempMasPart
{

	private JFrame frmListarTemporadas;
	private JTable tblDatos;
	private ContVentanaTempMaxPart cont;

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
					VentanaTempMasPart window = new VentanaTempMasPart();
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
	public VentanaTempMasPart()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		cont = new ContVentanaTempMaxPart(this);
		frmListarTemporadas = new JFrame();
		frmListarTemporadas.setTitle("Temporada con más participantes");
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
						cont.TempMaxPart();
					}
				}
		);
		
		frmListarTemporadas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setVisible(boolean valor)
	{
		frmListarTemporadas.setVisible(valor);
	}
	
	public void ListarTempMasParticipantes(VOTempMaxPart temp)
	{
		// Me traigo la lista de las temporadas, recorro y tiro cada dato a la tabla
		DefaultTableModel modelo=new DefaultTableModel();
		modelo.addColumn("Nro");
		modelo.addColumn("Año");
		modelo.addColumn("Capítulos");
		modelo.addColumn("Participantes");
		Object rowData[]= new Object[4];
		rowData[0] = temp.getNroTemp();
		rowData[1] = temp.getAnio();
		rowData[2] = temp.getCantCapitulos();
		rowData[3] = temp.getCantParticipantes();
		modelo.addRow(rowData);	
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
