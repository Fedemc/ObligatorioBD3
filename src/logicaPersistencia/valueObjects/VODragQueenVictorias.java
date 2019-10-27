package logicaPersistencia.valueObjects;

import java.io.Serializable;

public class VODragQueenVictorias extends VODragQueen implements Serializable
{
	private int nroPart;
	private int cantVictorias;
	
	public VODragQueenVictorias(String nombre, int nroTemp, int nroPart, int cantVictorias)
	{
		super(nombre, nroTemp);
		this.nroPart = nroPart;
		this.cantVictorias = cantVictorias;
	}

	public int getNroPart()
	{
		return nroPart;
	}

	public void setNroPart(int nroPart)
	{
		this.nroPart = nroPart;
	}

	public int getCantVictorias()
	{
		return cantVictorias;
	}

	public void setCantVictorias(int cantVictorias)
	{
		this.cantVictorias = cantVictorias;
	}
	
	

}
