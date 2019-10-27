package logicaPersistencia;

import logicaPersistencia.excepciones.PersistenciaException;

public abstract interface IPoolConexiones 
{
	void LiberarConexion(IConexion conexion, boolean ok) throws PersistenciaException;
	IConexion ObtenerConexiones(boolean modifica) throws PersistenciaException;

}
