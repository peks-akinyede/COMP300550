package panopoly;


import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
//import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
@SuppressWarnings({"unused","rawtypes","unchecked"})
public class GenerateCards {

	private KnowledgeBaseModule NOC          ;

	private KnowledgeBaseModule CATEGORIES   = null;
	private KnowledgeBaseModule CLOTHES      = null;
	private KnowledgeBaseModule CREATIONS    = null;
	private KnowledgeBaseModule DOMAINS      = null;
	private KnowledgeBaseModule WORLDS       = null;
	private KnowledgeBaseModule VEHICLES     = null;
	private KnowledgeBaseModule WEAPONS	     = null;
	private KnowledgeBaseModule PLACES       = null;
	private KnowledgeBaseModule SUPERLATIVES = null;
	private KnowledgeBaseModule COMPARATIVES = null;
	private KnowledgeBaseModule ANTONYMS	 = null;
	private KnowledgeBaseModule PAST_PERFECTS= null;


	private Vector allPeople				 = null;
	private Vector fictionalPeople			 = null;
	private Vector realPeople				 = null;
	private Vector men						 = null;
	private Vector women					 = null;
	private Hashtable NEG_QUALITIES 		 = null;
	private Hashtable POS_QUALITIES 		 = null;
	private Hashtable ALL_QUALITIES 		 = null;
	private Vector attributeFields 			 = null;
	private Vector allFields	 			 = null;
	private String knowledgeDir				 = null;




