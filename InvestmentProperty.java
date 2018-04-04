package tonyInterfaces;
//This property can be improved
public class InvestmentProperty extends RentalProperty implements Improvable{

	int houses, hotels;
	InvestmentProperty(String name, int price, int rent, Locatable leftSquare, Locatable rightSquare) {
		super(name, price, rent, leftSquare, rightSquare);
		hotels = 0;
		houses = 0;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getRentalAmount() {
		// TODO Auto-generated method stub
		return rent + (200*hotels) + (100*houses);
	}
	
	public void buyHouse(){
		houses++;
	}
	
	public void buyHotel(){
		hotels++;
	}

	@Override
	public int getNumHouses() {
		// TODO Auto-generated method stub
		return houses;
	}

	@Override
	public int getNumHotels() {
		// TODO Auto-generated method stub
		return hotels;
	}

}
