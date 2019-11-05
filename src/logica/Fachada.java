package logica;

import java.util.ArrayList;
import java.util.List;

import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueen;
import logica.valueObjects.VODragQueenRegistrarVictoria;
import logica.valueObjects.VODragQueenVictorias;
import logica.valueObjects.VOTempMaxPart;
import logica.valueObjects.VOTemporada;

import java.sql.Connection;
import java.sql.SQLException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import persistencia.consultas.AccesoBD;

public class Fachada extends UnicastRemoteObject implements IFachada
{
	private static Fachada fachada;
	
	private AccesoBD abd;
	
	private IPoolConexiones pool;
	
	/**
	 * Creación del objeto.
	 * @throws RemoteException
	 */
	private Fachada() throws RemoteException
	{
		abd = new AccesoBD();
		try {
			pool = new PoolConexiones();
		} catch(PersistenciaException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Devuelve la instancia de la fachada.
	 * @return
	 */
	public static Fachada getInstancia() 
	{
		if(fachada == null) {
			try
			{
				fachada = new Fachada();
			}
			catch(RemoteException rEx)
			{
				rEx.printStackTrace();
			}
			
		}
		
		return fachada;
	}
	
	/* Registrar una nueva temporada */
	public void nuevaTemporada(VOTemporada voT) throws RemoteException, PersistenciaException
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
	public void inscribirDragQueen(VODragQueen voD) throws RemoteException, PersistenciaException
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
		catch(PersistenciaException pEx)
		{
			pool.LiberarConexion(icon, false);
			throw new PersistenciaException(pEx.getMessage());
		}
	}
	
	/* Listar todas las temporadas */
	public List<VOTemporada> listarTemporadas() throws RemoteException, PersistenciaException
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
	
	/* Listar las DragQueens dada una temporada */
	public List<VODragQueenVictorias> listarDragQueensDeTemporada(int nroTemp) throws RemoteException, PersistenciaException
	{
		List<VODragQueenVictorias> resu = new ArrayList<VODragQueenVictorias>();
		IConexion icon = null;
		try
		{
			icon = pool.ObtenerConexiones(false);
			resu = abd.ListarDragQueens(icon, nroTemp);
			pool.LiberarConexion(icon, true);
		}
		catch(SQLException sqlEx)
		{
			pool.LiberarConexion(icon, false);
			String error = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(error);
		}
		catch(PersistenciaException pEx)
		{
			pool.LiberarConexion(icon, false);
			throw new PersistenciaException(pEx.getMessage());
		}
		
		return resu;
	}
	
	// Verificar q al menos exista una temporada
	public VOTempMaxPart temporadaConMasParticipantes() throws RemoteException, PersistenciaException
	{
		VOTempMaxPart resu = null;
		IConexion icon = null;
		
		try {
			icon = pool.ObtenerConexiones(false);
			List<VOTemporada> lista = abd.ListarTemporadas(icon);
			if (lista.size() > 0)
				resu = abd.TempConMasParticipantes(icon);
			
			pool.LiberarConexion(icon, true);
		} catch (SQLException e) {
			pool.LiberarConexion(icon, false);
			String error = "Error de SQL: " + e.getMessage();
			throw new PersistenciaException(error);
		} catch (PersistenciaException e) {
			pool.LiberarConexion(icon, false);
			throw e;
		}
		
		return resu;
	}
	
	public void registrarVictoria(VODragQueenRegistrarVictoria voRV) throws RemoteException, PersistenciaException
	{
		/*
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
		*/
		
		IConexion icon = pool.ObtenerConexiones(true);
		int nroParticipante = voRV.getNroPart();
		int nroTemporada= voRV.getNroTemp();
		try
		{
			 
			abd.RegistrarVictoria(icon, nroParticipante, nroTemporada);
			pool.LiberarConexion(icon, true);
		}
		catch(SQLException sqlEx)
		{
			pool.LiberarConexion(icon, false);
			String error = "Error de SQL: " + sqlEx.getMessage();
			throw new PersistenciaException(error);
		}
		catch(PersistenciaException pEx)
		{
			pool.LiberarConexion(icon, false);
			throw new PersistenciaException(pEx.getMessage());
		}
	}
	
	public VODragQueenVictorias obtenerGanadoraDeTemporada(int nroTemp) throws RemoteException, PersistenciaException
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
