import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundController {
	private static File jumpSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Mario Jump.wav");
	private static File powerUpSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Powerup.wav");
	private static File marioGetsHitSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Transformation.wav");
	private static File itemOutOfBoxSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Item Box.wav");
	private static File fireBallSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Fireball.wav");
	private static File squishSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Squish.wav");
	private static File tailSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/tail.wav");
	private static File deathSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Death.wav");
	
	private static void playSound(File f) {
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(f));
			clip.start();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void playMarioDeathSound(){
		//plays when mario dies
		playSound(deathSoundFile);
	}

	public static void playMarioJumpSound(){
		//plays when mario jumps
		playSound(jumpSoundFile);
	}

	public static void playPowerUpSound(){
		//plays when mario comes into contact with mushroom, flower, leaf, etc
		playSound(powerUpSoundFile);
	}
	public static void playMarioHitSound(){
		//plays when mario gets hit by something and becomes smaller or loses flower or cat etc
		//TODO need to call this function
		playSound(marioGetsHitSoundFile);
	}
	public static void playItemOutOfBoxSound() {
		playSound(itemOutOfBoxSoundFile);
	}
	
	public static void playFireballSound() {
		//plays when mario shoots a fireball
		playSound(fireBallSoundFile);
	}
	
	public static void playTailSound() {
		//plays when mario swings his tail
		playSound(tailSoundFile);
	}
	
	public static void playSquishSound() {
		//TODO need to call this function
		//should play when mario jumps on turtle etc
		playSound(squishSoundFile);
	}
	//TODO NEED TO ADD ALL SOUNDS IN THIS CLASS
}
