package tonyInterfaces;
//Bank Player
public class Bank implements Playable{

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return "Bank";
	}

	@Override
	public int getNetWorth() {
		// TODO Auto-generated method stub
		return 100000;
	}

}
