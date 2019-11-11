package logica.excepciones;

public class TemporadaException extends Exception 
{
	private String mensaje;
	
	public TemporadaException(String msj)
	{
		mensaje = msj;
	}
	
	public String getMessage()
	{
		return mensaje;
	}
}
