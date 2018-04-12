package tonyInterfaces;

public class Utility extends PrivateProperty {

	Dice dice;
	
	Utility (String name, int price, int mortgageValue, int[] rentTable, Dice dice, Locatable leftSquare, Locatable rightSquare) {
		super(name, price, mortgageValue, rentTable,leftSquare, rightSquare);
		this.rentTable = rentTable;
		this.dice = dice;
		
	}
	
	
	public int getRentMultiplier () {
		return rentTable[super.getOwner().getNumUtilitiesOwned()-1];
	}

	public int getRent () {
		return dice.getTotal() * getRentMultiplier();
	}
}
