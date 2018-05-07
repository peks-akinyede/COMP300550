package tonyInterfaces;

import java.util.ArrayList;

import java.lang.reflect.*;

public class Monopoly {

	
	public static int NUM_PLAYERS = 2;
	public static int NUM_BOTS = 0;
	
	
	private static final int START_MONEY = 1500;
	private static final int GO_MONEY = 200;
	private static final int JAIL_FINE = 50;
//	
//	private Players players = new Players();
//	private Player currPlayer;
//	private Dice dice = new Dice();
//	private WorldBuilder board = new WorldBuilder(dice);
//	private UI ui = new UI(players, board);
//	private ChanceDeck chanceDeck = new ChanceDeck();
//	private CommunityChestDeck communityChestDeck = new CommunityChestDeck();
//	private boolean gameOver = false;
//	private boolean onlyOneNotBankrupt = false;
//	private boolean turnFinished;
//	private boolean rollDone;
//	private int doubleCount;
	
	private Players players = new Players();
	private Player currPlayer;
	private Dice dice = new Dice();
	private WorldBuilder board = new WorldBuilder(dice);
	private UI ui;
	private ChanceDeck chanceDeck = new ChanceDeck();
	private CommunityChestDeck communityChestDeck = new CommunityChestDeck();
	private boolean gameOver = false;
	private boolean onlyOneNotBankrupt = false;
	private boolean turnFinished;
	private boolean rollDone;
	private int doubleCount;
	private Bot[] bots;
	private boolean hasBots = false;
	
	
	
	Monopoly (String[] args) {
		
		ui = new UI(players, board);
		//how many players
		NUM_PLAYERS = ui.inputNumberOfPlayers();
		//are there bots
		hasBots = ui.hasBots();
		
		if(hasBots){
			NUM_BOTS = ui.numberOfBots();
			bots = new Bot[NUM_BOTS];
			setupBots();
			ui.setBots(bots);
		}
		ui.display();
		return;
	}
		
	private void setupBots () {
		String[] botNames = new String [NUM_BOTS];
		String[] botnames = new String [NUM_BOTS];//names to be displayed for bots
		
		//Sets up two bots to play the game in case less than two players available
		
			for(int i=0;i<NUM_BOTS;i++){
				
				if(i%2==0){
					botNames[i] = "tonyInterfaces.lagosBoys";
					botnames[i] = board.getCharacter(i);
				}
				else{
					botNames[i] = "tonyInterfaces.SudoAptGetRekt";
					botnames[i] = board.getCharacter(i);
				}
								
			}
			
			
		
		
		for (int i=0; i<NUM_BOTS; i++) {
			Player bot = new Player(botnames[i],BoardPanel.TOKEN_NAME[i+(NUM_PLAYERS-NUM_BOTS)],(NUM_PLAYERS-NUM_BOTS)+i);
			bot.setAsBot();
			bot.setBotNumber(i);
			players.add(bot);
			try {
				Class<?> botClass = Class.forName(botNames[i]);
				Constructor<?> botCons = botClass.getDeclaredConstructor(BoardAPI.class, PlayerAPI.class, DiceAPI.class);
				bots[i] = (Bot) botCons.newInstance(board,players.get(i),dice);
			} catch (IllegalAccessException ex) {
				System.out.println("Error: Bot instantiation fail (IAE)");
			    Thread.currentThread().interrupt();
			} catch (InstantiationException ex) {
				System.out.println("Error: Bot instantiation fail (IE)");
			    Thread.currentThread().interrupt();
			} catch (ClassNotFoundException ex) {
				System.out.println("Error: Bot instantiation fail (CNFE)");
			    Thread.currentThread().interrupt();
			} catch (InvocationTargetException ex) {
				System.out.println("Error: Bot instantiation fail (ITE)");
			    Thread.currentThread().interrupt();
			} catch (NoSuchMethodException ex) {
				System.out.println("Error: Bot instantiation fail (NSME)");
			    Thread.currentThread().interrupt();
			}			
		}
		for(Player p: players.get()){
			System.out.println(p.getIdentifier()+ " " + p.getBotNumber());
		}
		
	}
	
	public void inputNames () {
		
			inputNamesPlayers();
		
	}
	
	
	
