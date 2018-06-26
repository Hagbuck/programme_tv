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
	public ArrayList<Channel> channelsList;
	//II)
	public ArrayList <Date> daysOfPrograms;
	//V)
	public TreeMap<Date,Emission> emissionBegin;
	public TreeMap<Date,Emission> emissionEnd;
	//VI)
	public HashMap<String,Personne> listOfActors;
	public HashMap<String,Personne> listOfDirectors;
	//VII)
	public TreeMap<String,Integer> nbEmissionByActor;
	public TreeMap<String,Integer> nbEmissionByDirector;
	//VIII
	public TreeMap<String,Integer> nbEmissionByType;
	//IX
	public HashMap<String, ArrayList<Emission>> DictionnaryList;
	//Others
	public HashMap<String,Integer> channedID;

	int length_max = 0;
	
	public Lists()
	{
		this.channelsList 		= new ArrayList<Channel>(); 
		this.daysOfPrograms		= new ArrayList <Date>();
		this.emissionBegin 		= new TreeMap<Date,Emission>();
		this.emissionEnd 		= new TreeMap<Date,Emission>();
		this.listOfActors		= new HashMap<String,Personne>();
		this.listOfDirectors 	= new HashMap<String,Personne>();
		this.nbEmissionByActor 	= new TreeMap<String,Integer>();
		this.nbEmissionByDirector 	= new TreeMap<String,Integer>();
		this.nbEmissionByType 	= new TreeMap<String,Integer>();
		this.DictionnaryList 	= new HashMap<String, ArrayList<Emission>>();
		this.channedID 			= new HashMap<String,Integer>();
	}
	
	public void triNbEmissionByType()
	{

		TreeValuesComparator comparateur = new TreeValuesComparator(this.nbEmissionByType);
		TreeMap<String,Integer> treeMapTriee = new TreeMap<String,Integer>(comparateur);
		treeMapTriee.putAll(this.nbEmissionByType);
		this.nbEmissionByType.clear();
		this.nbEmissionByType = treeMapTriee;
		
	}
	public void triNbEmissionByPersonne()
	{

		TreeValuesComparator comparateur = new TreeValuesComparator(this.nbEmissionByActor);
		TreeMap<String,Integer> treeMapTriee = new TreeMap<String,Integer>(comparateur);
		treeMapTriee.putAll(this.nbEmissionByActor);
		this.nbEmissionByActor.clear();
		this.nbEmissionByActor = treeMapTriee;
		
	}
	
	public void triNbEmissionByDirector()
	{

		TreeValuesComparator comparateur = new TreeValuesComparator(this.nbEmissionByDirector);
		TreeMap<String,Integer> treeMapTriee = new TreeMap<String,Integer>(comparateur);
		treeMapTriee.putAll(this.nbEmissionByDirector);
		this.nbEmissionByDirector.clear();
		this.nbEmissionByDirector = treeMapTriee;
		
	}
	
	
}
