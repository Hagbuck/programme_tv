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
	private HashMap<String, Channel> channelsList;
	//II)
	private ArrayList<Date> DaysOfPrograms;
	//III)
	private HashMap<Date, ArrayList<Emission>> programOfADay;
	//V)
	private TreeMap<Date,Emission> emissionBegin;
	private TreeMap<Date,Emission> emissionEnd;
	//VI)
	private ArrayList<Personne> listOfActors;
	private ArrayList<Personne> listOfDirectors;
	//VIII
	private TreeMap nbEmissionByType;
	//IX
	private HashMap<String, ArrayList<Emission>> DictionnaryList;
	//Others
	private ArrayList<Emission> EmissionList = new ArrayList<Emission>();
	
	
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

	public boolean ajoutDate(Date dateToAdd)
	{
		//test si
		return true;
	}

}
