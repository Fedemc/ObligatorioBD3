package logica;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueen;
import logica.valueObjects.VODragQueenRegistrarVictoria;
import logica.valueObjects.VODragQueenVictorias;
import logica.valueObjects.VOTempMaxPart;
import logica.valueObjects.VOTemporada;


public interface IFachada extends Remote
{
	
	void nuevaTemporada(VOTemporada voT) throws RemoteException, PersistenciaException;
	
	void inscribirDragQueen(VODragQueen voD) throws RemoteException, PersistenciaException;
	
	List<VOTemporada> listarTemporadas() throws RemoteException, PersistenciaException;
	
	List<VODragQueenVictorias> listarDragQueensDeTemporada(int nroTemp) throws RemoteException, PersistenciaException;
	
	VOTempMaxPart temporadaConMasParticipantes() throws RemoteException, PersistenciaException;
	
	void registrarVictoria(VODragQueenRegistrarVictoria voRV) throws RemoteException, PersistenciaException;
	
	VODragQueenVictorias obtenerGanadoraDeTemporada(int nroTemp) throws RemoteException, PersistenciaException;

}
