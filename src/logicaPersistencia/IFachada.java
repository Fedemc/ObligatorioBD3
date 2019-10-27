package logicaPersistencia;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import logicaPersistencia.valueObjects.VODragQueen;
import logicaPersistencia.valueObjects.VODragQueenVictorias;
import logicaPersistencia.valueObjects.VOTempMaxPart;
import logicaPersistencia.valueObjects.VOTemporada;
import logicaPersistencia.excepciones.*;


public interface IFachada extends Remote
{
	
	void NuevaTemporada(VOTemporada voT) throws RemoteException, PersistenciaException;
	
	void InscribirDragQueen(VODragQueen voD) throws RemoteException, PersistenciaException;
	
	List<VOTemporada> ListarTemporadas() throws RemoteException, PersistenciaException;
	
	List<VODragQueenVictorias> ListarDragQueens() throws RemoteException, PersistenciaException;
	
	VOTempMaxPart TempMasParticipantes() throws RemoteException, PersistenciaException;
	
	void RegistrarVictoria(int nroTemp, int nroPart) throws RemoteException, PersistenciaException;
	
	VODragQueenVictorias ObtenerGanadora(int nroTemp) throws RemoteException, PersistenciaException;

}
