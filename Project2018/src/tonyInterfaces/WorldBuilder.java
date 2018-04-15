package tonyInterfaces;



import java.util.Vector;

public class WorldBuilder implements BoardAPI {

	public static final int NUM_SQUARES = 40;
	public static final int POS_JAIL = 10;
	
	private Domains domains = new Domains();
	private Characters characters = new Characters(domains.getDomains());
	private NamedLocation[] squares = new NamedLocation[NUM_SQUARES];
	
	private Domain brown = new Domain("brown");
	private Domain lightBlue = new Domain("light blue");
	private Domain pink = new Domain("pink");
	private Domain orange = new Domain("orange");
	private Domain red = new Domain("red");
	private Domain yellow = new Domain("yellow");
	private Domain green = new Domain("green");
	private Domain darkBlue = new Domain("dark blue");

	
	
	
	public WorldBuilder(Dice dice){
		//The default squares

		
		
		
		String domain = domains.getDomain(0);
		Vector<String> locations = characters.getRandomLocationsFromDomain(domain, 2);//The first color group has 2 properties
		
		squares[0] = new NamedLocation("Go", null, null);
		squares[1] = new InvestmentProperty(locations.elementAt(0), 60, 50,new int[] {2,10,30,90,160,250}, null, squares[0],brown, 50);
		squares[2] = new Card("Community", null, squares[1]);		
		squares[3] = new InvestmentProperty(locations.elementAt(1), 60, 50,new int[] {4,20,60,180,320,450}, null, squares[2], brown, 50);
		squares[4] = new TaxableProperty("Income Tax", null, squares[3], 20, 200);
		squares[5] = new Vehicle(characters.getRandomPersonFromDomain(domain).getVehicle(), 200, 100,new int[] {25,50,100,200}, null, squares[4]);
		
		
		
		
		domain = domains.getDomain(1);
		locations = characters.getRandomLocationsFromDomain(domain, 3);//Subsequent color groups have 3 properties
		squares[6] = new InvestmentProperty(locations.elementAt(0), 100, 50,new int[] {6,30,90,270,400,550}, null, squares[5], lightBlue, 50);
		squares[7] = new Card("Chance", null, null);
		squares[7].setRight(squares[6]);
		squares[8] = new InvestmentProperty(locations.elementAt(1), 100, 50,new int[] {6,30,90,270,400,550}, null, squares[7], lightBlue, 50);
		squares[9] = new InvestmentProperty(locations.elementAt(2), 120, 60,new int[] {8,40,100,300,450,600}, null, squares[8], lightBlue, 50);
		squares[10] = new NamedLocation("Jail", null, null);
		squares[10].setRight(squares[9]);
		
		domain = domains.getDomain(2);
		locations = characters.getRandomLocationsFromDomain(domain, 3);
		squares[11] = new InvestmentProperty(locations.elementAt(0), 140, 70,new int[] {10,50,150,450,625,750}, null, squares[10], pink, 100);
		squares[12] = new Utility("Electric Co", 150, 75,new int[] {4,10},dice, null, squares[11]);
		squares[13] = new InvestmentProperty(locations.elementAt(1), 140, 70,new int[] {10,50,150,450,625,750}, null, squares[12], pink, 100);
		squares[14] = new InvestmentProperty(locations.elementAt(2), 160, 80,new int[] {12,60,180,500,700,900}, null, squares[13], pink, 100);
		squares[15] = new Vehicle(characters.getRandomPersonFromDomain(domain).getVehicle(), 200, 100,new int[] {25,50,100,200}, null, squares[14]);
		
		
		
		domain = domains.getDomain(3);
		
		locations = characters.getRandomLocationsFromDomain(domain, 3);
		squares[16] = new InvestmentProperty(locations.elementAt(0), 180, 90,new int[] {14,70,200,550,750,950}, null, squares[15],orange, 100);
		squares[17] =  new Card("Community", null, null);
		squares[17].setRight(squares[16]);
		squares[18] = new InvestmentProperty(locations.elementAt(1), 180, 90,new int[] {14,70,200,550,750,950}, null, squares[17],orange, 100);
		squares[19] = new InvestmentProperty(locations.elementAt(2), 200, 100,new int[] {16,80,220,600,800,1000}, null, squares[18],orange, 100);
		squares[20] = new NamedLocation("Free Parking", null, null);
		squares[20].setRight(squares[19]);
		
		domain = domains.getDomain(4);
		locations = characters.getRandomLocationsFromDomain(domain, 3);
		
		squares[21] = new InvestmentProperty(locations.elementAt(0), 220, 110,new int[] {18,90,250,700,875,1050}, null, squares[20],red, 150);
		squares[22] = new Card("Chance", null, null);
		squares[22].setRight(squares[21]);
		squares[23] = new InvestmentProperty(locations.elementAt(1), 220, 110,new int[] {18,90,250,700,875,1050}, null, squares[22],red, 150);
		squares[24] = new InvestmentProperty(locations.elementAt(2), 240, 120,new int[] {20,100,300,750,925,1100}, null, squares[23],red, 150);
		squares[25] = new Vehicle(characters.getRandomPersonFromDomain(domain).getVehicle(), 200, 100,new int[] {25,50,100,200},null, squares[24]);
		
		domain = domains.getDomain(5);
		locations = characters.getRandomLocationsFromDomain(domain, 3);
		
		squares[26] = new InvestmentProperty(locations.elementAt(0), 260, 150,new int[] {22,110,330,800,975,1150},null, squares[25],yellow, 150);
		squares[27] = new InvestmentProperty(locations.elementAt(1), 260, 150,new int[] {22,110,330,800,975,1150},null, squares[26],yellow, 150);
		squares[28] = new Utility("Water Works",150, 75,new int[] {4,10},dice, null, squares[27]);
		squares[29] = new InvestmentProperty(locations.elementAt(2), 280, 150,new int[] {22,120,360,850,1025,1200},null, squares[28],yellow, 150);
		squares[30] = new NamedLocation("Go To Jail", null, null);
		squares[30].setRight(squares[29]);
		
		domain = domains.getDomain(6);
		locations = characters.getRandomLocationsFromDomain(domain, 3);
		
		squares[31] = new InvestmentProperty(locations.elementAt(0), 300, 200,new int[] {26,130,390,900,1100,1275},null, squares[30],green, 200);
		squares[32] = new InvestmentProperty(locations.elementAt(1), 300, 200,new int[] {26,130,390,900,1100,1275},null, squares[31],green, 200);
		squares[33] =  new Card("Community", null, null);
		squares[33].setRight(squares[32]);
		squares[34] = new InvestmentProperty(locations.elementAt(2), 320, 200,new int[] {28,150,450,1000,1200,1400},null, squares[33],green, 200);
		squares[35] = new Vehicle(characters.getRandomPersonFromDomain(domain).getVehicle(), 200, 100,new int[] {25,50,100,200},null, squares[34]);
		squares[36] = new Card("Chance", null, null);
		squares[36].setRight(squares[35]);
		
		domain = domains.getDomain(7);
		locations = characters.getRandomLocationsFromDomain(domain, 2);//Last colour group only has two properties
		
		squares[37] = new InvestmentProperty(locations.elementAt(0), 350, 175,new int[] {35,175,500,1100,1300,1500}, null, squares[36], darkBlue, 200);
		squares[38] = new TaxableProperty("Super Tax", null, null, 20, 100);
		squares[38].setRight(squares[37]);
		squares[39] = new InvestmentProperty(locations.elementAt(1), 400, 200,new int[] {50,200,600,1400,1700,2000}, null, squares[38], darkBlue, 200);
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
		Dice dice = new Dice();
		
		WorldBuilder m = new WorldBuilder(dice);
		
	}

	@Override
	public NamedLocation getSquare(int index) {
		// TODO Auto-generated method stub
		return squares[index];
	}

	@Override
	public PrivateProperty getProperty(int index) {
		// TODO Auto-generated method stub
		return (PrivateProperty) squares[index];
	}

	@Override
	public PrivateProperty getProperty(String name) {
		// TODO Auto-generated method stub
		PrivateProperty property = null;
		for (NamedLocation s : squares) {
			if (s instanceof PrivateProperty) {
				PrivateProperty p = (PrivateProperty) s;
				if (p.equals(name)) {
					property = p;
				}
			}
		}
		return property;
	}

	@Override
	public boolean isProperty(int index) {
		// TODO Auto-generated method stub
		return squares[index] instanceof PrivateProperty;
	}

	@Override
	public boolean isProperty(String name) {
		// TODO Auto-generated method stub
		boolean found = false;
		for (NamedLocation s :squares) {
			if (s instanceof PrivateProperty) {
				PrivateProperty p = (PrivateProperty) s;
				if (p.equals(name)) {	
					found = true;
				}
			}
		}
		return found;
	}

	@Override
	public boolean isSite(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVehicle(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUtility(String name) {
		// TODO Auto-generated method stub
		return false;
	}
}
