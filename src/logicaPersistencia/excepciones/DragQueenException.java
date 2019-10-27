package logicaPersistencia.excepciones;

public class DragQueenException extends Exception 
{
	private String mensaje;
	
	public DragQueenException(String msj)
	{
		mensaje = msj;
	}
	
	public String DarMensaje()
	{
		return mensaje;
	}
}
