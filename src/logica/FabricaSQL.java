package logica;

import logica.excepciones.PersistenciaException;
import persistencia.daos.DAODragQueensSQL;
import persistencia.daos.DAOTemporadasSQL;
import persistencia.daos.IDAODragQueens;
import persistencia.daos.IDAOTemporadas;

public class FabricaSQL implements FabricaAbstracta {

	public IDAOTemporadas crearIDAOTemporadas() {
		return new DAOTemporadasSQL();
	}

	public IDAODragQueens crearIDAODragQueens(int nroT) {
		return new DAODragQueensSQL(nroT);
	}

	public IPoolConexiones crearIPoolConexiones() throws PersistenciaException {
		return new PoolConexionesSQL();
	}
}
