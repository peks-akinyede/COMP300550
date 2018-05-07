package tonyInterfaces;
//A regular square on the board that can't be bought
public class NamedLocation implements Identifiable, Locatable {

	private Locatable leftSquare;
	private Locatable rightSquare;
	private String name;

	NamedLocation(String name, Locatable leftSquare, Locatable rightSquare){
		this.leftSquare = leftSquare;
		this.rightSquare = rightSquare;
		this.name = name;
	}

	@Override
	public Locatable goLeft() {
		// TODO Auto-generated method stub
		return leftSquare;
	}

	@Override
	public Locatable goRight() {
		// TODO Auto-generated method stub
		return rightSquare;
	}

	public void setLeft(Locatable square){
		leftSquare = square;
	}

	public void setRight(Locatable square){
		rightSquare = square;
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public boolean equals (String string) {
		return name.equalsIgnoreCase(string);
	}
	
	public String toString () {
		return name;
	}

}
