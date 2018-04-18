package tonyInterfaces;
//This property can be improved
public class InvestmentProperty extends PrivateProperty implements Improvable{

	private static int MAX_NUM_UNITS=5; //Can have up to 4 houses, then the last building is a hotel
	
	private int numBuildings;
	private Domain colourGroup;
	private int buildingPrice;
	
	InvestmentProperty(String name, int price, int mortgageValue, int[] rentTable, Locatable leftSquare, Locatable rightSquare, Domain colourGroup, int buildingPrice) {
		super(name, price, mortgageValue, rentTable, leftSquare, rightSquare);
		this.buildingPrice = buildingPrice;
		this.colourGroup = colourGroup;
		numBuildings = 0;
		// TODO Auto-generated constructor stub
	}
	
	
	//Building methods
	
	public boolean canBuild (int numToBuild) {
		 return (numBuildings+numToBuild)<=MAX_NUM_UNITS;
	}
	
	public void build (int numToBuild) {
		if (canBuild(numToBuild)) {
			numBuildings = numBuildings + numToBuild;
		}
		return;
	}
	
	public boolean canDemolish (int numToDemolish) {
		return (numBuildings-numToDemolish)>=0;
	}
	
	public void demolish (int numToDemolish) {
		if (canDemolish(numToDemolish)) {
			numBuildings = numBuildings - numToDemolish;
		}
	}
	
	public void demolishAll () {
		numBuildings = 0;
		return;
	}
	
	public int getNumBuildings () {
		return numBuildings;
	}
	
	public int getBuildingPrice () {
		return buildingPrice;
	}
	
	public boolean hasBuildings () {
		return numBuildings > 0;
	}
	
	@Override
	public int getNumHouses() {
		// TODO Auto-generated method stub
		int numHouses;
		if (numBuildings < 5) {
			numHouses = numBuildings;
		} else {
			numHouses = 0;
		}
		return numHouses;
	}

	@Override
	public int getNumHotels() {
		// TODO Auto-generated method stub
		int numHotels;
		
		switch(numBuildings){
		
		case 5:
			numHotels = 1;
			break;
			
		case 6:
			numHotels = 2;
			break;
		default:
			numHotels = 0;
		}
		
		return numHotels;
	}
	
	// METHODS DEALING WITH Domains
	
		public Domain getColourGroup () {
			return colourGroup;
		}
		
	//Rent methods
		@Override//to do
		public int getRentalAmount() {
			// TODO Auto-generated method stub
			int rent;
			if (numBuildings==0 && super.getOwner().isGroupOwner(this)) {
				rent = rentTable[0];
			} else if (numBuildings==0 && super.getOwner().isGroupOwner(this)) {
				rent = 2*rentTable[0]; 
			} else {
				rent = rentTable[numBuildings];
			}
			return rent;
		}
		

}
