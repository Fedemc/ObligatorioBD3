package grafica.controladores;

import grafica.controladores.ControladorPrincipal;
import grafica.ventanas.VentanaListarDragQueenGanadora;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueenVictorias;
import java.rmi.RemoteException;
import java.util.List;

public class ControladorListarDragQueenGanadora
{
	private VentanaListarDragQueenGanadora ventana;
	private IFachada fachada;
	
	
	public ControladorListarDragQueenGanadora(VentanaListarDragQueenGanadora v)
	{
		ventana = v;
		fachada = ControladorPrincipal.getInstancia().getFachada();
	}
	
	public VODragQueenVictorias ListarDragQueenGanadora(int nroTemp) throws PersistenciaException, RemoteException
	{
		return fachada.obtenerGanadoraDeTemporada(nroTemp);
	}

}
