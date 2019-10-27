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

import logicaPersistencia.Fachada;

public class Main 
{
	public static void main (String[] args)
	{
		String error= new String();
		String driver=null;
		String url=null;
		String usuario=null;
		String password=null;
		String db=null;
		Connection con=null;
		
		try
		{
			/* Obtengo los datos de conexión desde el archivo de configuración */
			Properties p=new Properties();
			String nomArch="config/config.properties";
			p.load(new FileInputStream(nomArch));
			driver=p.getProperty("driver");
			url = p.getProperty("url");
			usuario=p.getProperty("user");
			password=p.getProperty("password");
			db=p.getProperty("DB");

			Class.forName(driver);
		}
		
		catch (ClassNotFoundException e)
		{
			error= e.toString();
		}
		catch (FileNotFoundException fEx)
		{
			error= fEx.toString();
		}
		catch (IOException ioEx)
		{
			error= ioEx.toString();
		}
		
		try
		{
			con = DriverManager.getConnection(url, usuario, password);
			
			DatabaseMetaData dbmtd=con.getMetaData();
			ResultSet rstDB = dbmtd.getCatalogs();
			con.setAutoCommit(false);
			con.setTransactionIsolation(con.TRANSACTION_SERIALIZABLE);
			boolean existe=false;
			while(rstDB.next() && !existe)
			{
				String nombreDB = rstDB.getString(1);
				if(nombreDB.equals(db))
				{
					existe=true;
				}
			}
			
			if(!existe)
			{		
				String consultaCrearBD="CREATE DATABASE "+ db;
				String consultaCrearTablaTemporadas = "CREATE TABLE Temporadas("
						+ "nroTemp INT, anio INT, cantCapitulos INT,"
						+ "PRIMARY KEY (nroTemp));";
				String consultaCrearTablaDragQueens = "CREATE TABLE DragQueens("
						+ "nroPart INT, nombre VARCHAR(45), cantVictorias INT DEFAULT 0, nroTemp INT, "
						+ "PRIMARY KEY (nroPart,nombre), FOREIGN KEY (nroTemp) REFERENCES Temporadas(nroTemp)"
						+ ");";
				
				System.out.print("Creando BD y tablas.\n");
				Statement stmt=con.createStatement();
				stmt.executeUpdate(consultaCrearBD);
				System.out.println("DB "+db+" creada.");
				stmt.executeQuery("Use "+db);
				System.out.println("Utilizando DB: "+db);
				stmt.executeUpdate(consultaCrearTablaTemporadas);
				System.out.println("Tabla Temporadas creada.");
				stmt.executeUpdate(consultaCrearTablaDragQueens);
				System.out.println("Tabla DragQueens creada.");
				
				stmt.close();
				con.commit();
			}
			else
			{
				System.out.println("Ya existe la DB.");
				String usarBD="use "+db;
				Statement stmt=con.createStatement();
				stmt.executeQuery(usarBD);
				stmt.close();
				con.commit();
			}
			
			//con.setAutoCommit(false);
		}
		catch (SQLException e)
		{
			try
			{
				con.rollback();
			}
			catch (SQLException ex)
			{
				error=ex.toString();
			}
			
			error= e.toString();
			System.out.println(error);
		}
		
		
		try
		{
			System.out.println("Accediendo a Fachada");
			//Obtengo datos de config de server
			Properties p=new Properties();
			String nomArch="config/config.properties";
			p.load(new FileInputStream(nomArch));
			String ip=p.getProperty("ipServidor");
			String puerto=p.getProperty("puertoServidor");
			int port = Integer.parseInt(puerto);
			
			//Pongo a correr el rmiregistry
			LocateRegistry.createRegistry(port);
			
			//publico el objeto remoto en dicha ip y puerto
			String ruta="//"+ip+":"+puerto+"/fachada";
			Fachada fachada=Fachada.GetInstancia();
			Naming.rebind(ruta, fachada);
			System.out.println("Servidor en linea");
		}
		catch(RemoteException rE)
		{
			rE.printStackTrace();
		}
		catch(MalformedURLException mEx)
		{
			mEx.printStackTrace();
		}
		catch(FileNotFoundException fnotfEx)
		{
			fnotfEx.printStackTrace();
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}	
	}
}
