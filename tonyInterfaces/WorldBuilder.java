package tonyInterfaces;



import java.util.Vector;

public class WorldBuilder {

	public static final int NUM_SQUARES = 40;
	
	private Domains domains = new Domains();
	private Characters characters = new Characters(domains.getDomains());
	private NamedLocation[] squares = new NamedLocation[NUM_SQUARES];

	
	
	
	public WorldBuilder(){
		//The default squares

		
		System.out.println("bob");
		
		String domain = domains.getDomain(0);
		Vector<String> locations = characters.getRandomLocationsFromDomain(domain, 2);//The first color group has 2 properties
		
		squares[0] = new NamedLocation("Go", null, null);
		squares[1] = new InvestmentProperty(locations.elementAt(0), 60, 50, null, squares[0]);
		squares[2] = new Card("Community", null, squares[1]);		
		squares[3] = new InvestmentProperty(locations.elementAt(1), 60, 50, null, squares[2]);
		squares[4] = new TaxableProperty("Income Tax", null, squares[3], 20, 200);
		squares[5] = new RentalProperty(characters.getRandomPersonFromDomain(domain).getVehicle(), 200, 100, null, squares[4]);
		
		
		
		
		domain = domains.getDomain(1);
		locations = characters.getRandomLocationsFromDomain(domain, 3);//Subsequent color groups have 3 properties
		squares[6] = new InvestmentProperty(locations.elementAt(0), 100, 50, null, squares[5]);
		squares[7] = new Card("Chance", null, null);
		squares[7].setRight(squares[6]);
		squares[8] = new InvestmentProperty(locations.elementAt(1), 100, 50, null, squares[7]);
		squares[9] = new InvestmentProperty(locations.elementAt(2), 120, 60, null, squares[8]);
		squares[10] = new NamedLocation("Jail", null, null);
		squares[10].setRight(squares[9]);
		
		domain = domains.getDomain(2);
		locations = characters.getRandomLocationsFromDomain(domain, 3);
		squares[11] = new InvestmentProperty(locations.elementAt(0), 140, 70, null, squares[10]);
		squares[12] = new RentalProperty("Electric Co", 150, 75, null, squares[11]);
		squares[13] = new InvestmentProperty(locations.elementAt(1), 140, 70, null, squares[12]);
		squares[14] = new InvestmentProperty(locations.elementAt(2), 160, 80, null, squares[13]);
		squares[15] = new RentalProperty(characters.getRandomPersonFromDomain(domain).getVehicle(), 200, 100, null, squares[14]);
		
		
		
		domain = domains.getDomain(3);
		
		locations = characters.getRandomLocationsFromDomain(domain, 3);
		squares[16] = new InvestmentProperty(locations.elementAt(0), 180, 90, null, squares[15]);
		squares[17] =  new Card("Community", null, null);
		squares[17].setRight(squares[16]);
		squares[18] = new InvestmentProperty(locations.elementAt(1), 180, 90, null, squares[17]);
		squares[19] = new InvestmentProperty(locations.elementAt(2), 200, 100, null, squares[18]);
		squares[20] = new NamedLocation("Free Parking", null, null);
		squares[20].setRight(squares[19]);
		
		domain = domains.getDomain(4);
		locations = characters.getRandomLocationsFromDomain(domain, 3);
		
		squares[21] = new InvestmentProperty(locations.elementAt(0), 220, 110, null, squares[20]);
		squares[22] = new Card("Chance", null, null);
		squares[22].setRight(squares[21]);
		squares[23] = new InvestmentProperty(locations.elementAt(1), 220, 110, null, squares[22]);
		squares[24] = new InvestmentProperty(locations.elementAt(2), 240, 120, null, squares[23]);
		squares[25] = new RentalProperty(characters.getRandomPersonFromDomain(domain).getVehicle(), 200, 100,null, squares[24]);
		
		domain = domains.getDomain(5);
		locations = characters.getRandomLocationsFromDomain(domain, 3);
		
		squares[26] = new InvestmentProperty(locations.elementAt(0), 260, 150,null, squares[25]);
		squares[27] = new InvestmentProperty(locations.elementAt(1), 260, 150,null, squares[26]);
		squares[28] = new RentalProperty("Water Works",150, 75, null, squares[27]);
		squares[29] = new InvestmentProperty(locations.elementAt(2), 280, 150,null, squares[28]);
		squares[30] = new NamedLocation("Go To Jail", null, null);
		squares[30].setRight(squares[29]);
		
		domain = domains.getDomain(6);
		locations = characters.getRandomLocationsFromDomain(domain, 3);
		
		squares[31] = new InvestmentProperty(locations.elementAt(0), 300, 200,null, squares[30]);
		squares[32] = new InvestmentProperty(locations.elementAt(1), 300, 200,null, squares[31]);
		squares[33] =  new Card("Community", null, null);
		squares[33].setRight(squares[32]);
		squares[34] = new InvestmentProperty(locations.elementAt(2), 320, 200,null, squares[33]);
		squares[35] = new RentalProperty(characters.getRandomPersonFromDomain(domain).getVehicle(), 200, 100,null, squares[34]);
		squares[36] = new Card("Chance", null, null);
		squares[36].setRight(squares[35]);
		
		domain = domains.getDomain(7);
		locations = characters.getRandomLocationsFromDomain(domain, 2);//Last colour group only has two properties
		
		squares[37] = new InvestmentProperty(locations.elementAt(0), 350, 175, null, squares[36]);
		squares[38] = new TaxableProperty("Super Tax", null, null, 20, 100);
		squares[38].setRight(squares[37]);
		squares[39] = new InvestmentProperty(locations.elementAt(1), 400, 200, null, squares[38]);
		squares[0].setRight(squares[39]);

		
		
		for(int i=0; i<squares.length;i++){
			
			squares[i].setLeft(squares[(i+1)%squares.length]);
		}
		for(int i=0; i<squares.length;i++){
			System.out.println(squares[i].goLeft().getIdentifier());
		}
		

	}

	public static void main(String[] args)
	{
		System.out.println("start");
		WorldBuilder m = new WorldBuilder();
		System.out.println("end");
	}
}
