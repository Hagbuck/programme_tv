package prog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class Channel {
	
	private String id;
	private String name;
	private String nameWithoutSpace;
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
	}
	
	public String toString()
	{
		return "[CHANNEL] - "+name;
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
	public String getNameWithoutSpace() {
		return nameWithoutSpace;
	}
	public void setNameWithoutSpace(String nameWithoutSpace)
	{
		this.nameWithoutSpace = nameWithoutSpace;
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

	public String printAllPrograms()
	{
		SimpleDateFormat form= new SimpleDateFormat("EEEE d MMM yyyy");
		
		String result = "";
		Iterator i = this.programOfADay.keySet().iterator();
		Date clef; 
		ArrayList<Emission> value = new ArrayList<Emission>();
		while (i.hasNext())
		{
			
		    clef = (Date) i.next();
		    result += "\n Emissions du " + form.format(clef) + "\n";
		    value = this.programOfADay.get(clef);
		    for(Emission e : value)
		    {
		    	result += "\n" + e.toString();
		    }
		}
		return result;
		
	}
}
