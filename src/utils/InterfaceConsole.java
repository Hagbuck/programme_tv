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

/**
 * Constructor de la Classe InterfaceConsole.
 * @author DrainMan
 *
 */
public class InterfaceConsole {
	
	/************** VARS ******************/
	private static boolean run = false;
	private static Lists inputLists;
	private static BufferedReader buf ;
	static final long ONE_MINUTE_IN_MILLIS=60000;
	
	/**
	 * Initie l'inteface console de l'application.
	 * @param p_lists Un objet contenant toute les listes n�cessaires.
	 * @throws IOException
	 */
	public static void initConsole(Lists p_lists) throws IOException 
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
	
	/**
	 * Lie et interpr�te les commandes pass�e en console.
	 * @param command La commande en forme string.
	 * @return true si l'interface doit continuer � tourner, false si elle doit s'arr�ter.
	 * @throws IOException
	 */
	public static boolean readMessage(String command) throws IOException
	{
		String [] splitCommand = command.split("-");
		String labelCommand = splitCommand[0].replaceAll("\\s+","");
		
		//Selon la commande entr�e
		switch (labelCommand) {
		
		
		/** Request HELP **/
		case "help":
			System.out.println("= = = = = = Command List = = = = = = ");
			System.out.println(" * 'chan' ~ Donne la liste des cha�nes connues par le syst�me.");
			System.out.println(" * 'chan - [NAME]' ~ Affiche la programmation de la chaine [NAME]");
			System.out.println(" * 'chan - [NAME] - dd/MM/YYYY'~ Donne la programmation de la chaine [NAME] � la date donn�e.");
			System.out.println(" * 'chan - [NAME] - [DATE] - [ID]' ~ Affiche la fiche de l'�mission [ID] appartenant � la chaine [NAME] pour la date [DATE].");
			System.out.println(" /!\\ * 'rightnow' ~ Donne les �missions en cours actuellement. (Heure du PC) ");
			System.out.println(" /!\\ * 'rightnow - dd/MM/YYYY:hh:mm' ~ Donne les �missions en cours � la date [DATE].");
			System.out.println(" * 'when' ~ Donne les jours aillant une programmation et connue par le syst�me.");
			System.out.println(" * 'emission_by_type' ~ Donne les types d'�missions tri�es par type.");
			System.out.println(" * 'actor' ~ Donne la liste des acteurs connus par le syst�me.");
			System.out.println(" * 'actor - [NAME]' ~ Donne la liste des �missions dans lesquelles joue l'acteur [NAME].");
			System.out.println(" * 'actor - [ID] - [NAME] ' ~ Affiche la fiche de l'�mission [ID] de l'acteur [NAME].");
			System.out.println(" * 'director' ~ Donne la liste des directeurs connus par le syst�me.");
			System.out.println(" * 'director - [NAME]' ~ Donne la liste des �missions dirig�es par le directeur [NAME].");
			System.out.println(" * 'director - [ID] - [NAME]' ~ Affiche la fiche de l'�mission [ID] du directeur [NAME].");
			System.out.println(" * 'search - [WORDS]' ~ Recherche la correspondances entre les mots [WORDS] et les �missions connues.");
			System.out.println(" * 'test' ~ Affichage de test de l'application.");
			System.out.println(" * 'exit' ~ Quitte l'application.");
			System.out.println(" * 'deathApp' ~ Supprime l'application et toute ses donn�es. NE PAS UTILISER !!");
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
			
		
		/** Affiche les emissions par type tri� par ordre croissant **/
		case "emission_by_type" : 
			System.out.println("============================================");
			getNbEmissionByType();
			System.out.println("============================================");
			return true;
		
		/** Affiche des informations sur les acteurs **/
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
			
		
		/** Affiche les �missions passant en ce moment (par d�faut) ou � la date donn�e. **/
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
	 * Affiche la liste des ch�ines.
	 * Li� � la commande :  'chan'
	 */
	private static void getListChannels()
	{

		 ArrayList<Channel> chanList = inputLists.channelsList;
		
		//Liste Vide
		if(chanList.size() == 0)
			logMsg("INFO", "Aucune cha�ne n'est connue par le syst�me.");
		
		//Au - un �l�ment
		else
		{
			for(int i=0;i < chanList.size();i++)
				System.out.println("["+i+"]" + chanList.get(i).toString());	
		}
	}
	
	
	/**
	 * Affiche la programation pour un jour et une cha�ne donn�e.
	 * @param id L'id de la channel.
	 * @param date La date demand�e.
	 * @throws ParseException 
	 */
	private static void getProgChannelByDate(String param,String date) throws ParseException
	{
		Date d = parseDate(date);
		Date d_cp = d;
		SimpleDateFormat form= new SimpleDateFormat("'Emission diffus� sur "+param+" ' EEEE d MMM yyyy ");
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
				logMsg("INFO","Pas correspondance trouv�.");
		}
		else
			System.out.println("Invalid Date dd/MM/YYYY");
	}
	
	
	/**
	 * Affiche la fiche de l'�mission id de la chaine param pour la date date.
	 * @param param Le nom de l'�mission.
	 * @param date La date de l'�mission concern�e.
	 * @param id L'id de l'�mission dans l'ensemble fournit.
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
						if( id_string < c.programOfADay.get(d).size() )
						{
							System.out.println(c.programOfADay.get(d).get(id_string).display());
							find = true;
						}		
						else
							logMsg("INFO", "ID Incorrect");
							
					}					
					else
						logMsg("INFO","Pas de programmation pour la cha�ne et le jour donn�e.");
				}		
			}
			
			if(!find)
				logMsg("INFO", "L'�mission n'a pas put �tre trouv�.");
		}	
		else
			System.out.println("Invalid Date dd/MM/YYYY");		
	}
	
	
	
	/**
	 * Retourne la liste des jours comportant une programmation.
	 */
	private static void getDaysWithProgramation()
	{
		ArrayList<Date> dayWithProgramation = inputLists.daysOfPrograms;
		
		//Si liste vide
		if(dayWithProgramation.size() == 0)
			logMsg("INFO", "Pas de date avec une programmation connue par le syst�me.");
			
		else
			for(int i=0 ; i < dayWithProgramation.size() ; i++)
				System.out.println(dayWithProgramation.get(i).toString());
	}
	


	

	/**
	 * Affiche les �missions par type tri� par nombre contenu dans le syt�me.
	 */
	private static void getNbEmissionByType()
	{
		TreeMap<String,Integer> emissionByType = inputLists.nbEmissionByType;
		Set set = emissionByType.entrySet();
		//PARCOURS
	    Iterator iterator = set.iterator();
	    while(iterator.hasNext()) {
	         Map.Entry<String,Integer> mapentry = (Map.Entry<String,Integer>)iterator.next();
	         System.out.print(mapentry.getKey() + " - [" +mapentry.getValue()+"]\n");
	    }
	}
	
	/**
	 * Donne la programmation d'une chaine (ignore la date)
	 * @param param Nom de la chaine concern�e
	 */
	private static void getProgChannel(String param)
	{
		for(Channel c : inputLists.channelsList)
			if(c.getName().equals(param))
				System.out.println("\n-- Programmation de " + c.getName() +"\n" + c.printAllPrograms());	
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
			System.out.println("Le "+ pers +" n'est pas connue du syst�me.");

	}
	
	/**
	 * Affiche la fiche de l'�mission pour la personne donn�e et l'id pr�cis�.
	 * @param pers actor ou director
	 * @param id de l'�mission pour cette personne
	 * @param param Nom de la personne.
	 */
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
				System.out.println("Le "+ pers +" n'est pas connue du syst�me.");
		}
		catch (NumberFormatException e) {logMsg("INFO", "ID incorrect.");}
	
	}
	
	/**
	 * Affiche la liste des personnes connues par le syst�me.
	 * @param pers "actor" or "director"
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
	

	/**
	 * Search emission with specifics words
	 * @param words contains all words
	 */
	private static void search(String words)
	{
		String [] wordsSplit = words.split(" ");
		
		 HashMap<String, ArrayList<Emission>> searchlist = inputLists.DictionnaryList;
		 ArrayList<Emission> correspondEmission = new ArrayList<Emission>();
		 
		 //Pour tout les mots pass� en param�tre
		 for(int i=0;i < wordsSplit.length;i++)
		 {
			 //On controle leur existence dans notre dictionnaire
			if(searchlist.containsKey(wordsSplit[i].toLowerCase()) && !wordsSplit[i].toLowerCase().equals(""))
			{
				//On display les emissions concern�es
				for(int j=0; j <  searchlist.get(wordsSplit[i].toLowerCase()).size() ; j++)
				{
					System.out.println(searchlist.get(wordsSplit[i].toLowerCase()).get(j).display());
				}
			}
			
		 }		 
	}
	
	/**
	 * Affiche les �missions en cours � la date donn�e.
	 * @param d Date de r�f�rence.
	 */
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
	 * Affiche un message de log.
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
	 * @return Date vide si parametre erron�e sinon la date associ�e.
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
				return sdf.parse(str_date);
		}		
		else
			return sdf.parse("00/00/0000");
	}
}
