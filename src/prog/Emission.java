package prog;

import java.util.ArrayList;
import java.util.Date;

public class Emission {
	
	private Date date_begin;
	private Date date_end;
	//private Channel channel; A DISCUTER
	private String title;
	private String desc;
	
	private ArrayList<Personne> actors;
	private int length;
	private String country;
	private String type;

	//added
	public 	String pathToIcon;
	private String unitOfLength;
	
	//changed
	private String channel;
	private ArrayList<Personne> directors;
	
	public Emission()
	{
		
	}
	public Emission(Date date_begin, Date date_end, String channel, String title, String desc, int length,String country, String type,String unitOfLength,String pathToIcon,ArrayList<Personne> actors,ArrayList<Personne> directors) {
		this.date_begin = date_begin;
		this.date_end = date_end;
		this.channel = channel;
		this.title = title;
		this.desc = desc;
		this.length = length;
		this.country = country;
		this.type = type;
		this.unitOfLength = unitOfLength;
		this.pathToIcon = pathToIcon;
		this.actors = actors;
		this.directors = directors;
	}
	
	public void addDirector(Personne p)
	{
		directors.add(p);
	}
	
	public void addActor(Personne p)
	{
		actors.add(p);
	}
	
	public Date getDateStart()
	{
		return date_begin;
	}
	public Date getDateEnd()
	{
		return date_end;
	}
	public ArrayList<Personne> getDirectors()
	{
		return this.directors;
	}
	public ArrayList<Personne> getActors()
	{
		return this.actors;
	}
	public String getType()
	{
		return this.type;
	}
	public String toString()
	{
		
		String str = "\n ---- [EMISSION] ---- ";
		str += "\n - Titre : "+this.title;
		str += "\n - Channel  "+this.channel.toString();
		
		return str;
		
	}

}
