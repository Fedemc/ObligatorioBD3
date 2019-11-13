package persistencia.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import logica.DragQueen;
import logica.IConexion;
import logica.Temporada;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VODragQueenVictorias;

public class DAODragQueensArchivo implements IDAODragQueens, Serializable {

	private static final long serialVersionUID = 1L;
	
	private int nroTemp;
	
	public DAODragQueensArchivo(int nroT) {
		this.nroTemp = nroT;
	}
	
	public void insBack(DragQueen drag, IConexion icon) throws PersistenciaException {
		List<DragQueen> lista = recuperarDatos(nroTemp);
		if(lista == null)		// Puede ser que sea la primera vez que creo el archivo de respaldo
		{
			lista = new ArrayList<DragQueen>();
		}
		lista.add(drag);
		respaldarDatos(lista);
	}

	public boolean esVacia(IConexion icon, int nroTemp) throws PersistenciaException {
		Properties prop = new Properties();
		boolean vacia = true;
		try {
			prop.load(new FileInputStream("config/config.properties"));
			File ruta = new File(prop.getProperty("rutaRespaldo"));
			
			for (File archivo : ruta.listFiles()) 
			{
				if (archivo.isFile() && archivo.getName().contains("dragqueens-" + nroTemp))
				{
					vacia = false;
					break;
				}
					
			}
		} catch (IOException e) {
			throw new PersistenciaException(e.getMessage());
		}
		
		return vacia;
	}

	public int largo(IConexion icon) throws PersistenciaException {
		List<DragQueen> lista = new ArrayList<DragQueen>();
		lista = recuperarDatos(nroTemp);
		return (lista != null ? lista.size() : 0);
	}

	public DragQueen k_esima(int nroP, IConexion icon) throws PersistenciaException {
		List<DragQueen> lista = recuperarDatos(nroTemp);
		for (DragQueen dq : lista) {
			if (dq.getNroPart() == nroP)
				return dq;
		}
		
		return null;
	}

	public List<VODragQueenVictorias> listarDragQueens(IConexion icon) throws PersistenciaException {
		List<DragQueen> lista = recuperarDatos(nroTemp);
		List<VODragQueenVictorias> result = new ArrayList<VODragQueenVictorias>();
		
		for (DragQueen dq : lista) {
			result.add(new VODragQueenVictorias(dq.getNombre(), nroTemp, dq.getNroPart(), 
					dq.getCantVictorias()));
		}
		
		return result;
	}

	public void registrarVictoria(int nroPart, IConexion icon) throws PersistenciaException {
		List<DragQueen> lista = recuperarDatos(nroTemp);
		for (DragQueen dq : lista) {
			if (dq.getNroPart() == nroPart) {
				DragQueen dq_aux = dq;
				int idx = lista.indexOf(dq);
				lista.remove(dq);
				dq_aux.sumarVictoria();
				lista.add(idx, dq_aux);
				respaldarDatos(lista);
				break;
			}
		}
	}

	public VODragQueenVictorias obtenerGanadora(IConexion icon) throws PersistenciaException {
		List<DragQueen> lista = recuperarDatos(nroTemp);
		DragQueen ganadora = null;
		
		for (DragQueen dq : lista) {
			if (ganadora == null || dq.getCantVictorias() > ganadora.getCantVictorias()) {
				ganadora = dq;
			}
		}
		
		return new VODragQueenVictorias(ganadora.getNombre(), nroTemp, ganadora.getNroPart(), 
				ganadora.getCantVictorias());
	}
	
	private List<DragQueen> recuperarDatos(int nroTemp) throws PersistenciaException {		// TODO Cuando viene a crear una DQ falla pq no encuentra el archivo
		Properties properties = new Properties();
		List<DragQueen> dq = null;
		
		try {
			properties.load(new FileInputStream("config/config.properties"));
			File file = new File(properties.getProperty("rutaRespaldo") + "/dragqueens-" + nroTemp + ".dat");
			if (file.exists()) {
				FileInputStream f = new FileInputStream(properties.getProperty("rutaRespaldo") + "/dragqueens-" + nroTemp + ".dat");
				ObjectInputStream o = new ObjectInputStream(f);
				dq = (List<DragQueen>) o.readObject();
				o.close();
				f.close();
			}
		}  catch (IOException e) {
			throw new PersistenciaException("Error al recuperar el respaldo: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new PersistenciaException("Error al instanciar la DragQueen: " + e.getMessage());
		}
		
		return dq;
	}
	
	private void respaldarDatos(List<DragQueen> dq) throws PersistenciaException {
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream("config/config.properties"));
			FileOutputStream f = new FileOutputStream(properties.getProperty("rutaRespaldo") + "/dragqueens-" + nroTemp + ".dat");
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(dq);
			o.close();
			f.close();
		} catch (IOException e) {
			throw new PersistenciaException("Error al respaldar datos: " + e.getMessage());
		}
	}

}
