package tonyInterfaces;

public class Person {

	
	private String name, gender, address, domain, vehicle;
	
	public Person(String name, String gender, String address, String domain, String vehicle){
		this.name = name;
		this.gender = gender;
		this.address = address;
		this.domain = domain;
		this.vehicle = vehicle;
	}
	
	public String getVehicle(){
		return this.vehicle;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String toString () {
		return name + " is a " + gender + " lives at " + address + " from domain: " + domain;
	}
	
	
	public String getDomain(){
		return this.domain;
	}
	
	public String getAddress(){
		return this.address;
	}
}
