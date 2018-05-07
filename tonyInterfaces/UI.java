package tonyInterfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;

import java.util.ArrayList;


public class UI {

	private static final int FRAME_WIDTH = 1150;
	private static final int FRAME_HEIGHT = 700;

	public static final String CURRENCY = " pounds";
	public static final String CURRENCY_SYMBOL = "€";

	public static final int CMD_QUIT = 0;
	public static final int CMD_DONE = 1;
	public static final int CMD_ROLL = 2;
	public static final int CMD_BUY = 3;
	public static final int CMD_AUCTION = 4;
	public static final int CMD_PROPERTY = 5;
	public static final int CMD_BALANCE = 6;
	public static final int CMD_BANKRUPT = 7;
	public static final int CMD_HELP = 8;
	public static final int CMD_MORTGAGE = 9;
	public static final int CMD_BUILD = 10;
	public static final int CMD_SELL = 11;
	public static final int CMD_REDEEM = 12;
	public static final int CMD_CHEAT = 13;
	public static final int CMD_DEMOLISH = 14;
	public static final int CMD_CARD = 15;
	public static final int CMD_PAY = 16;

	public static final int ERR_SYNTAX = 0;
	public static final int ERR_DOUBLE_ROLL = 1;
	public static final int ERR_NO_ROLL = 2;
	public static final int ERR_INSUFFICIENT_FUNDS = 3;
	public static final int ERR_IS_OWNED = 4;
	public static final int ERR_SELF_OWNED = 5;
	public static final int ERR_NOT_A_NAME = 6;
	public static final int ERR_TOO_MANY_BUILDINGS = 7;
	public static final int ERR_NOT_A_SITE = 8;
	public static final int ERR_NOT_YOURS = 9;
	public static final int ERR_TOO_FEW_BUILDINGS = 10;
	public static final int ERR_DOES_NOT_HAVE_GROUP = 11;
	public static final int ERR_DUPLICATE = 12;
	public static final int ERR_HAS_BUILDINGS = 13;
	public static final int ERR_IS_MORTGAGED = 14;
	public static final int ERR_IS_NOT_MORTGAGED = 15;
	public static final int SITE_IS_MORTGAGED = 16;
	public static final int ERR_NEGATIVE_BALANCE = 17;
	public static final int ERR_NOT_A_PROPERTY = 18;
	public static final int ERR_DOES_NOT_HAVE_GET_OUT_OF_JAIL_CARD = 19;
	public static final int ERR_NOT_IN_JAIL = 20;

	private final String[] errorMessages = {
			"Error: Not a valid command.",
			"Error: Too many rolls this turn.",
			"Error: You have a roll to play.",
			"Error: You don't have enough money.",
			"Error: The property is already owned.",
			"Error: You own the property.",
			"Error: Not a name.",
			"Error: Too many units.",
			"Error: That property is not a site.",
			"Error: You do not own that property.",
			"Error: Must be one or more units",
			"Error: You do not own the whole colour group.",
			"Error: Duplicate name.",
			"Error: The site has units on it.",
			"Error: The property has already been mortgaged.",
			"Error: The property has not been mortgaged.",
			"Error: The property has been mortgaged.",
			"Error: Cannot end a turn with a negative bank balance.",
			"Error: You are not on a property.",
			"Error: You do not have a get out of jail free card.",
			"Error: You are not in jail."
	};


	private JFrame frame = new JFrame();
	private BoardPanel boardPanel;	
	private InfoPanel infoPanel = new InfoPanel();
	private CommandPanel commandPanel = new CommandPanel();
	private String string;
	private boolean done;
	private int commandId;
	private WorldBuilder board;
	private Players players;
	private PrivateProperty inputProperty;
	private int inputNumber;
	private Player inputPlayer;
	private boolean inputWasPay;
	private Bot[] bots;
	



