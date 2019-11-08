package persistencia.daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import logica.IConexion;
import logica.Temporada;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOTempMaxPart;
import logica.valueObjects.VOTemporada;

public class DAOTemporadasArchivo implements IDAOTemporadas, Serializable {
	
	public boolean member(int nroTemp, IConexion icon) throws PersistenciaException {
		Temporada t = recuperarDatos(nroTemp);
		
		return (t != null);
	}

	public void insert(Temporada temp, IConexion icon) throws PersistenciaException {
		respaldarDatos(temp);
	}

	public Temporada find(int nroTemp, IConexion icon) throws PersistenciaException {
		return recuperarDatos(nroTemp);
	}

	public boolean esVacio(IConexion icon) throws PersistenciaException {
		Properties prop = new Properties();
		boolean vacio = true;
		
		try {
			prop.load(new FileInputStream("config/config.properties"));
			File ruta = new File(prop.getProperty("rutaRespaldo"));
			for (File archivo : ruta.listFiles()) 
			{
				if (archivo.isFile() && archivo.getPath().contains("temporada"))
				{
					vacio = false;
					break;
				}
					
			}
		} catch (IOException e) {
			throw new PersistenciaException(e.getMessage());
		}
		
		return vacio;
	}

	public List<VOTemporada> listarTemporadas(IConexion icon) throws PersistenciaException {
		List<VOTemporada> lista = new ArrayList<VOTemporada>();
		
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream("config/config.properties"));
			File ruta = new File(prop.getProperty("rutaRespaldo"));
			
			for (File archivo : ruta.listFiles()) {
				if (archivo.isFile() && archivo.getName().contains("temporada")) {
					String[] split = archivo.getName().split("-");
					split = split[1].split(".dat");
					Temporada t = recuperarDatos(Integer.parseInt(split[0]));
					lista.add(new VOTemporada(t.getNroTemp(), t.getAnio(), t.getCantCapitulos()));
				}
			}
		} catch (IOException e) {
			throw new PersistenciaException(e.getMessage());
		}
		
		return lista;
	}

	public VOTempMaxPart tempMasParticipantes(IConexion icon) throws PersistenciaException {
		Properties prop = new Properties();
		int maxTemp = 0;
		
		try {
			prop.load(new FileInputStream("config/config.properties"));
			File ruta = new File(prop.getProperty("rutaRespaldo"));
			
			for (File archivo : ruta.listFiles()) {
				if (archivo.isFile() && archivo.getName().contains("temporada")) {
					String[] split = archivo.getName().split("-");
					split = split[1].split(".dat");
					Temporada t = recuperarDatos(Integer.parseInt(split[0]));
					if (t.getCantParticipantes(icon) > maxTemp)
						maxTemp = t.getNroTemp();
				}
			}
		} catch (IOException e) {
			throw new PersistenciaException(e.getMessage());
		}
		
		if (maxTemp == 0)
			return null;
		
		Temporada temp = recuperarDatos(maxTemp);
		return new VOTempMaxPart(temp.getNroTemp(), temp.getAnio(), temp.getCantCapitulos(),
				temp.getCantParticipantes(icon));
	}

	private Temporada recuperarDatos(int nroTemp) throws PersistenciaException {
		Properties properties = new Properties();
		Temporada temporada = null;
		
		try {
			properties.load(new FileInputStream("config/config.properties"));
			File file = new File(properties.getProperty("rutaRespaldo") + "/temporada-" + nroTemp + ".dat");
			if (file.exists()) {
				FileInputStream f = new FileInputStream(properties.getProperty("rutaRespaldo") + "/temporada-" + nroTemp + ".dat");
				ObjectInputStream o = new ObjectInputStream(f);
				temporada = (Temporada) o.readObject();
				o.close();
				f.close();
			}
		}  catch (IOException e) {
			throw new PersistenciaException("Error al recuperar el respaldo: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			throw new PersistenciaException("Error al instanciar la temporada: " + e.getMessage());
		}
		
		return temporada;
	}
	
	private void respaldarDatos(Temporada temporada) throws PersistenciaException {
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream("config/config.properties"));
			FileOutputStream f = new FileOutputStream(properties.getProperty("rutaRespaldo") + "/temporada-" + temporada.getNroTemp() + ".dat");
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject(temporada);
			o.close();
			f.close();
		} catch (IOException e) {
			throw new PersistenciaException("Error al respaldar datos: " + e.getMessage());
		}
	}
	
}
