import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public abstract class SoundController {
	private static String prefix;
	//when running from command line, prefix = "../"
	//when running from eclipse, prefix = ""
	private static File jumpSoundFile,powerUpSoundFile,
	marioGetsHitSoundFile,itemOutOfBoxSoundFile, fireBallSoundFile,
	squishSoundFile, tailSoundFile, deathSoundFile, pipeSoundFile,
	bumpSoundFile, kickSoundFile;
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
		pipeSoundFile = new File(prefix+"SoundEffects/Pipe.wav");
		bumpSoundFile = new File(prefix+"SoundEffects/Bump.wav");
		kickSoundFile = new File(prefix+"SoundEffects/Kick.wav");
	}

	private static void playSound(File f) {
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(f));
			clip.start();
			Thread t1 = new Thread(new Runnable() {
				public void run() {
					while (clip.isRunning()) {}
					//sound is done playing after while loop
					clip.close();
					//Direct Clip threads accumulate need to kill thread once sound is done playing
					//this is to kill the Direct Clip thread that is created when a sound is made
					//without calling the close function, sounds create Direct Clip threads that are never killed
					//and throughout the game they accumulate
					//THIS LOOKS LIKE IT WORKS
				}
			});
			t1.setName("close DirectClip thread");
			t1.start();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void playKickSound() {
		//plays when mario kicks a turtle in shell mode and stopped so it starts spinning
		playSound(kickSoundFile);
	}

	public static void playBumpSound(){
		//plays when spinning turtle (in shell mode) bumps into a platform
		playSound(bumpSoundFile);
	}


	public static void playMarioGoesIntoPipeSound(){
		//plays when mario goes into pipe
		playSound(pipeSoundFile);
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
		//plays when mario gets hit by BadGuy and becomes smaller or loses flower or cat etc
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
		//plays when mario jumps on turtle or goomba
		playSound(squishSoundFile);
	}
	//TODO NEED TO ADD ALL SOUNDS IN THIS CLASS
}
