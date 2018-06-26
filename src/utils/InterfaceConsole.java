package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.plaf.BorderUIResource.EmptyBorderUIResource;

import prog.*;

public class InterfaceConsole {
	
	// static liste
	private static boolean run = false;
	private static Lists inputLists;
	private static BufferedReader buf ;
	static final long ONE_MINUTE_IN_MILLIS=60000;
	
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
			System.out.println("= = = = = = Command List = = = = = = ");
			System.out.println(" * 'chan' ~ Donne la liste des chaînes connues par le systême.");
			System.out.println(" * 'chan - [NAME]' ~ Affiche la programmation de la chaine [NAME]");
			System.out.println(" * 'chan - [NAME] - dd/MM/YYYY'~ Donne la programmation de la chaine [NAME] à la date donnée.");
			System.out.println(" * 'chan - [NAME] - [DATE] - [ID]' ~ Affiche la fiche de l'émission [ID] appartenant à la chaine [NAME] pour la date [DATE].");
			System.out.println(" /!\\ * 'rightnow' ~ Donne les émissions en cours actuellement. (Heure du PC) ");
			System.out.println(" /!\\ * 'rightnow - [DATE]' ~ Donne les émissions en cours à la date [DATE].");
			System.out.println(" * 'when' ~ Donne les jours aillant une programmation et connue par le systême.");
			System.out.println(" * 'emission_by_type' ~ Donne les types d'émissions triées par type.");
			System.out.println(" * 'actor' ~ Donne la liste des acteurs connus par le systême.");
			System.out.println(" * 'actor - [NAME]' ~ Donne la liste des émissions dans lesquelles joue l'acteur [NAME].");
			System.out.println(" * 'actor - [ID] - [NAME] ' ~ Affiche la fiche de l'émission [ID] de l'acteur [NAME].");
			System.out.println(" * 'director' ~ Donne la liste des directeurs connus par le systême.");
			System.out.println(" * 'director - [NAME]' ~ Donne la liste des émissions dirigées par le directeur [NAME].");
			System.out.println(" * 'director - [ID] - [NAME]' ~ Affiche la fiche de l'émission [ID] du directeur [NAME].");
			System.out.println(" * 'search - [WORDS]' ~ Recherche la correspondances entre les mots [WORDS] et les émissions connues.");
			System.out.println(" * 'test' ~ Affichage de test de l'application.");
			System.out.println(" * 'exit' ~ Quitte l'application.");
			System.out.println(" * 'deathApp' ~ Supprime l'application et toute ses données. NE PAS UTILISER !!");
			System.out.println("= = = = = = Fin List = = = = = = ");
			return true;
			
			
			
			
		/** Commande de test **/
		case "test" : 
			System.out.println("1...2...3... Test : Ok !");
			return true;

		
			
