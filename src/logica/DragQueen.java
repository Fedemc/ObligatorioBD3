package logica;

import java.io.Serializable;

public class DragQueen implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private int nroPart;
	private String nombre;
	private int cantVictorias;
	
	public DragQueen(int nroP, String nom, int cantV)
	{
		nroPart = nroP;
		nombre = nom;
		cantVictorias = cantV;
	}

	public int getNroPart()
	{
		return nroPart;
	}

	public String getNombre()
	{
		return nombre;
	}

	public int getCantVictorias()
	{
		return cantVictorias;
	}
	
	public void sumarVictoria() {
		cantVictorias++;
	}
	
}
