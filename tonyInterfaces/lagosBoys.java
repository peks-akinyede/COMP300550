package tonyInterfaces;
import java.util.ArrayList;

public class lagosBoys implements Bot {

	// The public API of YourTeamName must not change
	// You cannot change any other classes
	// YourTeamName may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects

	private BoardAPI board;
	private PlayerAPI player;
	private DiceAPI dice;
	private boolean paid = false, usedCard = false;
	private String command;
	private boolean checkForMonopoly = false;
	private boolean rolled = false;
	private int numberOfTimesGoneRound = 0;
	private int jailCount = 0;
	boolean bankrupt = false;

	ArrayList<Domain> frequentlyLandedOn = new ArrayList<Domain>();//Stores frequently landed on properties

	lagosBoys (BoardAPI board, PlayerAPI player, DiceAPI dice) {
		this.board = board;
		this.player = player;
		this.dice = dice;
		frequentlyLandedOn.add(new Domain("pink"));
		frequentlyLandedOn.add(new Domain("orange"));
		frequentlyLandedOn.add(new Domain("red"));
		frequentlyLandedOn.add(new Domain("yellow"));
		frequentlyLandedOn.add(new Domain("green"));
		return;
	}

	public String getName () {
		return "lagosBoys";
	}

	public String getCommand () {

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<InvestmentProperty> playerSites = new ArrayList<InvestmentProperty>();
		ArrayList<InvestmentProperty> sitesWithMonopoly = new ArrayList<InvestmentProperty>();
		ArrayList<Vehicle> playerStations = new ArrayList<Vehicle>();
		ArrayList<Utility> playerUtilities = new ArrayList<Utility>();
		ArrayList<PrivateProperty> mortgagedProperties = new ArrayList<PrivateProperty>();



		//Late stages of game


		//Check if bankrupt and mortgage properties until not bankrupt, utilities, sites and stations are mortgaged in that order
		if(player.getBalance() < 0){

			bankrupt = true;
			for (PrivateProperty property: player.getProperties()) {


				if(property instanceof InvestmentProperty){
					playerSites.add((InvestmentProperty) property);
				}

				if(property instanceof Vehicle){
					playerStations.add((Vehicle) property);
				}

				if(property instanceof Utility){
					playerUtilities.add((Utility) property);
				}

			}



			for (Utility utility: playerUtilities) {
				if(!utility.isMortgaged()){
					command = "mortgage";
					return command + " " + utility.getIdentifier();
				}
				if(player.getBalance()>100) break;
			}



			if(player.getBalance() < 0 || player.getBalance()<100){

				for (InvestmentProperty site: playerSites) {
					if(!site.isMortgaged() && !isFrequentlyLandedOn(site)){

						while(site.canDemolish(1) && player.getBalance()<100){
							command = "demolish";
							return command + " " + site.getIdentifier() + " " + 1; 
						}

						if(player.getBalance()>100) break;

						command = "mortgage";
						return command + " " + site.getIdentifier();
					}

				}

			}


			if(player.getBalance() < 0 || player.getBalance()<100){

				for (InvestmentProperty site: playerSites) {
					if(!site.isMortgaged()){

						while(site.canDemolish(1) && player.getBalance()<100){
							command = "demolish";
							return command + " " + site.getIdentifier() + " " + 1; 
						}

						if(player.getBalance()>100) break;

						command = "mortgage";
						return command + " " + site.getIdentifier();
					}
				}

			}


			if(player.getBalance() < 0){

				for (Vehicle station: playerStations) {

					if(!station.isMortgaged() && player.getBalance()<100){
						command = "mortgage";
						return command + " " + station.getIdentifier();

					}
					if(player.getBalance()>100) break;
				}

			}

		}

		boolean rolledToLeaveJail =false ;	

		//Early stages of game, leave jail quickly
		if(numberOfTimesGoneRound<=4){

			if(player.isInJail()){
				if(player.hasGetOutOfJailCard()){
					command = "card";
					usedCard = true;
					return command;
				}

				else if(player.getBalance() - 50 > 200){
					command = "pay";
					paid = true;
					return command;
				}

				else{
					if(!rolled){
						command = "roll";
						rolled = true;

						return command;
					}else{

						command = "done";
						rolled = false;
						return command;

					}
				}
			}

			rolledToLeaveJail = !paid && !usedCard ;				

		}else{

			//Stay in jail as long as possible
			if(player.isInJail()){

				if(player.hasGetOutOfJailCard()  && jailCount ==1){
					command = "card";
					usedCard = true;
					return command;
				}

				else if(!rolled){
					command = "roll";
					rolled = true;
					jailCount++;

					return command;
				}else{

					command = "done";
					rolled = false;
					return command;

				}

			}
			rolledToLeaveJail = !usedCard ;
		}


		usedCard = paid = false;

		if(!player.isInJail()){
			jailCount=0;
		}


		if(!rolled){

			command = "roll";
			rolled = true;
			return command;

		}


		if( (board.getSquare(player.getPosition()) instanceof InvestmentProperty) && !board.getProperty(player.getPosition()).isOwned() ){

			if( player.getBalance() - board.getProperty(player.getPosition()).getPrice() > 100){

				command = "buy";
				return command;

			}
		}


		if(player.passedGo()) numberOfTimesGoneRound++;




		//Build on properties with monopoly
		for (PrivateProperty property: player.getProperties()) {

			if(property instanceof InvestmentProperty){

				if(player.isGroupOwner((InvestmentProperty) property)){
					checkForMonopoly = true;
					sitesWithMonopoly.add((InvestmentProperty) property);
				}

			}
		}

		if(checkForMonopoly){


			int propertiesBuiltOn = 0;
			for (InvestmentProperty site: sitesWithMonopoly) {

				if(isFrequentlyLandedOn(site)){
					while(!site.isMortgaged() && (site.canBuild(1) && (player.getBalance()-site.getBuildingPrice())>200)){
						command = "build";						
						return command + " " + site.getIdentifier() + " " + 1; 
					}
					propertiesBuiltOn++;
				}

			}

			if(propertiesBuiltOn<sitesWithMonopoly.size() ){
				for (InvestmentProperty site: sitesWithMonopoly) {
					if(!isFrequentlyLandedOn(site)){
						while(!site.isMortgaged() && (site.canBuild(1) && (player.getBalance()-site.getBuildingPrice())>200)){
							command = "build";
							return command + " " + site.getIdentifier() + " " + 1; 
						}
					}
				}
			}

		}

		boolean mortgagedProperty = false;
		for (PrivateProperty property: player.getProperties()) {
			if(property.isMortgaged()){

				mortgagedProperties.add(property);
				mortgagedProperty = true;

			}
		}

		//Redeem mortgaged properties
		if(mortgagedProperty){

			for (PrivateProperty property: mortgagedProperties) {

				if(player.getBalance() - property.getMortgagePrice()>200){

					command = "redeem";
					return command + " " + property.getIdentifier();

				}

			}

		}


		for (PrivateProperty property: player.getProperties()) {

			if(!property.isMortgaged()){
				bankrupt = false;
				break;
			}
		}


		if(bankrupt && player.getBalance()<0){
			command = "bankrupt";
			return command;
		}

		if(dice.isDouble() && !rolledToLeaveJail){
			command = "roll";
			return command;
		}

		else if(rolled){
			command = "done";
			rolled = false;
			return command;
		}

		return command;
	}


	public boolean isFrequentlyLandedOn(InvestmentProperty site) {

		boolean answer = false;

		for (Domain Colorgroup: frequentlyLandedOn) {

			if(site.getColourGroup().getName().equals(Colorgroup.getName())){
				answer = true;
				return answer;
			}

		}

		return answer;
	}

	//Says chance unless the person has many houses or a hotel
	public String getDecision () {

		String decision = "chance";

		if(player.getNumHousesOwned()>1 || player.getNumHotelsOwned()>0){
			decision = "pay";
		}

		return decision;
	}

}
