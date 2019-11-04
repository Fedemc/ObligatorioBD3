package logicaPersistencia.accesoBD;

public class Consultas
{
	public static String nuevaTemporada()
	{
		return "INSERT INTO Temporadas VALUES (?,?,?)";
	}
	
	public static String inscribirDragQueen()
	{
		return "INSERT INTO DragQueens(nroPart, nombre, nroTemp) VALUES (?,?,?)";
	}
	
	public static String listarTemporadas()
	{
		return "SELECT * FROM Temporadas ORDER BY nroTemp";
	}
	
	public static String dragQueensDeTemporada()
	{
		return "SELECT * FROM DragQueens where nroTemp = ?";
	}
	
	public static String temporadaConNumero()
	{
		return "SELECT * FROM Temporadas WHERE nroTemp = ?";
	}
		
	/* Dado un nro de temporada cuenta la cantidad de DragQueens que participaron en la temporada */
	public static String cantidadParticipantesDeTemporada()
	{
		return "SELECT COUNT(*) cantParticipantes FROM DragQueens WHERE nroTemp = ?";
	}
	
	public static String registrarVictoria() 
	{
		return "UPDATE DragQueens SET cantVictorias=cantVictorias+1 WHERE nroPart = ? AND nroTemp = ?";
	}
	
	/* Dado una temporada devuelve la cantidad de victorias de cada drag queen. Iterar sobre el RS 
	 * devuelto para encontrar la que tenga mas victorias.*/
	public static String cantidadDeVictoriasDeDragQueensDeTemporada()
	{
		return "SELECT nroPart, cantVictorias FROM DragQueens WHERE nroTemp = ? ORDER BY nroPart";
	}
	
	public static String dragQueenPorNumeroDeParticipante()
	{
		return "SELECT nombre, nroTemp FROM DragQueens WHERE nroPart = ?";
	}
	
	public static String temporadaConMasParticipantes()
	{
		return "SELECT t.nroTemp, MAX(t.anio) AS nroAnio, MAX(t.cantCapitulos) AS cantCaps, COUNT(dq.nombre) AS cantParticipantes FROM temporadas t, dragqueens dq WHERE t.nroTemp = dq.nroTemp GROUP BY t.nroTemp ORDER BY COUNT(dq.nombre) DESC LIMIT 1";
	}
}
