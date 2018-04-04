package tonyInterfaces;

public class PrivateProperty extends NamedLocation implements Ownable{
	
	Playable owner;
	int price;

	PrivateProperty(String name, int price, Locatable leftSquare, Locatable rightSquare) {
		super(name, leftSquare, rightSquare);
		this.price = price;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Playable getOwner() {
		// TODO Auto-generated method stub
		return owner;
	}
	
	public void setOwner(Playable person){
		owner = person;
	}

	@Override
	public int getSalePrice() {
		// TODO Auto-generated method stub
		return price;
	}

}
