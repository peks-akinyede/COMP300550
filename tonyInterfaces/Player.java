package tonyInterfaces;

import java.util.ArrayList;

public class Player implements Playable, PlayerAPI{

private static final int MAX_NUM_JAIL_EXIT_ATTEMPTS = 3;

	
	private String name;
	private int position;
	private int balance;
	private int amount;
	private String tokenName;
	private int tokenId;
	private boolean passedGo;
	private ArrayList<PrivateProperty> properties = new ArrayList<PrivateProperty>();
	private boolean inJail;
	private int numJailExitAttempts;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private int bot_number=0;
	private boolean isBot = false;
	
	Player(String name, String tokenName, int tokenId) {
		this.name = name;
		this.tokenName = tokenName;
		this.tokenId = tokenId;
		position = 0;
		balance = 0;
		passedGo = false;
		inJail = false;
	}
	
	Player (Player player) {   // copy constructor
		this(player.getIdentifier(), player.getTokenName(), player.getTokenId());
	}
	
	//Getting Player Data
	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public void setBotNumber(int n){
		bot_number = n;
	}
	
	public int getBotNumber(){
		return bot_number;
	}
	
	public boolean isBot(){
		return isBot;
	}
	
	public void setAsBot(){
		isBot = true;
	}
	
	public String getTokenName () {
		return tokenName;
	}
	
	public int getTokenId () {
		return tokenId;
	}
	
	//Moving Player around
	public int getPosition () {
		return position;
	}

	public void move (int squares) {
		position = position + squares;
		if (position >= WorldBuilder.NUM_SQUARES) {
			position = position - WorldBuilder.NUM_SQUARES;
			passedGo = true;
		} else {
			passedGo = false;
		}
		if (position < 0) {
			position = position + WorldBuilder.NUM_SQUARES;
		} 
		return;
	}
	
	public void moveTo (int square) {
		if (square < position) {
			passedGo = true;
		} else {
			passedGo = false;
		}
		position = square;
		return;
	}

	public boolean passedGo () {
		return passedGo;
	}
	

	
	//Jail Methods
	
		public void goToJail () {
			position = WorldBuilder.POS_JAIL;
			inJail = true;
			numJailExitAttempts = 0;
			return;
		}
		
		public void freeFromJail () {
			position = WorldBuilder.POS_JAIL;
			inJail = false;
			
		}
		
		public boolean isInJail () {
			return inJail;
		}

		public void failedJailExitAttempt () {
			numJailExitAttempts++;
			
		}
		
		public boolean exceededJailExitAttempts () {
			return numJailExitAttempts >= MAX_NUM_JAIL_EXIT_ATTEMPTS;
		}
		
		public void addCard (Card card) {
			cards.add(card);
		}
		
		public boolean hasGetOutOfJailCard () {
			boolean hasCard = false;
			if (cards.size()> 0) {
				hasCard = cards.get(0).getAction() == CardDeck.ACT_GET_OUT_OF_JAIL;
			}
			return hasCard;
		}
		
		public Card getCard () {
			Card card = cards.get(0);
			cards.remove(0);
			return card;
		}
		
	//  Money methods
		
		public void doTransaction (int amount) {
			balance = balance + amount;
			this.amount = amount;
			return;
		}
			
		public int getTransaction () {
			return amount;
		}
		
		public int getBalance () {
			return balance;
		}
		
		@Override
		public int getNetWorth() {
			// TODO Auto-generated method stub
			int assets = balance;
			for (PrivateProperty property: properties) {
				assets = assets + property.getPrice();
			}
			return assets;
		}
		
	//  Property Methods
		
		public void addProperty (PrivateProperty property) {
			property.setOwner(this);
			properties.add(property);
			return;
		}
		
		public int getNumProperties () {
			return properties.size();
		}
		
		public PrivateProperty getLatestProperty () {
			return properties.get(properties.size()-1);
		}
		
		public ArrayList<PrivateProperty> getProperties () {
			return properties;
		}
		
		public int getNumVehiclesOwned () {
			int numOwned = 0;
			for (PrivateProperty p : properties) {
				if (p instanceof Vehicle) {
					numOwned++;
				}
			}
			return numOwned;
		}
		
		public int getNumUtilitiesOwned () {
			int numOwned = 0;
			for (PrivateProperty p : properties) {
				if (p instanceof Utility) {
					numOwned++;
				}
			}
			return numOwned;
		}
		
		public boolean isGroupOwner (InvestmentProperty site) {
			boolean ownsAll = true;
			Domain colourGroup = site.getColourGroup();
			for (InvestmentProperty s : colourGroup.getMembers()) {
				if (!s.isOwned() || (s.isOwned() && s.getOwner() != this))
					ownsAll = false;
			}
			return ownsAll;
		}
		
		public int getNumHousesOwned () {
			int numHousesOwned = 0;
			for (PrivateProperty p : properties) {
				if (p instanceof InvestmentProperty) {
					numHousesOwned = numHousesOwned + ((InvestmentProperty) p).getNumHouses();
				}
			}
			return numHousesOwned;
		}
		
		public int getNumHotelsOwned () {
			int numHotelsOwned = 0;
			for (PrivateProperty p : properties) {
				if (p instanceof InvestmentProperty) {
					numHotelsOwned = numHotelsOwned + ((InvestmentProperty) p).getNumHotels();
				}
			}
			return numHotelsOwned;
		}	
		
	// Some Java methods	
		
		public boolean equals (String name) {
			return this.name.toLowerCase() == name;
		}
		
		public String toString () {
			return name + " (" + tokenName + ")";
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return name;
		}

		@Override
		public int getAssets() {
			// TODO Auto-generated method stub
			int assets = balance;
			for (PrivateProperty property: properties) {
				assets = assets + property.getPrice();
			}
			return assets;
		}

		@Override
		public int getNumStationsOwned() {
			int numOwned = 0;
			for (PrivateProperty p : properties) {
				if (p instanceof Vehicle) {
					numOwned++;
				}
			}
			return numOwned;
		}

}
