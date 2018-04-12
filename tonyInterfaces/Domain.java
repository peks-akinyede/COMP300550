package tonyInterfaces;

import java.util.ArrayList;

public class Domain {
	
	private ArrayList<InvestmentProperty> sites = new ArrayList<InvestmentProperty>();
	private String name;
	
	Domain( String name){
		this.name = name;
	}
	
	public void addMember (InvestmentProperty site) {
		sites.add(site);
		return;
	}
	
	public ArrayList<InvestmentProperty> getMembers () {
		return sites;
	}
	
	public String getName () {
		return name;
	}
	
	public int size () {
		return sites.size();
	}

}
