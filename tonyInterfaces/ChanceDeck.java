package tonyInterfaces;

public class ChanceDeck extends CardDeck {
	
	public static final int CHANCE_CARD = 0;
	GenerateCards pan =  new GenerateCards();
	ChanceDeck () {

		// Movement
		cards.add(new Card(CHANCE_CARD,pan.MovementCards()+ " You've advanced to Go " ,ACT_GO_FORWARD,WorldBuilder.POS_GO));
		cards.add(new Card(CHANCE_CARD,pan.MovementCards()+" If you pass Go collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GO_FORWARD,WorldBuilder.POS_PALL_MALL));
		cards.add(new Card(CHANCE_CARD,pan.MovementCards() + " if you pass Go collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GO_FORWARD,WorldBuilder.POS_MARYLEBONE_STATION));
		cards.add(new Card(CHANCE_CARD,pan.MovementCards()+ " If you pass Go collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GO_FORWARD,WorldBuilder.POS_TRAFALGAR_SQ));
		cards.add(new Card(CHANCE_CARD,pan.MovementCards() + " If you pass Go collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GO_FORWARD,WorldBuilder.POS_MAYFAIR));
		cards.add(new Card(CHANCE_CARD,pan.MovementCards() + " back three sqaures",ACT_MOVE,-3	));
		
		// Fines
		cards.add(new Card(CHANCE_CARD,pan.fineCard()+ " For each house pay " + UI.CURRENCY_SYMBOL + "25. For each hotel pay " + UI.CURRENCY_SYMBOL + "100.",ACT_PAY_HOUSES,new int[] {25,100}));
		cards.add(new Card(CHANCE_CARD,pan.PoliticalCard()+ " Pay: " + UI.CURRENCY_SYMBOL + " 40 per house, " + UI.CURRENCY_SYMBOL + "115 per hotel.",ACT_PAY_HOUSES,new int[] {40,115}));
		cards.add(new Card(CHANCE_CARD,pan.fineCard()+ " Send large donation to charity to show you're really really sorry :-) " + UI.CURRENCY_SYMBOL + "150.",ACT_PAY,150));
		cards.add(new Card(CHANCE_CARD, pan.intoxicated() + " Pay UI.CURRENCY_SYMBOL" + " 20.",ACT_PAY,20));
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
	public static void main (String args[]) {	
		System.out.println(CHANCE_CARD);
	}

}
