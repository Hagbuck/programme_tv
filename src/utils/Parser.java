package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

import prog.Channel;
import prog.Emission;
import prog.Job;
import prog.Personne;


public class Parser {
	private String pathToXML;
	
	
	//GETTER SETTERS
	/**
	 * Permet de définir le répertoire où est stocké le fichier xml
	 * @param path : répertoire où le fichier xml est stocké
	 */
	public void setPath(String path)
	{
		this.pathToXML = path;
	}
	
	/**
	 * Permet de récupérer le répertoire où est stocké le fichier xml
	 *
	 */
	public String getPath()
	{
		return this.pathToXML;
	}
	
	// CONSTRUCTOR
	public Parser()
	{
		
	}
	public Parser(String pathToXML)
	{
		this.pathToXML = pathToXML;
	}
	
	//CLASSIC METHODS
	
	/**
	 * Parcours le fichier XML définit au préalable puis rempli les listes de l'objet "Lists" donné en paramètre
	 * 
	 * @param lists : objet "Lists" contenant un ensemble de listes pour les différentes fonctions du programme
	 * @return : retourne true si le parsage s'est effectué correctement (lecture du fichier, parsage des dates)
	 */
	public boolean parse(Lists lists)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		
		
		
	    XMLStreamReader xmlsr;
		try {
			xmlsr = xmlif.createXMLStreamReader(new FileInputStream(this.pathToXML));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (XMLStreamException e) {
			e.printStackTrace();
			return false;
		}

	    
	    int eventType;
	    String type;
	    
	    //for channel
	    String id;
	    Channel actualChannel = new Channel();
	    
	    //for Emission
	    String dateString = "";
	    Date date_begin = new Date();
	    Date date_end = new Date();
	    String channelID;
	    
	    String title = "";
	    String desc = "";
	    
	    int length = 0;
	    String unitOfLength = "";

	    
		String country = "";
		String typeOfEmission ="";
		String pathToIcon= "";
		
		ArrayList<Personne> actors = new ArrayList<Personne>();
		ArrayList<Personne> directors = new ArrayList<Personne>();
		
	    Emission actualEmission = new Emission();

	    //demarrage lecture du XML
	    try {
			while (xmlsr.hasNext()) 
			{
				eventType = xmlsr.next();
				
				switch (eventType) 
				{
					
				  	case XMLEvent.START_ELEMENT:
				  		
				  		type = xmlsr.getLocalName();

				  		// ---- CHANNEL ----
				  		if(type.equals("channel"))
				  		{
				  			
				  			lists.nbOfChannels++;
				  			actualChannel = new Channel();
				  			
				  			id = xmlsr.getAttributeValue(null, "id");
				            actualChannel.setId(id);

				            xmlsr.next();
				            boolean continu = true;
				            
				            while(continu) 
				            {
				            	if(xmlsr.isEndElement())
				            	{
				            		//fin des descriptions
				            		if(xmlsr.getLocalName().equals("channel")) 
				            		{
				            			continu = false;
				            		}
				            	}
				            	
				            	xmlsr.next();
				            	
				            	if (xmlsr.isStartElement())
				            	{
				            		//Nom de la chaine
				            		if(xmlsr.getLocalName().equals("display-name"))
					            	{
				            			
					            		xmlsr.next();
					            		actualChannel.setName(xmlsr.getText());
					            	}
				            		
				            		//lien vers l'icone de la chaine
					            	else if (xmlsr.getLocalName().equals("icon"))
					            	{
					            		actualChannel.pathToIcon = xmlsr.getAttributeValue(null, "src");

					            	}
				            	}
				            	
				            }
				            
				            //Ajout de la chaine dans l'arrayList et dans l'hashMap (Name / Int)  
				            lists.channelsList.add(actualChannel);
				            lists.channedID.put(actualChannel.getId(), lists.channelsList.indexOf(actualChannel));
				  		}
				  		
				  		// ---- PROGRAMME ----
				  		if(type.equals("programme"))
				  		{
				  			try {
				  				dateString = xmlsr.getAttributeValue(null, "start");
								date_begin = sdf.parse(dateString.substring(0,13));
								
								dateString = xmlsr.getAttributeValue(null, "stop");
								date_end = sdf.parse(dateString.substring(0,13));
								
								
							} catch (ParseException e) {
								e.printStackTrace();
							}
				  			channelID = xmlsr.getAttributeValue(null, "channel");
				  			
				  			String chanName = lists.channelsList.get(lists.channedID.get(channelID)).getName();
				  			

				  			xmlsr.next();
				            boolean continu = true;
				            boolean stillCredits = true;
				            
				            while(continu) 
				            {
				            	if(xmlsr.isEndElement())
				            	{
				            		//Si arrivé à la fin de l'émission
				            		if(xmlsr.getLocalName().equals("programme")) 
				            		{
				            			actualEmission = new Emission(date_begin, date_end,channelID,chanName,title,desc,length,country,typeOfEmission,unitOfLength,pathToIcon,actors,directors);
				            			addEmissionValuesToLists(actualEmission,lists);

				            			//reinitialisation des valeurs de l'emission courante
				            			continu = false;
				            			dateString = "";
				            			date_begin = null;
				            			date_end = null;
				            			channelID = "";
				            			title ="";
				            			desc = "";
				            			length = 0;
				            			unitOfLength = "";
				            			country = "" ;
				            			typeOfEmission ="";
				            			pathToIcon ="";
				            			actors.clear();
				            			directors.clear();
				            			
				            		}
				            	}
				            	
				            	xmlsr.next();
				            	
				            	if (xmlsr.isStartElement())
				            	{
				            		//titre
				            		if(xmlsr.getLocalName().equals("title"))
					            	{
					            		xmlsr.next();
					            		title = xmlsr.getText();
					            	}
				            		
				            		//description
					            	else if (xmlsr.getLocalName().equals("desc"))
					            	{
					            		xmlsr.next();
					            		while(!xmlsr.isEndElement())
					            		{
					            			
					            			desc += xmlsr.getText();
					            			xmlsr.next();
					            			
					            		}
					            		
					            	}
				            		
				            		//casting
					            	else if (xmlsr.getLocalName().equals("credits"))
					            	{
					            		while(stillCredits)
					            		{
					            			xmlsr.next();
					            			if(xmlsr.isEndElement())
							            	{
							            		if(xmlsr.getLocalName().equals("credits")) 
							            		{
							            			stillCredits = false;
							            		}
							            	}
					            			if (xmlsr.isStartElement())
							            	{
						            			if(xmlsr.getLocalName().equals("actor"))
						            			{
						            				xmlsr.next();
						            				ArrayList<Job> jobActor = new ArrayList<Job>();
						            				jobActor.add(Job.actor);
						            				String[] justName = xmlsr.getText().split("\\s\\(");
						            				actors.add(new Personne(justName[0],jobActor));
						            				
						            			}
						            			else if (xmlsr.getLocalName().equals("director"))
						            			{
						            				xmlsr.next();
						            				ArrayList<Job> jobDirector = new ArrayList<Job>();
						            				jobDirector.add(Job.director);
						            				directors.add(new Personne(xmlsr.getText(),jobDirector));

						            			}
							            	}
					            		}
					            		stillCredits = true;
					            	}
				            		
				            		//catégorie
					            	else if (xmlsr.getLocalName().equals("category"))
					            	{
					            		xmlsr.next();
					            		String[] catTab;
					            		catTab = xmlsr.getText().split(" ");
					            		typeOfEmission = catTab[0];
					            	}
				            		
				            		//duree
					            	else if (xmlsr.getLocalName().equals("length"))
					            	{
					            		unitOfLength = xmlsr.getAttributeValue(null, "units");
					            		xmlsr.next();
					            		length = Integer.parseInt(xmlsr.getText());
					            		
					            		if(unitOfLength.equals("minutes") && length > lists.length_max)
					            			lists.length_max = length;
					            		else if(unitOfLength.equals("hours") && length * 60 > lists.length_max )
					            			lists.length_max = length * 60;
					            	}
				            		
				            		// icone pour l'emission
					            	else if (xmlsr.getLocalName().equals("icon"))
					            	{
					            		pathToIcon = xmlsr.getAttributeValue(null, "src");
					            	}
				            		
				            		//Pays de l'emission
					            	else if (xmlsr.getLocalName().equals("country"))
					            	{
					            		xmlsr.next();
					            		country = xmlsr.getText();
					            	}

				            	}
				            	
				            }
				            
				  		}
				  		break;
				  		
				  	default:
				  		break;
				}
			}
		} catch (XMLStreamException e) {
			e.printStackTrace();
			return false;
		}
	    
	    lists.triNbEmissionByType();
	    
	    	
	    Iterator<String> i = lists.listOfActors.keySet().iterator();
	    String k ="";
	    while(i.hasNext())
	    {
	    	k = i.next();
	    	lists.nbEmissionByActor.put(lists.listOfActors.get(k).getName(), lists.listOfActors.get(k).getNbPlayedEmission());
	    }
	    lists.triNbEmissionByPersonne();
	    
	    i = lists.listOfDirectors.keySet().iterator();
	    k ="";
	    while(i.hasNext())
	    {
	    	k = i.next();
	    	lists.nbEmissionByDirector.put(lists.listOfDirectors.get(k).getName(), lists.listOfDirectors.get(k).getNbPlayedEmission());
	    }
	    
	    lists.triNbEmissionByDirector();
	    
	    return true;
	    
	    
	    
	}
	
