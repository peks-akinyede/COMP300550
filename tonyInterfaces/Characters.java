package tonyInterfaces;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Characters {
	
	private ArrayList<Person> characters = new ArrayList<Person>();
	private Vector<String> vehicles = new Vector<String>();
	
	
	public Characters(Vector<String> domains){
		
		KnowledgeBaseModule n          = new KnowledgeBaseModule( "Veale's The NOC List.txt");
		
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
	
	public Person getRandomPersonWithVehicle() {
		Random RND 		= new Random();
		Person person = characters.get(RND.nextInt(characters.size()));
		int count = 0;
		
		
		while(person.getVehicle()==null && count < 5){
			person = characters.get(RND.nextInt(characters.size()));
			count++;
		}
		
		// TODO Auto-generated method stub
		return person;
	}
	
	public Vector<String> getRandomLocationsFromDomain(String domain, int number, Vector<String> all_locations){
		Vector<String> locations = new Vector<String>();
		
		int i=0;
		int count = 0;
		boolean here = false;
		do{
			
			Person person = getRandomPersonFromDomain(domain);
			
			if(person.getAddress() != null && !locations.contains(person.getAddress()) && !all_locations.contains(person.getAddress())){
				locations.add(person.getAddress());
				i++;
				
			}
			

			
			//To account for when there aren't enough unique addresses
			if(count > 8){
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
		
		
		if(locations.size()<number){
			Person person = getRandomPersonFromDomain(domain);
			
			String current_person = "";
			for(int j = 0; j <= number - locations.size(); j++){
				current_person = person.getName();
				locations.add(person.getName()+ "'s House" );
				do{
					person = getRandomPersonFromDomain(domain);
				}while(person.getName() == current_person);
			}
		}
		
		
		
		return locations;
	}
	
	
	public String toString () {
		return characters.toString();
	}
	

	public String getRandomVehicle() {
		String vehicle = "";
		Random RND 		= new Random();
		Person person = characters.get(RND.nextInt(characters.size()));
		vehicle = person.getVehicle();
		int count = 0;
		
		
		while(vehicle==null && count < 5){
			person = characters.get(RND.nextInt(characters.size()));
			vehicle = person.getVehicle();
			count++;
		}
		
		
		
		if(vehicles.contains(vehicle)){
			vehicle = person.getName() + "'s Car";
		}
		
		if(vehicles.contains(vehicle)){
			vehicle = person.getName() + "'s Mom's Car";
		}
		
		if(vehicles.contains(vehicle)){
			vehicle = person.getName() + "'s Dad's Car";
		}
		
		
		if(!vehicles.contains(vehicle)){
			vehicles.add(vehicle);
		}
		
		// TODO Auto-generated method stub
		
		return vehicle;
	}

}
