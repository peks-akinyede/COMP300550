public class CommunityChestDeck extends CardDeck {
	GenerateCards pan =  new GenerateCards();
	public static final int COMMUNITY_CHEST = 1;
	CommunityChestDeck () {
		

		// Movement
		cards.add(new Card(COMMUNITY_CHEST,"Advance to Go."+pan.MovementCards()+"forword to",ACT_GO_FORWARD,Board.POS_GO));
		cards.add(new Card(COMMUNITY_CHEST,pan.MovementCards()+ "back to",ACT_GO_BACKWARD,Board.POS_OLD_KENT_RD));

		// Taxes, Fines, etc.
		cards.add(new Card(COMMUNITY_CHEST,pan.fineCard()+" Pay fine of" +  UI.CURRENCY_SYMBOL + "100.",ACT_PAY,100));
		cards.add(new Card(COMMUNITY_CHEST,pan.fineCard()+" Pay fine of" + UI.CURRENCY_SYMBOL + "50.",ACT_PAY,50));
		cards.add(new Card(COMMUNITY_CHEST,pan.fineCard()+" Pay fine of" + UI.CURRENCY_SYMBOL + "50.",ACT_PAY,50));
		cards.add(new Card(COMMUNITY_CHEST,"Pay a  " + UI.CURRENCY_SYMBOL + "10 fine or take a Chance.",ACT_PAY_OR_CHANCE,10));

		// Receipts
		cards.add(new Card(COMMUNITY_CHEST,pan.RewardCard() + UI.CURRENCY_SYMBOL + "200.",ACT_RECEIVE,200));
		cards.add(new Card(COMMUNITY_CHEST,pan.RewardCard() + UI.CURRENCY_SYMBOL + "100.",ACT_RECEIVE,100));
		cards.add(new Card(COMMUNITY_CHEST,pan.RewardCard() + UI.CURRENCY_SYMBOL + "100.",ACT_RECEIVE,100));
		cards.add(new Card(COMMUNITY_CHEST,pan.RewardCard() + UI.CURRENCY_SYMBOL + "50.",ACT_RECEIVE,50));
		cards.add(new Card(COMMUNITY_CHEST,pan.RewardCard() + UI.CURRENCY_SYMBOL + "25.",ACT_RECEIVE,25));
		cards.add(new Card(COMMUNITY_CHEST,pan.RewardCard() + UI.CURRENCY_SYMBOL + "20.",ACT_RECEIVE,20));
		cards.add(new Card(COMMUNITY_CHEST,pan.RewardCard() + UI.CURRENCY_SYMBOL + "10.",ACT_RECEIVE,10));
		cards.add(new Card(COMMUNITY_CHEST,pan.RewardCard() + UI.CURRENCY_SYMBOL + "10 from each player",ACT_GIFTS,10));

		// Jail
		cards.add(new Card(COMMUNITY_CHEST,pan.goToJail()+"Go to jail. Move directly to jail. Do not pass Go. Do not collect " + UI.CURRENCY_SYMBOL + "200.",ACT_GOTO_JAIL));
		cards.add(new Card(COMMUNITY_CHEST,pan.GetOutOfJaillCard()+". This card may be kept until needed or sold",ACT_GET_OUT_OF_JAIL));
		
		return;
	}

}