	/**
	 * Remplissage des listes à la fin du parsage d'une emission
	 * @param emissionToAdd : emission contenant les données à ajouter
	 * @param lists :  objet "Lists" contenant un ensemble de listes pour les différentes fonctions du programme
	 */
	
	@SuppressWarnings("deprecation")
	private void addEmissionValuesToLists(Emission emissionToAdd, Lists lists)
	{
		//Date de début de l'émission
		Date dateStart = emissionToAdd.getDateStart();
		//Date de fin de l'émission
		Date dateEnd = emissionToAdd.getDateEnd();
		
		//Pour ne garder que le jour (mise à 0 des heures,minutes,secondes)
		Date dateAtZero = new Date(dateStart.getTime());
		dateAtZero.setHours(0);
		dateAtZero.setMinutes(0);
		dateAtZero.setSeconds(0);
		
		//ajout liste des dates de programmation
		if(!lists.daysOfPrograms.contains(dateAtZero)) 
		{
			lists.daysOfPrograms.add(dateAtZero);
		}
		

		//ajout dans channel de l'emission a la date begin
		int indexOfChannel = lists.channedID.get(emissionToAdd.getChannelID());
		lists.channelsList.get(indexOfChannel).addEmission(dateAtZero, emissionToAdd);
		

		//End Date
		
		dateAtZero = new Date(dateEnd.getTime());
		dateAtZero.setHours(0);
		dateAtZero.setMinutes(0);
		dateAtZero.setSeconds(0);
		
		//ajout liste des dates de programmation
		if(!lists.daysOfPrograms.contains(dateAtZero)) 
		{
			lists.daysOfPrograms.add(dateAtZero);
		}
		
		
		//ajout dans channel de l'emission a la date end
		lists.channelsList.get(indexOfChannel).addEmission(dateAtZero, emissionToAdd);
		
		
		
		// ajout de l'emission dans treemap debut
		lists.emissionBegin.put(dateStart, emissionToAdd);
		
		
		// ajout de l'emission dans treemap fin
		lists.emissionEnd.put(dateEnd, emissionToAdd);
		
		// ajouts actors
		if(!emissionToAdd.getActors().isEmpty())
		{
			for (Personne p : emissionToAdd.getActors())
			{
				if(!lists.listOfActors.containsKey(p.getName()))
				{
					lists.listOfActors.put(p.getName(), p);
				}
				lists.listOfActors.get(p.getName()).addEmission(emissionToAdd);
			}
		}
		
		
		
		// ajout directors
		if(!emissionToAdd.getDirectors().isEmpty())
		{
			for (Personne p : emissionToAdd.getDirectors())
			{
				if(lists.listOfDirectors.containsKey(p.getName()) == false)
				{
					lists.listOfDirectors.put(p.getName(), p);
				}
				lists.listOfDirectors.get(p.getName()).addEmission(emissionToAdd);
			}	
		}
		
		

		
		// ajout type a la treemap type
		if(!lists.nbEmissionByType.containsKey(emissionToAdd.getType()))
		{
			lists.nbEmissionByType.put(emissionToAdd.getType(), 1);
		}
		else {
			lists.nbEmissionByType.put(emissionToAdd.getType(), lists.nbEmissionByType.get(emissionToAdd.getType()) +1 );
		}
		
		// ajout de mots clés dans hashmap dictionnaire
		String[] allDesc = emissionToAdd.getDesc().split("['.,:-]|\\s");
		String[] badWords = {"le","la","les","sur","une","un","de","des","du","par","au","a","et","l","n","s","d","ses","se","sa","c","il","elle","elles","ils"};
		boolean badWord = false;
		for (String s : allDesc)
		{
			s = s.toLowerCase();
			for(String bw : badWords)
			{
				if(bw.equalsIgnoreCase(s))
				{
					badWord = true;
					break;
				}
			}
			if(!badWord)
			{
				if(lists.DictionnaryList.get(s) == null)
				{
					lists.DictionnaryList.put(s,new ArrayList<Emission>());
				}
				if(!lists.DictionnaryList.get(s).contains(emissionToAdd))
				{
					lists.DictionnaryList.get(s).add(emissionToAdd);
				}
			}
			badWord = false;	
		}	
	}
}
