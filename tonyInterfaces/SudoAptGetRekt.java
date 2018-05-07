package tonyInterfaces;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map.Entry;

/**
 * Monopoly Bot, more tactical than other lagosBoys Bot
 *
 * Authors:
 * - Edwin Lebreton
 * - Michael Mullen 
 * - Cathal Weakliam 
 */

public class SudoAptGetRekt implements Bot {

	private static final int JAIL_FINE = 50;
	private static final int LOW_ASSETS = 300;
	private static final int LOW_BALANCE = 200;

	private static final boolean PAUSE_BEFORE_MOVE = false;
	private static final int PAUSE_LENGTH_MILLIS = 100;
	private static final boolean LOG = false;
	private static final String TEAM_NAME = "SudoAptGetRekt";

	private static final HashMap<InvestmentProperty, int[]> fullNameToRentTable;

	static {
		Vector<InvestmentProperty> sites = WorldBuilder.getSites();
		fullNameToRentTable = new HashMap<>();
		fullNameToRentTable.put(sites.elementAt(0), new int[]{2, 10, 30, 90, 160, 250});
		fullNameToRentTable.put(sites.elementAt(1), new int[]{4, 20, 60, 180, 320, 450});
		fullNameToRentTable.put(sites.elementAt(2), new int[]{6, 30, 90, 270, 400, 550});
		fullNameToRentTable.put(sites.elementAt(3), new int[]{6, 30, 90, 270, 400, 550});
		fullNameToRentTable.put(sites.elementAt(4), new int[]{8, 40, 100, 300, 450, 600});
		fullNameToRentTable.put(sites.elementAt(5), new int[]{10, 50, 150, 450, 625, 750});
		fullNameToRentTable.put(sites.elementAt(6), new int[]{10, 50, 150, 450, 625, 750});
		fullNameToRentTable.put(sites.elementAt(7), new int[]{12, 60, 180, 500, 700, 900});
		fullNameToRentTable.put(sites.elementAt(8), new int[]{14, 70, 200, 550, 750, 950});
		fullNameToRentTable.put(sites.elementAt(9), new int[]{14, 70, 200, 550, 750, 950});
		fullNameToRentTable.put(sites.elementAt(10), new int[]{16, 80, 220, 600, 800, 1000});
		fullNameToRentTable.put(sites.elementAt(11), new int[]{18, 90, 250, 700, 875, 1050});
		fullNameToRentTable.put(sites.elementAt(12), new int[]{18, 90, 250, 700, 875, 1050});
		fullNameToRentTable.put(sites.elementAt(13), new int[]{20, 100, 300, 750, 925, 1100});
		fullNameToRentTable.put(sites.elementAt(14), new int[]{22, 110, 330, 800, 975, 1150});
		fullNameToRentTable.put(sites.elementAt(15), new int[]{22, 110, 330, 800, 975, 1150});
		fullNameToRentTable.put(sites.elementAt(16), new int[]{22, 120, 360, 850, 1025, 1200});
		fullNameToRentTable.put(sites.elementAt(17), new int[]{26, 130, 390, 900, 1100, 1275});
		fullNameToRentTable.put(sites.elementAt(18), new int[]{26, 130, 390, 900, 1100, 1275});
		fullNameToRentTable.put(sites.elementAt(19), new int[]{28, 150, 450, 1000, 1200, 1400});
		fullNameToRentTable.put(sites.elementAt(20), new int[]{35, 175, 500, 1100, 1300, 1500});
		fullNameToRentTable.put(sites.elementAt(21), new int[]{50, 200, 600, 1400, 1700, 2000});
	}

	private BoardAPI board;
	private PlayerAPI player;
	private DiceAPI dice;
	private boolean rolledThisTurn;
	private boolean inJailThisTurn;

	SudoAptGetRekt(BoardAPI board, PlayerAPI player, DiceAPI dice) {
		this.board = board;
		this.player = player;
		this.dice = dice;
		rolledThisTurn = false;
	}

	public String getName() {
		return TEAM_NAME;
	}

	public String getCommand() {
		String command = chooseCommand();
		if (PAUSE_BEFORE_MOVE) {
			pause();
		}
		if (LOG) {
			logging(command);
		}
		updateState(command);
		return command;
	}

