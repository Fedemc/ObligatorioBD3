package logica.excepciones;

public class PersistenciaException extends Exception 
{
	private String mensaje;
	
	public PersistenciaException(String msj)
	{
		mensaje = msj;
	}
	
	public String getMessage()
	{
		return mensaje;
	}
}
