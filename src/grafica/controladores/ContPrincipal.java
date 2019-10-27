package grafica.controladores;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.Properties;
import logicaPersistencia.IFachada;

public class ContPrincipal
{
	private static ContPrincipal instancia;
	private IFachada interfazFachada;
	
	private ContPrincipal()
	{
		try
		{
			Properties p = new Properties();
			String nomArch = "config/config.properties";
			p.load(new FileInputStream(nomArch));
			String ip = p.getProperty("ipServidor");
			String puerto = p.getProperty("puertoServidor");
			String ruta = "//" + ip + ":" + puerto + "/fachada";
			
			interfazFachada = (IFachada) Naming.lookup(ruta);
			
		}
		catch(MalformedURLException mEx)
		{
			mEx.printStackTrace();			
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();			
		}
		catch(NotBoundException nobEx)
		{
			nobEx.printStackTrace();
		}
	}
	
	public static ContPrincipal GetInstancia()
	{
		if(instancia == null)
		{
			instancia = new ContPrincipal();
		}
		
		return instancia;
	}
	
	public IFachada GetIFachada()
	{
		return interfazFachada;
	}
}
