package prog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Emission {
	
	private String 				title;
	private String 				desc;
	private Date 				date_begin;
	private Date 				date_end;
	private int 				length;
	private String 				unitOfLength;
	private String 				channel;
	private String 				country;
	private String 				type;
	private String 				pathToIcon;
	private ArrayList<Personne> actors;
	private ArrayList<Personne> directors;	
	private String				channelName;
	
	//private Channel channel; A DISCUTER

	public Emission()
	{
		
	}
	public Emission(Date date_begin, Date date_end, String channel,String channelName, String title, String desc, int length,String country, String type,String unitOfLength,String pathToIcon,ArrayList<Personne> actors,ArrayList<Personne> directors) {
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
		this.channelName = channelName;
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
	public String getChannelID()
	{
		return this.channel;
	}
	public String getDesc()
	{
		return this.desc;
	}
	
	public String toString()
	{
		SimpleDateFormat form= new SimpleDateFormat("'le' EEEE d MMM yyyy 'à' HH:mm");
		
		String str = "\n ---- [EMISSION] ---- ";
		str += "\n - Titre : "+this.title;
		str += "\n - Channel  : "+this.channelName;
		str += "\n - Début : "+form.format(this.date_begin) + " \n - Fin : "+form.format(this.date_end);
		
		return str;
		
	}
	
	public String toString(int id)
	{
		SimpleDateFormat form= new SimpleDateFormat("'le' EEEE d MMM yyyy 'à' HH:mm");
		
		String str = "\n - - - - [EMISSION] ["+id+"] - - - - ";
		str += "\n - Titre : "+this.title;
		str += "\n - Début : "+form.format(this.date_begin) + " \n - Fin : "+form.format(this.date_end);
		
		return str;
		
	}
	
	public String display()
	{	
		SimpleDateFormat form= new SimpleDateFormat("'le' EEEE d MMM yyyy 'à' HH:mm");
		
		String str = "\n - - - - [FICHE EMISSION] - - - - \n";
		str += "\n - Titre : "+this.title;
		str += "\n - Commence "+form.format(this.date_begin);
		str += "\n - Fini "+form.format(this.date_end);
		str += "\n - Type : "+this.type;
		str += "\n  - Description : "+desc + "\n";
		
		return str;		
	}
	

}
