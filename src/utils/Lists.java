package utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import prog.Channel;
import prog.Emission;
import prog.Personne;

public class Lists {
	//I)
	public HashMap<String, Channel> channelsList;
	//II)
	public ArrayList<Date> DaysOfPrograms;
	//III)
	public HashMap<Date, ArrayList<Emission>> programOfADay;
	//V)
	public TreeMap<Date,Emission> emissionBegin;
	public TreeMap<Date,Emission> emissionEnd;
	//VI)
	public ArrayList<Personne> listOfActors;
	public ArrayList<Personne> listOfDirectors;
	//VIII
	public TreeMap nbEmissionByType;
	//IX
	public HashMap<String, ArrayList<Emission>> DictionnaryList;
	//Others
	public ArrayList<Emission> EmissionList = new ArrayList<Emission>();
	
	
	public Lists()
	{
		this.channelsList = new HashMap<String, Channel>(); 
		this.DaysOfPrograms = new ArrayList<Date>();
		this.programOfADay =  new HashMap<Date, ArrayList<Emission>>();
		this.emissionBegin = new TreeMap<Date,Emission>();
		this.emissionEnd = new TreeMap<Date,Emission>();
		this.listOfActors= new ArrayList<Personne>();
		this.listOfDirectors = new ArrayList<Personne>();
		this.nbEmissionByType = new TreeMap();
		this.DictionnaryList = new HashMap<String, ArrayList<Emission>>();
		this.EmissionList = new ArrayList<Emission>();	
	}
}
