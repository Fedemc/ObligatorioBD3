import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;

import logica.Fachada;

public class MainServidor 
{
	public static void main(String[] args)
	{
		Properties prop = new Properties();		
		
		// Cargamos el archivo de configuración del sistema y
		// el driver de conexión a la base de datos.
		try {
			prop.load(new FileInputStream("config/config.properties"));
			// Creamos la conexión por RMI a la fachada.
			String puerto = prop.getProperty("puertoServidor");
			System.out.println("Iniciando servidor RMI...");
			LocateRegistry.createRegistry(Integer.parseInt(puerto));
			Fachada fachada = Fachada.getInstancia();
			Naming.rebind("//" + prop.getProperty("ipServidor") + ":" + puerto + "/dragqueens", fachada);
			
			System.out.println("Servidor en línea, puerto: " + puerto + ".");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
