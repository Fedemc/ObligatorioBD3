package logica.valueObjects;

import java.io.Serializable;

public class VOTemporada implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int nroTemp;
	private int anio;
	private int cantCapitulos;
	
	
	public VOTemporada(int nroT, int anio, int cantC)
	{
		nroTemp=nroT;
		this.anio=anio;
		cantCapitulos=cantC;
	}
	
	public int getNroTemp() 
	{
		return nroTemp;
	}
	
	public void setNroTemp(int nroTemp) 
	{
		this.nroTemp = nroTemp;
	}
	
	public int getAnio() 
	{
		return anio;
	}
	
	public void setAnio(int anio) 
	{
		this.anio = anio;
	}
	
	public int getCantCapitulos() 
	{
		return cantCapitulos;
	}
	
	public void setCantCapitulos(int cantCapitulos) 
	{
		this.cantCapitulos = cantCapitulos;
	}
	
}
