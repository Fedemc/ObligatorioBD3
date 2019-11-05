package logica;

import java.sql.Connection;


public abstract interface IConexion
{
	Connection getConnection();
}
