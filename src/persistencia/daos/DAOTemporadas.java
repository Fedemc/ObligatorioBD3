package persistencia.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import logica.IConexion;
import logica.Temporada;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOTempMaxPart;
import logica.valueObjects.VOTemporada;
import persistencia.consultas.Consultas;

public class DAOTemporadas
{
	
	public DAOTemporadas()
	{
		
	}
	
	public boolean member(int nroTemp, IConexion icon) throws PersistenciaException
	{
		boolean existe = false;
		Connection con = icon.getConnection();
		try
		{
			PreparedStatement pstmt = con.prepareStatement(Consultas.temporadaConNumero());
			pstmt.setInt(1, nroTemp);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				existe = true;
			}
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
		
		return existe;
	}
	
	public void insert(Temporada temp, IConexion icon) throws PersistenciaException
	{
		Connection con = icon.getConnection();
		try
		{
			String query = Consultas.nuevaTemporada();
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, temp.getNroTemp());
			pstmt.setInt(2, temp.getAnio());
			pstmt.setInt(3, temp.getCantCapitulos());
			pstmt.executeUpdate();
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
	}
	
	public Temporada find(int nroTemp, IConexion icon) throws PersistenciaException
	{
		Connection con = icon.getConnection();
		Temporada resu = null;
		try
		{
			PreparedStatement pstmt = con.prepareStatement(Consultas.temporadaConNumero());
			pstmt.setInt(1, nroTemp);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				resu =  new Temporada(rs.getInt("nroTemp"), rs.getInt("anio"), rs.getInt("cantCaps"));
			}
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
		
		return resu;
	}
	
	public boolean esVacio(IConexion icon) throws PersistenciaException
	{
		boolean vacio = false;
		Connection con = icon.getConnection();
		try
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(Consultas.listarTemporadas());
			vacio = !rs.next();
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
		
		return vacio;
	}
	
	public List<VOTemporada> listarTemporadas(IConexion icon) throws PersistenciaException
	{
		Connection con = icon.getConnection();
		List<VOTemporada> resu = new ArrayList<VOTemporada>();
		try
		{
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(Consultas.listarTemporadas());
			while(rs.next())
			{
				VOTemporada voTemp = new VOTemporada(rs.getInt("nroTemp"), rs.getInt("anio"), rs.getInt("cantCapitulos"));
				resu.add(voTemp);
			}
			rs.close();
			stmt.close();
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
		
		return resu;
	}
	
	public VOTempMaxPart tempMasParticipantes(IConexion icon) throws PersistenciaException
	{
		VOTempMaxPart resu = null;
		Connection con = icon.getConnection();
		try
		{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(Consultas.temporadaConMasParticipantes());
			if(rs.next())
			{
				resu = new VOTempMaxPart(rs.getInt("nroTemp"), rs.getInt("anio"), rs.getInt("cantCapitulos"), rs.getInt("cantParticipantes"));
			}			
		}
		catch(SQLException e)
		{
			throw new PersistenciaException("ERROR SQL: " + e.toString());
		}
		
		return resu;				 
	}	
}