	public void inputNamesBots(){
		
		int playerId = 0;
		do {
			ui.inputNameBots(playerId);
			playerId++;
		} while (!ui.isDone()&& playerId<NUM_BOTS && players.canAddPlayer());
		return;
		
	}
	
	public void inputNamesPlayers () {
		int playerId = 0;
		do {
			ui.inputNameNoBots(playerId);
			if (!ui.isDone()) {
				boolean duplicate = false;
				for (Player p : players.get()) {
					if (ui.getString().toLowerCase().equals(p.getIdentifier().toLowerCase())) {
						duplicate = true;
					}
				}
				if (!duplicate) {
					players.add(new Player(ui.getString(),ui.getTokenName(playerId),playerId));
					playerId++;
				} else {
					ui.displayError(UI.ERR_DUPLICATE);
				}
			}
		} while (!ui.isDone() && playerId<NUM_PLAYERS-NUM_BOTS && players.canAddPlayer());
		return;
	}
	
	public void giveStartMoney () {
		for (Player p : players.get()) {
			p.doTransaction (START_MONEY);
			ui.displayBankTransaction (p);
		}
		return;
	}
	
	public void decideStarter () {
		Players inPlayers = new Players(players), selectedPlayers = new Players();
		boolean tie = false;
		do {
			int highestTotal = 0;
			for (Player p : inPlayers.get()) {
				dice.rollDice(2,6);//roll two 6 sided dice
				ui.displayDice(p,dice);
				if (dice.getTotal() > highestTotal) {
					tie = false;
					highestTotal = dice.getTotal();
					selectedPlayers.clear();
					selectedPlayers.add(p);
				} else if (dice.getTotal() == highestTotal) {
					tie = true;
					selectedPlayers.add(p);
				}
			}
			if (tie) {
				ui.displayRollDraw();
				inPlayers = new Players(selectedPlayers);
				selectedPlayers.clear();
			}
		} while (tie);
		currPlayer = selectedPlayers.get(0);
		ui.displayRollWinner(currPlayer);
		ui.display();
		return;
	}
	
	private void checkPassedGo () {
		if (currPlayer.passedGo()) {
			currPlayer.doTransaction(+GO_MONEY);
			ui.displayPassedGo(currPlayer);
			ui.displayBankTransaction(currPlayer);
		}
		return;
	}
	
	private void cardAction (Card card) {
		switch (card.getAction()) {
			case CardDeck.ACT_GO_FORWARD :
				currPlayer.moveTo(card.getDestination());
				ui.display();
				checkPassedGo();
				squareArrival();
				break;
			case CardDeck.ACT_GO_BACKWARD:
				currPlayer.moveTo(card.getDestination());
				ui.display();
				squareArrival();
				break;						
			case CardDeck.ACT_MOVE :
				currPlayer.move(card.getNumSpaces());
				ui.display();
				squareArrival();
				break;
			case CardDeck.ACT_GOTO_JAIL :
				currPlayer.goToJail();
				ui.display();
				rollDone = true;
				break;
			case CardDeck.ACT_GET_OUT_OF_JAIL:
				currPlayer.addCard(card);
				break;
			case CardDeck.ACT_PAY_HOUSES :
				int amount = currPlayer.getNumHousesOwned() * card.getHouseCost() + currPlayer.getNumHotelsOwned() * card.getHotelCost();
				currPlayer.doTransaction(-amount);				
				ui.displayBankTransaction(currPlayer);
				break;
			case CardDeck.ACT_PAY :
				currPlayer.doTransaction(-card.getAmount());
				ui.displayBankTransaction(currPlayer);
				break;
			case CardDeck.ACT_RECEIVE :
				currPlayer.doTransaction(+card.getAmount());
				ui.displayBankTransaction(currPlayer);
				break;
			case CardDeck.ACT_PAY_OR_CHANCE :
				ui.inputPayOrChance(currPlayer);
				if (ui.inputWasPay()) {
					currPlayer.doTransaction(-card.getAmount());
					ui.displayBankTransaction(currPlayer);
				} else {
					Card secondCard = chanceDeck.get();
					ui.displayCard(secondCard);
					cardAction(secondCard);
					chanceDeck.add(secondCard);
				}
				break;
			case CardDeck.ACT_GIFTS :
				for (Player otherPlayer : players.getOtherPlayers(currPlayer)) {
					currPlayer.doTransaction(+card.getAmount());
					otherPlayer.doTransaction(-card.getAmount());
					ui.displayTransaction(otherPlayer, currPlayer);
				}
				break;
		}	
	
	}
	
