package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import logica.excepciones.PersistenciaException;


public class PoolConexionesArchivo implements IPoolConexiones
{
	
	private static final long serialVersionUID = 1L;
	private int cantidadLectores;
	private boolean escribiendo;
	
	public PoolConexionesArchivo() throws PersistenciaException
	{
		cantidadLectores = 0;
		escribiendo = false;
	}
	
	public synchronized IConexion obtenerConexiones(boolean modifica) throws PersistenciaException
	{
		if (modifica)
			comenzarEscritura();
		else
			comenzarLectura();
		
		return null;
	}
	
	public synchronized void liberarConexion(IConexion conexion, boolean ok) throws PersistenciaException
	{
		if (escribiendo)
			finalizarEscritura();
		else
			finalizarLectura();
	}
	
	
	
	public synchronized void comenzarLectura() {
		try {
			if (escribiendo)
				this.wait();
			
			cantidadLectores++;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void finalizarLectura() {
		cantidadLectores--;
		this.notifyAll();
	}
	
	public synchronized void comenzarEscritura() {
		try {
			if (cantidadLectores > 0 || escribiendo)
				this.wait();
			
			escribiendo = true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	public synchronized void finalizarEscritura() {
		escribiendo = false;
		this.notifyAll();
	}
}
