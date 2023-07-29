import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;

public class MysteryBox extends Platform {
	//extends Platform means that this is something mario would not be able
	//to walk/jump into. if he does, it will halt him
	private static MyImage mysteryBox1Image, mysteryBox2Image, mysteryBox3Image,
	mysteryBox4Image, mysteryBoxFinalImage;
	private enum MYSTERYBOX_STATE {STATE_1, STATE_2, STATE_3, STATE_4, FINAL};
	MYSTERYBOX_STATE mysteryBoxState;

	public MysteryBox() {
		super(mysteryBox1Image);
		mysteryBoxState = MYSTERYBOX_STATE.STATE_1;
		changeState();
	}

	public void toggleState() {
		//changes mysterybox image from stage 1 to stage 2, 2->3, 3->4, 4->1
		if (stateIsFinal()) return;
		Image newImage;
		if (mysteryBoxState == MYSTERYBOX_STATE.STATE_1) {
			newImage = mysteryBox2Image;
			mysteryBoxState = MYSTERYBOX_STATE.STATE_2;
		} else if (mysteryBoxState == MYSTERYBOX_STATE.STATE_2) {
			newImage = mysteryBox3Image;
			mysteryBoxState = MYSTERYBOX_STATE.STATE_3;
		} else if (mysteryBoxState == MYSTERYBOX_STATE.STATE_3) {
			newImage = mysteryBox4Image;
			mysteryBoxState = MYSTERYBOX_STATE.STATE_4;
		} else {//if (mysteryBoxState == MYSTERYBOX_STATE.STATE_4) {
			newImage = mysteryBox1Image;
			mysteryBoxState = MYSTERYBOX_STATE.STATE_1;
		}
		setImage(newImage);
	}

	public void changeState() {
		//each mysterybox changes its pictures (changes state) in their own thread
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (!stateIsFinal()) {
						Thread.sleep(150);
						toggleState();
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("END OF CHANGING STATE FOR MYSTERYBOX");
			}
		});
		t1.setName("mystery box changing states");
		t1.start();
	}

	public boolean stateIsFinal() {
		return  mysteryBoxState == MYSTERYBOX_STATE.FINAL;
	}
	
	public void setToFinalState() {
		mysteryBoxState = MYSTERYBOX_STATE.FINAL;
	}
	
	public void hitByMario() {
		//mushroom, coin, flower, leaf... is created by Factory object, not MysteryBox
		setImage(mysteryBoxFinalImage);
		setToFinalState();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				double dy = -MovingObject.scalingFactor;
				move(dy);//move up
				dy = -dy;
				move(dy);//move down
			}
		});
		t1.setName("mysterybox hit by mario");
		t1.start();
	}

	public void move(double dy) {
		for (int i=0; i<10; i++) {
			super.move(0, dy);
			try {
				Thread.sleep(30);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void setObjects(MyImage image1, MyImage image2, MyImage image3, MyImage image4, MyImage imageFinal) {
		mysteryBox1Image = image1;
		mysteryBox2Image = image2;
		mysteryBox3Image = image3;
		mysteryBox4Image = image4;
		mysteryBoxFinalImage = imageFinal;
	}
}
