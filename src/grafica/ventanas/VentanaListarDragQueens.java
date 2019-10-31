package grafica.ventanas;

import grafica.controladores.ContVentanaListarDragQueens;
import logicaPersistencia.valueObjects.VODragQueenVictorias;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class VentanaListarDragQueens
{

	private JFrame frmListarDragqueens;
	private JTable tblDatos;
	private ContVentanaListarDragQueens cont;
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
		cont = new ContVentanaListarDragQueens(this);
		frmListarDragqueens = new JFrame();
		frmListarDragqueens.setTitle("Listar DragQueens");
		frmListarDragqueens.setBounds(100, 100, 771, 517);
		frmListarDragqueens.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmListarDragqueens.getContentPane().setLayout(null);
		
		tblDatos = new JTable();
		tblDatos.setBorder(UIManager.getBorder("ComboBox.border"));
		tblDatos.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane(tblDatos);
		scrollPane.setBounds(133, 0, 622, 478);
		frmListarDragqueens.getContentPane().add(scrollPane);
		
		JButton btnListarDragQueens = new JButton("Listar");
		btnListarDragQueens.setBounds(10, 209, 89, 23);
		frmListarDragqueens.getContentPane().add(btnListarDragQueens);
		
		txtNroTemp = new JTextField();
		txtNroTemp.setBounds(10, 165, 89, 20);
		frmListarDragqueens.getContentPane().add(txtNroTemp);
		txtNroTemp.setColumns(10);
		
		JLabel lblNroTemp = new JLabel("Nro de Temporada");
		lblNroTemp.setBounds(10, 140, 89, 14);
		frmListarDragqueens.getContentPane().add(lblNroTemp);
		
		btnListarDragQueens.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cont.ListarDragQueens(Integer.parseInt(txtNroTemp.getText()));
			}
		});
		
		frmListarDragqueens.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void setVisible(boolean valor)
	{
		frmListarDragqueens.setVisible(valor);
	}
	
	public void ListarDragQueens(List<VODragQueenVictorias> lista)
	{

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
	
	public void mostrarError(String res)
	{
		JOptionPane.showMessageDialog(frmListarDragqueens, res, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public void mostrarResultado(String res)
	{
		
		JOptionPane.showMessageDialog(frmListarDragqueens, res, "Resultado", JOptionPane.INFORMATION_MESSAGE);
		frmListarDragqueens.dispose();
	}
}
