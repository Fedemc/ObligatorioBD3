package grafica.controladores;

import grafica.controladores.ControladorPrincipal;
import grafica.ventanas.VentanaNuevaTemporada;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.valueObjects.VOTemporada;

import java.rmi.RemoteException;


public class ControladorNuevaTemporada
{
	private IFachada fachada;
	
	private VentanaNuevaTemporada ventana;
	
	public ControladorNuevaTemporada(VentanaNuevaTemporada v)
	{
		ventana = v;
		fachada = ControladorPrincipal.getInstancia().getFachada();
	}
	
	public void nuevaTemporada(int nroT, int anio, int cantCaps) throws PersistenciaException, RemoteException
	{
		VOTemporada voT = new VOTemporada(nroT,anio,cantCaps);
		fachada.nuevaTemporada(voT);
	}
}
