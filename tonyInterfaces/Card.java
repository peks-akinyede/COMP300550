package tonyInterfaces;

public class Card extends NamedLocation implements Cardable, Actionable {

	public Card(String name, Locatable leftSquare, Locatable rightSquare){
		super(name,leftSquare,rightSquare);
	}
	
	@Override
	public boolean performActionOn(Playable player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String explainAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Actionable getCardAction() {
		// TODO Auto-generated method stub
		return null;
	}


}
