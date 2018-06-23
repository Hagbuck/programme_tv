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
	private ArrayList<Personne> director;
	
	public Emission()
	{
		
	}
	public Emission(Date date_begin, Date date_end, String channel, String title, String desc, int length,
			String country, String type) {
		this.date_begin = date_begin;
		this.date_end = date_end;
		this.channel = channel;
		this.title = title;
		this.desc = desc;
		this.length = length;
		this.country = country;
		this.type = type;
	}
	
	public void addDirector(Personne p)
	{
		director.add(p);
	}
	
	public void addActor(Personne p)
	{
		actors.add(p);
	}
	
	public String toString()
	{
<<<<<<< HEAD
		return "[Emission]" + this.title;
=======
		String str = " ---- [EMISSION] ---- ";
		str += "\n - Titre : "+this.title;
		str += "\n - Channel  "+this.channel.toString();
		
		return str;
		
>>>>>>> ef03c9f86d313ac4a9d6001b83647b69ef9e85fa
	}

}
