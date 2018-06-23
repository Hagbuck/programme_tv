package prog;

import java.util.ArrayList;
import java.util.HashMap;

public class Personne {

	private String fullName;
	private ArrayList<Job> jobs;
	private ArrayList<Emission> playedEmission;
	
	
	public Personne(String p_fname,ArrayList<Job> p_jobs)
	{
		fullName = p_fname;
		jobs = p_jobs;
		playedEmission = new ArrayList<Emission>();
	}
	
	public void addEmission(Emission em)
	{
		playedEmission.add(em);
	}
	
	public String getName()
	{
		return this.fullName;
	}
	public ArrayList<Job> getJobs()
	{
		return this.jobs;
	}
	public int getNbPlayedEmission() {return playedEmission.size();}
} 
