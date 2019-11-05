package logica;

import java.util.List;

import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueenVictorias;
import persistencia.daos.DAODragQueens;

public class Temporada
{
	private int nroTemp;
	private int anio;
	private int cantCapitulos;
	private DAODragQueens secuencia;
	
	
	public Temporada(int nroT, int anio, int cantCaps)
	{
		nroTemp = nroT;
		this.anio = anio;
		cantCapitulos = cantCaps;
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
	
	public int getCantParticipantes(IConexion icon) throws PersistenciaException 
	{
		int cant = 0;
		secuencia.setNroTemp(this.getNroTemp());
		cant = secuencia.largo(icon);
		return cant;
	}
	
	// Recorre la secuencia verificando si hay una DQ con ese nroPart
	public boolean tieneDragQueen(int nroPart)
	{
		boolean tiene=false;
		
		
		
		return tiene;
	}
	
	// Inserta una nueva DQ en la secuencia
	public void inscribirDragQueen(DragQueen dq)
	{
		
	}
	
	// Recorre la secuencia y devuelve la DQ con el nroPart
	public DragQueen obtenerDragQueen(int nroPart)
	{
		DragQueen resu = null;
		
		
		return resu;
	}
	
	// Recorre la secuencia, extrayendo los datos de cada participante a un VODQ y lo inserta en la lista a devolver
	public List<VODragQueenVictorias> listarDragQueens()
	{
		List<VODragQueenVictorias> resu = null;
		
		return resu;
	}
	
	// Recorre la secuencia buscando el nroPart, y le asigna una victoria
	public void registrarVictoria(int nroPart)
	{
		
	}
	
	// Recorre la secuencia y extrae la que tiene mas victorias
	public VODragQueenVictorias obtenerGanadora()
	{
		VODragQueenVictorias resu = null;
		
		return resu;
	}
}
