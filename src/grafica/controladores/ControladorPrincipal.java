package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Properties;
import logica.IFachada;

public class ControladorPrincipal
{
	private static ControladorPrincipal instancia;
	
	private IFachada fachada;
	
	private ControladorPrincipal()
	{
		Properties prop = new Properties();
		
		try {
			// Cargamos el archivo de configuraci�n del sistema e iniciamos la conexi�n
			// mediante RMI.
			System.out.println("1. Iniciando conexi�n con el servidor RMI...");
			prop.load(new FileInputStream("config/config.properties"));
			fachada = (IFachada) Naming.lookup("//" + prop.getProperty("ipServidor") + ":" + prop.getProperty("puertoServidor") + "/dragqueens");
			System.out.println("Conexi�n al servidor RMI en l�nea.");
		} catch(IOException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ControladorPrincipal getInstancia()
	{
		if(!(instancia instanceof ControladorPrincipal) || instancia == null)
			instancia = new ControladorPrincipal();
		
		return instancia;
	}
	
	public IFachada getFachada()
	{
		return fachada;
	}
}
