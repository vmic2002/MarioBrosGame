

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class SoundController {
	private static File jumpSoundFile,powerUpSoundFile,
	marioGetsHitSoundFile,itemOutOfBoxSoundFile, fireBallSoundFile,
	squishSoundFile, tailSoundFile, deathSoundFile, pipeSoundFile,
	bumpSoundFile, kickSoundFile, coinSoundFile;

	private static boolean runningOnTomcatServer;
	private ServerToClientMessenger messenger;
	public SoundController(ServerToClientMessenger messenger) {
		this.messenger = messenger;
	}
	
	public static void setPrefix(String prefix) {
		//when running from command line, prefix = "../"
		//when running from eclipse, prefix = ""
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
		coinSoundFile = new File(prefix+"SoundEffects/Coin.wav");
	}
	public static void setRunningOnTomcatServer(boolean b) {runningOnTomcatServer=b;}

	private void playSound(File f) {
        //should stop playing current sound when a new sound needs to be played
        //this needs to be done for both destkop and online game
		if (runningOnTomcatServer) {
			//send message to client to play sound on client side
			//on server side, playing wav files (not really playing them when running on server, but when playing on desktop wav files are used)
			//on client side, playing mp3 files because they have smaller file sizes, less loading time
			messenger.sendPlaySoundMessage(f.getName().substring(0, f.getName().length()-3)+"mp3");
			//{ "type": "playSound", "soundName": "Coin.mp3" }
		} else {
			try{
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(f));
				clip.start();
				GameThread t1 = new GameThread(new MyRunnable() {
					@Override
					public void doWork() throws InterruptedException {
						while (clip.isRunning()) {}
						//sound is done playing after while loop
						clip.close();
						//Direct Clip threads accumulate need to kill thread once sound is done playing
						//this is to kill the Direct Clip thread that is created when a sound is made
						//without calling the close function, sounds create Direct Clip threads that are never killed
						//and throughout the game they accumulate
						//THIS LOOKS LIKE IT WORKS
					}
				},"close DirectClip thread", "");
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public void playCoinSound() {
		//plays when mario collects floating coin or jumps in mysterybox/brick that had coin inside
		playSound(coinSoundFile);
	}

	public void playKickSound() {
		//plays when mario kicks a turtle in shell mode and stopped so it starts spinning
		playSound(kickSoundFile);
	}

	public void playBumpSound(){
		//plays when spinning turtle (in shell mode) bumps into a platform
		playSound(bumpSoundFile);
	}


	public void playMarioGoesIntoPipeSound(){
		//plays when mario goes into pipe
		playSound(pipeSoundFile);
	}

	public void playMarioDeathSound(){
		//plays when mario dies
		playSound(deathSoundFile);
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
		//plays when mario gets hit by BadGuy and becomes smaller or loses flower or cat etc
		playSound(marioGetsHitSoundFile);
	}
	public void playItemOutOfBoxSound() {
		playSound(itemOutOfBoxSoundFile);
	}

	public void playFireballSound() {
		//plays when mario shoots a fireball
		playSound(fireBallSoundFile);
	}

	public void playTailSound() {
		//plays when mario swings his tail
		playSound(tailSoundFile);
	}

	public void playSquishSound() {
		//plays when mario jumps on turtle or goomba
		playSound(squishSoundFile);
	}
	//TODO NEED TO ADD ALL SOUNDS IN THIS CLASS
}
