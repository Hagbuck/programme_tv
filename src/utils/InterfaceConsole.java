package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.plaf.BorderUIResource.EmptyBorderUIResource;

import prog.*;

public class InterfaceConsole {
	
	// static liste
	private static boolean run = false;
	private static Lists inputLists;
	private static BufferedReader buf ;
	
	public static void initConsole(Lists p_lists) throws IOException //Passé liste en paramètre
	{
		inputLists = p_lists;
		
		buf = new BufferedReader(new InputStreamReader(System.in));
		run = true;
		
		
		while(run == true)
		{
			System.out.println(" ===> Entrez une commande" );
			String s = buf.readLine();
			run = readMessage(s);
		}

		
	}
	
	public static boolean readMessage(String command) throws IOException
	{
		String [] splitCommand = command.split("-");
		String labelCommand = splitCommand[0].replaceAll("\\s+","");
		
		//Selon la commande entrée
		switch (labelCommand) {
		
		
		/** Request HELP **/
		case "help":
			System.out.println("Liste des commandes non disponible.");
			return true;
			
			
			
			
		/** Commande de test **/
		case "test" : 
			System.out.println("1...2...3... Test : Ok !");
			return true;

			
			
			
		/** Affiche des infos sur les chaines **/
		// "chan" : Affiche toute les chaines connues
		// "chan - [NAME] : Donne la programmation de toute la chaine [NAME]
		// "chan - [NAME] - [DATE] : Donne la programmation de la chaine [NAME] pour la date [DATE]
		case "chan" :
			System.out.println("============================================");
			
			if(splitCommand.length == 1)
				getListChannels();
			
			else if(splitCommand.length == 2)
				getProgChannel(splitCommand[1].replaceAll("\\s+",""));
			
			
			else if(splitCommand.length == 3)
			{
				try 
				{
					getProgChannelByDate(splitCommand[1].replaceAll("\\s+",""),splitCommand[2].replaceAll("\\s+",""));
				} 
				catch (ParseException e)
				{
					e.printStackTrace();
				}	
			}
					
			
			System.out.println("============================================");
			return true;
			
		
			
		/** Affiche les jours comportant un programme **/
		case "when":
			System.out.println("============================================");
			getDaysWithProgramation();
			System.out.println("============================================");
			return true;
			
		
			
		/** Quitte l'application **/
		case "exit" : 
			System.out.println("Fermeture de l'application. Merci de votre utilisation.");
			return false;
			
		
		/** Affiche les emissions par type trié par ordre croissant **/
		case "emission_by_type" : 
			System.out.println("============================================");
			getNbEmissionByType();
			System.out.println("============================================");
			return true;
		
		/** Affiche des informations sur les acteurs **/
		// "actor" : Affiche tout les acteurs
		// "actor - [NAME]" : Affiche les émissions dans lequelle joue l'acteur
		case "actor" :
			System.out.println("============================================");
			
			if(splitCommand.length == 1)
				getPers("actor");
			else if(splitCommand.length == 2)
				getInfoPers("actor",splitCommand[1]);
			else
				System.out.println("Syntax incorecte");
			
			System.out.println("============================================");
			return true;
		
			
		/** Affiche des informations sur les directeurs **/
		// "director" : Affiche tout les acteurs
		// "director - [NAME]" : Affiche les émissions dans lequelle joue l'acteur
		case "director":
			System.out.println("============================================");
			
			if(splitCommand.length == 1)
				getPers("director");
			else if(splitCommand.length == 2)
				getInfoPers("director",splitCommand[1]);
			else
				System.out.println("Syntax incorecte");
			
			System.out.println("============================================");
			return true;
		
			
		/** Fonction de recherche du systeme **/
		case "search": 
			System.out.println("============================================");
	
			search(splitCommand[1]);
			
			System.out.println("============================================");
			return true;
			
		
		/** Commande non reconnue par le systeme **/
		default:
			System.out.println("Commande inconnue.");
			return true;

		}
		
	}
	
	
	
	/**
	 * Display Channel List. Call with command 'chan'
	 */
	private static void getListChannels()
	{

		 ArrayList<Channel> chanList = inputLists.channelsList;
		
		//Liste Vide
		if(chanList.size() == 0)
			logMsg("INFO", "Aucune chaîne n'est connue par le systême.");
		
		//Au - un élément
		else
		{
			
			for(int i=0;i < chanList.size();i++)
				System.out.println("["+i+"]" + chanList.get(i).toString());	
		}
	}
	
	
	/**
	 * Display Programmation for one day and a specific channel
	 * TODO
	 * @param id Id of the channel
	 * @throws ParseException 
	 */
	private static void getProgChannelByDate(String param,String date) throws ParseException
	{
		Date d = parseDate(date);
		System.out.println(d);
		
		if(!d.equals(new Date(0,0,0)))
		{
			for(Channel c : inputLists.channelsList)
			{
				if(c.getName().equals(param))
				{
					//System.out.println("\n-- Programmation de " + c.getName() +"\n" + c.programOfADay);
					Set<Date> keys = c.programOfADay.keySet();
					for(Date key : keys)
						if(key.equals(d))
							System.out.println(c.programOfADay.get(d));
				}
					
			}
		}
		
		else
			System.out.println("Invalid Date dd/MM/YYYY");
	}
	
	
	
