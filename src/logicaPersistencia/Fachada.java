package logicaPersistencia;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import logicaPersistencia.accesoBD.AccesoBD;
import logicaPersistencia.excepciones.*;
import logicaPersistencia.IPoolConexiones;
import logicaPersistencia.valueObjects.*;

public class Fachada extends UnicastRemoteObject implements IFachada
{
	
	private static Fachada instancia;
	private AccesoBD abd;
	private IPoolConexiones pool;
	
	private Fachada() throws RemoteException
	{
		abd = new AccesoBD();
		try
		{
			pool = new PoolConexiones();
		}
		catch(PersistenciaException pEx)
		{
			System.out.println(pEx.toString());
		}
		
	}
	
	public static Fachada GetInstancia() 
	{
		if(instancia == null)
		{
			try
			{
				instancia = new Fachada();
			}
			catch(RemoteException rEx)
			{
				rEx.printStackTrace();
			}
			
		}
		
		return instancia;
	}
	
	/* Registrar una nueva temporada */
	public void NuevaTemporada(VOTemporada voT) throws RemoteException, PersistenciaException
	{
		int nroTemp = voT.getNroTemp();
		boolean existe=false;
		IConexion icon = pool.ObtenerConexiones(true);
		try
		{
			VOTemporada voExiste = abd.TempConNroTemp(icon, nroTemp);
			if(voExiste != null)
			{
				existe=true;
			}
		}
		catch(SQLException sqlEx)
		{
			pool.LiberarConexion(icon, false);
			String error = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(error);
		}
		if(!existe)
		{
			int anio = voT.getAnio();
			int cantCaps = voT.getCantCapitulos();
			
			try
			{
				abd.NuevaTemporada(icon, nroTemp, anio, cantCaps);
				pool.LiberarConexion(icon, true);
			}
			catch(SQLException sqlEx)
			{
				pool.LiberarConexion(icon, false);
				String error = "Error de SQL: " + sqlEx.getMessage();
				throw new PersistenciaException(error);
			}
		}
		else
		{
			pool.LiberarConexion(icon, false);
			throw new PersistenciaException("ERROR: Ya existe una temporada con ese nro de temporada.");
		}
	}
	
	/* Inscribir una nueva DragQueen */
	public void InscribirDragQueen(VODragQueen voD) throws RemoteException, PersistenciaException
	{
		IConexion icon = pool.ObtenerConexiones(true);
		String nombre = voD.getNombre();
		int nroTemp = voD.getNroTemp();
		try
		{
			abd.InscribirDragQueen(icon, nombre, nroTemp);
			pool.LiberarConexion(icon, true);
		}
		catch(SQLException sqlEx)
		{
			pool.LiberarConexion(icon, false);
			String error = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(error);
		}
	}
	
