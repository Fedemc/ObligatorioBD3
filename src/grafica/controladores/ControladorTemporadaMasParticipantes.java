package grafica.controladores;

import grafica.controladores.ControladorPrincipal;
import grafica.ventanas.VentanaListarTemporadas;
import grafica.ventanas.VentanaTemporadaMasParticipantes;
import logicaPersistencia.IFachada;
import logicaPersistencia.excepciones.PersistenciaException;
import logicaPersistencia.valueObjects.VOTempMaxPart;
import logicaPersistencia.valueObjects.VOTemporada;

import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

public class ControladorTemporadaMasParticipantes
{
	private IFachada fachada;
	
	private VentanaTemporadaMasParticipantes ventana;
	
	public ControladorTemporadaMasParticipantes(VentanaTemporadaMasParticipantes v)
	{
		ventana = v;
		fachada = ControladorPrincipal.getInstancia().getFachada();
	}
	
	public VOTempMaxPart TempMaxPart() throws PersistenciaException, RemoteException
	{
		return fachada.temporadaConMasParticipantes();
	}
}
