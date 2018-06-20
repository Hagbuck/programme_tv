package prog;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class Parser {
	private String pathToXML;
	public int nbOfChannels;
	
	
	private HashMap<Integer, Channel> ChannelsList;
	private HashMap<String, ArrayList<Emission>> DictionnaryList;
	private HashMap<Date, ArrayList<Emission>> ProgramOfADay;
	
	private ArrayList<Date> DaysOfPrograms;
	
	private TreeMap nbEmissionByType;
	
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
		this.nbOfChannels = 0;
	}
	
	//CLASSIC METHODS
	public boolean parse()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		XMLInputFactory xmlif = XMLInputFactory.newInstance();
		
	    XMLStreamReader xmlsr;
		try {
			xmlsr = xmlif.createXMLStreamReader(new FileReader(this.pathToXML));
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

				  		if(type.equals("channel"))
				  		{
				  			
				  			nbOfChannels++;
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
				            //System.out.println(actualChannel.toString() + " --- source "+ actualChannel.pathToIcon);

				  		}
				  		
				  		if(type.equals("programme"))
				  		{
				  			//exemple date 20180430062500 +0200
				  			//			  "AAAAMMddHHmmss Z"
				  			
				  			//start date / end date / channelID
				  			try {
				  				dateString = xmlsr.getAttributeValue(null, "start");
								date_begin = sdf.parse(dateString.substring(0,13));
								//TimeZone.getTimeZone("GMT " + dateString.substring(14));
								
								dateString = xmlsr.getAttributeValue(null, "stop");
								date_end = sdf.parse(dateString.substring(0,13));
								
								
							} catch (ParseException e) {
								e.printStackTrace();
							}
				  			channelID = xmlsr.getAttributeValue(null, "channel");
				  			
				  			
				  			//title
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
				            			
				            			//System.out.println("---- PROGRAM ----\n");
				            			/*
							  			System.out.println( 
							  					  "\n" + date_begin.toString()
							  					+ "\n" + date_end.toString()
							  					+ "\n" + channelID
							  					+ "\n" + title
							  					+ "\n" + desc
							  					+ "\n" + length
							  					+ "\n" + unitOfLength
							  					+ "\n" + country
							  					+ "\n" + typeOfEmission
							  					+ "\n" + pathToIcon
							  					+ "\n" + actors.toString()
							  					+ "\n" + directors.toString()
							  			);
							  			*/
							  			actualEmission = new Emission(date_begin, date_end,channelID,title,desc,length,country,typeOfEmission);
				            			
				            			
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

				            		/*
				            		private ArrayList<Personne> actors;
				            		private ArrayList<Personne> director;
				            		*/
				            		
				            		
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
						            				//System.out.println("un acteur : " + xmlsr.getText() );
						            				//actors.add(Personne(xmlsr.getText(),Job.actor));
						            				//TODO
						            			}
						            			else if (xmlsr.getLocalName().equals("director"))
						            			{
						            				xmlsr.next();
						            				//System.out.println("un directeur : " + xmlsr.getText() );
						            				//directors.add(new Personne(xmlsr.getText(),Job.actor));
						            				//TODO
						            			}
							            	}
					            		}
					            		stillCredits = true;
					            	}
					            	else if (xmlsr.getLocalName().equals("category"))
					            	{
					            		xmlsr.next();
					            		typeOfEmission = xmlsr.getText();
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
				  		
				  		
				  	case XMLEvent.END_ELEMENT:
				  		type = xmlsr.getLocalName();
				  		if(type.equals("channel"))
				  		{	
				  			//System.out.println("Fin Channel");
				  			//actualChannel.toString();
				  			//ChannelsList.put(nbOfChannels, actualChannel);
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
	    return true;
	    
	}
}
