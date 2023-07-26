import java.util.ArrayList;
import java.util.HashMap;



public class StatsController {
	//keeps track of score, number of lives, powerups in reserve , num coins for each character
	
	private static final int pointsKillTurtleByFireball = 100;
	private static final int pointsKillGoombaByFireball = 50;
	private static final int pointsKillGoombaByJumpingOnIt = 2*pointsKillGoombaByFireball;
	private static final int pointsKillShootingFlowerByFireball = 150;
	private static final int pointsKillBulletBillByJumpingOnIt = 200;


	private static final int pointsCollectCoin = 10;
	private static final int livesGainedByGreenMushroom = 1;


	//mario, luigi, etc are characters
	private static Mario[] characters;
	private static HashMap<Mario, CharacterStat> characterStats;

	public static void initializeStats(Mario[] characters1) {
		characters = characters1;
		characterStats = new HashMap<Mario, CharacterStat>();
		for (int i=0; i<characters.length;i++)
			characterStats.put(characters[i], new CharacterStat());
	}
	
	public static void printAllStats() {
		for (Mario m:characters) {
			System.out.println(m.character.name());
			CharacterStat c = characterStats.get(m); 
			System.out.println("\tSCORE: "+c.getScore());
			System.out.print("\tNUM LIVES: "+c.getNumLives()+"\n\tPOWER UPS: ");
			for (String s:c.getPowerUps()) System.out.print(s+" ");
			System.out.println("\n\tNUM COINS: "+c.getNumCoins());
		}
	}

	/*
	 * TODO could increase score if mario makes turtle spin and kill other bad guys, after certain number of badguys killed gain one life per kill
	 */
	 
	
	//TODO call this func
	public static void addPowerUpInReserve(Mario mario, String powerUp) {characterStats.get(mario).addPowerUp(powerUp);}

	//TODO call this func
	public static void killTurtleByFireball(Mario mario) {characterStats.get(mario).increaseScore(pointsKillTurtleByFireball);}
	
	//TODO call this func
	public static void killGoombaByFireball(Mario mario) {characterStats.get(mario).increaseScore(pointsKillGoombaByFireball);}
	
	//this func is called :)
	public static void killGoombaByJumpingOnIt(Mario mario) {characterStats.get(mario).increaseScore(pointsKillGoombaByJumpingOnIt);}
	
	//TODO call this func
	public static void killShootingFlowerByFireball(Mario mario) {characterStats.get(mario).increaseScore(pointsKillShootingFlowerByFireball);}
	
	//this func is called :)
	public static void killBulletBillByJumpingOnIt(Mario mario) {characterStats.get(mario).increaseScore(pointsKillBulletBillByJumpingOnIt);}



	//this func is called :)
	public static void collectCoin(Mario mario) {
		CharacterStat c = characterStats.get(mario); 
		c.increaseScore(pointsCollectCoin);
		c.incrementNumCoinsByOne();
	}

	//TODO call this func
	public static void hitGreenLifeMushroom(Mario mario) {characterStats.get(mario).increaseNumLives(livesGainedByGreenMushroom);}

}