	public GenerateCards()
	{
		knowledgeDir = "C:\\Users\\robert\\Documents\\.Year 3_Part2\\software eng3\\noc\\TSV Lists\\";
		//String knowledgeDir				 = null;   // directory where knowledge-base(s) can be found
		NOC           = new KnowledgeBaseModule(knowledgeDir + "Veale's The NOC List.txt", 0);
		//	CATEGORIES    = new KnowledgeBaseModule(knowledgeDir + "Category Hierarchy.txt", 0);

		CLOTHES       = new KnowledgeBaseModule(knowledgeDir + "clothing line.txt", 1);  // 1 is the column number of the key value
		//		CREATIONS     = new KnowledgeBaseModule(knowledgeDir + "Veale's creations.txt", 0);
		//		DOMAINS       = new KnowledgeBaseModule(knowledgeDir + "Veale's domains.txt", 0);
		//		WORLDS        = new KnowledgeBaseModule(knowledgeDir + "Veale's fictional worlds.txt", 0);
		VEHICLES      = new KnowledgeBaseModule(knowledgeDir + "vehicle fleet.txt", 1);  // 1 is the column number of the key value
		//		WEAPONS	      = new KnowledgeBaseModule(knowledgeDir + "Veale's weapon arsenal.txt", 1);  // 1 is the column number of the key value
		//		PLACES        = new KnowledgeBaseModule(knowledgeDir + "Veale's place elements.txt", 0);		
		//		SUPERLATIVES  = new KnowledgeBaseModule(knowledgeDir + "superlatives.txt", 0);
		//		COMPARATIVES  = new KnowledgeBaseModule(knowledgeDir + "comparatives.txt", 0);
		//		ANTONYMS	  = new KnowledgeBaseModule(knowledgeDir + "antonyms.txt", 0);
		//		PAST_PERFECTS = new KnowledgeBaseModule(knowledgeDir + "past perfects.txt", 0);
		//		POS_QUALITIES = NOC.getInvertedField("Positive Talking Points");
		//		NEG_QUALITIES = NOC.getInvertedField("Negative Talking Points");
		//		ALL_QUALITIES = NOC.getInvertedField("Positive Talking Points");
		//		ALL_QUALITIES = NOC.getInvertedField("Negative Talking Points", ALL_QUALITIES);
		//		
		allPeople       = NOC.getAllFrames();

		fictionalPeople = NOC.getAllKeysWithFieldValue("Fictive Status", "fictional");
		realPeople      = NOC.difference(allPeople, fictionalPeople);

		men			    = NOC.getAllKeysWithFieldValue("Gender", "male");
		women			= NOC.getAllKeysWithFieldValue("Gender", "female");

		allFields		= NOC.getFieldNames();

		attributeFields = new Vector();
		attributeFields.add("Negative Talking Points");
		attributeFields.add("Positive Talking Points");	



	}
	public  String MovementCards(){
		String outPut = null;
		int i = new Random().nextInt(822);
		Vector ID = NOC.getAllFrames();
		String Character =null;
		for (int e = 0; e < ID.size(); e++)
		{
			Character   = (String)ID.elementAt(i);
			String pronoun    = "he";
			String possPro	  = "his";
			if (NOC.hasFieldValue("Gender", Character, "female"))
			{
				pronoun = "she";
				possPro = "her";
			}
			Vector vehicles   = NOC.getFieldValues("Vehicle of Choice", Character);
			String comesAlong = null;
			if (vehicles == null) {
				comesAlong = Character + " comes along and " +pronoun+ " gives you piggy back ride to";
				outPut  = comesAlong;
			}
			if (vehicles != null) 
			{
				for (int v = 0; v < vehicles.size(); v++)
				{
					String vehicle = (String)vehicles.elementAt(v);
					String determiner = VEHICLES.getFirstValue("Determiner", vehicle);
					String affordance = VEHICLES.getFirstValue("Affordances", vehicle);
					comesAlong   = Character + " comes along "+ affordance +" "+determiner+" "+ vehicle +" and " +pronoun+ " brings you ";

					outPut  = comesAlong;
				}
			}
		}
		return outPut;


	}
	public  String fineCard(){
		String outPut = null;
		String Character =null;
		Character = EvilReward();
		String pronoun    = "he";
		String possPro	  = "his";
		if (NOC.hasFieldValue("Gender", Character, "female"))
		{
			pronoun = "she";
			possPro = "her";
		}
		Vector vehicles   = NOC.getFieldValues("Vehicle of Choice", Character);
		Vector actions    = NOC.getFieldValues("Typical Activity", Character);

		String Activity = null;
		if (actions == null ){
			Activity = "You have been caught plotting schemes with "+Character+ ".";
			outPut = Activity;
		}else
			for (int a = 0; a < actions.size(); a++)
			{
				String action = (String)actions.elementAt(a);
				Activity   = "You have been caught " + action + " with "+Character+ ".";
				outPut = Activity;
			}
		

		return outPut + Character;
	}
	public  String RewardCard(){
		String outPut = null;
		String Character =null;
		int rnd = new Random().nextInt(2);
		if (rnd == 0) {
			Character = EvilReward();
		}else {
			Character = GoodReward();
			
		}

		String pronoun    = "he";
		String possPro	  = "his";
		if (NOC.hasFieldValue("Gender", Character, "female"))
		{
			pronoun = "she";
			possPro = "her";
		}
		Vector vehicles   = NOC.getFieldValues("Vehicle of Choice", Character);
		Vector actions    = NOC.getFieldValues("Typical Activity", Character);

		String Activity = null;
		if (actions == null ){
			Activity = "You have begging on the streets ";
		}else
			for (int a = 0; a < actions.size(); a++)
			{
				String action = (String)actions.elementAt(a);
				Activity   = "You have been " + action + ".";
			}
		String comesAlong = null;
		if (vehicles == null) {
			comesAlong = Character + " comes along and " +pronoun+ " gives you ";
			outPut  = Activity + " "+ comesAlong;
			;}
		if (vehicles != null) 
		{
			for (int v = 0; v < vehicles.size(); v++)
			{
				String vehicle = (String)vehicles.elementAt(v);
				String det = VEHICLES.getFirstValue("Determiner", vehicle);
				String aff = VEHICLES.getFirstValue("Affordances", vehicle);
				comesAlong   = Character + " comes along "+ aff +" "+det+" "+ vehicle +" and " +pronoun+ " gives you ";
				outPut  = Activity + " " + comesAlong;
			}
		}

		return outPut;
	}

