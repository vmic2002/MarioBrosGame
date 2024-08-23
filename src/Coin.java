

//import java.awt.Image;
//import acm.graphics.GCanvas;
import acm.graphics.GObject;

public abstract class Coin extends MovingObject implements Dynamic{
	//coin extend MovingObject because it is added to level by DynamicFactory
	//2 types of coins -> floating coins (in level) and coins that come out of mysterybox, bricks etc
	//floating coins moved in FloatingCoinsBlock.startSpinningBlock(), see FloatingCoinsBlock.java
	//private static MyGCanvas canvas;
	private static MyImage coin1Image, coin2Image, coin3Image;
	public final static int pauseBetweenStates = 15;
	public enum COIN_STATE {STATE_1, STATE_2, STATE_3, COLLECTED};
	//"collected" state means Mario collected the coin and it should be removed from the canvas as well as the dynamicLevelParts
	COIN_STATE coinState;
	

	public Coin(Lobby lobby) {
		super(coin1Image, lobby);
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


	public abstract void collectedByMario(Mario mario);

	public static void setObjects(MyImage image1, MyImage image2, MyImage image3) {//, MyGCanvas canvas1) {
		coin1Image = image1;
		coin2Image = image2;
		coin3Image = image3;
		//canvas = canvas1;
	}



	@Override
	public long getID() {
		return this.getImageID();
	}

	@Override
	public void kill() {
		if (!lobby.levelController.endingLevel()) {
			lobby.canvas.remove(this);
			alive = false;
			lobby.levelController.currLevel.removeDynamic(this);
		}
	}

	

	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		//not really needed since coin does not move
		return false;
	}


}
