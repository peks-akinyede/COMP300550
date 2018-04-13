
public class Monopoly {
	
	private static final int START_MONEY = 1500;
	private static final int GO_MONEY = 200;
	private static final int JAIL_FINE = 50;
	
	private Players players = new Players();
	private Player currPlayer;
	private Dice dice = new Dice();
	private Board board = new Board(dice);
	private UI ui = new UI(players, board);
	private ChanceDeck chanceDeck = new ChanceDeck();
	private CommunityChestDeck communityChestDeck = new CommunityChestDeck();
	private boolean gameOver = false;
	private boolean onlyOneNotBankrupt = false;
	private boolean turnFinished;
	private boolean rollDone;
	private int doubleCount;
	
	Monopoly () {
		ui.display();
		return;
	}

	
}