	/**
	 * Display the days who comport a programmation
	 */
	private static void getDaysWithProgramation()
	{
		ArrayList<Date> dayWithProgramation = inputLists.daysOfPrograms;
		
		//Si liste vide
		if(dayWithProgramation.size() == 0)
			logMsg("INFO", "Pas de date avec une programmation connue par le systême.");
			
		else
		{
			for(int i=0 ; i < dayWithProgramation.size() ; i++)
				System.out.println(dayWithProgramation.get(i).toString());
		}
	}
	


	

	/**
	 * Affiche les émissions par type trié par nombre contenu dans le systeme.
	 */
	private static void getNbEmissionByType()
	{
		TreeMap<String,Integer> emissionByType = inputLists.nbEmissionByType;
		Object[] keysStr = emissionByType.keySet().toArray();
		Object[] values = emissionByType.values().toArray();
		
		TreeMap<Integer,String> emissionByType2 = new TreeMap<Integer,String>();
		for(int i =0;i < keysStr.length ; i++)
			emissionByType2.put((Integer) values[i], (String) keysStr[i]);
		
		
		Set<Integer> keys = emissionByType2.keySet();
		for(Integer key : keys)
			System.out.println(emissionByType2.get(key).toString() + "- ["+key.toString()+"]");
	}
	
	/**
	 * Donne la programmation d'une chaine (ignore la date)
	 * @param param Nom de la chaine concernée
	 */
	private static void getProgChannel(String param)
	{
		for(Channel c : inputLists.channelsList)
		{
			if(c.getName().equals(param))
				System.out.println("\n-- Programmation de " + c.getName() +"\n" + c.programOfADay);
		}
		
	}
	
	/**
	 * Donne les films en lien avec une personne
	 * @param pers Can be actor or director
	 * @param param Nom de la personne
	 */
	private static void getInfoPers(String pers, String param)
	{
		HashMap<String,Personne> useList;
		
		if(pers == "actor")
			useList = inputLists.listOfActors;
		else 
			useList = inputLists.listOfDirectors;
		
		
		if(useList.containsKey(param))
		{
			ArrayList<Emission> actorsList = useList.get(param).playedEmission;
			for(int i=0; i < actorsList.size();i++)
				System.out.println(actorsList.get(i));
		}
		

		else if(useList.containsKey(param.substring(1)))
		{
			ArrayList<Emission> actorsList = useList.get(param.substring(1)).playedEmission;
			for(int i=0; i < actorsList.size();i++)
				System.out.println(actorsList.get(i));
		}
		
		else
			System.out.println("Le "+ pers +" n'est pas connue du systême.");

	}
	
	/**
	 * Display actors and directors in the system
	 * TODO - Trié par films
	 * @param pers Can be actor or director
	 */
	private static void getPers(String pers)
	{
		HashMap<String,Personne> useList;
		
		if(pers == "actor")
			useList = inputLists.listOfActors;
		else 
			useList = inputLists.listOfDirectors;
		
		Set<String> keys = useList.keySet();
		
		for(String key : keys)
			System.out.println(key + " ~ ["+ useList.get(key).getNbPlayedEmission() +"]");
			
	}
	
	
	//TODO key en full minuscurle pour recherche
	/**
	 * Search emission with specifics words
	 * @param words contains all words
	 */
	private static void search(String words)
	{
		String [] wordsSplit = words.split(" ");
		
		 HashMap<String, ArrayList<Emission>> searchlist = inputLists.DictionnaryList;
		 ArrayList<Emission> correspondEmission = new ArrayList<Emission>();
		 
		 //Pour tout les mots passé en paramètre
		 for(int i=0;i < wordsSplit.length;i++)
		 {
			 //On controle leur existence dans notre dictionnaire
			if(searchlist.containsKey(wordsSplit[i]) && !wordsSplit[i].equals(""))
			{
				//On display les emissions concernées
				for(int j=0; j <  searchlist.get(wordsSplit[i]).size() ; j++)
				{
					System.out.println(searchlist.get(wordsSplit[i]).get(j).display());
				}
			}
			
		 }		 
	}
	
	
	/**
	 * Log message for the system.
	 * @param type The type of message
	 * @param str The message
	 */
	private static void logMsg(String type,String str)
	{
		System.out.println("["+type+"] - "+str);
	}
	
	
	
	/**
	 * Parse un string pour contenir une date
	 * @param str_date String de date
	 * @return Date vide si parametre erronée sinon la date associée.
	 * @throws ParseException 
	 */
	private static Date parseDate(String str_date) throws ParseException
	{
		String [] splitDate = str_date.split("/");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if(splitDate.length == 3 )
		{
			int jour = Integer.parseInt(splitDate[0]);
			int mois = Integer.parseInt(splitDate[1]);
			int an = Integer.parseInt(splitDate[2]);
			
			if(mois < 0 || mois > 12)
				return sdf.parse("00/00/0000");
			else if(jour < 0 || jour > 31)
				return sdf.parse("00/00/00");
			else if(splitDate[2].length() != 4)
				return sdf.parse("00/00/0000");
			else
			{
				
				return sdf.parse(str_date);
			}

		}
		
		else
			return sdf.parse("00/00/0000");
	}
	
}
