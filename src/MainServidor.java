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
		Connection con = null;
		Properties prop = new Properties();
		
		try {
			// Cargamos el archivo de configuración del sistema y
			// el driver de conexión a la base de datos.
			prop.load(new FileInputStream("config/config.properties"));
			Class.forName(prop.getProperty("driver"));
			
			// Creamos la conexión y seteamos la configuración inicial.
			con = DriverManager.getConnection(prop.getProperty("url"),
					prop.getProperty("user"), prop.getProperty("password"));
			con.setAutoCommit(false);
			con.setTransactionIsolation(con.TRANSACTION_SERIALIZABLE);
			
			// Creamos la base de datos
			System.out.println("1. Creando base de datos...");
			
			Statement stmt = con.createStatement();
			stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS dbdq;");
			stmt.executeQuery("USE dbdq;");
			System.out.println("Utilizando la base de datos dbdq");
			
			// Creamos las tablas
			System.out.println("2. Creando las tablas necesarias...");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Temporadas(" + 
					"nroTemp INT, anio INT, cantCapitulos INT,"+ 
					"PRIMARY KEY (nroTemp));");
			System.out.println("Tabla Temporadas...");
			
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS DragQueens(" + 
					"nroPart INT, nombre VARCHAR(45), cantVictorias INT DEFAULT 0, nroTemp INT, " + 
					"PRIMARY KEY (nroPart,nombre), FOREIGN KEY (nroTemp) REFERENCES Temporadas(nroTemp)" + 
					");");
			System.out.println("Tabla DragQueens...");
			System.out.println("Proceso correcto.");
			
			stmt.close();
			con.commit();
			
			// Creamos la conexión por RMI a la fachada.
			String puerto = prop.getProperty("puertoServidor");
			System.out.println("3. Iniciando servidor RMI...");
			LocateRegistry.createRegistry(Integer.parseInt(puerto));
			Fachada fachada = Fachada.getInstancia();
			Naming.rebind("//" + prop.getProperty("ipServidor") + ":" + puerto + "/dragqueens", fachada);
			
			System.out.println("Servidor en línea, puerto: " + puerto + ".");
		} catch (IOException | ClassNotFoundException | SQLException | NumberFormatException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
