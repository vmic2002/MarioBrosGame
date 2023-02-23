import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class MysteryBox extends Block {
	//extends Block means that this is something mario would not be able
	//to walk/jump into. if he does, it will halt him
	private static GCanvas canvas;
	private static Image mysteryBox1Image;
	private static Image mysteryBox2Image;
	private static Image mysteryBox3Image;
	private static Image mysteryBox4Image;
	private static Image mysteryBoxFinalImage;
	private enum MYSTERYBOX_STATE {STATE_1, STATE_2, STATE_3, STATE_4, FINAL};
	MYSTERYBOX_STATE mysteryBoxState;

	public MysteryBox() {
		super(mysteryBox1Image);
		mysteryBoxState = MYSTERYBOX_STATE.STATE_1;
	}
	public boolean stateIsFinal() {
		return  mysteryBoxState == MYSTERYBOX_STATE.FINAL;
	}
	//TODO need to alternate mystery box images
	public void hitByMario() {
		//mushroom, coin, flower, leaf... is created by Factory object, not MysteryBox
		setImage(mysteryBoxFinalImage);
		mysteryBoxState = MYSTERYBOX_STATE.FINAL;
	}
	
	public static void setObjects(Image image1, Image image2, Image image3, Image image4, Image imageFinal, GCanvas canvas1) {
		mysteryBox1Image = image1;
		mysteryBox2Image = image2;
		mysteryBox3Image = image3;
		mysteryBox4Image = image4;
		mysteryBoxFinalImage = imageFinal;
		canvas = canvas1;
	}
	
}