	private String GoodReward()
	{
		String Character = null;
		Vector goodTraitOne = NOC.getAllKeysWithFieldValue("Positive Talking Points", "noble");
		Vector goodTraitTwo = NOC.getAllKeysWithFieldValue("Positive Talking Points", "heroic");
		Vector goodTraitThree = NOC.getAllKeysWithFieldValue("Positive Talking Points", "honorable");
		Vector goodTraitFour = NOC.getAllKeysWithFieldValue("Positive Talking Points", "honest");
		Vector goodTraitFive = NOC.getAllKeysWithFieldValue("Positive Talking Points", "incorruptable");
		Set<String> set = new HashSet<>(goodTraitOne);
		set.addAll(goodTraitTwo);
		set.addAll(goodTraitThree);
		set.addAll(goodTraitFour);
		set.addAll(goodTraitFive);
		Vector<String> goodpeople = new Vector<>(set);
		int rnd = new Random().nextInt(goodpeople.size());
		for (int e = 0; e < goodpeople.size(); e++)
		{
			Character   = (String)goodpeople.elementAt(rnd);
		}
		return Character;
	}

	private String EvilReward()
	{
		String Character = null;
		Vector badTraitOne = NOC.getAllKeysWithFieldValue("Negative Talking Points", "evil");
		Vector badTraitTwo = NOC.getAllKeysWithFieldValue("Negative Talking Points", "cruel");
		Vector badTraitThree = NOC.getAllKeysWithFieldValue("Negative Talking Points", "murderous");
		Vector badTraitFour = NOC.getAllKeysWithFieldValue("Negative Talking Points", "sadistic");
		Vector badTraitFive = NOC.getAllKeysWithFieldValue("Negative Talking Points", "racist");
		Set<String> set = new HashSet<>(badTraitOne);
		set.addAll(badTraitTwo);
		set.addAll(badTraitThree);
		set.addAll(badTraitFour);
		set.addAll(badTraitFive);
		Vector<String> badpeople = new Vector<>(set);
		int rnd = new Random().nextInt(badpeople.size());
		for (int e = 0; e < badpeople.size(); e++)
		{
			Character   = (String)badpeople.elementAt(rnd);
		}
		return Character;
	}

