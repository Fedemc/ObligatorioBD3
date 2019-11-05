package persistencia.consultas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logica.IConexion;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueen;
import logica.valueObjects.VODragQueenVictorias;
import logica.valueObjects.VOTempMaxPart;
import logica.valueObjects.VOTemporada;

public class AccesoBD
{
	public void NuevaTemporada(IConexion icon, int nroTemp, int anio, int cantCapitulos) throws SQLException 
	{
		Connection con = icon.getConnection();
		String query = Consultas.nuevaTemporada();
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
		Connection con = icon.getConnection();
		int nroPart = 0;
		
		// Verifico si existe una temporada con ese nro
		PreparedStatement pstmtExisteNroTemp = con.prepareStatement(Consultas.temporadaConNumero());
		pstmtExisteNroTemp.setInt(1, nroTemp);
		ResultSet rs = pstmtExisteNroTemp.executeQuery();
		if(rs.next())
		{
			// Existe la temporada
			// Obtengo la cant de participantes, para poder numerar al nuevo
			String cantParticipantesQuery = Consultas.cantidadParticipantesDeTemporada();
			PreparedStatement pstmt = con.prepareStatement(cantParticipantesQuery);
			pstmt.setInt(1, nroTemp);
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				nroPart = rs.getInt("cantParticipantes");
				nroPart++;
			}
			String query = Consultas.inscribirDragQueen();
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
		Connection con = icon.getConnection();
		List<VOTemporada> resu = new ArrayList<VOTemporada>();
		String query = Consultas.listarTemporadas();
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
		Connection con = icon.getConnection();
		List<VODragQueenVictorias> resu = new ArrayList<VODragQueenVictorias>();
		String query = Consultas.temporadaConNumero();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroTemp);
		ResultSet rs = pstmt.executeQuery();
		boolean existe=rs.next();
		rs.close();
		if(existe)
		{
			query = Consultas.dragQueensDeTemporada();
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
		Connection con = icon.getConnection(); 
		VOTemporada resu = null;
		String query = Consultas.temporadaConNumero();
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
		Connection con = icon.getConnection();
		int resu=0;
		String query = Consultas.cantidadParticipantesDeTemporada();
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
		Connection con = icon.getConnection();
		String query = Consultas.registrarVictoria();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroPart);
		pstmt.setInt(2, nroTemp);
		pstmt.executeUpdate();
		pstmt.close();
	}
	
	public VODragQueenVictorias NroPartDragQueenConMasVictorias(IConexion icon, int nroTemp) throws SQLException
	{
		Connection con = icon.getConnection();
		int nroPart = 0;
		int mayor = 0;
		String query = Consultas.cantidadDeVictoriasDeDragQueensDeTemporada();
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
		Connection con = icon.getConnection();
		VODragQueen resu = null;
		String query = Consultas.dragQueenPorNumeroDeParticipante();
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, nroPart);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next())
		{
			resu = new VODragQueen(rs.getString("nombre"), rs.getInt("nroTemp"));
		}
		
		rs.close();
		pstmt.close();
		return resu;
	}	
	
	public VOTempMaxPart TempConMasParticipantes(IConexion icon) throws SQLException
	{
		Connection con = icon.getConnection();
		VOTempMaxPart resu = null;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(Consultas.temporadaConMasParticipantes());
		if (rs.next()) {
			resu = new VOTempMaxPart(rs.getInt("nroTemp"), rs.getInt("nroAnio"), rs.getInt("cantCaps"), rs.getInt("cantParticipantes"));
		}
		
		rs.close();
		stmt.close();
		
		return resu;
	}
	
}
