package grafica.ventanas;

import grafica.controladores.ControladorListarDragQueenGanadora;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueenVictorias;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class VentanaListarDragQueenGanadora
{

	private JFrame frmListarDragQueenGanadora;
	private JTable tblDatos;
	private ControladorListarDragQueenGanadora cont;
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
					VentanaListarDragQueenGanadora window = new VentanaListarDragQueenGanadora();
					window.frmListarDragQueenGanadora.setVisible(true);
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
	public VentanaListarDragQueenGanadora()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		cont = new ControladorListarDragQueenGanadora(this);
		frmListarDragQueenGanadora = new JFrame();
		frmListarDragQueenGanadora.setTitle("Listado de Drag Queens por temporada");
		frmListarDragQueenGanadora.setBounds(100, 100, 300, 470);
		frmListarDragQueenGanadora.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListarDragQueenGanadora.getContentPane().setLayout(null);
		
		tblDatos = new JTable();
		tblDatos.setBorder(UIManager.getBorder("ComboBox.border"));
		tblDatos.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(tblDatos);
		scrollPane.setBounds(10, 103, 264, 314);
		frmListarDragQueenGanadora.getContentPane().add(scrollPane);
		
		JLabel lblListadoDeDrag = new JLabel("Listado de Drag Queens por temporada");
		lblListadoDeDrag.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDeDrag.setForeground(SystemColor.textHighlight);
		lblListadoDeDrag.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblListadoDeDrag.setBounds(10, 11, 264, 14);
		frmListarDragQueenGanadora.getContentPane().add(lblListadoDeDrag);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 36, 264, 60);
		frmListarDragQueenGanadora.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnListarDragQueenGanadora = new JButton("Listar");
		btnListarDragQueenGanadora.setBounds(165, 26, 89, 23);
		panel.add(btnListarDragQueenGanadora);
		
		txtNroTemp = new JTextField();
		txtNroTemp.setBounds(10, 27, 145, 20);
		panel.add(txtNroTemp);
		txtNroTemp.setColumns(10);
		
		JLabel lblNroTemp = new JLabel("Nro. temporada");
		lblNroTemp.setBounds(10, 11, 89, 14);
		panel.add(lblNroTemp);
		
		btnListarDragQueenGanadora.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListarDragQueenGanadora();
				
			}
		});
		
		frmListarDragQueenGanadora.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setVisible(boolean valor)
	{
		frmListarDragQueenGanadora.setVisible(valor);
	}
	
	public void ListarDragQueenGanadora()
	{	
		VODragQueenVictorias lista = null;
		try {
			 lista = cont.ListarDragQueenGanadora(Integer.parseInt(txtNroTemp.getText()));					
		} catch (NumberFormatException | RemoteException | PersistenciaException e1) {
			JOptionPane.showMessageDialog(frmListarDragQueenGanadora, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		// Me traigo la lista de las DragQueens, recorro y tiro cada dato a la tabla
		DefaultTableModel modelo=new DefaultTableModel();
		modelo.addColumn("NroParticipante");
		modelo.addColumn("Nombre");
		modelo.addColumn("Victorias");
		Object rowData[]= new Object[3];
		
			rowData[0] = lista.getNroPart();
			rowData[1] = lista.getNombre();
			rowData[2] = lista.getCantVictorias();
			modelo.addRow(rowData);
				
		tblDatos.setModel(modelo);
	}
}