	public String GetOutOfJaillCard(){
		String Activity =null;
		Vector ID = NOC.getAllFrames();
		String Character   = (String)ID.elementAt(805);
		Vector actions    = NOC.getFieldValues("Typical Activity", Character);
		for (int a = 0; a < actions.size(); a++)
		{
			String action = (String)actions.elementAt(a);
			Activity   = " is adept at " + action ;
		}

		return Character + Activity + ", call him if you're stuck";
	}
	public String goToJail () {
		String output = null;
		String nemesis = null;
		String c = null;
		Vector cops = NOC.getAllKeysWithFieldValue("Category", "Policeman");
		Vector Detectives = NOC.getAllKeysWithFieldValue("Category", "Detective");
		Set<String> set = new HashSet<>(cops);
		set.addAll(Detectives);
		Vector<String> Heros = new Vector<>(set);

		int rnd = new Random().nextInt(Heros.size());
		String Character = (String)Heros.get(rnd);
		Vector enemy = NOC.getFieldValues("Opponent", Character );
		if(enemy == null) { 
			Vector EvilPeople = NOC.getAllKeysWithFieldValue("Negative Talking Points", "evil");
			int r = new Random().nextInt(EvilPeople.size());
			nemesis = (String)EvilPeople.get(r);

		}else
			try {
				for (int a = 0; a < enemy.size(); a++)
				{
					nemesis   = (String)enemy.elementAt(0);
				}
			}catch (Exception e) {
			}
		try {
			Vector Crime = NOC.getFieldValues("Typical Activity", nemesis );
			if(Crime == null) {

				Vector EvilPeople = NOC.getAllKeysWithFieldValue("Negative Talking Points", "evil");
				int r = new Random().nextInt(EvilPeople.size());
				c = (String)EvilPeople.get(r);
				Crime = NOC.getFieldValues("Typical Activity", c );
				for (int a = 0; a < Crime.size(); a++)
				{
					int ra = new Random().nextInt(Crime.size());
					c   = (String)Crime.elementAt(ra);
				}
			}else
				for (int a = 0; a < Crime.size(); a++)
				{
					int r = new Random().nextInt(Crime.size());
					c   = (String)Crime.elementAt(r);
				}
		}catch (Exception e) {
		}

		output = "You are arrested by " + Character +" for "+ c  +" with "+ nemesis;
		return  output;
	}
	public String quiz() {
		Vector ID = NOC.getAllFrames();
		String Character =null;
		int i = new Random().nextInt(822);
		for (int e = 0; e < ID.size(); e++)
		{
			Character   = (String)ID.elementAt(i);//509
		}
		String outfit= getClothingEnsemble(Character);
		Vector gender = NOC.getFieldValues("Gender", Character);
		String Gender = (String)gender.elementAt(0);
		String action = getA(Character);

		System.out.println(" " + Character);
		System.out.println("you are approached by a "+Gender +" wearing " + outfit + " talking about " +action  );
		System.out.println("who is it?");
		Scanner scan = new Scanner(System.in);
		String Answer = scan.nextLine();


		if(Answer.equalsIgnoreCase(Character)) {
			System.out.println("correct you have won money");
		}else
		{System.out.println("wrong");

		}

		;
		return ("It is "+Character );
	}
	private String getA(String Character){
		Vector actions    = NOC.getFieldValues("Typical Activity", Character);

		String Activity = null;
		if (actions == null ){

			Activity = "you are doing in particular";
		}else

			for (int a = 0; a < actions.size(); a++)
			{
				String action = (String)actions.elementAt(a);

				Activity   = "" + action + ".";
			}
		return Activity;
	}
	private String getClothingEnsemble(String person)
	{
		Vector ensembles = getClothingEnsembles(person);

		if (ensembles == null || ensembles.size() == 0)
			return "nothing in particular";

		if (ensembles.size() == 1)
			return (String)ensembles.elementAt(0);

		return (String)ensembles.elementAt(new Random().nextInt(ensembles.size()));
	}
	private Vector getClothingEnsembles(String person)
	{
		Vector clothes = NOC.getFieldValues("Seen Wearing", person);
		if (clothes == null || clothes.size() == 0) 
			return null;

		Vector ensembles = new Vector();

		if (clothes.size() == 1)
			ensembles.add(quantifyClothing((String)clothes.elementAt(0)));


		for (int i = 0; i < clothes.size(); i++)
		{
			String item1 = (String)clothes.elementAt(i);

			for (int j = 0; j < clothes.size(); j++)
			{
				if (i == j) continue;

				String item2 = (String)clothes.elementAt(j);

				Vector overlap = NOC.intersect(CLOTHES.getFieldValues("Covers", item1), CLOTHES.getFieldValues("Covers", item2));

				if (overlap == null || overlap.size() == 0)
					ensembles.add(quantifyClothing(item1) + " and " + quantifyClothing(item2));
			}
		}

		return ensembles;
	}
	private String quantifyClothing(String clothing)
	{
		String det = CLOTHES.getFirstValue("Determiner", clothing);

		if (det == null)
			return clothing;
		else
			return det + " " + clothing;
	}
	public String gamble() {
		String output = null;
		Vector ID = NOC.getAllFrames();
		String Character =null;
		int i = new Random().nextInt(822);
		for (int e = 0; e < ID.size(); e++)
		{
			Character   = (String)ID.elementAt(i);//509
		}
		Vector Weapon = NOC.getFieldValues("Weapon of Choice", Character);
		String weapon = (String)Weapon.elementAt(0);
		Vector gender = NOC.getFieldValues("Gender", Character);
		String Gender = (String)gender.elementAt(0);
		String action = getA(Character);

		System.out.println(" " + Character);
		System.out.println("you are approached by "+Character+" Weilding " + weapon + " talking about " +action );
		System.out.println("do you wish to fight them");
		Scanner scan = new Scanner(System.in);
		String Answer = scan.nextLine();
		if(Answer.equalsIgnoreCase("yes")) {
			output = ("you have chosen to fight");
		}else
		{output = ("you have chosen to run");

		}

		return output;

	}
	public static void main(String[] args)
	{//TESTS
		GenerateCards pan = new GenerateCards();
		for (int i=0; i<500;i++)
			//	System.out.println(pan.MovementCards());
			//		System.out.println(pan.goToJail());
				System.out.println(pan.RewardCard());
			//				System.out.println(pan.fineCard() +"  "+ i);
			//		System.out.println(pan.GetOutOfJaillCard());
			//		System.out.println(pan.quiz());
			//		System.out.println(pan.gamble());
	}

}
