package tonyInterfaces;

public class Vehicle extends PrivateProperty {

	Vehicle(String name, int price, int mortgageValue, int[] rentTable, Locatable leftSquare, Locatable rightSquare) {
		super(name, price, mortgageValue, rentTable, leftSquare, rightSquare);
		// TODO Auto-generated constructor stub
	}
	
	public int getRent () {
		return rentTable[super.getOwner().getNumVehiclesOwned()-1];
	}

}
