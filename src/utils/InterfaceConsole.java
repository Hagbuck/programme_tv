package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
		
		switch (labelCommand) {
		case "help":
			System.out.println("Liste des commandes non disponible.");
			return true;
			
		case "test" : 
			System.out.println("1...2...3... Test : Ok !");
			return true;

		case "1" :
		case "chan" :
			System.out.println("============================================");
			
			if(splitCommand.length == 1)
				getListChannels();
			
			/*
			else if(splitCommand.length == 3)
				int i = 1;
			*/
			
			System.out.println("============================================");
			return true;
			
		case "2" :
		case "when":
			System.out.println("============================================");
			getDaysWithProgramation();
			System.out.println("============================================");
			return true;
			
		case "3" :
		case "what":
			System.out.println("============================================");
			
			//Sasie & controle de la date
			System.out.println("===> Entrez une date (MM/JJ/AAAA) ");
			String str_date = buf.readLine();
			Date temp = parseDate(str_date);
			
			//Si la date n'est pas valide
			if(temp.equals(new Date(0,0,0)))
				logMsg("ERROR", "Date entrée non valide. Syntax : MM/JJ/AAAA");
			else
				getdailyProgramation(new Date());
			
			//Fin
			System.out.println("============================================");
			return true;
			
		case "exit" : 
			System.out.println("Fermeture de l'application. Merci de votre utilisation.");
			return false;
			
		case "chan_by_type" : 
			System.out.println("============================================");
			getNbEmissionByType();
			System.out.println("============================================");
			return true;
		
		default:
			System.out.println("Commande inconnue.");
			return true;

		}
		
	}
	
	private static void getListChannels()
	{

		HashMap<String,Channel> chanList = inputLists.channelsList;
		
		//Liste Vide
		if(chanList.size() == 0)
			logMsg("INFO", "Aucune chaîne n'est connue par le systême.");
		
		//Au - un élément
		else
		{
			Iterator it = chanList.keySet().iterator();
			int i = 0;
			while(it.hasNext())
			{
				String key = (String) it.next();
				System.out.println("["+i+"]" + chanList.get(key).toString());
				i++;
			}			
		}
	}
	
	private static void getdailyProgramation(Date date)
	{
		HashMap<Date, ArrayList<Emission>> dayWithProgramation = inputLists.programOfADay;
		//Date existe dans la liste
		if(dayWithProgramation.containsKey(date))
		{
			for(int i=0;i<dayWithProgramation.get(date).size();i++)
				System.out.println(dayWithProgramation.get(date).get(i).toString());
		}
		//Liste Vide ou pas de programme pour le jour donné.
		else
			logMsg("INFO","Aucune programmation pour le jour donné.");		
	}
	
	private static void getDaysWithProgramation()
	{
		HashMap<Date, ArrayList<Emission>> dayWithProgramation = inputLists.programOfADay;
		
		//Si liste vide
		if(dayWithProgramation.size() == 0)
			logMsg("INFO", "Pas de date avec une programmation connue par le systême.");
			
		else
		{
			Iterator it = dayWithProgramation.keySet().iterator();
			while(it.hasNext())
			{
				Date key = (Date) it.next();
				System.out.println(key.toString());
			}			
		}
	}
	
	
	private static void logMsg(String type,String str)
	{
		System.out.println("["+type+"] - "+str);
	}
	
	private static Date parseDate(String str_date)
	{
		String [] splitDate = str_date.split("/");
		
		if(splitDate.length == 3 )
		{
			int mois = Integer.parseInt(splitDate[0]);
			int jour = Integer.parseInt(splitDate[1]);
			int an = Integer.parseInt(splitDate[2]);
			
			if(mois < 0 || mois > 12)
				return new Date(0,0,0);
			else if(jour < 0 || jour > 31)
				return new Date(0,0,0);
			else if(splitDate[2].length() != 4)
				return new Date(0,0,0);
			else
				return new Date(an,mois,jour);
			
		}
		
		else
			return new Date(0,0,0);
	}
	

	private static void getNbEmissionByType()
	{
		TreeMap<String,Integer> emissionByType = inputLists.nbEmissionByType;
		Set<String> keys = emissionByType.keySet();
		for(String key : keys)
			System.out.println(emissionByType.get(key).toString() + "- ["+key.toString()+"]");
	}
}
