package grafica.controladores;

import grafica.controladores.ContPrincipal;
import grafica.ventanas.VentanaNuevaTemporada;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.valueObjects.VOTemporada;

import java.rmi.RemoteException;


public class ContVentanaNuevaTemporada
{
	private IFachada iFachada;
	private VentanaNuevaTemporada vent;
	
	public ContVentanaNuevaTemporada(VentanaNuevaTemporada v)
	{
		vent = v;
		iFachada = ContPrincipal.GetInstancia().GetIFachada();
	}
	
	public void InscribirNuevaTemporada(int nroT, int anio, int cantCaps)
	{
		VOTemporada voT = new VOTemporada(nroT,anio,cantCaps);
		try
		{
			iFachada.NuevaTemporada(voT);
			vent.mostrarResultado("Temporada ingresada correctamente");
		}
		catch(PersistenciaException pEx)
		{
			vent.mostrarError(pEx.DarMensaje());
		}
		catch(RemoteException rEx)
		{
			vent.mostrarError(rEx.toString());
		}
	}
}
