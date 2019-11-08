package logica;

import logica.excepciones.PersistenciaException;
import persistencia.daos.IDAODragQueens;
import persistencia.daos.IDAOTemporadas;

public interface FabricaAbstracta {

	public IDAOTemporadas crearIDAOTemporadas();
	
	public IDAODragQueens crearIDAODragQueens(int nroT);
	
	public IPoolConexiones crearIPoolConexiones() throws PersistenciaException;
}
