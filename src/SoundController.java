import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundController {
	private File jumpSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Mario Jump.wav");
	private File powerUpSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Powerup.wav");
	private File marioGetsHitSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Transformation.wav");
	private File itemOutOfBoxSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Item Box.wav");
	private File fireBallSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Fireball.wav");
	private File squishSoundFile = new File("/Users/victormicha/eclipse-workspace/MarioBrosGame/SoundEffects/Squish.wav");
	
	public SoundController() {

	}

	private void playSound(File f) {
		try{
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(f));
			clip.start();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void playMarioJumpSound(){
		//plays when mario jumps
		playSound(jumpSoundFile);
	}

	public void playPowerUpSound(){
		//plays when mario comes into contact with mushroom, flower, leaf, etc
		playSound(powerUpSoundFile);
	}
	public void playMarioHitSound(){
		//plays when mario gets hit by something and becomes smaller or loses flower or cat etc
		//TODO need to call this function
		playSound(marioGetsHitSoundFile);
	}
	public void playItemOutOfBoxSound() {
		//TODO need to call this function
		playSound(itemOutOfBoxSoundFile);
	}
	
	public void playFireballSound() {
		//TODO need to call this function
		playSound(fireBallSoundFile);
	}
	
	public void playSquishSound() {
		//TODO need to call this function
		//should play when mario jumps on turtle etc
		playSound(squishSoundFile);
	}
	//TODO NEED TO ADD ALL SOUNDS IN THIS CLASS
}