	private void squareArrival () {
		ui.displaySquare(currPlayer);
		NamedLocation square = board.getSquare(currPlayer.getPosition());
		if (square instanceof PrivateProperty && ((PrivateProperty) square).isOwned() && !((PrivateProperty) square).getOwner().equals(currPlayer) ) {
			int rent = ((PrivateProperty) square).getRentalAmount();
			Player owner = ((PrivateProperty) square).getOwner();
			currPlayer.doTransaction(-rent);
			owner.doTransaction(+rent);
			ui.displayTransaction(currPlayer, owner);
		} else if (square instanceof Chance) {
			Card card = chanceDeck.get();
			ui.displayCard(card);
			cardAction(card);
			chanceDeck.add(card);			
		} else if (square instanceof CommunityChest) {
			Card card = communityChestDeck.get();
			ui.displayCard(card);
			cardAction(card);
			communityChestDeck.add(card);
		}
	else if (square instanceof TaxableProperty) {
			int amount = ((TaxableProperty) square).getFlatAmount();
			currPlayer.doTransaction(-amount);					
			ui.displayBankTransaction(currPlayer);
		} else if (square instanceof GoToJail) {
			currPlayer.goToJail();
			rollDone = true;
		}
		ui.display();
		return;
	}
	
	private void rollCommand () {
		if (!rollDone) {
			if (currPlayer.getBalance() >= 0) {
				dice.rollDice(2,6);
				ui.displayDice(currPlayer, dice);
				if (!currPlayer.isInJail()) {
					currPlayer.move(dice.getTotal());
					ui.display();
					checkPassedGo();
					squareArrival();
					if (dice.isDouble()) {
						doubleCount++;
						if (doubleCount == 3) {
							ui.displayThreeDoubles(currPlayer);
							currPlayer.goToJail();
							rollDone = true;
						}
					} else {
						rollDone = true;
					}
				} else {
					if (dice.isDouble()) {
						currPlayer.freeFromJail();
						ui.displayFreeFromJail(currPlayer);
					} else {
						currPlayer.failedJailExitAttempt();
						if (currPlayer.exceededJailExitAttempts()) {
							currPlayer.doTransaction(-JAIL_FINE);
							ui.displayJailFine(currPlayer,JAIL_FINE);							
							currPlayer.freeFromJail();
							ui.displayFreeFromJail(currPlayer);
						}
					}
					currPlayer.move(dice.getTotal());
					ui.display();
					rollDone = true;
				}
			} else {
				ui.displayError(UI.ERR_NEGATIVE_BALANCE);	
			}
		} else {
			ui.displayError(UI.ERR_DOUBLE_ROLL);
		}
		return;
	}
	
	private void buyCommand () {
		if (board.getSquare(currPlayer.getPosition()) instanceof PrivateProperty) {
			PrivateProperty property = (PrivateProperty) board.getSquare(currPlayer.getPosition());
			System.out.println(property.getIdentifier()+ " buy board");
			if (!property.isOwned()) {
				if (currPlayer.getBalance() >= property.getPrice()) {				
					currPlayer.doTransaction(-property.getPrice());
					ui.displayBankTransaction(currPlayer);
					currPlayer.addProperty(property);
					ui.displayLatestProperty(currPlayer);
				} else {
					ui.displayError(UI.ERR_INSUFFICIENT_FUNDS);
				}
			} else {
				ui.displayError(UI.ERR_IS_OWNED);
			}
		} else {
			ui.displayError(UI.ERR_NOT_A_PROPERTY);
		}
		return;
	}
	
