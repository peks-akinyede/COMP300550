//package tonyInterfaces;
//
//public class Main {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Bank bank = new Bank();
//		TaxableProperty tax = new TaxableProperty("Tax", null, null, 20, 500);
//		NamedLocation jail = new NamedLocation("Jail", null, null);
//		//All properties owned by bank initially
//		PrivateProperty a = new PrivateProperty("Magda's house", 100, tax, jail);
//		a.setOwner(bank);
//		RentalProperty b = new RentalProperty("Aminat's house", 300, 50, null, tax);
//		b.setOwner(bank);
//		InvestmentProperty c = new InvestmentProperty("Sarah's house", 200, 30, jail, b);
//		c.setOwner(bank);
//		
//		//Setting the left and right of the properties
//		tax.setRight(a);
//		tax.setLeft(b);
//		jail.setLeft(a);
//		jail.setRight(c);
//		b.setLeft(c);
//		
//		System.out.println(tax.getIdentifier() + " is a Taxable property and has tax percent:" + tax.getIncomePercentage()
//		+ " and flat amount:" + tax.getFlatAmount()+" and has "+ tax.goLeft().getIdentifier()+
//				" to it's left and "+ tax.goRight().getIdentifier() + " to it's right\n");
//		System.out.println(jail.getIdentifier() + " is a Named location and has "+ jail.goLeft().getIdentifier()+
//				" to it's left and "+ jail.goRight().getIdentifier() + " to it's right\n");
//		System.out.println(a.getIdentifier()+" is a private property, costs " + a.getSalePrice()+
//				" and has "+ a.goLeft().getIdentifier()+
//				" to it's left and "+ a.goRight().getIdentifier() + " to it's right\n");
//		System.out.println(b.getIdentifier()+" is a rentable property, costs " + b.getSalePrice()+
//				" has rent "+ b.getRentalAmount()+" has mortgage "+b.getMortgageAmount()+" and has "+ b.goLeft().getIdentifier()+
//				" to it's left and "+ b.goRight().getIdentifier() + " to it's right\n");
//		System.out.println(c.getIdentifier()+" is an investment property, costs " + c.getSalePrice()+
//				" has rent "+ c.getRentalAmount()+" has mortgage "+c.getMortgageAmount()+" and has "+ c.goLeft().getIdentifier()+
//				" to it's left and "+ c.goRight().getIdentifier() + " to it's right\n");
//		
//		//Buying 1 house and 1 hotel
//		c.buyHotel();
//		c.buyHotel();
//		System.out.println(c.getIdentifier()+ " has rent " + c.getRentalAmount()+ " after purchase of houses and hotels\n");
//		
//		System.out.println("All properties are owned by the bank right now");
//		System.out.println(a.getIdentifier() + " is owned by: " + a.getOwner().getIdentifier());
//		System.out.println(b.getIdentifier() + " is owned by: " + b.getOwner().getIdentifier());
//		System.out.println(c.getIdentifier() + " is owned by: " + c.getOwner().getIdentifier());
//		
//	}
//	
//
//
//}

// public class Main {
// 	public static void main (String args[]) {	
// 		Monopoly monopoly = new Monopoly();		
// 		monopoly.inputNames();
// 		monopoly.giveStartMoney();
// 		monopoly.decideStarter();
// 		do {
// 			monopoly.processTurn();
// 			if (!monopoly.isGameOver()) {
// 				monopoly.nextPlayer();
// 			}
// 		} while (!monopoly.isGameOver());
// 		monopoly.decideWinner();
// 		monopoly.displayGameOver();
// 		return;
// 	}
// }
