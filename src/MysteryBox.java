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
	}

	public void toggleState() {
		//changes mysterybox image from stage 1 to stage 2, 2->3, 3->4, 4->1
		if (stateIsFinal()) return;
		MyImage newImage;
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

	public void startChangingState() {
		//each mysterybox changes its pictures (changes state) in their own thread
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException{
				while (!stateIsFinal()) {
					ThreadSleep.sleep(15);
					toggleState();
				}
				System.out.println("END OF CHANGING STATE FOR MYSTERYBOX");
			}
		},"mystery box changing states");
	}

	public boolean stateIsFinal() {
		return  mysteryBoxState == MYSTERYBOX_STATE.FINAL;
	}

	public void setToFinalState() {
		mysteryBoxState = MYSTERYBOX_STATE.FINAL;
	}

	public void hitByMario(boolean marioBigOrSmall) {
		setToFinalState();
		SoundController.playItemOutOfBoxSound();
		setImage(mysteryBoxFinalImage);
		double x  = this.getX();
		double y = this.getY();
		
		DynamicFactory.addHourglass(x, y, this.getWidth());
		
		/*if (!marioBigOrSmall) {//small mario gets mushroom or (less probable) hourglass
			if (Math.random()>0.25)
				DynamicFactory.addMushroom(x, y, this.getWidth());
			else
				DynamicFactory.addHourglass(x, y, this.getWidth());
		} else {
			//if mario is big, he has equal change of getting fireflower,
			//hourglass, leaf, or tanooki
			if (Math.random()>0.75)
				DynamicFactory.addFireFlower(x, y, this.getWidth());
			else if (Math.random()>0.5)
				DynamicFactory.addHourglass(x, y, this.getWidth());
			else if (Math.random()>0.25)
				DynamicFactory.addLeaf(x, y, this.getWidth());
			else
				DynamicFactory.addTanooki(x, y, this.getWidth());
		}*/
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException {
				double dy = -MovingObject.getBaseLineSpeed();
				move(dy);//move up
				dy = -dy;
				move(dy);//move down
			}
		},"mysterybox hit by mario");
	}

	public void move(double dy) throws InterruptedException {
		for (int i=0; i<10; i++) {
			super.move(0, dy);
			ThreadSleep.sleep(3);
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
