package utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import prog.Channel;
import prog.Emission;
import prog.Personne;

public class Lists {
	public int nbOfChannels;
	//I)
	public HashMap<String, Channel> channelsList;
	//III)
	public HashMap<Date, ArrayList<Emission>> programOfADay;
	//V)
	public TreeMap<Date,Emission> emissionBegin;
	public TreeMap<Date,Emission> emissionEnd;
	//VI)
	
	public HashMap<String,Personne> listOfActors;
	public HashMap<String,Personne> listOfDirectors;

	//VIII
	public TreeMap<String,Integer> nbEmissionByType;
	//IX
	public HashMap<String, ArrayList<Emission>> DictionnaryList;
	//Others
	public ArrayList<Emission> EmissionList = new ArrayList<Emission>();
	
	
	public Lists()
	{
		this.channelsList 		= new HashMap<String, Channel>(); 
		this.programOfADay 		=  new HashMap<Date, ArrayList<Emission>>();
		this.emissionBegin 		= new TreeMap<Date,Emission>();
		this.emissionEnd 		= new TreeMap<Date,Emission>();
		this.listOfActors		= new HashMap<String,Personne>();
		this.listOfDirectors 	= new HashMap<String,Personne>();
		this.nbEmissionByType 	= new TreeMap<String,Integer>();
		this.DictionnaryList 	= new HashMap<String, ArrayList<Emission>>();
		this.EmissionList 		= new ArrayList<Emission>();	
	}
}
