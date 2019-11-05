package logica.valueObjects;

import java.io.Serializable;

public class VOTempMaxPart extends VOTemporada implements Serializable
{
	private int cantParticipantes;
	
	public VOTempMaxPart(int nroT, int anio, int cantC, int cantParticipantes)
	{
		super(nroT, anio, cantC);
		this.cantParticipantes = cantParticipantes;
	}

	public int getCantParticipantes()
	{
		return cantParticipantes;
	}

	public void setCantParticipantes(int cantParticipantes)
	{
		this.cantParticipantes = cantParticipantes;
	}
	
	
}

	