	private String chooseCommand() {
		if (negativeBalance()) {
			return determineNegativeBalanceMove();
		}
		if (canBuy() && shouldBuy()) {

			return "buy";
		}
		if (jailed()) {
			return determineJailMove();
		}
		if (canRoll()) {
			return "roll";
		}
		String buildCommand = maybeBuild();
		if (buildCommand != null) {
			return buildCommand;
		}
		String redeemCommand = maybeRedeem();
		if (redeemCommand != null) {
			return redeemCommand;
		}
		return "done";
	}

	// Pause before a move to give an observer time to see what the bot is doing.
	private void pause() {
		try {
			Thread.sleep(PAUSE_LENGTH_MILLIS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Log some basic information about the state of the game to the console.
	 */
	private void logging(String command) {
		System.out.println("--------------------------------------");
		System.out.println("player: " + player);
		System.out.println("player is in jail: " + player.isInJail());
		System.out.println("player balance: " + player.getBalance());
		System.out.println("dice isDoubles: " + dice.isDouble());
		System.out.println("rolledThisTurn: " + rolledThisTurn);
		System.out.println("command chosen: " + command);
	}

	/**
	 * Update variables keeping track of the state of the game.
	 */
	private void updateState(String command) {
		if (player.isInJail()) {
			inJailThisTurn = true;
		}
		switch (command) {
		case "roll":
			rolledThisTurn = true;
			break;
		case "done":
			rolledThisTurn = false;
			inJailThisTurn = false;
			break;
		}
	}

	private boolean negativeBalance() {
		return player.getBalance() < 0;
	}

	/**
	 * Return the command to use when the player's balance is negative.
	 *
	 * This first tries to find a good property to mortgage that isn't too valuable. If it can't, it
	 * defaults to a naive strategy of looping through properties and selling the first buildings /
	 * mortgaging the first properties that it can. If there are no more buildings to demolish or
	 * properties to mortgage, it declares bankruptcy.
	 */
	private String determineNegativeBalanceMove() {
		PrivateProperty victim = selectNextPropertyToMortgage();
		if (victim != null) {
			// Victim found
			return mortgageCommand(victim);
		} else {
			// otherwise use naive strategy
			for (PrivateProperty property : player.getProperties()) {
				if (property instanceof InvestmentProperty && ((InvestmentProperty) property).getNumBuildings() > 0) {
					return demolishCommand(property, 1);
				}
				if (!property.isMortgaged()) {
					return mortgageCommand(property);
				}
			}
		}
		return "bankrupt";
	}

	private String demolishCommand(PrivateProperty property, int numToDemolish) {
		String shortName = property.getIdentifier();
		return "demolish " + shortName + " " + numToDemolish;
	}

	private String mortgageCommand(PrivateProperty property) {
		String shortName = property.getIdentifier();
		return "mortgage " + shortName;
	}

	/**
	 * Can the bot legally buy the property it is currently on?
	 */
	private boolean canBuy() {
		if (!board.isProperty(player.getPosition())) {
			return false;
		}

		PrivateProperty property = board.getProperty(player.getPosition());
		return !property.isOwned() && property.getPrice() < player.getBalance();
	}

	/**
	 * Is it wise to buy the property the bot is currently on?
	 */
	private boolean shouldBuy() {
		PrivateProperty property = board.getProperty(player.getPosition());

		int assetsAfterBuy = calculateAssets() - property.getPrice() + property.getMortgageAmount();
		if (assetsAfterBuy < LOW_ASSETS) {
			return false;
		}

		if (property instanceof InvestmentProperty) {
			InvestmentProperty site = (InvestmentProperty) property;
			ColourGroupStatus status = getColourGroupStatus(site.getColourGroup());
			return !(status == ColourGroupStatus.FUTILE);
		}
		if (property instanceof Vehicle) {
			return true;
		}
		if (property instanceof Utility) {
			return false;
		}

		return true;
	}

	/**
	 * Can the bot legally use the 'pay' command?
	 */
	private boolean canPay() {
		return player.isInJail() && JAIL_FINE < player.getBalance();
	}

	/**
	 * Can the bot legally roll the dice?
	 */
	private boolean canRoll() {
		return (!rolledThisTurn || dice.isDouble())
				&& !negativeBalance()
				&& !inJailThisTurn;
	}

	public String getDecision() {
		int HOUSE_LIMIT = 5;
		if (player.getNumHousesOwned() < HOUSE_LIMIT
				|| mostChanceCardAdvanceToPropertiesAvailable()) {
			// Take a chance
			return "chance";
		} else {
			return "pay";
		}
	}

	/**
	 * Determines whether a majority of properties that you can be sent to by a
	 * chance card are available to be bought or not
	 */
	private boolean mostChanceCardAdvanceToPropertiesAvailable() {
		final int[] ADVANCE_TO_POSITIONS = {11, 15, 34, 39};

		int amountOwned = 0;
		int amountAvailable = 0;

		for (int position : ADVANCE_TO_POSITIONS) {
			if (board.getProperty(position).isOwned()) {
				amountOwned++;
			} else {
				amountAvailable++;
			}
		}

		return amountAvailable > amountOwned;
	}

	private boolean jailed() {
		return player.isInJail();
	}

	private String determineJailMove() {
		// If we should leave jail, try to get out immediately
		if (worthwhileLeavingJail()) {
			if (player.hasGetOutOfJailCard()) {
				return "card";
			} else if (canPay()) {
				return "pay";
			}
		} else if (!rolledThisTurn) {
			return "roll";
		}
		return "done";
	}

	/**
	 * Determine if it is worthwhile getting out of jail now or staying there until forced to
	 * leave by fine or dice roll
	 *
	 * Primarily takes into account the state of properties along "jail-row" (orange and pink
	 * properties), but also the game state as a whole, and how much money our player currently has
	 *
	 * @return true if we should leave jail immediately, false otherwise
	 */
	private boolean worthwhileLeavingJail() {
		if (player.getBalance() < LOW_BALANCE) {
			// If we have such a low balance, our best bet is to wait out in jail
			// and hope that we can collect rent from our opponent while jailed
			return false;
		}

		// Closest colour groups to the jail square
		Domain orange = ((InvestmentProperty) board.getProperty(16)).getColourGroup();
		Domain pink = ((InvestmentProperty) board.getProperty(11)).getColourGroup();

		if (opponentHasMonopolyOnColourGroup(orange)) {
			// Most likely to land on an orange square when rolling out of jail
			// So if there are any amount of houses here, it will be better to
			// sit it out in jail for as long as possible
			if (numberOfBuildingsOnColorGroup(orange) > 0) {
				return false;
			}
		}

		if (opponentHasMonopolyOnColourGroup(pink)) {
			// Not very likely to land on a pink square when rolling out of jail
			// In fact, we cannot land on Pall Mall (11) as it is only one space away
			// So, we should only decide to stay in jail as long as possible if there
			// is a substantial number of houses on Whitehall (13) or Northumberland Ave (14)
			InvestmentProperty whitehall = (InvestmentProperty) board.getProperty(13);
			InvestmentProperty northumberland = (InvestmentProperty) board.getProperty(14);
			// Threshold for whitehall is higher because there is less chance of landing on it
			// and it incurs less rent for landing on it
			int WHITEHALL_THRESHOLD = 2;
			int NORTHUMBERLAND_THRESHOLD = 1;
			if (whitehall.getNumBuildings() > WHITEHALL_THRESHOLD ||
					northumberland.getNumBuildings() > NORTHUMBERLAND_THRESHOLD) {
				return false;
			}
		}

		int ANY_OTHER_THRESHOLD = 3;
		// If we've reached a stage in the game where there are any highly developed
		// properties on the board, we should probably be better off just waiting it
		// out in jail
		if (opponentHasAnySiteWithMoreBuildings(ANY_OTHER_THRESHOLD)) {
			return false;
		}
		// If we've got this far, then we should leave jail immediately
		return true;
	}

	private int numberOfBuildingsOnColorGroup(Domain colourGroup) {
		int numBuildings = 0;
		for (InvestmentProperty site : colourGroup.getMembers()) {
			numBuildings += site.getNumBuildings();
		}
		return numBuildings;
	}

	private boolean opponentHasMonopolyOnColourGroup(Domain colourGroup) {
		return getColourGroupStatus(colourGroup) == ColourGroupStatus.FUTILE;
	}

	private boolean opponentHasAnySiteWithMoreBuildings(int threshold) {
		for (int i = 0; i < WorldBuilder.NUM_SQUARES; i++) {
			NamedLocation square = board.getSquare(i);
			if (!(square instanceof InvestmentProperty)) {
				// Skip this square
				continue;
			}

			InvestmentProperty site = (InvestmentProperty) square;
			if (ownedByOpponent(site) && site.getNumBuildings() > threshold) {
				return true;
			}
		}
		return false;
	}

	private boolean ownedByOpponent(InvestmentProperty site) {
		return site.isOwned() && !(site.getOwner() == player);
	}

	/**
	 * Determine if the bot should build a building somewhere. If yes, return the command to build
	 * it. If no, return null.
	 *
	 * Prioritises buildings that are predicted to give a good return on investment.
	 */
	private String maybeBuild() {
		ArrayList<InvestmentProperty> sitesForBuilding = new ArrayList<>();
		for (PrivateProperty property : player.getProperties()) {
			if (property instanceof InvestmentProperty
					&& player.isGroupOwner((InvestmentProperty) property)
					&& ((InvestmentProperty) property).canBuild(1)
					&& !property.isMortgaged()
					&& player.getBalance() > ((InvestmentProperty) property).getBuildingPrice()) {
				sitesForBuilding.add((InvestmentProperty) property);
			}
		}

		if (sitesForBuilding.isEmpty()) {
			return null;
		}

		// Sort sites by the predicted return on investment of building there
		sitesForBuilding.sort((site1, site2) ->
		Integer.compare(houseValue(site1), houseValue(site2)));
		InvestmentProperty bestSite = sitesForBuilding.get(sitesForBuilding.size() - 1);

		int assetsAfterBuild = calculateAssets()
				- bestSite.getBuildingPrice()
				+ getBuildingSellingPrice(bestSite);

		if (assetsAfterBuild < LOW_ASSETS) {
			return null;
		}
		return buildCommand(bestSite, 1);
	}

	/**
	 * Estimate the value of building one more house on this site.
	 * Assumes that it is possible to build another house.
	 */
	private static int houseValue(InvestmentProperty site) {
		int currentRent = site.getRentalAmount();
		int[] rentTable = fullNameToRentTable.get(site);
		int increasedRent = rentTable[site.getNumBuildings() + 1];
		int rentIncrease = increasedRent - currentRent;
		return rentIncrease - site.getBuildingPrice();
	}

	private int getBuildingSellingPrice(InvestmentProperty site) {
		return site.getBuildingPrice() / 2;
	}

	private String buildCommand(PrivateProperty property, int numToBuild) {
		return "build " + property.getIdentifier() + " " + numToBuild;
	}

	/**
	 * Determine if the bot should redeem any mortgaged property. If yes, return the command to
	 * redeem it. If no, return null.
	 *
	 * This will only suggest redeeming a property if buildings can be built on it.
	 */
	private String maybeRedeem() {
		for (PrivateProperty property : player.getProperties()) {
			if (property instanceof InvestmentProperty
					&& player.isGroupOwner((InvestmentProperty) property)
					&& property.isMortgaged()
					&& player.getBalance() > property.getMortgagePrice()) {

				int assetsAfterRedeem = calculateAssets()
						- property.getMortgagePrice()
						+ property.getMortgageAmount();

				if (assetsAfterRedeem < LOW_ASSETS) {
					continue;
				}
				return redeemCommand(property);
			}
		}
		return null;
	}

	private String redeemCommand(PrivateProperty property) {
		return "redeem " + property.getIdentifier();
	}

	/**
	 * We reach a situation whereby we need to sell off some of our assets in order
	 * to either buy other assets or prevent bankruptcy.
	 * We want to select the next victim to mortgage, from the following criteria:
	 *   A utility that we own
	 *   Owned in a colour group where the opponent owns another property in the group
	 *   A station that we own, but own either none or only one other (1/2 total)
	 *   Owned in a colour group where we don't have a monopoly (yet!)
	 *   A station that we own, where we own two others (three total)
	 *   Owned in a colour group we have monopolized but haven't begun developing
	 *   A station we own, where we own three other stations (monopolized, but cannot develop on)
	 *
	 * If we don't find any suitable properties, we return null, which indicates that some buildings
	 * need to be demolished if we want to afford whatever it is we're trying to buy/pay off
	 *
	 *
	 * The properties that we loop through are sorted in order of the mortgage value, increasing
	 */
	private PrivateProperty selectNextPropertyToMortgage() {
		ArrayList<PrivateProperty> properties = propertiesThatCanBeMortgaged();

		// Sort properties by their mortgage value
		properties.sort((property1, property2) ->
		Integer.compare(property1.getMortgagePrice(), property2.getMortgagePrice()));

		PrivateProperty victim = findFirstUtilityOwned(properties);
		if (victim != null) {
			// Found a victim
			return victim;
		}

		victim = findPropertyOwnedInFutileColourGroup(properties);
		if (victim != null) {
			// Found a victim
			return victim;
		}

		victim = findStationWithMaxNumberOwned(2, properties);
		if (victim != null) {
			// Found a victim
			return victim;
		}

		victim = findStandaloneProperty(properties); // This can be redeemed later if needed
		if (victim != null) {
			// Found a victim
			return victim;
		}

		victim = findStationWithMaxNumberOwned(3, properties);
		if (victim != null) {
			// Found a victim
			return victim;
		}

		victim = findUndevelopedPropertyInMonopoly(properties);
		if (victim != null) {
			// Found a victim
			return victim;
		}

		victim = findStationWithMaxNumberOwned(4, properties);
		if (victim != null) {
			// Found a victim
			return victim;
		}

		// No properties which match any of these criteria 
		// So return null to revert to naive strategy
		return null;
	}

	/**
	 * Returns a property that is not in a monopoly, however it may be part of one in the future
	 */
	private PrivateProperty findStandaloneProperty(ArrayList<PrivateProperty> properties) {
		for (PrivateProperty property : properties) {
			if (property instanceof InvestmentProperty) {
				ColourGroupStatus status = getColourGroupStatus(((InvestmentProperty) property).getColourGroup());
				if (status == ColourGroupStatus.STANDALONE) {
					// We don't own a monopoly on this site's colour group yet
					// but we may in future
					return property;
				}
			}
		}
		return null;
	}

	private PrivateProperty findUndevelopedPropertyInMonopoly(ArrayList<PrivateProperty> properties) {

		for (PrivateProperty property : properties) {
			if (property instanceof InvestmentProperty) {
				InvestmentProperty site = (InvestmentProperty) property;
				Domain group = site.getColourGroup();

				if (getColourGroupStatus(group) == ColourGroupStatus.MONOPOLISED_FOR
						&& !site.hasBuildings()) {
					return site;
				}
			}
		}

		// No suitable property found
		return null;
	}

	/**
	 * Creates a list of all properties that we currently own, less those that have already been
	 * mortgaged
	 *
	 * Does not account for properties that have buildings built on them. They will have to be
	 * demolished mortgaging can occur
	 */
	private ArrayList<PrivateProperty> propertiesThatCanBeMortgaged() {
		ArrayList<PrivateProperty> allProperties = player.getProperties();
		ArrayList<PrivateProperty> mortgageableProperties = new ArrayList<>();

		for (PrivateProperty property : allProperties) {
			if (!property.isMortgaged()) {
				mortgageableProperties.add(property);
			}
		}

		return mortgageableProperties;
	}

	private PrivateProperty findStationWithMaxNumberOwned(int maxAmount, ArrayList<PrivateProperty> properties) {
		if (getNumberOfStationsOwned() >= maxAmount) {
			for (PrivateProperty property : properties) {
				if (property instanceof Vehicle) {
					return property;
				}
			}
		}
		// No suitable station found
		return null;
	}

	private int getNumberOfStationsOwned() {
		ArrayList<PrivateProperty> properties = player.getProperties();
		int count = 0;
		for (PrivateProperty property : properties) {
			if (property instanceof Vehicle) {
				count++;
			}
		}
		return count;
	}

	private PrivateProperty findPropertyOwnedInFutileColourGroup(ArrayList<PrivateProperty> properties) {
		for (PrivateProperty property : properties) {
			if (property instanceof InvestmentProperty) {
				InvestmentProperty site = (InvestmentProperty) property;
				if (getColourGroupStatus(site.getColourGroup()) == ColourGroupStatus.FUTILE) {
					// Found a satisfying property
					return property;
				}
			}
		}

		// No properties in useless colour group found
		return null;
	}

	private ColourGroupStatus getColourGroupStatus(Domain group) {
		ArrayList<InvestmentProperty> properties = group.getMembers();
		HashMap<Player, Integer> ownerCount = new HashMap<>();

		for (PrivateProperty property : properties) {
			int prevCount = 0;
			if (ownerCount.containsKey(property.getOwner())) {
				prevCount = ownerCount.get(property.getOwner());
			}

			ownerCount.put(property.getOwner(), prevCount + 1);
		}

		// None are owned
		if (ownerCount.containsKey(null) && ownerCount.get(null) == properties.size()) {
			return ColourGroupStatus.AVAILABLE;
		}

		// We own all of them
		if (ownerCount.containsKey(player) && ownerCount.get(player) == properties.size()) {
			return ColourGroupStatus.MONOPOLISED_FOR;
		}

		// Any opponent (not us, or the bank) owns all of them
		for (Entry<Player, Integer> entry : ownerCount.entrySet()) {
			if (entry.getValue() == properties.size() &&
					!(entry.getKey() == player || entry.getKey() == null)) {
				return ColourGroupStatus.MONOPOLISED_AGAINST;
			}
		}

		// No one can get a monopoly because two different players own at least one in the group
		int uniqueOwnerCount = 0;

		for (Entry<Player, Integer> entry : ownerCount.entrySet()) {
			if (entry.getValue() > 0 && !(entry.getKey() == null)) {
				// We have entries in the map that have correspond to an owner (not null)
				uniqueOwnerCount++;
			}
		}

		// More than one owner of properties in the group
		if (uniqueOwnerCount > 1) {
			return ColourGroupStatus.FUTILE;
		}

		// Opponent only needs one to secure a monopoly
		for (Entry<Player, Integer> entry : ownerCount.entrySet()) {
			// This assumes that the group is already not futile, so ensure
			// that this part of the function comes after the futile check
			if (entry.getValue() == properties.size() - 1 && entry.getKey() != null) {
				return ColourGroupStatus.DANGEROUS;
			}
		}

		// We own some of the properties, rest are owned by the bank
		if (ownerCount.containsKey(player) && ownerCount.containsKey(null) &&
				ownerCount.get(player) > 0 && ownerCount.get(null) == 0) {
			return ColourGroupStatus.STANDALONE;
		}

		Integer tempNotOwned = ownerCount
				.remove(null); // remove ones owned by bank (will be null for count of0)
		if (ownerCount.size() == 1) {
			// Only one owner, rest must be owned by the bank
			return ColourGroupStatus.VULNERABLE;
		}

		// Put back the count of bank-owned properties (if it had existed)
		if (tempNotOwned != null) {
			ownerCount.put(null, tempNotOwned);
		}

		// All possible combinations are set out above, if we get this far something
		// has gone wrong logically - output the logic state to the console

		String errorOutput = "";
		for (Entry<Player, Integer> entry : ownerCount.entrySet()) {
			errorOutput += entry.getKey() + ": " + entry.getValue() + "\n";
		}

		throw new RuntimeException("Something went wrong with the cgstatus.\n"
				+ " Map:" + errorOutput);

	}

	private PrivateProperty findFirstUtilityOwned(ArrayList<PrivateProperty> properties) {
		for (PrivateProperty property : properties) {
			if (property instanceof Utility) {
				return property;
			}
		}
		// No utilities found
		return null;
	}

	/**
	 * Calculate the bot's assets - i.e. balance + selling price of all buildings + mortgage value
	 * of all properties
	 */
	private int calculateAssets() {
		int total = player.getBalance();
		for (PrivateProperty property : player.getProperties()) {
			if (!property.isMortgaged()) {
				total += property.getMortgagePrice();
			}
			if (property instanceof InvestmentProperty) {
				total += ((InvestmentProperty) property).getNumBuildings() * ((InvestmentProperty) property).getBuildingPrice();
			}
		}
		return total;
	}

	private enum ColourGroupStatus {
		// no one owns any property in group:
		AVAILABLE,
		// we have one (or two) in group, rest of group available:
		STANDALONE,
		// we have a monopoly on the group:
		MONOPOLISED_FOR,
		// opponent has one in group, rest of group available:
		VULNERABLE,
		// opponent only needs one more to get a monopoly:
		DANGEROUS,
		// opponent has a monopoly on the group:
		MONOPOLISED_AGAINST,
		// no one can get a monopoly on the group:
		FUTILE,
	}
}