package logica;

import logica.excepciones.PersistenciaException;

public abstract interface IPoolConexiones 
{
	void liberarConexion(IConexion conexion, boolean ok) throws PersistenciaException;
	IConexion obtenerConexiones(boolean modifica) throws PersistenciaException;

}
