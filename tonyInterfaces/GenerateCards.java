package tonyInterfaces;


import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;
//import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
@SuppressWarnings({"unused","rawtypes","unchecked"})
public class GenerateCards {
	private Domains domains = new Domains();
	private Characters characters = new Characters(domains.getDomains());

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




	public GenerateCards()
	{

		NOC           = new KnowledgeBaseModule("Veale's The NOC List.txt", 0);

		CLOTHES       = new KnowledgeBaseModule("Veale's clothing line.txt", 1);  // 1 is the column number of the key value

		VEHICLES      = new KnowledgeBaseModule("Veale's vehicle fleet.txt", 1);  // 1 is the column number of the key value



	}
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	// get to a new location in a snazzy vehicle															//
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//

	public  String MovementCards(){
		String outPut = null;
		String domain = getdomain();
		String Character = getCharacter(domain);

		Vector ID = NOC.getAllFrames();

		for (int e = 0; e < ID.size(); e++)
		{

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
					if (affordance == null && determiner == null) {
						comesAlong   = "You're going on an adventure. "+ Character + " comes along "+ " via "+ possPro+ " "+vehicle +" and " +pronoun+ " says get in.";
					}
					else if (affordance == null) {
						comesAlong   = "You're going on an adventure. "+ Character + " comes along "+ " via "+ determiner+ " "+vehicle +" and " +pronoun+ " says get in.";
					}
					else if (determiner == null) {
						comesAlong   = "You're going on an adventure. "+ Character + " comes along "+ affordance +" "+ vehicle +" and " +pronoun+ " says get in.";
					}else {
						comesAlong   = "You're going on an adventure. "+ Character + " comes along "+ affordance +" "+determiner+" "+ vehicle +" and " +pronoun+ " says get in.";
					}

					outPut  = comesAlong;
				}
			}
		}
		return outPut;


	}
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	// get a fine for being bold															//
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	public  String fineCard(){
		String outPut = null;
		String Character =null;
		Character = EvilDoing();
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


		return outPut;
	}
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	// Receiving rewards from admiring Characters															//
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	public  String RewardCard(){
		String outPut = null;
		String Character =null;
		int rnd = new Random().nextInt(2);
		if (rnd == 0) {
			Character = EvilDoing();
		}else {
			Character = Heroism();

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
			Activity = "You have been begging on the streets ";
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
			{String vehicle = (String)vehicles.elementAt(v);
			String determiner = VEHICLES.getFirstValue("Determiner", vehicle);
			String affordance = VEHICLES.getFirstValue("Affordances", vehicle);
			if (affordance == null && determiner == null) {
				comesAlong   = Character + " comes along "+ " via "+ possPro+ " "+vehicle +" and " +pronoun+ "  gives you ";
			}
			else if (affordance == null) {
				comesAlong   =  Character + " comes along "+ " via "+ determiner+ " "+vehicle +" and " +pronoun+ "  gives you ";
			}
			else if (determiner == null) {
				comesAlong   =  Character + " comes along "+ affordance +" the "+ vehicle +" and " +pronoun+ "  gives you ";
			}else {
				comesAlong   = Character + " comes along "+ affordance +" "+determiner+" "+ vehicle +" and " +pronoun+ "  gives you ";
			}
			outPut  = Activity + " " + comesAlong;
			}
		}

		return outPut;
	}

	private String Heroism()
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

	private String EvilDoing()
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

	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	// getting screwed over by politicians
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	public  String PoliticalCard(){
		String Character = null;
		String output = null;
		Vector Politicians = NOC.getAllKeysWithFieldValue("Category", "Politician");
		Politicians.remove("Malcolm X");//dont really belong
		Politicians.remove("Davy Crockett");
		int rnd = new Random().nextInt(Politicians.size());
		for (int e = 0; e < Politicians.size(); e++)
		{
			Character   = (String)Politicians.elementAt(rnd);
		}
		String pronoun    = "he";
		String possPro	  = "his";
		if (NOC.hasFieldValue("Gender", Character, "female"))
		{
			pronoun = "she";
			possPro = "her";
		}
		output = "You get on the wrong side of " +Character+ " " + pronoun+ " slaps you with a bill for street repairs. ";

		return output;
	}
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	// get out of jail courtesy of Snake															//
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//

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
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	// get arrested by the firm hand of the law	
	// for doing bad things with bad people e.g. giving noogies with biff
	//-----------------------------------------------------------------------------------------------//
	//-----------------------------------------------------------------------------------------------//
	public String goToJail () {
		String output = null;
		String nemesis = null;
		String crime = null;
		Vector cops = NOC.getAllKeysWithFieldValue("Category", "Policeman");
		Vector Detectives = NOC.getAllKeysWithFieldValue("Category", "Detective");
		Vector superH = NOC.getAllKeysWithFieldValue("Category", "Superhero");
		Set<String> set = new HashSet<>(cops);
		set.addAll(Detectives);
		set.addAll(superH);
		Vector<String> Heros = new Vector<>(set);

		int rnd = new Random().nextInt(Heros.size());
		String Character = (String)Heros.get(rnd);
		Vector enemy = NOC.getFieldValues("Opponent", Character );
		if(enemy == null) { 

			nemesis = EvilDoing();


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
				crime = (String)EvilPeople.get(r);
				Crime = NOC.getFieldValues("Typical Activity", crime );
				for (int a = 0; a < Crime.size(); a++)
				{
					int ra = new Random().nextInt(Crime.size());
					crime   = (String)Crime.elementAt(ra);
				}
			}else
				for (int a = 0; a < Crime.size(); a++)
				{
					int r = new Random().nextInt(Crime.size());
					crime   = (String)Crime.elementAt(r);
				}
		}catch (Exception e) {
		}

		output = "You are taken down by " + Character +" for "+ crime  +" with "+ nemesis;
		return  output;
	}

	public  String intoxicated(){
		String Character = null;
		String output = null;
		Vector drink = NOC.getAllKeysWithFieldValue("Negative Talking Points", "alcoholic");
		Vector drugs = NOC.getAllKeysWithFieldValue("Negative Talking Points", "drug-addled");
		Set<String> set = new HashSet<>(drink);
		set.addAll(drugs);
		Vector<String> addicts = new Vector<>(set);

		int rnd = new Random().nextInt(addicts.size());
		for (int e = 0; e < addicts.size(); e++)
		{
			Character   = (String)addicts.elementAt(rnd);
		}

		output = "You get twisted on a night out with " +Character+ " and slapped with a fine for public intoxication. ";

		return output;
	}
	public String getdomain() {
		int i = new Random().nextInt(7);
		String domain = (domains.getDomain(i));
		return domain ;


	}
	public String getCharacter(String doamin) {
		Person p = characters.getRandomPersonFromDomain(doamin);
		String Character = ""+p.getName();
		return Character;

	}

}