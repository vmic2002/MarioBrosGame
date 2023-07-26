import java.awt.Image;
import acm.graphics.GCanvas;
import acm.graphics.GObject;

public class Coin extends MovingObject implements Dynamic{
	//coin extend MovingObject because it is added to level by DynamicFactory
	//2 types of coins -> floating coins (in level) and coins that come out of mysterybox, bricks etc
	//TODO make coin come out of mysterybox, brick, etc, for now only "floating" coins in level
	public long id;
	private static GCanvas canvas;
	private static Image coin1Image, coin2Image, coin3Image;
	private static int pauseBetweenStates = 150;
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
		Image newImage;
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
		//TODO maybe when level will have lots of coins it will be worth it to have one thread doing spinning multiple coins
		Thread t1 = new Thread(new Runnable() {
			public void run() {changeState();}
		});
		t1.setName("floating coin changing state");
		t1.start();
	}

	public void changeState() {
		try {
			while (!collected() && alive) {
				Thread.sleep(pauseBetweenStates);
				toggleState();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		canvas.remove(this);
		alive = false;
		LevelController.currLevel.removeDynamic(this);
		System.out.println("END OF CHANGING STATE FOR FLOATING COIN");
	}

	public void collectedByMario(Mario mario) {
		//THIS IS FOR FLOATING COINS
		if (collected()) return;
		StatsController.collectCoin(mario);
		coinState = COIN_STATE.COLLECTED;
		SoundController.playCoinSound();
	}

	public static void setObjects(Image image1, Image image2, Image image3, GCanvas canvas1) {
		coin1Image = image1;
		coin2Image = image2;
		coin3Image = image3;
		canvas = canvas1;
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}

	@Override
	public long getID() {
		return id;
	}

	@Override
	public void move() {
		//TODO the move function will be for coins retrieved from mysteryboxes+bricks (NOT floating coins)
		//since they have a small animation of moving out of the box
	}

	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		//not really needed since coin does not move
		return false;
	}	
}