package grafica.controladores;

import grafica.controladores.ContPrincipal;
import grafica.ventanas.VentanaListarDragQueens;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.IFachada;
import logicaPersistencia.valueObjects.VODragQueenVictorias;
import java.rmi.RemoteException;
import java.util.List;

public class ContVentanaListarDragQueens
{
	private VentanaListarDragQueens vent;
	private IFachada ifac;
	
	
	public ContVentanaListarDragQueens(VentanaListarDragQueens v)
	{
		vent = v;
		ifac = ContPrincipal.GetInstancia().GetIFachada();
	}
	
	public void ListarDragQueens(int nroTemp)
	{
		try
		{
			List<VODragQueenVictorias> lista = ifac.ListarDragQueens(nroTemp);
			vent.ListarDragQueens(lista);
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
