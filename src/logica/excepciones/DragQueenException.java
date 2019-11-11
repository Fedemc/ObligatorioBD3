package logica.excepciones;

public class DragQueenException extends Exception 
{
	private String mensaje;
	
	public DragQueenException(String msj)
	{
		mensaje = msj;
	}
	
	public String getMessage()
	{
		return mensaje;
	}
}
