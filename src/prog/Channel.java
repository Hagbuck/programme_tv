package prog;

public class Channel {
	
	private String id;
	private String name;
	public String pathToIcon;
	
	public Channel(String p_id,String p_name,String p_path)
	{
		this.id = p_id;
		this.name = p_name;
		this.pathToIcon = p_path;
	}
	
	public String toString()
	{
		return "[CHANNEL] - "+name+" ("+id+")";
	}

	/******* GETER & SETER ******/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
