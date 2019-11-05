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
			// Cargamos el archivo de configuración del sistema e iniciamos la conexión
			// mediante RMI.
			System.out.println("1. Iniciando conexión con el servidor RMI...");
			prop.load(new FileInputStream("config/config.properties"));
			fachada = (IFachada) Naming.lookup("//" + prop.getProperty("ipServidor") + ":" + prop.getProperty("puertoServidor") + "/dragqueens");
			System.out.println("Conexión al servidor RMI en línea.");
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
