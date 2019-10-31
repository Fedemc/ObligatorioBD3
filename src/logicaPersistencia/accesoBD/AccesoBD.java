package logicaPersistencia.accesoBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.IConexion;
import logicaPersistencia.valueObjects.*;

public class AccesoBD
{
	public void NuevaTemporada(IConexion icon, int nroTemp, int anio, int cantCapitulos) throws SQLException 
	{
		Connection con = icon.GetConnection();
		String query = Consultas.NuevaTemporada();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroTemp);
		pstmt.setInt(2, anio);
		pstmt.setInt(3, cantCapitulos);
		pstmt.executeUpdate();
		
		pstmt.close();
		con.commit();
	}
	
	public void InscribirDragQueen(IConexion icon, String nombre, int nroTemp) throws SQLException, PersistenciaException
	{
		Connection con = icon.GetConnection();
		int nroPart = 0;
		
		// Verifico si existe una temporada con ese nro
		PreparedStatement pstmtExisteNroTemp = con.prepareStatement(Consultas.TempConNroTemp());
		pstmtExisteNroTemp.setInt(1, nroTemp);
		ResultSet rs = pstmtExisteNroTemp.executeQuery();
		if(rs.next())
		{
			// Existe la temporada
			// Obtengo la cant de participantes, para poder numerar al nuevo
			String cantParticipantesQuery = Consultas.CantParticipantesTemp();
			PreparedStatement pstmt = con.prepareStatement(cantParticipantesQuery);
			pstmt.setInt(1, nroTemp);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				nroPart = rs.getInt("cantParticipantes");
				nroPart++;
			}
			String query = Consultas.InscribirDragQueen();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nroPart);
			pstmt.setString(2, nombre);
			pstmt.setInt(3, nroTemp);
			pstmt.executeUpdate();
			
			pstmt.close();
			con.commit();
		}
		else
		{
			throw new PersistenciaException("ERROR: No existe una temporada registrada con ese nro.");
		}
	}
	
	public List<VOTemporada> ListarTemporadas(IConexion icon) throws SQLException
	{
		Connection con = icon.GetConnection();
		List<VOTemporada> resu = new ArrayList<VOTemporada>();
		String query = Consultas.ListarTemporadas();
		Statement stmt=con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next())
		{
			VOTemporada voTemp = new VOTemporada(rs.getInt("nroTemp"), rs.getInt("anio"), rs.getInt("cantCapitulos"));
			resu.add(voTemp);
		}
		
		rs.close();
		stmt.close();
		return resu;
	}
	
	public List<VODragQueenVictorias> ListarDragQueens(IConexion icon, int nroTemp) throws SQLException, PersistenciaException
	{
		Connection con = icon.GetConnection();
		List<VODragQueenVictorias> resu = new ArrayList<VODragQueenVictorias>();
		String query = Consultas.TempConNroTemp();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroTemp);
		ResultSet rs = pstmt.executeQuery();
		boolean existe=rs.next();
		rs.close();
		if(existe)
		{
			query = Consultas.ListarDragQueens();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nroTemp);
			ResultSet rs1 = pstmt.executeQuery();
			while(rs1.next())
			{
				VODragQueenVictorias voDQ = new VODragQueenVictorias(rs1.getString("nombre"), rs1.getInt("nroTemp"), rs1.getInt("nroPart"), rs1.getInt("cantVictorias"));
				resu.add(voDQ);
			}
			rs1.close();
		}
		else
		{
			throw new PersistenciaException("ERROR: No existe una temporada registrada con ese nro.");
		}
		
		pstmt.close();
		return resu;
	}
	
	/* Verificar que exista el nroTemp en la tabla antes de ejecutar la consulta */
	public VOTemporada TempConNroTemp(IConexion icon, int nroTemp) throws SQLException
	{
		Connection con = icon.GetConnection(); 
		VOTemporada resu = null;
		String query = Consultas.TempConNroTemp();
		PreparedStatement pstmt=con.prepareStatement(query);
		pstmt.setInt(1, nroTemp);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next())
		{
			resu = new VOTemporada(rs.getInt("nroTemp"), rs.getInt("anio"), rs.getInt("cantCapitulos"));
		}
		
		rs.close();
		pstmt.close();
		
		return resu;
	}
	
	
	public int CantParticipantesTemp(IConexion icon, int nroTemp) throws SQLException
	{
		Connection con = icon.GetConnection();
		int resu=0;
		String query = Consultas.CantParticipantesTemp();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroTemp);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())
		{
			resu=rs.getInt("cantParticipantes");
		}
		
		rs.close();
		pstmt.close();
		
		return resu;
	}
	
	public void RegistrarVictoria(IConexion icon, int nroTemp, int nroPart) throws SQLException
	{
		Connection con = icon.GetConnection();
		String query = Consultas.RegistrarVictoria();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroPart);
		pstmt.setInt(2, nroTemp);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public VODragQueenVictorias NroPartDragQueenConMasVictorias(IConexion icon, int nroTemp) throws SQLException
	{
		Connection con = icon.GetConnection();
		int nroPart = 0;
		int mayor = 0;
		String query = Consultas.NroPartDragQueenConMasVictorias();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroTemp);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next())
		{
			int cantVictorias = rs.getInt("cantVictorias");
			if(cantVictorias >= mayor)
			{
				mayor = cantVictorias;
				nroPart = rs.getInt("nroPart");
			}
		}
		VODragQueen voDQ = this.DragQueenConNroPart(icon, nroPart);
		VODragQueenVictorias resu= new VODragQueenVictorias(voDQ.getNombre(), nroTemp, nroPart, mayor);
		
		rs.close();
		pstmt.close();
		return resu;
	}
	
	public VODragQueen DragQueenConNroPart(IConexion icon, int nroPart) throws SQLException
	{
		Connection con = icon.GetConnection();
		VODragQueen resu = null;
		String query = Consultas.DragQueenConNroPart();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroPart);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())
		{
			resu = new VODragQueen(rs.getString("nombre"), rs.getInt("nroTemp"));
		}
		
		return resu;
	}	
	
}
