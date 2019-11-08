package persistencia.daos;

import java.util.List;

import logica.DragQueen;
import logica.IConexion;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueenVictorias;

public interface IDAODragQueens {

	public void insBack(DragQueen drag, IConexion icon) throws PersistenciaException;
	
	public boolean esVacia(IConexion icon, int nroTemp) throws PersistenciaException;
	
	public int largo(IConexion icon) throws PersistenciaException;
	
	public DragQueen k_esima(int nroP, IConexion icon) throws PersistenciaException;
	
	public List<VODragQueenVictorias> listarDragQueens(IConexion icon) throws PersistenciaException;
	
	public void registrarVictoria(int nroPart, IConexion icon) throws PersistenciaException;
	
	public VODragQueenVictorias obtenerGanadora(IConexion icon) throws PersistenciaException;	
}
