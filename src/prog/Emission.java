package prog;

import java.util.ArrayList;
import java.util.Date;

public class Emission {
	
	private Date date_begin;
	private Date date_end;
	private Channel channel;
	private String title;
	private String desc;
	private Personne director;
	private ArrayList<Personne> actors;
	private int length;
	private String country;
	private String type;

	public Emission(Date date_begin, Date date_end, Channel channel, String title, String desc, int length,
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
	
	public void setDirector(Personne p)
	{
		director = p;
	}
	
	public void addActor(Personne p)
	{
		actors.add(p);
	}

}