	UI (Players players, WorldBuilder board) {

		this.players = players;
		this.board = board;
		boardPanel = new BoardPanel(this.players);
		BoardNames boardnames = new BoardNames(board);
		boardnames.setOpaque(false);

		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLayout(new BorderLayout());
		frame.setTitle("Monopoly");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLayeredPane namePane = new JLayeredPane();
		namePane.setPreferredSize(new Dimension(700,700));

		namePane.add(boardPanel, new Integer(1));
		boardPanel.setBounds(0, 0, 700, 680);
		namePane.add(boardnames, new Integer(2));
		boardnames.setBounds(0,0,700,680);


		frame.add(namePane, BorderLayout.CENTER);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				infoPanel, commandPanel);
		//JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
		//splitPane, scoreBoardPanel);
		splitPane.setDividerSize(1);
		//splitPane2.setDividerSize(1);
		splitPane.setResizeWeight(0.95); 


		frame.add(splitPane, BorderLayout.EAST);

		frame.setResizable(false);
		frame.setVisible(true);
	}


	public void setBots(Bot[] bots){
		this.bots = bots;
	}

	//  METHODS DEALING WITH USER INPUT

	public int inputNumberOfPlayers(){
		boolean inputValid = false;
		int inputNumber = 0;
		infoPanel.displayString("How many players? Enter between 2 and 6 please");
		do {
			commandPanel.inputString();
			string = commandPanel.getString();
			string = string.trim();
			if ( string.matches("[0-9]+")){
				inputNumber = Integer.parseInt(string);
				if(inputNumber>= 2 && inputNumber<=6){
					inputValid = true;
				}
			}
			infoPanel.displayString("> " + string);
			if (!inputValid) {
				displayError(ERR_SYNTAX);
			}

		} while (!inputValid);

		return inputNumber;
	}

	public void inputNameBots (int numPlayer) {
		if (numPlayer == 0) {
			infoPanel.displayString("Enter new player name (" + boardPanel.getTokenName(numPlayer) + "):");			
		} else {
			infoPanel.displayString("Enter new player name (" + boardPanel.getTokenName(numPlayer)  +  ") or done:");
		}
		if (numPlayer < Monopoly.NUM_PLAYERS) {
			string = bots[numPlayer].getName();
			done = false;
		} else if (numPlayer == Monopoly.NUM_PLAYERS) {
			string = "DONE";
			done = true;
		}
		infoPanel.displayString("> " + string);
		return;
	}


	public void inputNameNoBots (int numPlayers) {
		boolean inputValid = false;
		if (numPlayers == 0) {
			infoPanel.displayString("Enter new player name (" + boardPanel.getTokenName(numPlayers) + "):");			
		} else {
			infoPanel.displayString("Enter new player name (" + boardPanel.getTokenName(numPlayers)  +  ") or done:");
		}
		do {
			commandPanel.inputString();
			string = commandPanel.getString();
			string = string.trim();
			if (string.length()==0) {
				inputValid = false;
				done = false;
			} else if ( (numPlayers > 0) && (string.toLowerCase().equals("done")) ) {
				inputValid = true;
				done = true;
			} else if (string.contains(" ")) {
				inputValid = false;
				done = false;
			} else {
				inputValid = true;
			}
			infoPanel.displayString("> " + string);
			if (!inputValid) {
				displayError(ERR_NOT_A_NAME);
			}
		} while (!inputValid);
		return;
	}

	public boolean hasBots(){
		boolean answer = false;
		boolean inputValid = false;
		infoPanel.displayString("Do you want to play with bots? Answer 'yes' or 'no' please");
		do {
			commandPanel.inputString();
			string = commandPanel.getString();
			string = string.trim();
			if ( string.equalsIgnoreCase("yes")){
				inputValid = true;
				answer = true;
			}else if(string.equalsIgnoreCase("no")){
				inputValid = true;
				answer = false;
			}
			infoPanel.displayString("> " + string);
			if (!inputValid) {
				displayError(ERR_SYNTAX);
			}

		} while (!inputValid);
		return answer;
	}

	public int numberOfBots(){
		boolean inputValid = false;
		int inputNumber = 0;
		infoPanel.displayString("How many bots? Enter between 1 and "+ (Monopoly.NUM_PLAYERS-1) + " please");
		do {
			commandPanel.inputString();
			string = commandPanel.getString();
			string = string.trim();
			if ( string.matches("[0-9]+")){
				inputNumber = Integer.parseInt(string);
				if(inputNumber>= 1 && inputNumber<=(Monopoly.NUM_PLAYERS-1)){
					inputValid = true;
				}
			}

			infoPanel.displayString("> " + string);
			
			if (!inputValid) {
				displayError(ERR_SYNTAX);
			}

		} while (!inputValid);

		return inputNumber;
	}


	private boolean hasNoArgument (String[] words) {
		return (words.length == 1);
	}

	private boolean hasOneArgument (String[] words) {
		return (words.length == 2);
	}	


	public void inputCommand (Player player) {
		boolean inputValid = false;
		do {
			
			
			infoPanel.displayString(player + " type your command:");
			
			
			if(player.isBot()){
				System.out.println("person chosen for input is " +player.getBotNumber()+ " "+ player.getIdentifier());
				string = bots[player.getBotNumber()].getCommand();
			}
			else{
				commandPanel.inputString();
				string = commandPanel.getString();
			}
			infoPanel.displayString("> " + string);
			string = string.toLowerCase();
			string = string.trim();
			string = string.replaceAll("( )+", " ");
			String[] words = string.split(" ");
			int num_index=0;
			boolean found = false;
			String prop_name= "bob";
			for(int i=words.length-1;i>0&&!found;i--){
				if(words[i].matches("[0-9]+")){
					found = true;
					num_index = i;
				}
			}

			switch (words[0]) {
			case "quit" :
				commandId = CMD_QUIT;
				inputValid = hasNoArgument(words);
				break;
			case "done" :
				commandId = CMD_DONE;
				inputValid = hasNoArgument(words);
				break;
			case "roll" :
				commandId = CMD_ROLL;
				inputValid = hasNoArgument(words);
				break;
			case "buy" :
				commandId = CMD_BUY;
				inputValid = hasNoArgument(words);
				break;
			case "property" :
				commandId = CMD_PROPERTY;
				inputValid = hasNoArgument(words);
				break;
			case "balance" :
				commandId = CMD_BALANCE;
				inputValid = hasNoArgument(words);
				break;
			case "bankrupt" :
				commandId = CMD_BANKRUPT;
				inputValid = hasNoArgument(words);
				break;
			case "mortgage" :
				commandId = CMD_MORTGAGE;

				prop_name = words[1];
				if(words.length>2){
					int i=2;
					while(i<words.length){
						prop_name += " " + words[i];
						i++;
					}
				}

				if (board.isProperty(prop_name)) { 
					inputProperty = board.getProperty(prop_name);
					inputValid = true;
				} else {
					inputValid = false;
				}
				break;
			case "redeem" :
				commandId = CMD_REDEEM;

				prop_name = words[1];
				if(words.length>2){
					int i=2;
					while(i<words.length){
						prop_name += " " + words[i];
						i++;
					}
				}

				if (board.isProperty(prop_name)) { 
					inputProperty = board.getProperty(prop_name);
					inputValid = true;
				} else {
					inputValid = false;
				}
				break;
			case "build" :
				commandId = CMD_BUILD;

				prop_name = words[1];
				if(words.length>3){
					int i=2;
					while(!words[i].equalsIgnoreCase("1")){
						prop_name += " " + words[i];
						i++;
					}
				}

				if (board.isSite(prop_name) && words[num_index].matches("[0-9]+")) { 
					inputProperty = board.getProperty(prop_name);
					inputNumber = Integer.parseInt(words[num_index]);
					inputValid = true;
				} else {
					inputValid = false;
				}
				break;
			case "demolish" :
				commandId = CMD_DEMOLISH;

				prop_name = words[1];
				if(words.length>3){
					int i=2;
					while(!words[i].equalsIgnoreCase("1")){
						prop_name += " " + words[i];
						i++;
					}
				}

				if (board.isSite(prop_name) && words[num_index].matches("[0-9]+")) { 
					inputProperty = board.getProperty(prop_name);
					inputNumber = Integer.parseInt(words[num_index]);
					inputValid = true;
				} else {
					inputValid = false;
				}
				break;		
			case "card":
				commandId = CMD_CARD;
				inputValid = true;
				break;
			case "pay":
				commandId = CMD_PAY;
				inputValid = true;
				break;
			case "help" :
				commandId = CMD_HELP;
				inputValid = hasOneArgument(words);
				inputValid = true;
				break;
			default:
				inputValid = false;
			}
			if (!inputValid) {
				displayError(ERR_SYNTAX);
			}
		} while (!inputValid);
		if (commandId == CMD_DONE) {
			done = true;
		} else {
			done = false;
		}		
		return;
	}

	public String getString () {
		return string; 
	}

	public String getTokenName (int tokenId) {
		return boardPanel.getTokenName(tokenId);
	}

	public int getCommandId () {
		return commandId;
	}

	public boolean isDone () {
		return done;
	}

	public PrivateProperty getInputProperty () {
		return inputProperty;
	}

	public Player getInputPlayer () {
		return inputPlayer;
	}

	public int getInputNumber () {
		return inputNumber;
	}

	public void inputPayOrChance (Player player) {
		boolean inputValid = false;
		do {
			infoPanel.displayString(player + " type pay or chance:");
			commandPanel.inputString();
			string = commandPanel.getString();
			infoPanel.displayString("> " + string);
			string = commandPanel.getString();
			string = string.toLowerCase();
			string = string.trim();
			switch (string) {
			case "pay":
				inputWasPay = true;
				inputValid = true;
				break;
			case "chance":
				inputWasPay = false;
				inputValid = true;
				break;
			}
			if (!inputValid) {
				displayError(ERR_SYNTAX);
			}
		} while (!inputValid);
		return;
	}

	public boolean inputWasPay () {
		return inputWasPay;
	}

	// DISPLAY METHODS

	public void display () {
		boardPanel.refresh();
		return;
	}

	public void displayString (String string) {
		infoPanel.displayString(string);
		return;
	}

	public void displayBankTransaction (Player player) {
		if (player.getTransaction() > 0) {
			infoPanel.displayString(player + " receives " + player.getTransaction() + CURRENCY + " from the bank.");
		} else if (player.getTransaction() == 0) {
			infoPanel.displayString("No money is transferred.");			
		}
		else {
			infoPanel.displayString(player + " pays " + (-player.getTransaction()) + CURRENCY + " to the bank.");			
		}
		return;
	}

	public void displayTransaction (Player fromPlayer, Player toPlayer) {
		infoPanel.displayString(fromPlayer + " pays " + toPlayer.getTransaction() + CURRENCY + " to " + toPlayer + ".");
		return;
	}

	public void displayDice (Player player, Dice dice) {
		infoPanel.displayString(player + " rolls " + dice + ".");
		return;
	}

	public void displayRollDraw () {
		infoPanel.displayString("Draw");
		return;
	}

	public void displayRollWinner (Player player) {
		infoPanel.displayString(player + " wins the roll.");
		return;
	}

	public void displayGameOver () {
		infoPanel.displayString("GAME OVER");
		return;
	}

	public void displayCommandHelp () {
		infoPanel.displayString("Available commands: roll, buy, pay rent, build, demolish, mortgage, redeem, bankrupt, property, balance, done, quit. ");
		infoPanel.displayString("Available commands in jail: roll, card, pay. ");
		return;
	}

	public void displayBalance (Player player) {
		infoPanel.displayString(player + "'s balance is " + player.getBalance() + CURRENCY + ".");
		return;
	}

	public void displayError (int errorId) {
		infoPanel.displayString(errorMessages[errorId]);
		return;
	}

	public void displayPassedGo (Player player) {
		infoPanel.displayString(player + " passed Go.");
		return;
	}

	public void displayLatestProperty (Player player) {
		infoPanel.displayString(player + " bought " + player.getLatestProperty() + ".");
	}

	public void displayProperty (Player player) {
		ArrayList<PrivateProperty> propertyList = player.getProperties();
		if (propertyList.size() == 0) {
			infoPanel.displayString(player + " owns no property.");
		} else {
			infoPanel.displayString(player + " owns the following property...");
			for (PrivateProperty p : propertyList) {
				String mortgageStatus = "";
				if (p.isMortgaged()) {
					mortgageStatus = ", is mortgaged";
				}
				if (p instanceof InvestmentProperty) {
					InvestmentProperty site = (InvestmentProperty) p;
					String buildStatus = "";
					if (site.getNumBuildings()==0) {
						buildStatus = "with no buildings";
					} else if (site.getNumBuildings()==1) {
						buildStatus = "with 1 house";
					} else if (site.getNumBuildings()<5) {
						buildStatus = "with " + site.getNumBuildings() + " houses";
					} else if (site.getNumBuildings()==5) {
						buildStatus = "with a hotel";
					}
					infoPanel.displayString(site + " (" + site.getColourGroup().getName() + "), rent " + site.getRentalAmount() + CURRENCY + ", " + buildStatus + mortgageStatus + ".");		
				} else if (p instanceof Vehicle) {
					infoPanel.displayString(p + ", rent " + ((Vehicle) p).getRent() + CURRENCY + mortgageStatus + ".");	
				} else if (p instanceof Utility) {
					infoPanel.displayString(p + ", rent " + ((Utility) p).getRentMultiplier() + " times dice" + mortgageStatus + ".");
				}
			}
		}
	}

	public void displaySquare (Player player) {
		NamedLocation square = board.getSquare(player.getPosition());
		infoPanel.displayString(player + " arrives at " + square.getIdentifier() + ".");
		if (square instanceof PrivateProperty) {
			PrivateProperty property = (PrivateProperty) square;
			if (property.isOwned()) {
				infoPanel.displayString("The property is owned by " + property.getOwner() + ".");				
			} else {
				infoPanel.displayString("The property is not owned.");								
			}
		}
		return;
	}

	public void displayBuild (Player player, InvestmentProperty site, int numUnits) {
		if (numUnits==1) {
			infoPanel.displayString(player + " builds 1 unit on " + site + ".");			
		} else {
			infoPanel.displayString(player + " builds " + numUnits + " units on " + site + ".");
		}
		return;
	}

	public void displayDemolish (Player player, InvestmentProperty site, int numUnits) {
		if (numUnits==1) {
			infoPanel.displayString(player + " demolishes 1 unit on " + site + ".");			
		} else {
			infoPanel.displayString(player + " demolishes " + numUnits + " units on " + site + ".");
		}
		return;
	}	

	public void displayBankrupt (Player player) {
		infoPanel.displayString(player + " is bankrupt.");
		return;
	}

	public void displayMortgage (Player player, PrivateProperty property) {
		infoPanel.displayString(player + " mortgages " + property + " for " + property.getMortgageAmount() + CURRENCY + ".");
		return;				
	}

	public void displayMortgageRedemption (Player player, PrivateProperty property) {
		infoPanel.displayString(player + " redeems " + property + " for " + property.getMortgagePrice() + CURRENCY + ".");
		return;
	}

	public void displayAssets (Player player) {
		infoPanel.displayString(player + " has assets of " + player.getNetWorth() + CURRENCY + ".");
		return;
	}

	public void displayWinner (Player player) {
		infoPanel.displayString("The winner is " + player + ".");
		return;
	}

	public void displayDraw (ArrayList<Player> players) {
		infoPanel.displayString("The following players drew the game " + players + ".");
		return;
	}

	public void displayCard (Card card) {
		infoPanel.displayString("The card says: " + card);
		return;
	}

	public void displayThreeDoubles (Player player) {
		infoPanel.displayString(player + " rolled three doubles. Go to Jail.");
		return;
	}

	public void displayFreeFromJail (Player player) {
		infoPanel.displayString(player + " is free from jail.");
		return;
	}

	public void displayJailFine (Player player, int fine) {
		infoPanel.displayString(player + " pays fine of " + fine + CURRENCY + " to leave jail.");
		return;
	}
}
