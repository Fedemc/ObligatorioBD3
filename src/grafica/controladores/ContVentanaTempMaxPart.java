package grafica.controladores;

import grafica.controladores.ContPrincipal;
import grafica.ventanas.VentanaListarTemporadas;
import grafica.ventanas.VentanaTempMasPart;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.valueObjects.VOTempMaxPart;
import logicaPersistencia.valueObjects.VOTemporada;

import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

public class ContVentanaTempMaxPart
{
	private IFachada iFachada;
	private VentanaTempMasPart vent;
	
	public ContVentanaTempMaxPart(VentanaTempMasPart v)
	{
		vent = v;
		iFachada = ContPrincipal.GetInstancia().GetIFachada();
	}
	
	public void TempMaxPart()
	{
		VOTempMaxPart temp = null;
		try
		{
			temp = iFachada.TempMasParticipantes();
			vent.ListarTempMasParticipantes(temp);
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
