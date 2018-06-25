package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	public void setPath(String path)
	{
		this.pathToXML = path;
	}
	
	public String getPath()
	{
		return this.pathToXML;
	}
	
	
	public Parser()
	{
	}
	public Parser(String pathToXML)
	{
		this.pathToXML = pathToXML;
	}
	
	//CLASSIC METHODS
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

	    try {
			while (xmlsr.hasNext()) 
			{
				eventType = xmlsr.next();
				
				switch (eventType) 
				{
				  	case XMLEvent.START_ELEMENT:
				  		
				  		type = xmlsr.getLocalName();

				  		//DETECT CHANNEL
				  		if(type.equals("channel"))
				  		{
				  			
				  			lists.nbOfChannels++;
				  			actualChannel = new Channel();
				  			
				  			id = xmlsr.getAttributeValue(null, "id");
				            actualChannel.setId(id);

				            xmlsr.next();
				            boolean continu = true;
				            
				            //si c'est une channel on récupère le display-name et le lien vers l'icon
				            while(continu) 
				            {
				            	if(xmlsr.isEndElement())
				            	{
				            		if(xmlsr.getLocalName().equals("channel")) 
				            		{
				            			continu = false;
				            		}
				            	}
				            	
				            	xmlsr.next();
				            	if (xmlsr.isStartElement())
				            	{

				            		if(xmlsr.getLocalName().equals("display-name"))
					            	{
				            			
					            		xmlsr.next();
					            		actualChannel.setName(xmlsr.getText());
					            	}
				            		
					            	else if (xmlsr.getLocalName().equals("icon"))
					            	{
					            		actualChannel.pathToIcon = xmlsr.getAttributeValue(null, "src");

					            	}
				            	}
				            	
				            }

				            lists.channelsList.add(actualChannel);
				            lists.channedID.put(actualChannel.getId(), lists.channelsList.indexOf(actualChannel));
				  		}
				  		
				  		//PROGRAMME
				  		if(type.equals("programme"))
				  		{
				  			//exemple date 20180430062500 +0200
				  			//			  "AAAAMMddHHmmss Z"
				  			
				  			try {
				  				dateString = xmlsr.getAttributeValue(null, "start");
								date_begin = sdf.parse(dateString.substring(0,13));
								
								dateString = xmlsr.getAttributeValue(null, "stop");
								date_end = sdf.parse(dateString.substring(0,13));
								
								
							} catch (ParseException e) {
								e.printStackTrace();
							}
				  			channelID = xmlsr.getAttributeValue(null, "channel");
				  			
				  			

				  			xmlsr.next();
				            boolean continu = true;
				            boolean stillCredits = true;
				            
				            //si c'est une channel on récupère le display-name et le lien vers l'icon
				            while(continu) 
				            {
				            	if(xmlsr.isEndElement())
				            	{
				            		if(xmlsr.getLocalName().equals("programme")) 
				            		{
				            			actualEmission = new Emission(date_begin, date_end,channelID,title,desc,length,country,typeOfEmission,unitOfLength,pathToIcon,actors,directors);
				            			addEmissionValuesToLists(actualEmission,lists);

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
				            		if(xmlsr.getLocalName().equals("title"))
					            	{
					            		xmlsr.next();
					            		title = xmlsr.getText();
					            	}
					            	else if (xmlsr.getLocalName().equals("desc"))
					            	{
					            		xmlsr.next();
					            		desc = xmlsr.getText();
					            	}
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
						            				actors.add(new Personne(xmlsr.getText(),jobActor));
						            				
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
					            	else if (xmlsr.getLocalName().equals("category"))
					            	{
					            		xmlsr.next();
					            		String[] catTab;
					            		catTab = xmlsr.getText().split(" ");
					            		typeOfEmission = catTab[0];
					            	}
					            	else if (xmlsr.getLocalName().equals("length"))
					            	{
					            		unitOfLength = xmlsr.getAttributeValue(null, "units");
					            		xmlsr.next();
					            		length = Integer.parseInt(xmlsr.getText());
					            	}
					            	else if (xmlsr.getLocalName().equals("icon"))
					            	{
					            		pathToIcon = xmlsr.getAttributeValue(null, "src");
					            	}
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
	    
	    return true;
	    
	    
	    
	}
	
	@SuppressWarnings("deprecation")
	private void addEmissionValuesToLists(Emission emissionToAdd, Lists lists)
	{
		// ajout de l'emission dans la hashmap des dates dans les channels
		//Start Date
		
		Date dateStart = emissionToAdd.getDateStart();

		Date dateEnd = emissionToAdd.getDateEnd();
		
		
		Date dateAtZero = new Date(dateStart.getTime());
		dateAtZero.setHours(0);
		dateAtZero.setMinutes(0);
		dateAtZero.setSeconds(0);
		
		//ajout liste des dates globales
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
