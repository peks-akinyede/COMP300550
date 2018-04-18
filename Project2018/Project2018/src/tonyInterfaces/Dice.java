package tonyInterfaces;

import java.util.Random;
public class Dice implements Rollable, DiceAPI {
	private final static Random RND = new Random();
	private static final int NUM_DICE = 2;
	
	private int[] dice = new int [NUM_DICE];
	
	
	public int rollDice(int numDice, int numSides) {
		for (int i = 0; i < NUM_DICE; i++){
			dice[i] = RND.nextInt(numSides)+1;
		}
		return dice[0] + dice[1];
	}


	@Override
	public int[] getDice() {
		// TODO Auto-generated method stub
		return dice;
	}


	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return (dice[0] + dice[1]);
	}


	@Override
	public boolean isDouble () {
		return dice[0] == dice[1];
	}
	
	@Override
	public String toString () {
		return dice[0] + " " + dice[1];
	}
}
