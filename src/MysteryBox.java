

public class MysteryBox extends Platform {
	//extends Platform means that this is something mario would not be able
	//to walk/jump into. if he does, it will halt him
	private static MyImage mysteryBox1Image, mysteryBox2Image, mysteryBox3Image,
	mysteryBox4Image, mysteryBoxFinalImage;
	private enum MYSTERYBOX_STATE {STATE_1, STATE_2, STATE_3, STATE_4, FINAL};
	MYSTERYBOX_STATE mysteryBoxState;


	// a mystery box either spawns a power up, a coin, or RANDOM
	public enum SPAWN {Leaf, Mushroom, Tanooki, FireFlower, Hourglass, Coin, RANDOM};
	private SPAWN spawn;
	public MysteryBox(SPAWN s) {
		super(mysteryBox1Image);
		mysteryBoxState = MYSTERYBOX_STATE.STATE_1;
		spawn = s;
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

	public void hitByMario(boolean marioBigOrSmall, Mario m) {
		//only called once, mystery box only spawns something once
		setToFinalState();
		SoundController.playItemOutOfBoxSound();
		setImage(mysteryBoxFinalImage);
		double x  = this.getX();
		double y = this.getY();

		if (spawn.equals(SPAWN.RANDOM)) {
			double rand = Math.random();
			if (!marioBigOrSmall) {//small mario gets mushroom, hourglass, or coin
				if (rand<0.6)
					DynamicFactory.addMushroom(x, y, this.getWidth());
				else if (rand<0.7)
					DynamicFactory.addHourglass(x, y, this.getWidth());
				else DynamicFactory.addMysteryBoxCoin(x, y, this.getWidth(), m);
			} else {
				//if mario is big, he has equal change of getting fireflower,
				//hourglass, leaf, tanooki, or coin
				if (rand>0.8)
					DynamicFactory.addFireFlower(x, y, this.getWidth());
				else if (rand>0.6)
					DynamicFactory.addHourglass(x, y, this.getWidth());
				else if (rand>0.4)
					DynamicFactory.addLeaf(x, y, this.getWidth());
				else if (rand>0.2)
					DynamicFactory.addTanooki(x, y, this.getWidth());
				else DynamicFactory.addMysteryBoxCoin(x, y, this.getWidth(), m);
			}
		} else if (spawn.equals(SPAWN.FireFlower)) {
			DynamicFactory.addFireFlower(x, y, this.getWidth());
		} else if (spawn.equals(SPAWN.Hourglass)) {
			DynamicFactory.addHourglass(x, y, this.getWidth());
		} else if (spawn.equals(SPAWN.Leaf)) {
			DynamicFactory.addLeaf(x, y, this.getWidth());
		} else if (spawn.equals(SPAWN.Mushroom)) {
			DynamicFactory.addMushroom(x, y, this.getWidth());
		} else if (spawn.equals(SPAWN.Tanooki)) {
			DynamicFactory.addTanooki(x, y, this.getWidth());
		} else if (spawn.equals(SPAWN.Coin)) {
			DynamicFactory.addMysteryBoxCoin(x, y, this.getWidth(), m);
		}
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException {
				double dy = 0.8*MysteryBoxCoin.dy;//so that mysterybox does not catch up to mysteryboxcoin
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
