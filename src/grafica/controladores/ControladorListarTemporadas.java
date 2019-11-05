package grafica.controladores;

import grafica.controladores.ControladorPrincipal;
import grafica.ventanas.VentanaListarTemporadas;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOTemporada;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ArrayList;

public class ControladorListarTemporadas
{
	private IFachada fachada;
	
	private VentanaListarTemporadas ventana;
	
	public ControladorListarTemporadas(VentanaListarTemporadas v)
	{
		ventana = v;
		fachada = ControladorPrincipal.getInstancia().getFachada();
	}
	
	public List<VOTemporada> ListarTemporadas() throws RemoteException, PersistenciaException
	{
		return fachada.listarTemporadas();
	}
}
