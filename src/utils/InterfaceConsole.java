package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterfaceConsole {
	
	// static liste
	private static boolean run = false;
	
	public static void initConsole() throws IOException //Passé liste en paramètre
	{
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		run = true;
		
		
		while(run == true)
		{
			System.out.println("===================== \nEntrez une commande \n=====================" );
			String s = buf.readLine();
			run = readMessage(s);
		}

		
	}
	
	public static boolean readMessage(String command)
	{
		switch (command) {
		case "help":
			System.out.println("Liste des commandes non disponible.");
			return true;
			
		case "test" : 
			System.out.println("1...2...3... Test : Ok !");
			return true;

		case "exit" : 
			System.out.println("Fermeture de l'application. Merci de votre utilisation.");
			return false;
		
		default:
			System.out.println("Commande inconnue.");
			return true;

		}
		
	}
	
	private static void getListChannels()
	{
		
		
	}
	
	

}
