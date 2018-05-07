package tonyInterfaces;


import java.util.ArrayList;


public interface PlayerAPI {
	
	public String getName ();
	public String getTokenName ();
	public int getTokenId ();
	public int getPosition ();
	public boolean passedGo ();
	public boolean isInJail ();
	public boolean hasGetOutOfJailCard ();
	public int getTransaction ();
	public int getBalance ();
	public int getAssets ();
	public int getNumProperties ();
	public PrivateProperty getLatestProperty ();
	public ArrayList<PrivateProperty> getProperties ();
	public boolean isGroupOwner (InvestmentProperty site);
	public int getNumStationsOwned ();
	public int getNumUtilitiesOwned ();
	public int getNumHousesOwned ();
	public int getNumHotelsOwned ();
	public boolean equals (String name);
	public String toString ();

}