		/** Commande de suppression de l'application **/
		case "deathApp": 
			logMsg("WARNING", "SUPPRESSION EN COURS.... ");
			logMsg("INFO", "It's just a joke ! I got you !");
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
				try{getProgChannelByDate(splitCommand[1].replaceAll("\\s+",""),splitCommand[2].replaceAll("\\s+",""));} 
				catch (ParseException e){e.printStackTrace();}	
			}
			
			else if(splitCommand.length == 4)
			{
				try {getEmission(splitCommand[1].replaceAll("\\s+",""),splitCommand[2].replaceAll("\\s+",""),splitCommand[3].replaceAll("\\s+",""));} 
				catch (ParseException e) {e.printStackTrace();}
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
			else if(splitCommand.length == 3)
				getEmissionByPers("actor",splitCommand[1].replaceAll("\\s+",""), splitCommand[2]);
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
			else if(splitCommand.length == 3)
				getEmissionByPers("director",splitCommand[1].replaceAll("\\s+",""), splitCommand[2]);
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
			
			
		case "rightnow" : 
			System.out.println("============================================");
			if(splitCommand.length == 1)
				inprogress("");
			else if(splitCommand.length == 2)
				inprogress(splitCommand[1].replaceAll("\\s+",""));
			else
				logMsg("WARNING", "Synthax incorrecte.");

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
		Date d_cp = d;
		SimpleDateFormat form= new SimpleDateFormat("'Emission diffusé sur "+param+" ' EEEE d MMM yyyy ");
		System.out.println(form.format(d_cp));
		
		if(!d.equals(new Date(0,0,0)))
		{
			boolean find = false;
			
			for(Channel c : inputLists.channelsList)
			{
				if(c.getName().equals(param))
				{
					Set<Date> keys = c.programOfADay.keySet();
					for(Date key : keys)
						if(key.equals(d))
							for(int i =0 ;i < c.programOfADay.get(d).size();i++ )
							{
								find = true;
								System.out.println(c.programOfADay.get(d).get(i).toString(i));
							}
				}
					
			}
			
			if(!find)
				logMsg("INFO","Pas correspondance trouvé.");
		}
		
		else
			System.out.println("Invalid Date dd/MM/YYYY");
	}
	
	
	/**
	 * Affiche la fiche de l'émission id de la chaine param pour la date date.
	 * @param param
	 * @param date
	 * @param id
	 * @throws ParseException
	 */
	private static void getEmission(String param,String date,String id) throws ParseException
	{
		Date d = parseDate(date);
		
		int id_string = Integer.parseInt(id);
		
		
		if(!d.equals(new Date(0,0,0)))
		{
			boolean find = false;
			
			for(Channel c : inputLists.channelsList)
			{
				if(c.getName().equals(param))
				{
					if( c.programOfADay.containsKey(d) )
					{
						if( id_string < c.programOfADay.size() )
						{
							System.out.println(c.programOfADay.get(d).get(id_string).display());
							find = true;
						}
							
						else
							logMsg("INFO", "ID Incorrect");
							
					}					
					else
						logMsg("INFO","Pas de programmation pour la chaîne et le jour donnée.");
				}
					
			}
			
			if(!find)
				logMsg("INFO", "L'émission n'a pas put être trouvé.");
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
		//System.out.println(emissionByType);
		Set set = emissionByType.entrySet();
	    Iterator iterator = set.iterator();
	    while(iterator.hasNext()) {
	         Map.Entry<String,Integer> mapentry = (Map.Entry<String,Integer>)iterator.next();
	         System.out.print(mapentry.getKey() + " - [" +mapentry.getValue()+"]\n");
	    }
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
				System.out.println("\n-- Programmation de " + c.getName() +"\n" + c.printAllPrograms());
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
				System.out.println(actorsList.get(i).toString(i));
		}
		

		else if(useList.containsKey(param.substring(1)))
		{
			ArrayList<Emission> actorsList = useList.get(param.substring(1)).playedEmission;
			for(int i=0; i < actorsList.size();i++)
				System.out.println(actorsList.get(i).toString(i));
		}
		
		else
			System.out.println("Le "+ pers +" n'est pas connue du systême.");

	}
	
	
	private static void getEmissionByPers(String pers, String id, String param)
	{
		HashMap<String,Personne> useList;
		
		if(pers == "actor")
			useList = inputLists.listOfActors;
		else 
			useList = inputLists.listOfDirectors;
		
		try {			
			int int_id = Integer.parseInt(id);
			
			if(useList.containsKey(param))
			{
				ArrayList<Emission> actorsList = useList.get(param).playedEmission;
				if(int_id < actorsList.size())
					System.out.println(actorsList.get(int_id).display());
			}
			

			else if(useList.containsKey(param.substring(1)))
			{
				ArrayList<Emission> actorsList = useList.get(param.substring(1)).playedEmission;
				if(int_id < actorsList.size())
					System.out.println(actorsList.get(int_id).display());
			}
			
			else
				System.out.println("Le "+ pers +" n'est pas connue du systême.");
		}
		catch (NumberFormatException e) {logMsg("INFO", "ID incorrect.");}
		
		
		

		
		
	}
	
	/**
	 * Display actors and directors in the system
	 * TODO - Trié par films
	 * @param pers Can be actor or director
	 */
	private static void getPers(String pers)
	{
		TreeMap<String,Integer> useList;
		
		if(pers == "actor")
			useList = inputLists.nbEmissionByActor;
		else 
			useList = inputLists.nbEmissionByDirector;
		
		Set set = useList.entrySet();
	    Iterator iterator = set.iterator();
	    while(iterator.hasNext()) {
	         Map.Entry<String,Integer> mapentry = (Map.Entry<String,Integer>)iterator.next();
	         System.out.print(mapentry.getKey() + " - [" +mapentry.getValue()+"]\n");
	    }

			
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
	
	
	private static void inprogress(String d)
	{
		//RECUP
		TreeMap<Date,Emission> begin = inputLists.emissionBegin;
		TreeMap<Date,Emission> end = inputLists.emissionEnd;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy:hh:mm");
		try 
		{
			Date dd = new Date();
			if(!d.equals(""))
				dd =  sdf.parse(d);
			
			//VARS
			int duree_max = inputLists.length_max;
			Date date_lim_sup = dd;
			Date date_lim_inf = dd;
			
			//Build date limite supp
			Calendar trans_date = Calendar.getInstance();
			trans_date.setTime(date_lim_sup);
			long t= trans_date.getTimeInMillis();
			date_lim_sup =new Date(t + (duree_max * ONE_MINUTE_IN_MILLIS));
			
			//Build date limite inf
			trans_date = Calendar.getInstance();
			trans_date.setTime(date_lim_inf);
			t= trans_date.getTimeInMillis();
			date_lim_inf =new Date(t - (duree_max * ONE_MINUTE_IN_MILLIS));
			
			ArrayList<Emission> em_end = new ArrayList<Emission>();
			ArrayList<Emission> em_begin = new ArrayList<Emission>();
			ArrayList<Emission> final_em = new ArrayList<Emission>();
			
			
			for (Entry<Date, Emission> entree : end.entrySet()) 
				if(date_lim_sup.after(entree.getKey()) && dd.before(entree.getKey()))
					em_end.add(entree.getValue());
				
				
			for (Entry<Date, Emission> entree : begin.entrySet()) 
				if(date_lim_inf.before(entree.getKey()) && dd.after(entree.getKey()) )
					em_begin.add(entree.getValue());
				
				
			if(em_begin.size() > em_end.size())
			{
				for(int i=0;i < em_end.size();i++ )
					if(em_begin.contains(em_end.get(i)))
						final_em.add(em_end.get(i));
			}
			
			else
			{
				for(int i=0;i < em_begin.size();i++ )
					if(em_end.contains(em_begin.get(i)))
						final_em.add(em_begin.get(i));
			}
			
			for(int i = 0; i < final_em.size() ; i++)
				System.out.println(final_em.get(i).display());
						
		} 
		
		catch (ParseException e) {logMsg("WARNING", "Date invalide.");}
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
