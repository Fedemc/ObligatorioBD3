package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import logica.DragQueen;
import logica.IConexion;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueenVictorias;
import persistencia.consultas.Consultas;

public class DAODragQueens
{
	private int nroTemp;
	
	public DAODragQueens(int nroTemp)
	{
		
	}
	
	public void insBack(DragQueen drag)
	{
		
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
			largo = rs.getInt("cantParticipantes");
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}		
		
		return largo;
	}
	
	public DragQueen k_esima(int nroP)
	{
		DragQueen resu=null;
		
		return resu;
	}
	
	public List<VODragQueenVictorias> listarDragQueens()
	{
		List<VODragQueenVictorias> resu = null;
		
		return resu;
	}
	
	public void registrarVictoria(int nroPart)
	{
		
	}
	
	public VODragQueenVictorias obtenerGanadora()
	{
		VODragQueenVictorias ganadora=null;
		
		return ganadora;
	}
	
	public void setNroTemp(int nroT)
	{
		nroTemp = nroT;
	}

}
