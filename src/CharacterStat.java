

import java.util.ArrayList;

public class CharacterStat{
	//mario, luigi, etc are characters
	private int score;
	private int numLives;
	private ArrayList<String> powerUps;//powerUps in reserve
	private int numCoins;//at 100 coins numLives++
	private static final int maxScore = 2000000000;
	private static final int maxNumLives = 99;
	private static final int maxNumCoins = 100;
	public CharacterStat() {
		score = 0;
		numLives = 0;
		powerUps = new ArrayList<String>();
		numCoins = 0;
	}

	public int getScore() {return score;}
	public int getNumLives() {return numLives;}
	public ArrayList<String> getPowerUps(){return powerUps;}
	public int getNumCoins() {return numCoins;}
	
	public void increaseScore(int x) {
		int newScore = score+x;
		if (newScore>maxScore) score = (newScore)%maxScore;//score loops back around
		else score = newScore;
	}
	
	public void increaseNumLives(int x) {
		if (numLives==maxNumLives) return;//can't go higher than maxNumLives, just stay at max if more lives gained
		int newLives = numLives+x;
		if (newLives<=maxNumLives) numLives = newLives;
		else numLives = maxNumLives;
	}
	
	public void addPowerUp(String s) {powerUps.add(s);}
	
	public void incrementNumCoinsByOne() {
		int newCoins = numCoins+1;
		if (newCoins==maxNumCoins) {
			increaseNumLives(1);
			numCoins = 0;
		} else {
			numCoins = newCoins;
		}
	}
}