	/* Listar todas las temporadas */
	public List<VOTemporada> ListarTemporadas() throws RemoteException, PersistenciaException
	{
		List<VOTemporada> resu = new ArrayList<VOTemporada>();
		IConexion icon=null;
		try
		{
			icon = pool.ObtenerConexiones(false);
		}
		catch(PersistenciaException pEx)
		{
			String error = "Error de SQL: " + pEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		
		try
		{
			resu = abd.ListarTemporadas(icon);
			pool.LiberarConexion(icon, true);
		}
		catch(SQLException sqlEx)
		{
			pool.LiberarConexion(icon, false);
			String error = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		return resu;
	}
	
	/* Listar todas las DragQueens */
	public List<VODragQueenVictorias> ListarDragQueens() throws RemoteException, PersistenciaException
	{
		List<VODragQueenVictorias> resu = new ArrayList<VODragQueenVictorias>();
		try
		{
			IConexion icon = pool.ObtenerConexiones(false);
			resu = abd.ListarDragQueens(icon);
		}
		catch(SQLException sqlEx)
		{
			String error = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		return resu;
	}
	
	// Verificar q al menos exista una temporada
	public VOTempMaxPart TempMasParticipantes() throws RemoteException, PersistenciaException
	{
		int max = 0;
		int nroTempMax = 0;
		int cantParts = 0;
		boolean error = false;
		VOTempMaxPart resu = new VOTempMaxPart(1,1,1,1);
		// Traerme todas las temporadas
		// Ver cual tiene mas participantes
		// Devolver nro Temp y ejecutar TempConNroTemp
		List<VOTemporada> listaTemporadas = new ArrayList<VOTemporada>();
		try
		{
			IConexion icon = pool.ObtenerConexiones(false);
			listaTemporadas = abd.ListarTemporadas(icon);
			if(listaTemporadas.isEmpty())
			{
				error=true;
			}
			else
			{
				for(VOTemporada t : listaTemporadas)
				{
					int nroTemp = t.getNroTemp();
					int cantParticipantes = abd.CantParticipantesTemp(icon, nroTemp);
					if(cantParticipantes > max)
					{
						max = cantParticipantes;
						nroTempMax = nroTemp;
						cantParts = cantParticipantes;
					}
				}
				
				// Me traigo el VO de la temporada con mas participantes
				VOTemporada voT = abd.TempConNroTemp(icon, nroTempMax);
				resu = new VOTempMaxPart(voT.getAnio(), voT.getNroTemp(), voT.getCantCapitulos(), cantParts);
			}
		}
		catch(SQLException sqlEx)
		{
			String msj = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(msj);
		}
		
		if(error)
		{	
			String msj = "ERROR: No existen Temporadas registradas en el sistema.";
			throw new PersistenciaException(msj);
		}
		
		return resu;
	}
	
	public void RegistrarVictoria(int nroTemp, int nroPart) throws RemoteException, PersistenciaException
	{
		boolean errorVOT=false, errorVODQ=false;
		
		try
		{
			IConexion icon = pool.ObtenerConexiones(false);
			VOTemporada voT = abd.TempConNroTemp(icon, nroTemp);
			if(voT.equals(null))
			{
				errorVOT = true;
			}
			VODragQueen voDQ = abd.DragQueenConNroPart(icon, nroPart);
			if(voDQ.equals(null))
			{
				errorVODQ = true;
			}
			
			if(errorVOT)
			{
				String msj = "ERROR: No existe una Temporada con el nroTemp(" + nroTemp + ") en el sistema";
				throw new PersistenciaException(msj);
			}
			
			if(errorVOT)
			{
				String msj = "ERROR: No existe una Temporada con el nroTemp(" + nroTemp + ") en el sistema";
				throw new PersistenciaException(msj);
			}
			
			abd.RegistrarVictoria(icon, nroTemp, nroPart);
		}
		catch(SQLException sqlEx)
		{
			String msj = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(msj);
		}
	}
	
	public VODragQueenVictorias ObtenerGanadora(int nroTemp) throws RemoteException, PersistenciaException
	{
		VODragQueenVictorias resu = new VODragQueenVictorias("",1,1,1);
		boolean errorVOT = false, errorNoHayDQs = false;
		try
		{
			IConexion icon = pool.ObtenerConexiones(false);
			VOTemporada voT = abd.TempConNroTemp(icon, nroTemp);
			if(voT.equals(null))
			{
				errorVOT = true;
			}
			
			int cantParticipantes = 0;
			cantParticipantes = abd.CantParticipantesTemp(icon, nroTemp);
			if(cantParticipantes == 0)
			{
				errorNoHayDQs = true;
			}
			
			if(errorVOT)
			{
				String msj = "ERROR: No existe una Temporada con el nroTemp(" + nroTemp + ") en el sistema.";
				throw new PersistenciaException(msj);
			}
			
			if(errorVOT)
			{
				String msj = "ERROR: La Temporada con el nroTemp(" + nroTemp + ") no tiene participantes registrados.";
				throw new PersistenciaException(msj);
			}
			
			resu = abd.NroPartDragQueenConMasVictorias(icon, nroTemp);
			
		}
		catch(SQLException sqlEx)
		{
			String msj = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(msj);
		}
		
		return resu;
	}
	
}
