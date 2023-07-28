import java.util.HashMap;

import acm.graphics.GCanvas;

public class DynamicFactory {
	//this class spawns MovingObjects that implement Dynamic and adds them to dynamicLevelParts
	//dynamically while a level is being played and calls their move function
	//powerups, fireballs, bulletbill etc
	private static GCanvas canvas;
	
	public static void setCanvas(GCanvas canvas1) {
		canvas = canvas1;
	}

	private static void addMovingObject(MovingObject movingObject) {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				//code here runs concurrently
				movingObject.move();
			}
		});
		t1.setName("moving object");//TODO movingObjects should have better thread name for move function
		t1.start();
	}

	private static void addPowerUp(double x, double y, double mysteryBoxWidth, MovingObject powerUp) {
		//adds power up behing mystery box and makes it move up
		//so it looks like power up is coming out of mysteryBox
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				LevelController.currLevel.addLevelPartDynamically(powerUp);
				canvas.add(powerUp, x+(mysteryBoxWidth-powerUp.getWidth())/2, y);
				powerUp.sendToBack();
				while (powerUp.getY()>y-powerUp.getHeight()) {
					powerUp.move(0, -MovingObject.scalingFactor/2.0);
					try {
						Thread.sleep(15);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				powerUp.setLocation(powerUp.getX(), y-powerUp.getHeight());
				powerUp.move();
			}
		});
		t1.setName("power up");
		t1.start();
	}

	public static void addFlowerFireBall(double x, double y, boolean rightOrLeft, Mario mario) {
		//called when flower in pipe shoots a fireball at mario
		FireBall fireBall = new FireBall(rightOrLeft);
		canvas.add(fireBall, x, y);
		LevelController.currLevel.addLevelPartDynamically(fireBall);
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				fireBall.shootAtMario(mario);
			}
		});
		t1.setName("fireflower fireball");
		t1.start();
	}

	public static void addBulletBill(double x, double y, boolean rightOrLeft) {
		//called when BillBlaster shoots a BulletBill
		BulletBill bulletBill = new BulletBill(rightOrLeft);
		canvas.add(bulletBill, x, y);
		bulletBill.sendToBack();//spawns behind BillBlaster
		LevelController.currLevel.addLevelPartDynamically(bulletBill);
		addMovingObject(bulletBill);
	}

	public static void addFireBall(double x, double y, boolean rightOrLeft) {
		//called when fire mario launches a fireball
		FireBall fireBall = new FireBall(rightOrLeft);
		canvas.add(fireBall, x, y);
		LevelController.currLevel.addLevelPartDynamically(fireBall);
		addMovingObject(fireBall);
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

	public static void addCoin(double x, double y) {
		//TODO call this addCoin function when Mario jumps into mysterybox or brick
		Coin coin = new Coin();
		canvas.add(coin, x, y);
		LevelController.currLevel.addLevelPartDynamically(coin);
		addMovingObject(coin);
	}

	public static void addMushroom(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Mushroom mushroom = new Mushroom();
		addPowerUp(x, y, mysteryBoxWidth, mushroom);
		//return mushroom;
	}

	public static void addFireFlower(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		FireFlower fireFlower = new FireFlower();
		addPowerUp(x, y, mysteryBoxWidth, fireFlower);
		//return fireFlower;
	}

	public static void addLeaf(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Leaf leaf = new Leaf(Math.random()>0.5);
		addPowerUp(x, y, mysteryBoxWidth, leaf);
		//return leaf;
	}

	public static void addTanooki(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Tanooki tanooki = new Tanooki();
		addPowerUp(x, y, mysteryBoxWidth, tanooki);
	}
}
//TODO also maybe if user holds fireball key then the fireball could charge until it is really big  
