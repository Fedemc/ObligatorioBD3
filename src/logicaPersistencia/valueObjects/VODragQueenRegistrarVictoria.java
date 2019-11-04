package logicaPersistencia.valueObjects;

import java.io.Serializable;

public class VODragQueenRegistrarVictoria implements Serializable
{
	private int nroPart;
	private int nroTemp;
	
	public VODragQueenRegistrarVictoria(int nroPart, int nroTemp)
	{
		this.nroPart = nroPart;
		this.nroTemp = nroTemp;
	}

	public int getNroPart()
	{
		return nroPart;
	}

	public void setNroPart(int nroPart)
	{
		this.nroPart = nroPart;
	}

	public int getNroTemp()
	{
		return nroTemp;
	}

	public void setNroTemp(int nroTemp)
	{
		this.nroTemp = nroTemp;
	}	
	
}
