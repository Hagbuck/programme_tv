package prog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Channel {
	
	private String id;
	private String name;
	public String pathToIcon;
	public HashMap<Date, ArrayList<Emission>> programOfADay;


	public Channel()
	{
		this.programOfADay = new HashMap<Date, ArrayList<Emission>>();
	}
	public Channel(String p_id,String p_name,String p_path)
	{
		this.id = p_id;
		this.name = p_name;
		this.pathToIcon = p_path;
		this.programOfADay = new HashMap<Date, ArrayList<Emission>>();
		System.out.println("creation channel termine");
	}
	
	public String toString()
	{
		return "[CHANNEL] - "+name+" ("+id+")";
	}

	/******* GETER & SETER ******/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addEmission(Date date,Emission emission)
	{
		if(this.programOfADay.get(date)==null)
		{
			this.programOfADay.put(date, new ArrayList<Emission>());
		}
		if(!this.programOfADay.get(date).contains(emission))
		{
			this.programOfADay.get(date).add(emission);
		}
	}
	public ArrayList<Emission> getProgramOfADay(Date date)
	{
		return this.programOfADay.get(date);
	}

}
