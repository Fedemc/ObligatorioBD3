package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import logica.excepciones.DragQueenException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.TemporadaException;
import logica.valueObjects.VODragQueen;
import logica.valueObjects.VODragQueenRegistrarVictoria;
import logica.valueObjects.VODragQueenVictorias;
import logica.valueObjects.VOTempMaxPart;
import logica.valueObjects.VOTemporada;


public interface IFachada extends Remote
{
	
	void nuevaTemporada(VOTemporada voT) throws RemoteException, PersistenciaException, TemporadaException;
	
	void inscribirDragQueen(VODragQueen voD) throws RemoteException, PersistenciaException, TemporadaException;
	
	List<VOTemporada> listarTemporadas() throws RemoteException, PersistenciaException, TemporadaException;
	
	List<VODragQueenVictorias> listarDragQueensDeTemporada(int nroTemp) throws RemoteException, PersistenciaException, TemporadaException;
	
	VOTempMaxPart temporadaConMasParticipantes() throws RemoteException, PersistenciaException, TemporadaException;
	
	void registrarVictoria(VODragQueenRegistrarVictoria voRV) throws RemoteException, PersistenciaException, DragQueenException, TemporadaException;
	
	VODragQueenVictorias obtenerGanadoraDeTemporada(int nroTemp) throws RemoteException, PersistenciaException, TemporadaException;

}
