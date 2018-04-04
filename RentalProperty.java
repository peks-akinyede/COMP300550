package tonyInterfaces;
//This class is for properties that can't be improved by building but can still be bought and got rent from
public class RentalProperty extends PrivateProperty implements Rentable, Mortgagable{
	int rent;
	
	RentalProperty(String name, int price, int rent,  Locatable leftSquare, Locatable rightSquare) {
		super(name, price, leftSquare, rightSquare);
		this.rent = rent;
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getMortgageAmount() {
		// TODO Auto-generated method stub
		return price/2;
	}
	@Override
	public int getRentalAmount() {
		// TODO Auto-generated method stub
		return rent;
	}
}
