package persistencia.daos;

import java.util.List;

import logica.IConexion;
import logica.Temporada;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOTempMaxPart;
import logica.valueObjects.VOTemporada;

public interface IDAOTemporadas {

	public boolean member(int nroTemp, IConexion icon) throws PersistenciaException;
	
	public void insert(Temporada temp, IConexion icon) throws PersistenciaException;
	
	public Temporada find(int nroTemp, IConexion icon) throws PersistenciaException;
	
	public boolean esVacio(IConexion icon) throws PersistenciaException;
	
	public List<VOTemporada> listarTemporadas(IConexion icon) throws PersistenciaException;
	
	public VOTempMaxPart tempMasParticipantes(IConexion icon) throws PersistenciaException;
}
