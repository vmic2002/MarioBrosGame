import java.awt.Image;

import acm.graphics.GCanvas;
import acm.graphics.GObject;



public class Coin extends MovingObject implements Dynamic{
	//coin extend MovingObject because it is added to level by DynamicFactory
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
		System.out.println("END OF CHANGING STATE FOR COIN");
	}

	public void collectedByMario(Mario mario) {
		//TODO make PointsController class to increase points of mario and coin number
		coinState = COIN_STATE.COLLECTED;
		//TODO need to add sound when mario collects coin
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
		//each coin changes its pictures (changes state) in its own thread
		changeState();
	}

	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		//not really needed since coin does not move
		return false;
	}	
}