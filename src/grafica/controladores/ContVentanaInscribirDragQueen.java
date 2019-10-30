package grafica.controladores;

import grafica.controladores.ContPrincipal;
import grafica.ventanas.VentanaInscribirDragQueen;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.valueObjects.VODragQueen;

import java.rmi.RemoteException;

public class ContVentanaInscribirDragQueen
{
	private IFachada iFachada;
	private VentanaInscribirDragQueen vent;
	
	public ContVentanaInscribirDragQueen(VentanaInscribirDragQueen v)
	{
		vent = v;
		iFachada = ContPrincipal.GetInstancia().GetIFachada();
	}
	
	public void InscribirDragQueen(String nom, int nroTemp)
	{
		VODragQueen voD = new VODragQueen(nom, nroTemp);
		try
		{
			iFachada.InscribirDragQueen(voD);
			vent.mostrarResultado("Participante inscripto correctamente.");
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
