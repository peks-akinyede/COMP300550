package tonyInterfaces;

public class PrivateProperty extends NamedLocation implements Ownable, Mortgagable, Rentable{
	
	private Player owner = null;
	private int price;
	private boolean isOwned;
	private boolean isMortgaged;
	private int mortgagePrice;
	protected int[] rentTable;
	

	PrivateProperty(String name, int price, int mortgageValue, int[] rentTable, Locatable leftSquare, Locatable rightSquare) {
		super(name, leftSquare, rightSquare);
		this.price = price;
		this.rentTable = rentTable;
		isOwned = false;
		isMortgaged = false;
		mortgagePrice = mortgageValue;
		// TODO Auto-generated constructor stub
	}

	//Ownership methods
	@Override
	public Player getOwner() {
		// TODO Auto-generated method stub
		return owner;
	}
	
	public boolean isOwned(){
		return isOwned;
	}
	
	public void setOwner(Player person){
		owner = person;
		isOwned = true;
	}

	//Money methods
	@Override
	public int getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

	@Override
	public int getRentalAmount() {
		// TODO Auto-generated method stub
		return rentTable[0];
	}

	//Mortgage methods
	public boolean isMortgaged(){
		return isMortgaged;
	}
	
	@Override
	public int getMortgageAmount() {
		// TODO Auto-generated method stub
		return mortgagePrice;
	}
	
	public void setMortgaged() {
		isMortgaged = true;
	}
	
	public void unmortgage() {
		isMortgaged = false;
	}
	
	//Mortgage redemption costs 10% more than mortgage price
	public int getMortgagePrice () {
		return (int) (((float)  mortgagePrice) * 1.1f);
	}
	

}
