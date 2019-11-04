package logicaPersistencia;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import logicaPersistencia.valueObjects.VODragQueenRegistrarVictoria;
import logicaPersistencia.valueObjects.VODragQueen;
import logicaPersistencia.valueObjects.VODragQueenVictorias;
import logicaPersistencia.valueObjects.VOTempMaxPart;
import logicaPersistencia.valueObjects.VOTemporada;
import logicaPersistencia.excepciones.*;


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
