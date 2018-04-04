package tonyInterfaces;

import java.util.Vector;

public class Domains {
	
	private Vector<String> domains = new Vector<String>();
	
	//Sets up the domains that properties from this class would be part of
	public Domains(){
		
		String kdir = "C:\\Users\\Pekun\\OneDrive - University College Dublin\\School Stuff\\Stage 3\\The-NOC-List-master\\NOC\\DATA\\TSV Lists\\";
		KnowledgeBaseModule d = new KnowledgeBaseModule(kdir + "Veale's domains.txt");
		KnowledgeBaseModule n          = new KnowledgeBaseModule(kdir + "Veale's The NOC List.txt");
		
		//Go through NOC List and populate the domains arraylist
		Vector<String> fields = d.getAllFrames();//prints first column of file (possibly)

		while(domains.size()<8){
			
			String potential_domain ;
			potential_domain = d.selectRandomlyFrom(fields);
			//Need to select domain with enough characters to make properties from
			if(!domains.contains(potential_domain) && n.getAllKeysWithFieldValue("Domains", potential_domain).size() > 3){
				domains.addElement(potential_domain);
			}
			
		}
	}
	
	
	
	public Vector<String> getDomains(){
		return this.domains;
	}
	
	public String getDomain(int i){
		return domains.elementAt(i);
	}
	
	public String toString () {
		return domains.toString();
	}
	
	public static void main(String[] args)
	{
		Domains d = new Domains();
		System.out.println(d);
	}
	
}
