package grafica.ventanas;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import grafica.controladores.ControladorTemporadaMasParticipantes;
import logica.excepciones.PersistenciaException;
import logica.excepciones.TemporadaException;
import logica.valueObjects.VOTempMaxPart;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;


public class VentanaTemporadaMasParticipantes
{

	private JFrame frmListarTemporadas;
	private JTable tblDatos;
	private ControladorTemporadaMasParticipantes cont;

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
					VentanaTemporadaMasParticipantes window = new VentanaTemporadaMasParticipantes();
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
	public VentanaTemporadaMasParticipantes()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		cont = new ControladorTemporadaMasParticipantes(this);
		frmListarTemporadas = new JFrame();
		frmListarTemporadas.setTitle("Temporada con más participantes");
		frmListarTemporadas.setBounds(100, 100, 350, 122);
		frmListarTemporadas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListarTemporadas.getContentPane().setLayout(null);
		
		tblDatos = new JTable();
		tblDatos.setBorder(UIManager.getBorder("ComboBox.border"));
		tblDatos.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(tblDatos);
		scrollPane.setBounds(10, 36, 314, 39);
		frmListarTemporadas.getContentPane().add(scrollPane);				
		
		JLabel lblTemporadaConMs = new JLabel("Temporada con m\u00E1s participantes");
		lblTemporadaConMs.setHorizontalAlignment(SwingConstants.CENTER);
		lblTemporadaConMs.setForeground(SystemColor.textHighlight);
		lblTemporadaConMs.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTemporadaConMs.setBounds(10, 11, 314, 14);
		frmListarTemporadas.getContentPane().add(lblTemporadaConMs);
		frmListarTemporadas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		listarTemporadaMasParticipantes();
	}
	
	public void setVisible(boolean valor)
	{
		frmListarTemporadas.setVisible(valor);
	}
	
	public void listarTemporadaMasParticipantes()
	{
		VOTempMaxPart vo = null;
		
		try {
			vo = cont.TempMaxPart();
		} catch (RemoteException | PersistenciaException | TemporadaException e) {
			JOptionPane.showMessageDialog(frmListarTemporadas, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		// Me traigo la lista de las temporadas, recorro y tiro cada dato a la tabla
		DefaultTableModel modelo=new DefaultTableModel();
		modelo.addColumn("Nro");
		modelo.addColumn("Año");
		modelo.addColumn("Capítulos");
		modelo.addColumn("Participantes");
		if (vo != null) {
			Object rowData[]= new Object[4];
			rowData[0] = vo.getNroTemp();
			rowData[1] = vo.getAnio();
			rowData[2] = vo.getCantCapitulos();
			rowData[3] = vo.getCantParticipantes();
			modelo.addRow(rowData);	
		}
		
		tblDatos.setModel(modelo);
	}
}
