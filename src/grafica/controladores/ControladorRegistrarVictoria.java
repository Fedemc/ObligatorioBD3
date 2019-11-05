package grafica.controladores;

import grafica.controladores.ControladorPrincipal;
import grafica.ventanas.VentanaRegistrarVictoria;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueenRegistrarVictoria;
import java.rmi.RemoteException;


public class ControladorRegistrarVictoria
{
	private IFachada fachada;
	
	private VentanaRegistrarVictoria ventana;
	
	public ControladorRegistrarVictoria(VentanaRegistrarVictoria v)
	{
		ventana = v;
		fachada = ControladorPrincipal.getInstancia().getFachada();
	}
	
	public void RegistrarVictoria(int nroParticipante, int nroTemporada) throws PersistenciaException, RemoteException
	{
		VODragQueenRegistrarVictoria voRV = new VODragQueenRegistrarVictoria(nroParticipante, nroTemporada);
		fachada.registrarVictoria(voRV);
	}
}