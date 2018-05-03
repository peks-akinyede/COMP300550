import panopoly.GenerateCards;

public class ChanceDeck extends CardDeck {
	
	public static final int CHANCE_CARD = 0;
	GenerateCards pan =  new GenerateCards();
	ChanceDeck () {

		// Movement
		cards.add(new Card(CHANCE_CARD,"Advance to Go." + pan.MovementCards(),ACT_GO_FORWARD,Board.POS_GO));
		cards.add(new Card(CHANCE_CARD,pan.MovementCards()+"Pall Mall. If you pass Go collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GO_FORWARD,Board.POS_PALL_MALL));
		cards.add(new Card(CHANCE_CARD,pan.MovementCards() + " to Marylebone Station and if you pass Go collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GO_FORWARD,Board.POS_MARYLEBONE_STATION));
		cards.add(new Card(CHANCE_CARD,pan.MovementCards()+ " Trafalgar Square. If you pass Go collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GO_FORWARD,Board.POS_TRAFALGAR_SQ));
		cards.add(new Card(CHANCE_CARD,pan.MovementCards() + "Mayfair. If you pass Go collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GO_FORWARD,Board.POS_MAYFAIR));
		cards.add(new Card(CHANCE_CARD,pan.MovementCards() + "Go back three sqaures",ACT_MOVE,-3	));
		
		// Fines
		cards.add(new Card(CHANCE_CARD,pan.fineCard()+ " For each house pay " + UI.CURRENCY_SYMBOL + "25. For each hotel pay " + UI.CURRENCY_SYMBOL + "100.",ACT_PAY_HOUSES,new int[] {25,100}));
		cards.add(new Card(CHANCE_CARD,pan.fineCard()+ "Pay: " + UI.CURRENCY_SYMBOL + " 40 per house, " + UI.CURRENCY_SYMBOL + "115 per hotel.",ACT_PAY_HOUSES,new int[] {40,115}));
		cards.add(new Card(CHANCE_CARD,pan.fineCard()+ "Pay school fees of " + UI.CURRENCY_SYMBOL + "150.",ACT_PAY,150));
		cards.add(new Card(CHANCE_CARD,"Drunk in charge fine. " + UI.CURRENCY_SYMBOL + "20.",ACT_PAY,20));
		cards.add(new Card(CHANCE_CARD,"Speeding fine. " + UI.CURRENCY_SYMBOL + "15.",ACT_PAY,15));
		
		// Payments
		cards.add(new Card(CHANCE_CARD,pan.RewardCard() + "Your building loan matures. Receive " + UI.CURRENCY_SYMBOL + "150.",ACT_RECEIVE,150));
		cards.add(new Card(CHANCE_CARD,pan.RewardCard() + "You have won a crossword competition. Collect " + UI.CURRENCY_SYMBOL + "100.",ACT_RECEIVE,100));
		cards.add(new Card(CHANCE_CARD,pan.RewardCard()+ "Bank pays you divided of " + UI.CURRENCY_SYMBOL + "50.",ACT_RECEIVE,50));

		// Jail
		cards.add(new Card(CHANCE_CARD,pan.goToJail() + "Go to jail. Move directly to jail. Do not pass Go. Do not collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GOTO_JAIL));
		cards.add(new Card(CHANCE_CARD,pan.GetOutOfJaillCard()+ "Get out of jail free.",ACT_GET_OUT_OF_JAIL));

		shuffle();
		
		return;
	}

}