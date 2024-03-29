//import java.awt.Image;
//import acm.graphics.GCanvas;
import acm.graphics.GObject;

public class Coin extends MovingObject implements Dynamic{
	//coin extend MovingObject because it is added to level by DynamicFactory
	//2 types of coins -> floating coins (in level) and coins that come out of mysterybox, bricks etc
	//TODO make coin come out of mysterybox, brick, etc, for now only "floating" coins in level
	//public long dynamicId;
	private static MyGCanvas canvas;
	private static MyImage coin1Image, coin2Image, coin3Image;
	private static int pauseBetweenStates = 15;
	private enum COIN_STATE {STATE_1, STATE_2, STATE_3, COLLECTED};
	//"collected" state means Mario collected the coin and it should be removed from the canvas as well as the dynamicLevelParts
	COIN_STATE coinState;


	public Coin() {
		super(coin1Image);
		coinState = COIN_STATE.STATE_1;
	}

	public void toggleState() {
		//changes coin image from stage 1 to stage 2, 2->3, 3->1
		if (collected()) return;
		MyImage newImage;
		if (coinState == COIN_STATE.STATE_1) {
			newImage = coin2Image;
			coinState = COIN_STATE.STATE_2;
		} else if (coinState == COIN_STATE.STATE_2) {
			newImage = coin3Image;
			coinState = COIN_STATE.STATE_3;
		} else {//if (coinState == COIN_STATE.STATE_3) {
			newImage = coin1Image;
			coinState = COIN_STATE.STATE_1;
		}
		setImageAndRelocate(newImage);
	}

	public boolean collected() {return coinState==COIN_STATE.COLLECTED;}

	public void startSpinning() {
		//each floating coin changes its pictures (changes state) in its own thread
		//TODO maybe when level will have lots of coins it will be worth it to have one thread doing spinning block of coins
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException{changeState();}
		},"floating coin changing state");
	}

	public void changeState() throws InterruptedException{
		while (!collected() && alive) {
			ThreadSleep.sleep(pauseBetweenStates);
			toggleState();
		}	
		kill();
		System.out.println("END OF CHANGING STATE FOR FLOATING COIN");
	}

	public void collectedByMario(Mario mario) {
		//THIS IS FOR FLOATING COINS
		if (collected()) return;
		CharacterStatsController.collectCoin(mario);
		coinState = COIN_STATE.COLLECTED;
		SoundController.playCoinSound();
	}

	public static void setObjects(MyImage image1, MyImage image2, MyImage image3, MyGCanvas canvas1) {
		coin1Image = image1;
		coin2Image = image2;
		coin3Image = image3;
		canvas = canvas1;
	}



	@Override
	public long getID() {
		return this.getImageID();
	}

	@Override
	public void kill() {
		if (!LevelController.endingLevel()) {
			canvas.remove(this);
			alive = false;
			LevelController.currLevel.removeDynamic(this);
		}
	}

	@Override
	public void move() throws InterruptedException {
		//TODO the move function will be for coins retrieved from mysteryboxes+bricks (NOT floating coins)
		//since they have a small animation of moving out of the box
	}

	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		//not really needed since coin does not move
		return false;
	}


}