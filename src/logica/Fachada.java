package logica;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueen;
import logica.valueObjects.VODragQueenRegistrarVictoria;
import logica.valueObjects.VODragQueenVictorias;
import logica.valueObjects.VOTempMaxPart;
import logica.valueObjects.VOTemporada;

import java.sql.SQLException;
import java.rmi.server.UnicastRemoteObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import persistencia.daos.DAOTemporadasSQL;
import persistencia.daos.IDAOTemporadas;

public class Fachada extends UnicastRemoteObject implements IFachada
{
	private static Fachada fachada;
	
	private IDAOTemporadas diccio;
	
	private IPoolConexiones pool;
	
	/**
	 * Creación del objeto.
	 * @throws RemoteException
	 */
	private Fachada() throws RemoteException
	{
		Properties prop = new Properties();
		FabricaAbstracta fabrica = null;
		
		try {
			prop.load(new FileInputStream("config/config.properties"));
			fabrica = (FabricaAbstracta) Class.forName(prop.getProperty("fabrica")).newInstance();
			pool = fabrica.crearIPoolConexiones();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException 
				| IOException | PersistenciaException e) {
			e.printStackTrace();
		}
		
		diccio = fabrica.crearIDAOTemporadas();
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
		IConexion icon = null;
		try
		{
			icon = pool.obtenerConexiones(false);
		}
		catch(PersistenciaException pEx)
		{
			String error = "Error al obtener conexion a persistencia: " + pEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		try
		{
			existe = diccio.member(nroTemp, icon);
			if(!existe)
			{
				int anio = voT.getAnio();
				int cantCaps = voT.getCantCapitulos();
				Temporada temp = new Temporada(nroTemp, anio, cantCaps);
				diccio.insert(temp, icon);
				pool.liberarConexion(icon, true);
			}
			else
			{
				pool.liberarConexion(icon, true);
				throw new PersistenciaException("Ya existe una temporada registrada con ese nro de temporada.");
			}
		}
		catch(PersistenciaException e)
		{
			pool.liberarConexion(icon, false);
			throw e;
		}
	}
	
	/* Inscribir una nueva DragQueen */
	public void inscribirDragQueen(VODragQueen voD) throws RemoteException, PersistenciaException
	{
		IConexion icon = null;
		try
		{
			icon = pool.obtenerConexiones(false);
		}
		catch(PersistenciaException pEx)
		{
			String error = "Error al obtener conexion a persistencia: " + pEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		String nombre = voD.getNombre();
		int nroTemp = voD.getNroTemp();
		try
		{
			if(diccio.member(nroTemp, icon))
			{
				Temporada temp = diccio.find(nroTemp, icon);
				DragQueen dq = new DragQueen(temp.getCantParticipantes(icon)+1, voD.getNombre(), 0);
				temp.inscribirDragQueen(dq, icon);
				pool.liberarConexion(icon, true);
			}
			else
			{
				pool.liberarConexion(icon, true);
				throw new PersistenciaException("No existe una temporada registrada con ese nro de temporada.");
			}
		}
		catch(PersistenciaException e)
		{
			pool.liberarConexion(icon, false);
			throw e;
		}
	}
	
	/* Listar todas las temporadas */
	public List<VOTemporada> listarTemporadas() throws RemoteException, PersistenciaException
	{
		List<VOTemporada> resu = new ArrayList<VOTemporada>();
		IConexion icon=null;
		try
		{
			icon = pool.obtenerConexiones(false);
		}
		catch(PersistenciaException pEx)
		{
			String error = "Error al obtener conexion a persistencia: " + pEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		try
		{
			resu = diccio.listarTemporadas(icon);
			pool.liberarConexion(icon, true);
		}
		catch(PersistenciaException e)
		{
			pool.liberarConexion(icon, false);
			throw e;
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
			icon = pool.obtenerConexiones(false);
		}
		catch(PersistenciaException pEx)
		{
			String error = "Error al obtener conexion a persistencia: " + pEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		try
		{
			if(diccio.member(nroTemp, icon))
			{
				Temporada temp = diccio.find(nroTemp, icon);
				resu = temp.listarDragQueens(icon);
				pool.liberarConexion(icon, true);
			}
			else
			{
				pool.liberarConexion(icon, true);
				throw new PersistenciaException("No existe una temporada registrada con ese nro de temporada.");
			}			
		}
		catch(PersistenciaException e)
		{
			pool.liberarConexion(icon, false);
			throw e;
		}
		
		return resu;
	}
	
	// Verificar q al menos exista una temporada
	public VOTempMaxPart temporadaConMasParticipantes() throws RemoteException, PersistenciaException
	{
		VOTempMaxPart resu = null;
		IConexion icon = null;
		try
		{
			icon = pool.obtenerConexiones(false);
		}
		catch(PersistenciaException pEx)
		{
			String error = "Error al obtener conexion a persistencia: " + pEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		try 
		{
			if(!diccio.esVacio(icon))
			{
				resu = diccio.tempMasParticipantes(icon);
				pool.liberarConexion(icon, true);
			}
			else
			{
				pool.liberarConexion(icon, true);
				throw new PersistenciaException("No hay temporadas registradas.");
			}
		} 
		catch (PersistenciaException e) 
		{
			pool.liberarConexion(icon, false);
			throw e;
		}
		
		return resu;
	}
	
	public void registrarVictoria(VODragQueenRegistrarVictoria voRV) throws RemoteException, PersistenciaException
	{		
		IConexion icon = null;
		try
		{
			icon = pool.obtenerConexiones(false);
		}
		catch(PersistenciaException pEx)
		{
			String error = "Error al obtener conexion a persistencia: " + pEx.getMessage();
			throw new PersistenciaException(error);
		}
		
		int nroParticipante = voRV.getNroPart();
		int nroTemporada= voRV.getNroTemp();
		try
		{
			if(diccio.member(nroTemporada, icon))
			{
				Temporada temp = diccio.find(nroTemporada, icon);
				if(temp.tieneDragQueen(nroParticipante, icon))
				{
					temp.registrarVictoria(nroParticipante, icon);
					pool.liberarConexion(icon, true);
				}
				else
				{
					pool.liberarConexion(icon, true);
					throw new PersistenciaException("No existe una DragQueen registrada con ese nro de participante.");
				}
			}
			else
			{
				pool.liberarConexion(icon, true);
				throw new PersistenciaException("No existe una temporada registrada con ese nro de temporada.");
			}
		}
		catch(PersistenciaException e)
		{
			pool.liberarConexion(icon, false);
			throw e;
		}
	}
	
	public VODragQueenVictorias obtenerGanadoraDeTemporada(int nroTemp) throws RemoteException, PersistenciaException
	{
		VODragQueenVictorias resu = new VODragQueenVictorias("",1,1,1);
		boolean errorVOT = false, errorNoHayDQs = false;
		IConexion icon = null;
		try
		{
			icon = pool.obtenerConexiones(false);
		}
		catch(PersistenciaException pEx)
		{
			String error = "Error al obtener conexion a persistencia: " + pEx.getMessage();
			throw new PersistenciaException(error);
		}
		try
		{
			if(diccio.member(nroTemp, icon))
			{
				Temporada temp = diccio.find(nroTemp, icon);
				if(!temp.getSecuencia().esVacia(icon, nroTemp))
				{
					resu = temp.obtenerGanadora(icon);
					pool.liberarConexion(icon, true);
				}
				else
				{
					pool.liberarConexion(icon, true);
					throw new PersistenciaException("La temporada no tiene participantes registrados.");
				}
			}
			else
			{
				pool.liberarConexion(icon, true);
				throw new PersistenciaException("No existe una temporada registrada con ese nro de temporada.");
			}						
		}
		catch(PersistenciaException e)
		{
			pool.liberarConexion(icon, false);
			throw e;
		}
		
		return resu;
	}
	
}
