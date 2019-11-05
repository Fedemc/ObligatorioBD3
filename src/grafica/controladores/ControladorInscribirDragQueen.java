package grafica.controladores;

import grafica.controladores.ControladorPrincipal;
import grafica.ventanas.VentanaInscribirDragQueen;
import logica.IFachada;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueen;


import java.rmi.RemoteException;

public class ControladorInscribirDragQueen
{
	private IFachada fachada;
	private VentanaInscribirDragQueen ventana;
	
	public ControladorInscribirDragQueen(VentanaInscribirDragQueen v)
	{
		ventana = v;
		fachada = ControladorPrincipal.getInstancia().getFachada();
	}
	
	public void InscribirDragQueen(String nom, int nroTemp) throws PersistenciaException, RemoteException
	{
		fachada.inscribirDragQueen(new VODragQueen(nom, nroTemp));
	}
	
}
