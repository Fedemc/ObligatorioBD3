package grafica.controladores;

import grafica.controladores.ContPrincipal;
import grafica.ventanas.VentanaListarTemporadas;
import grafica.ventanas.VentanaNuevaTemporada;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.valueObjects.VOTemporada;

import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

public class ContVentanaListarTemporadas
{
	private IFachada iFachada;
	private VentanaListarTemporadas vent;
	
	public ContVentanaListarTemporadas(VentanaListarTemporadas v)
	{
		vent = v;
		iFachada = ContPrincipal.GetInstancia().GetIFachada();
	}
	
	public void ListarTemporadas()
	{
		List<VOTemporada> lista = new ArrayList<VOTemporada>();
		try
		{
			lista = iFachada.ListarTemporadas();
			vent.ListarTemporadas(lista);
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
