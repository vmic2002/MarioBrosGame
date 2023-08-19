import java.util.HashMap;

import acm.graphics.GCanvas;

public class DynamicFactory {
	//this class spawns MovingObjects that implement Dynamic and adds them to dynamicLevelParts
	//dynamically while a level is being played and calls their move function
	//powerups, fireballs, bulletbill etc
	private static MyGCanvas canvas;

	public static void setCanvas(MyGCanvas canvas1) {
		canvas = canvas1;
	}

	private static void addPowerUp(double x, double y, double mysteryBoxWidth, PowerUp powerUp) {
		//adds power up behing mystery box and makes it move up
		//so it looks like power up is coming out of mysteryBox
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException {
				LevelController.currLevel.addLevelPartDynamically(powerUp);
				canvas.add(powerUp, x+(mysteryBoxWidth-powerUp.getWidth())/2, y);
				ServerToClientMessenger.sendAddImageToScreenMessage(powerUp);
				powerUp.sendToBack();
				while (powerUp.getY()>y-powerUp.getHeight()) {
					powerUp.move(0, -MovingObject.getBaseLineSpeed()/2.0);

					ThreadSleep.sleep(2);

				}
				powerUp.setLocation(powerUp.getX(), y-powerUp.getHeight());
				powerUp.move();//instead of calling powerUp.startMove(), 
				//to call move func in new thread, call move func in current thread
			}
		},"power up move function");
	}

	public static void addMushroom(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Mushroom mushroom = new Mushroom();
		addPowerUp(x, y, mysteryBoxWidth, mushroom);
	}

	public static void addFireFlower(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		FireFlower fireFlower = new FireFlower();
		addPowerUp(x, y, mysteryBoxWidth, fireFlower);
	}

	public static void addLeaf(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Leaf leaf = new Leaf(Math.random()>0.5);
		addPowerUp(x, y, mysteryBoxWidth, leaf);
	}

	public static void addTanooki(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Tanooki tanooki = new Tanooki();
		addPowerUp(x, y, mysteryBoxWidth, tanooki);
	}


	public static void addHourglass(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Hourglass hourglass = new Hourglass();
		addPowerUp(x, y, mysteryBoxWidth, hourglass);
	}

	public static void addCoin(double x, double y) {
		//TODO call this addCoin function when Mario jumps into mysterybox or brick
		Coin coin = new Coin();
		canvas.add(coin, x, y);
		ServerToClientMessenger.sendAddImageToScreenMessage(coin);
		LevelController.currLevel.addLevelPartDynamically(coin);
		coin.startMove();
	}
	public static void addFireBall(double x, double y, boolean rightOrLeft) {
		//called when fire mario launches a fireball
		FireBall fireBall = new FireBall(rightOrLeft);
		canvas.add(fireBall, x, y);
		ServerToClientMessenger.sendAddImageToScreenMessage(fireBall);
		LevelController.currLevel.addLevelPartDynamically(fireBall);
		fireBall.startMove();
	}

	public static void addFlowerFireBall(double x, double y, boolean rightOrLeft, Mario mario) {
		//called when flower in pipe shoots a fireball at mario
		FireBall fireBall = new FireBall(rightOrLeft);
		canvas.add(fireBall, x, y);
		ServerToClientMessenger.sendAddImageToScreenMessage(fireBall);
		LevelController.currLevel.addLevelPartDynamically(fireBall);
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException{
				fireBall.shootAtMario(mario);
			}
		},"shootingflower fireball");
	}

	public static void addBulletBill(double x, double y, boolean rightOrLeft) {
		//called when BillBlaster shoots a BulletBill
		BulletBill bulletBill = new BulletBill(rightOrLeft);
		canvas.add(bulletBill, x, y);
		ServerToClientMessenger.sendAddImageToScreenMessage(bulletBill);
		bulletBill.sendToBack();//spawns behind BillBlaster
		LevelController.currLevel.addLevelPartDynamically(bulletBill);
		bulletBill.startMove();
	}

	
	//!!!!!
	//functions below are called at level creation time (in LevelController.playLevelX func) 
	//and add levelparts to temp hashmap
	//!!!!!

	public static void addGoomba(double x, double y, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		Goomba goomba = new Goomba();
		canvas.add(goomba, x, y-goomba.getHeight());
		Level.addLevelPartDynamically(goomba, dynamicLevelParts);
	}

	public static void addRedTurtle(double x, double y, double width, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		RedTurtle turtle = new RedTurtle(width);
		canvas.add(turtle, x, y-turtle.getHeight());
		Level.addLevelPartDynamically(turtle, dynamicLevelParts);
	}

	public static void addUpShootingFlower(double x, double y, int timeOffset, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		ShootingFlower flower = new UpShootingFlower(timeOffset);
		double width = flower.getWidth();
		canvas.add(flower, x-width/2, y);
		flower.sendToBack();
		Level.addLevelPartDynamically(flower, dynamicLevelParts);
	}

	public static void addDownShootingFlower(double x, double y, int timeOffset, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		ShootingFlower flower = new DownShootingFlower(timeOffset);
		double width = flower.getWidth();
		double height = flower.getHeight();
		canvas.add(flower, x-width/2, y-height);
		flower.sendToBack();
		Level.addLevelPartDynamically(flower, dynamicLevelParts);
	}


	public static double addFloatingCoin(double x, double y, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		//called at level creation time (in LevelController.playLevelX func) for coins that float in air
		Coin coin = new Coin();
		double height = coin.getHeight();//this is height of coin in stage 1 (when it is tallest)
		canvas.add(coin, x, y);
		Level.addLevelPartDynamically(coin, dynamicLevelParts);
		//addMovingObject(coin); THIS LINE IS COMMENTED BECAUSE FLOATING COINS DONT CALL MOVE FUNCTION (SEE COIN.JAVA FOR MORE DETAILS)
		//coins start spinning once level is instantiated (in Level() constructor)
		return height;
	}

	public static void addFloatingCoinsRectangle(double x, double y, int w, int h, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		//called at level creation time
		//adds a 2D array of floating coins of width w (num coins wide) and height h (num coins high)
		//to dynamicLevelParts
		double coinHeight = DynamicFactory.addFloatingCoin(x, y, dynamicLevelParts);
		double space = coinHeight/4;
		//assuming coinWidth is equal to coinHeight (it basically is, coin1.png is very close to being a square)
		if (w<2) w=2;
		if (h<2) h=2;
		for (int i=0; i<w; i++) {
			for (int j=1; j<h; j++) {
				DynamicFactory.addFloatingCoin(x+i*(coinHeight+space), y+j*(coinHeight+space), dynamicLevelParts);
			}
		}
		for (int i=1; i<w; i++) {
			DynamicFactory.addFloatingCoin(x+i*(coinHeight+space), y, dynamicLevelParts);
		}
	}

	public static void addFloatingCoinsTriangle(double x, double y, int h, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		//called at level creation time
		//adds floating coins in triangle pattern
		//if h=2, 4 coins total, if h=3, 9 coins total...
		//there are h^2 coins total!!!
		if (h<2) h=2;
		double coinHeight = DynamicFactory.addFloatingCoin(x, y, dynamicLevelParts);
		double space = coinHeight/4;
		int i;
		for (i=1; i<h; i++) {
			for (int j=i; j>=0; j--) {
				DynamicFactory.addFloatingCoin(x+i*(coinHeight+space), y-j*(coinHeight+space), dynamicLevelParts);
			}
		}
		for (int newI=h-2; i<2*h; i++) {
			for (int j=newI; j>=0; j--) {
				DynamicFactory.addFloatingCoin(x+i*(coinHeight+space), y-j*(coinHeight+space), dynamicLevelParts);
			}
			newI--;
		}
	}
}