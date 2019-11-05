package grafica.ventanas;

import grafica.controladores.ControladorListarDragQueens;
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

public class VentanaListarDragQueens
{

	private JFrame frmListarDragqueens;
	private JTable tblDatos;
	private ControladorListarDragQueens cont;
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
					VentanaListarDragQueens window = new VentanaListarDragQueens();
					window.frmListarDragqueens.setVisible(true);
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
	public VentanaListarDragQueens()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		cont = new ControladorListarDragQueens(this);
		frmListarDragqueens = new JFrame();
		frmListarDragqueens.setTitle("Listado de Drag Queens por temporada");
		frmListarDragqueens.setBounds(100, 100, 300, 470);
		frmListarDragqueens.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListarDragqueens.getContentPane().setLayout(null);
		
		tblDatos = new JTable();
		tblDatos.setBorder(UIManager.getBorder("ComboBox.border"));
		tblDatos.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(tblDatos);
		scrollPane.setBounds(10, 103, 264, 314);
		frmListarDragqueens.getContentPane().add(scrollPane);
		
		JLabel lblListadoDeDrag = new JLabel("Listado de Drag Queens por temporada");
		lblListadoDeDrag.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoDeDrag.setForeground(SystemColor.textHighlight);
		lblListadoDeDrag.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblListadoDeDrag.setBounds(10, 11, 264, 14);
		frmListarDragqueens.getContentPane().add(lblListadoDeDrag);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 36, 264, 60);
		frmListarDragqueens.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnListarDragQueens = new JButton("Listar");
		btnListarDragQueens.setBounds(165, 26, 89, 23);
		panel.add(btnListarDragQueens);
		
		txtNroTemp = new JTextField();
		txtNroTemp.setBounds(10, 27, 145, 20);
		panel.add(txtNroTemp);
		txtNroTemp.setColumns(10);
		
		JLabel lblNroTemp = new JLabel("Nro. temporada");
		lblNroTemp.setBounds(10, 11, 89, 14);
		panel.add(lblNroTemp);
		
		btnListarDragQueens.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListarDragQueens();
				
			}
		});
		
		frmListarDragqueens.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setVisible(boolean valor)
	{
		frmListarDragqueens.setVisible(valor);
	}
	
	public void ListarDragQueens()
	{
		List<VODragQueenVictorias> lista = new ArrayList<VODragQueenVictorias>();
		
		try {
			lista = cont.ListarDragQueens(Integer.parseInt(txtNroTemp.getText()));					
		} catch (NumberFormatException | RemoteException | PersistenciaException e1) {
			JOptionPane.showMessageDialog(frmListarDragqueens, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		// Me traigo la lista de las DragQueens, recorro y tiro cada dato a la tabla
		DefaultTableModel modelo=new DefaultTableModel();
		modelo.addColumn("NroParticipante");
		modelo.addColumn("Nombre");
		modelo.addColumn("Victorias");
		Object rowData[]= new Object[3];
		
		for(VODragQueenVictorias voDq : lista)
		{
			rowData[0] = voDq.getNroPart();
			rowData[1] = voDq.getNombre();
			rowData[2] = voDq.getCantVictorias();
			modelo.addRow(rowData);
		}		
		tblDatos.setModel(modelo);
	}
}