	private void buildCommand () {
		PrivateProperty property = ui.getInputProperty();
		System.out.println(property.getIdentifier()+ " build board");
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if (property instanceof InvestmentProperty) {
				InvestmentProperty site = (InvestmentProperty) property;
				if (currPlayer.isGroupOwner(site)) {
					if (!site.isMortgaged()) {
						int numBuildings = ui.getInputNumber();
						if (numBuildings>0) {
							System.out.println(numBuildings);
							System.out.println(site.canBuild(numBuildings));
							if (site.canBuild(numBuildings)) {
								int debit = numBuildings*site.getBuildingPrice();
								if (currPlayer.getBalance()>debit) {
									site.build(numBuildings);
									currPlayer.doTransaction(-debit);
									ui.displayBuild(currPlayer,site,numBuildings);
								} else {
									ui.displayError(UI.ERR_INSUFFICIENT_FUNDS);
								}
							} else {
								ui.displayError(UI.ERR_TOO_MANY_BUILDINGS);
							}
						} else {
							ui.displayError(UI.ERR_TOO_FEW_BUILDINGS);
						}
					} else {
						ui.displayError(UI.SITE_IS_MORTGAGED);
					}
				} else {
					ui.displayError(UI.ERR_DOES_NOT_HAVE_GROUP);
				}
			} else {
				ui.displayError(UI.ERR_NOT_A_SITE);
			}
		} else {
			ui.displayError(UI.ERR_NOT_YOURS);
		}
		return;
	}
	
	private void demolishCommand () {
		PrivateProperty property = ui.getInputProperty();
		System.out.println(property.getIdentifier()+ " demolish board");
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if (property instanceof InvestmentProperty) {
				InvestmentProperty site = (InvestmentProperty) property;
				int numBuildings = ui.getInputNumber();
				if (numBuildings>0) {
					if (site.canDemolish(numBuildings)) {
						site.demolish(numBuildings);
						int credit = numBuildings * site.getBuildingPrice()/2;
						currPlayer.doTransaction(+credit);
						ui.displayDemolish(currPlayer,site,numBuildings);
					} else {
						ui.displayError(UI.ERR_TOO_MANY_BUILDINGS);
					}
				} else {
					ui.displayError(UI.ERR_TOO_FEW_BUILDINGS);
				}
			} else {
				ui.displayError(UI.ERR_NOT_A_SITE);
			}
		} else {
			ui.displayError(UI.ERR_NOT_YOURS);
		}
		return;		
	}
	
	private void bankruptCommand () {
		ui.displayBankrupt(currPlayer);
		Player tempPlayer = players.getNextPlayer(currPlayer);
		players.remove(currPlayer);
		currPlayer = tempPlayer;
		if (players.numPlayers()==1) {
			gameOver = true;
			onlyOneNotBankrupt = true;
		}
		ui.display();
		return;
	}
	
	private void mortgageCommand () {
		PrivateProperty property = ui.getInputProperty();
		System.out.println(property.getIdentifier()+ " motgage board");
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if ((property instanceof InvestmentProperty) && !((InvestmentProperty) property).hasBuildings() || (property instanceof Vehicle) || (property instanceof Utility)) {
//				System.out.println(!property.isMortgaged()+" mono");
				if (!property.isMortgaged()) {
					property.setMortgaged();
					currPlayer.doTransaction(+property.getMortgagePrice());
					ui.displayMortgage(currPlayer,property);
				} else {
					ui.displayError(UI.ERR_IS_MORTGAGED);
				}
			} else {
				ui.displayError(UI.ERR_HAS_BUILDINGS);
			}
		} else {
			ui.displayError(UI.ERR_NOT_YOURS);
		}
		return;		
	}
	
	private void cardCommand () {
		if (currPlayer.isInJail()) {
			if (currPlayer.hasGetOutOfJailCard()) {
				Card card = currPlayer.getCard();
				if (card.getType() == ChanceDeck.CHANCE_CARD) {
					chanceDeck.add(card);
				} else {
					communityChestDeck.add(card);
				}
				currPlayer.freeFromJail();
				ui.displayFreeFromJail(currPlayer);
			} else {
				ui.displayError(UI.ERR_DOES_NOT_HAVE_GET_OUT_OF_JAIL_CARD);
			}
		} else {
			ui.displayError(UI.ERR_NOT_IN_JAIL);
		}
		
	}
	
	private void payCommand () {
		if (currPlayer.isInJail()) {
			if (currPlayer.getBalance() >= JAIL_FINE) {
				currPlayer.doTransaction(-JAIL_FINE);
				currPlayer.freeFromJail();
				ui.displayBankTransaction(currPlayer);
				ui.displayFreeFromJail(currPlayer);
				ui.display();
			} else {
				ui.displayError(UI.ERR_DOES_NOT_HAVE_GET_OUT_OF_JAIL_CARD);
			}
		} else {
			ui.displayError(UI.ERR_NOT_IN_JAIL);
		}
		return;
	}
	
	private void redeemCommand () {
		PrivateProperty property = ui.getInputProperty();
		System.out.println(property.getIdentifier()+ " redeem board");
		if (property.isOwned() && property.getOwner().equals(currPlayer)) {
			if (property.isMortgaged()) {
				int price = property.getMortgageAmount();
				if (currPlayer.getBalance() >= price) {
					property.unmortgage();
					currPlayer.doTransaction(-price);
					ui.displayMortgageRedemption(currPlayer,property);
				} else {
					ui.displayError(UI.ERR_INSUFFICIENT_FUNDS);
				}
			} else {
				ui.displayError(UI.ERR_IS_NOT_MORTGAGED);
			}
		} else {
			ui.displayError(UI.ERR_NOT_YOURS);
		}
		return;			
	}
	
	private void doneCommand () {
		if (rollDone) {
			if (currPlayer.getBalance() >= 0) {
				turnFinished = true;								
			} else {
				ui.displayError(UI.ERR_NEGATIVE_BALANCE);
			}
		} else {
			ui.displayError(UI.ERR_NO_ROLL);
		}
		return;
	}
	
	public void processTurn () {
		
	
		System.out.println("person playing is "+ currPlayer.getBotNumber()+ " "+ currPlayer.getIdentifier());
		turnFinished = false;
		rollDone = false;
		doubleCount = 0;
		do {
			ui.inputCommand(currPlayer);
			switch (ui.getCommandId()) {
				case UI.CMD_ROLL :
					rollCommand();
					break;
				case UI.CMD_BUY :
					buyCommand();
					break;
				case UI.CMD_BALANCE :
					ui.displayBalance(currPlayer);
					break;
				case UI.CMD_PROPERTY :
					ui.displayProperty(currPlayer);
					break;
				case UI.CMD_BANKRUPT :
					bankruptCommand();
					turnFinished = true;
					break;
				case UI.CMD_BUILD :
					buildCommand();
					break;
				case UI.CMD_DEMOLISH :
					demolishCommand();
					break;
				case UI.CMD_MORTGAGE :
					mortgageCommand();
					break;
				case UI.CMD_REDEEM :
					redeemCommand();
					break;
				case UI.CMD_CARD :
					cardCommand();
					break;
				case UI.CMD_PAY:
					payCommand();
					break;
				case UI.CMD_HELP :
					ui.displayCommandHelp();
					break;
				case UI.CMD_DONE :
					doneCommand();
					break;
				case UI.CMD_QUIT : 
					turnFinished = true;
					gameOver = true;
					break;
			}
		} while (!turnFinished);
		return;
	}
	
	public void nextPlayer () {
		currPlayer = players.getNextPlayer(currPlayer);
		return;
	}
	
	public void decideWinner () {
		if (onlyOneNotBankrupt) {
			ui.displayWinner(currPlayer);			
		} else {
			ArrayList<Player> playersWithMostAssets = new ArrayList<Player>();
			int mostAssets = players.get(0).getNetWorth();
			for (Player player : players.get()) {
				ui.displayAssets(player);
				if (player.getNetWorth() > mostAssets) {
					playersWithMostAssets.clear(); 
					playersWithMostAssets.add(player);
					mostAssets = player.getNetWorth();
				} else if (player.getNetWorth() == mostAssets) {
					playersWithMostAssets.add(player);
				}
			}
			if (playersWithMostAssets.size() == 1) {
				ui.displayWinner(playersWithMostAssets.get(0));
			} else {
				ui.displayDraw(playersWithMostAssets);
			}
		}
		return;
	}
	
	public void displayGameOver () {
		ui.displayGameOver ();
		return;
	}
	
	public boolean isGameOver () {
		return gameOver;
	}
}