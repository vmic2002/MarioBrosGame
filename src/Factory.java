import acm.graphics.GCanvas;

public abstract class Factory {
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
		t1.start();
	}

	private static void addPowerUp(double x, double y, double mysteryBoxWidth, MovingObject powerUp) {
		//adds power up behing mystery box and makes it move up
		//so it looks like power up is coming out of mysteryBox
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				canvas.add(powerUp, x+(mysteryBoxWidth-powerUp.getWidth())/2, y-50);
				powerUp.sendToBack();
				while (powerUp.getY()>y-powerUp.getHeight()) {
					powerUp.move(0, -5);
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
		t1.start();
	}

	public static void addFireBall(double x, double y, boolean rightOrLeft) {
		//called when fire mario launches a fireball
		FireBall fireBall = new FireBall(rightOrLeft);
		canvas.add(fireBall, x, y);
		LevelController.currLevel.addNewLevelPart(fireBall);
		addMovingObject(fireBall);
	}

	public static Mushroom addMushroom(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Mushroom mushroom = new Mushroom();
		addPowerUp(x, y, mysteryBoxWidth, mushroom);
		return mushroom;
	}

	public static FireFlower addFireFlower(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		FireFlower fireFlower = new FireFlower();
		addPowerUp(x, y, mysteryBoxWidth, fireFlower);
		return fireFlower;
	}

	public static Leaf addLeaf(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Leaf leaf = new Leaf(Math.random()>0.5);
		addPowerUp(x, y, mysteryBoxWidth, leaf);
		return leaf;
	}
}
//TODO also maybe if user holds fireball key then the fireball could charge until it is really big  
