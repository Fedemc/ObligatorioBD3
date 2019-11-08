package logica;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueenVictorias;
import persistencia.daos.DAODragQueensSQL;
import persistencia.daos.IDAODragQueens;

public class Temporada implements Serializable
{
	private int nroTemp;
	private int anio;
	private int cantCapitulos;
	private IDAODragQueens secuencia;
	
	
	public Temporada(int nroT, int anio, int cantCaps)
	{
		nroTemp = nroT;
		this.anio = anio;
		cantCapitulos = cantCaps;
		
		Properties prop = new Properties();
		FabricaAbstracta fabrica = null;
		
		try {
			prop.load(new FileInputStream("config/config.properties"));
			secuencia = ((FabricaAbstracta) Class.forName(prop.getProperty("fabrica")).newInstance()).crearIDAODragQueens(nroT);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}


	public int getNroTemp()
	{
		return nroTemp;
	}

	public int getAnio()
	{
		return anio;
	}

	public int getCantCapitulos()
	{
		return cantCapitulos;
	}
	
	public IDAODragQueens getSecuencia()
	{
		return secuencia;
	}
	
	public int getCantParticipantes(IConexion icon) throws PersistenciaException
	{
		return secuencia.largo(icon);
	}
	
	// Llama a secuencia.k_esima
	public boolean tieneDragQueen(int nroPart, IConexion icon) throws PersistenciaException
	{
		DragQueen dq = secuencia.k_esima(nroPart, icon);
		return dq != null;
	}
	
	// Llama a secuencia.insback
	public void inscribirDragQueen(DragQueen dq, IConexion icon) throws PersistenciaException
	{
		secuencia.insBack(dq, icon);
	}
	
	// Llama a secuencia.k_esimo
	public DragQueen obtenerDragQueen(int nroPart, IConexion icon) throws PersistenciaException
	{
		DragQueen resu = secuencia.k_esima(nroPart, icon);
		return resu;
	}
	
	// Llama a secuencia.listarDragQueens
	public List<VODragQueenVictorias> listarDragQueens(IConexion icon) throws PersistenciaException
	{
		List<VODragQueenVictorias> resu = secuencia.listarDragQueens(icon);
		return resu;
	}
	
	// Llama a secuencia.registrarVictoria
	public void registrarVictoria(int nroPart, IConexion icon) throws PersistenciaException
	{
		secuencia.registrarVictoria(nroPart, icon);
	}
	
	// Llama a secuencia.obtenerGanadora
	public VODragQueenVictorias obtenerGanadora(IConexion icon) throws PersistenciaException
	{
		VODragQueenVictorias resu = secuencia.obtenerGanadora(icon);
		return resu;
	}
}
