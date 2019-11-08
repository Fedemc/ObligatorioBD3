package logica;

import logica.excepciones.PersistenciaException;
import persistencia.daos.DAODragQueensArchivo;
import persistencia.daos.DAOTemporadasArchivo;
import persistencia.daos.IDAODragQueens;
import persistencia.daos.IDAOTemporadas;

public class FabricaArchivo implements FabricaAbstracta {

	public IDAOTemporadas crearIDAOTemporadas() {
		return new DAOTemporadasArchivo();
	}

	public IDAODragQueens crearIDAODragQueens(int nroT) {
		return new DAODragQueensArchivo(nroT);
	}

	public IPoolConexiones crearIPoolConexiones() throws PersistenciaException {
		return new PoolConexionesArchivo();
	}
}
