import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public abstract class SoundController {
	private static String prefix;
	//when running from command line, prefix = "../"
	//when running from eclipse, prefix = ""
	private static File jumpSoundFile;
	private static File powerUpSoundFile;
	private static File marioGetsHitSoundFile;
	private static File itemOutOfBoxSoundFile;
	private static File fireBallSoundFile;
	private static File squishSoundFile;
	private static File tailSoundFile;
	private static File deathSoundFile;
	public static void setPrefix(String s) {
		prefix = s;
		jumpSoundFile = new File(prefix+"SoundEffects/Mario Jump.wav");
		powerUpSoundFile = new File(prefix+"SoundEffects/Powerup.wav");
		marioGetsHitSoundFile = new File(prefix+"SoundEffects/Transformation.wav");
		itemOutOfBoxSoundFile = new File(prefix+"SoundEffects/Item Box.wav");
		fireBallSoundFile = new File(prefix+"SoundEffects/Fireball.wav");
		squishSoundFile = new File(prefix+"SoundEffects/Squish.wav");
		tailSoundFile = new File(prefix+"SoundEffects/tail.wav");
		deathSoundFile = new File(prefix+"SoundEffects/Death.wav");
	}
	
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
