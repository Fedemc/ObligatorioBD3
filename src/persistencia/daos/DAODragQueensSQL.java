package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logica.DragQueen;
import logica.IConexion;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueen;
import logica.valueObjects.VODragQueenVictorias;
import persistencia.consultas.Consultas;

public class DAODragQueensSQL implements IDAODragQueens
{
	private int nroTemp;
	
	public DAODragQueensSQL(int nroT)
	{
		nroTemp = nroT;
	}
	
	public void insBack(DragQueen drag, IConexion icon) throws PersistenciaException
	{
		Connection con = icon.getConnection();
		try
		{
			PreparedStatement pstmt = con.prepareStatement(Consultas.inscribirDragQueen());
			pstmt.setInt(1, drag.getNroPart());
			pstmt.setString(2, drag.getNombre());
			pstmt.setInt(3, nroTemp);
			pstmt.executeUpdate();
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
	}
	
	public boolean esVacia(IConexion icon, int nroTemp) throws PersistenciaException
	{
		boolean vacio = false;
		Connection con = icon.getConnection();
		try
		{
			PreparedStatement pstmt = con.prepareStatement(Consultas.dragQueensDeTemporada());
			pstmt.setInt(1, nroTemp);
			ResultSet rs = pstmt.executeQuery();
			vacio = !rs.next();			
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
		
		return vacio;
	}
	
	public int largo(IConexion icon) throws PersistenciaException
	{
		int largo = 0;
		Connection con = icon.getConnection();
		try
		{
			PreparedStatement pstmt = con.prepareStatement(Consultas.cantidadParticipantesDeTemporada());
			pstmt.setInt(1, nroTemp);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				largo = rs.getInt("cantParticipantes");
			}			
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}		
		
		return largo;
	}
	
	public DragQueen k_esima(int nroP, IConexion icon) throws PersistenciaException
	{
		DragQueen resu=null;
		Connection con = icon.getConnection();
		try
		{
			PreparedStatement pstmt = con.prepareStatement(Consultas.dragQueenPorNumeroDeParticipanteYNumeroTemporada());
			pstmt.setInt(1, nroP);
			pstmt.setInt(2, nroTemp);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				resu = new DragQueen(rs.getInt("nroPart"), rs.getString("nombre"), rs.getInt("cantVictorias"));
			}
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
		
		return resu;
	}
	
	public List<VODragQueenVictorias> listarDragQueens(IConexion icon) throws PersistenciaException
	{
		List<VODragQueenVictorias> resu = new ArrayList<VODragQueenVictorias>();
		Connection con = icon.getConnection();
		try
		{
			PreparedStatement pstmt = con.prepareStatement(Consultas.dragQueensDeTemporada());
			pstmt.setInt(1, nroTemp);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				VODragQueenVictorias voDQ = new VODragQueenVictorias(rs.getString("nombre"), rs.getInt("nroTemp"), rs.getInt("nroPart"), rs.getInt("cantVictorias"));
				resu.add(voDQ);
			}
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
		
		return resu;
	}
	
	public void registrarVictoria(int nroPart, IConexion icon) throws PersistenciaException
	{
		Connection con = icon.getConnection();
		try
		{
			PreparedStatement pstmt = con.prepareStatement(Consultas.registrarVictoria());
			pstmt.setInt(1, nroPart);
			pstmt.setInt(2, nroTemp);
			pstmt.executeUpdate();
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
	}
	
	public VODragQueenVictorias obtenerGanadora(IConexion icon) throws PersistenciaException
	{
		VODragQueenVictorias ganadora=null;
		Connection con = icon.getConnection();
		int mayor = 0, nroPart = 0;
		String nombre = null;
		try
		{
			PreparedStatement pstmt = con.prepareStatement(Consultas.cantidadDeVictoriasDeDragQueensDeTemporada());
			pstmt.setInt(1, nroTemp);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				int victorias = rs.getInt("cantVictorias");
				if(victorias >= mayor)
				{
					mayor = victorias;
					nroPart = rs.getInt("nroPart");
					nombre = rs.getString("nombre");
				}
			}
			ganadora = new VODragQueenVictorias(nombre, nroTemp, nroPart, mayor);
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
		
		return ganadora;
	}
}
