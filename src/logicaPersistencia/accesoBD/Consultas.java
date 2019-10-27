package logicaPersistencia.accesoBD;

public class Consultas
{
	public static String NuevaTemporada()
	{
		String query = "INSERT INTO Temporadas VALUES (?,?,?)";
		return query;
	}
	
	public static String InscribirDragQueen()
	{
		String query = "INSERT INTO DragQueens(nroPart, nombre, nroTemp) VALUES (?,?,?)";
		return query;
	}
	
	public static String ListarTemporadas()
	{
		String query = "SELECT * FROM Temporadas ORDER BY nroTemp";
		return query;
	}
	
	public static String ListarDragQueens()
	{
		String query = "SELECT * FROM DragQueens ORDER BY nroPart";
		return query;
	}
	
	public static String TempConNroTemp()
	{
		String query = "SELECT * FROM Temporadas WHERE nroTemp = ?";
		return query;
	}
		
	/* Dado un nro de temporada cuenta la cantidad de DragQueens que participaron en la temporada */
	public static String CantParticipantesTemp()
	{
		String query = "SELECT COUNT(*) cantParticipantes FROM DragQueens WHERE nroTemp = ?";
		return query;
	}
	
	public static String RegistrarVictoria() 
	{
		String query = "UPDATE DragQueens SET cantVictorias=cantVictorias+1 WHERE nroPart = ? AND nroTemp = ?";
		return query;
	}
	
	/* Dado una temporada devuelve la cantidad de victorias de cada drag queen. Iterar sobre el RS 
	 * devuelto para encontrar la que tenga mas victorias.*/
	public static String NroPartDragQueenConMasVictorias()
	{
		String query = "SELECT nroPart, cantVictorias FROM DragQueens WHERE nroTemp = ? ORDER BY nroPart";
		return query;
	}
	
	public static String DragQueenConNroPart()
	{
		String query = "SELECT nombre, nroTemp FROM DragQueens WHERE nroPart = ?";
		return query;
	}
}
