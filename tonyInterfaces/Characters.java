package tonyInterfaces;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Characters {
	
	private ArrayList<Person> characters = new ArrayList<Person>();
	
	
	public Characters(Vector<String> domains){
		
		
		//String kdir =  "/Users/abdulsalim/Desktop/mydesktop/The-NOC-List-master/NOC/DATA/TSV Lists/";
		String kdir =  "C:\\Users\\robert\\Documents\\.Year 3_Part2\\software eng3\\noc\\TSV Lists\\";
		KnowledgeBaseModule n          = new KnowledgeBaseModule(kdir + "Veale's The NOC List.txt");
		
		//Go through domains and populate characters
		for(String domain : domains){
			
			Vector<String> characters_in_domain = n.getAllKeysWithFieldValue("Domains", domain);


			for(String person : characters_in_domain){
				String gender, address = null, vehicle;
				gender = n.getFirstValue("Gender", person);
				vehicle = n.getFirstValue("Vehicle of Choice", person);
				int i = 1;
				while(address == null && i < 4){
					address = n.getFirstValue("Address " + i, person);

					i++;
				}
				characters.add(new Person(person, gender, address, domain, vehicle));
			}
		}
		
		
	}
	
	
	public Person getCharacter(String name){
		
		for(Person person : characters){
			if(person.getName() == name){
				return person;
			}
		}
		
		return null;
	}
	
	
	public Person getRandomPersonFromDomain(String domain){
		Random RND 		= new Random();
		
		ArrayList<Person> domain_characters = new ArrayList<Person>();
		Person chosen_person;
		
		for(Person person : characters){
			if(person.getDomain() == domain){
				domain_characters.add(person);
			}
		}
		
		
		
		chosen_person = domain_characters.get(RND.nextInt(domain_characters.size()));

		
		return chosen_person;
	}
	
	public Vector<String> getRandomLocationsFromDomain(String domain, int number){
		Vector<String> locations = new Vector<String>();
		
		int i=0;
		int count = 0;
		boolean here = false;
		do{
			
			Person person = getRandomPersonFromDomain(domain);
			
			if(person.getAddress() != null && !locations.contains(person.getAddress())){
				locations.add(person.getAddress());
				i++;
				
			}
			//System.out.println("here");

			
			//To account for when there aren't enough unique addresses
			if(count > 5){
				String current_person = "";
				for(int j = 0; j <= number - locations.size(); j++){
					current_person = person.getName();
					locations.add(person.getName()+ "'s House" );
					do{
						person = getRandomPersonFromDomain(domain);
					}while(person.getName() == current_person);
				}
				here = true;
			}
			
			count++;
			
		}while(i<number && !here);
		
		
		
		return locations;
	}
	
	
	public String toString () {
		return characters.toString();
	}
	
	public static void main(String[] args)
	{
		Domains d = new Domains();
		Characters c = new Characters(d.getDomains());
		System.out.println(d);
		System.out.println(c);
	}

}
