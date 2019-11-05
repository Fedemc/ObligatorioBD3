package grafica.controladores;

import grafica.controladores.ControladorPrincipal;
import grafica.ventanas.VentanaListarDragQueens;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueenVictorias;
import java.rmi.RemoteException;
import java.util.List;

public class ControladorListarDragQueens
{
	private VentanaListarDragQueens ventana;
	private IFachada fachada;
	
	
	public ControladorListarDragQueens(VentanaListarDragQueens v)
	{
		ventana = v;
		fachada = ControladorPrincipal.getInstancia().getFachada();
	}
	
	public List<VODragQueenVictorias> ListarDragQueens(int nroTemp) throws PersistenciaException, RemoteException
	{
		return fachada.listarDragQueensDeTemporada(nroTemp);
	}

}
